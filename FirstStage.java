import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;

import java.io.*;
import java.nio.Buffer;
import java.nio.file.FileSystemException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class contains functions doing necessary setups for text analysis such as omitting stopwords,
 * indicating tf, tf-idf matrixes, categories of tasks
 * Created by Ya?mur on 7.5.2016.
 */
public class FirstStage {
    public static ArrayList<String> stopwords = new ArrayList<>();
    public static ArrayList<String> dictionary = new ArrayList<>();
    public static ArrayList<String> dictionary_updated = new ArrayList<>();
    public static ArrayList<String> dictionary_reduced = new ArrayList<>();
    public static ArrayList<Double> idf_values = new ArrayList<>();
    public static ArrayList<String> categories = new ArrayList<>();
    public static HashMap<String, String> ucubik_with_appendix= new HashMap<>();
    public static ArrayList<String> ucubik_kok=new ArrayList<>();
    public static ArrayList<String> train_rec_tf_idf = new ArrayList<>();
    public static ArrayList<String> test_rec_tf_idf = new ArrayList<>();
    public static ArrayList<String> test_rec_tf_idf_reduced = new ArrayList<>();
    public static ArrayList<String> train_rec_tf_idf_reduced = new ArrayList<>();
    public double count_task = 11269;
    public static String tasks_file_name="Test-Word-Count.csv";
    public static String reduced_file_name="reduced_words_train_s_001.txt";
    public FirstStage() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("stopwords.txt"));
            String line = br.readLine();
            for (;line != null;line=br.readLine()) {
                if (!(stopwords.contains(line))) {
                    line = line.replace("\u0000", ""); // removes NULL chars
                    line = line.replace("\\u0000", "");
                    if (!(stopwords.contains(line))) {
                        stopwords.add(line);
                    }
                }
            }
            br.close();
        } catch (@SuppressWarnings("Since15") FileSystemException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void initCategoriesAndCounters(){
        int count_of_cat=4;
        for (int i = 1; i<count_of_cat; i++){
            categories.add(String.valueOf(i));
            RocchioAlgorithm.class_counter.put(String.valueOf(i),0);
        }
    }

    /**
     * This function is for converting normal TF-IDF matrix to List of Lists format
     */
    public static void convertNormalToLIL(){
        ArrayList<String> LILFormatted=new ArrayList<>();
        for (int i=0;i<FirstStage.train_rec_tf_idf.size();i++){
            String[] curr_task=FirstStage.train_rec_tf_idf.get(i).split(";");
            String LILFormat=curr_task[0];
            for(int j=1;j<curr_task.length;j++){
                if(!curr_task[j].equals("0"))
                    LILFormat=LILFormat+";"+(j+1)+";"+curr_task[j];
            }
            LILFormatted.add(LILFormat);
        }
        FirstStage.train_rec_tf_idf.clear();
        FirstStage.train_rec_tf_idf.addAll(LILFormatted);
        LILFormatted.clear();
    }

    /**
     * This function is for reading reduced words created in the Feature Reduction phase statically.
     */
    public static void readIDFAndAttrFromFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(reduced_file_name));
            String idf = "";
            for (idf=br.readLine(); idf != null; idf = br.readLine()) {
                dictionary_reduced.add(idf.split(";")[0]);
            }
            br.close();
            br = new BufferedReader(new FileReader("idf.txt"));
            for (idf=br.readLine(); idf != null; idf = br.readLine()) {
                String [] word_idf=idf.split(";");
                    idf_values.add(Double.parseDouble(word_idf[1]));
                    dictionary.add(word_idf[0]);
                    dictionary_updated.add(word_idf[0]);
            }
            br.close();
            ArrayList<Double> idf_values_to_be_removed=new ArrayList<>();
            for (int i=0;i<dictionary.size();i++){
                if(!dictionary_reduced.contains(dictionary.get(i)))
                    idf_values_to_be_removed.add(idf_values.get(i));
            }
            idf_values.removeAll(idf_values_to_be_removed);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void calculateIDFFor20NewsGroupsData() {
        try {
            PrintWriter writer = new PrintWriter("idf.txt");
            this.createDictionary();
            for (int i = 0; i < dictionary.size(); i++) {
                BufferedReader br1_tasks=null;
                br1_tasks = new BufferedReader(new FileReader("Train-Word-Count1.csv"));
                String task1 = br1_tasks.readLine();
                int count=0;
                for (task1 = br1_tasks.readLine(); task1 != null; task1 = br1_tasks.readLine()) {
                    String [] rec=task1.split(";");
                    if(rec[1].equals(dictionary.get(i))){
                        count=count+Integer.parseInt(rec[2]);
                        break;
                    }
                }
                br1_tasks.close();
                br1_tasks = new BufferedReader(new FileReader("Train-Word-Count2.csv"));
                task1 = br1_tasks.readLine();
                for (task1 = br1_tasks.readLine(); task1 != null; task1 = br1_tasks.readLine()) {
                    String [] rec=task1.split(";");
                    if(rec[1].equals(dictionary.get(i))){
                        count=count+Integer.parseInt(rec[2]);
                        break;
                    }
                }
                br1_tasks.close();
                double idf;
                if(count>0){
                    idf = Math.log10(count_task / count);
                    writer.println(dictionary.get(i) + ";"+idf);
                    idf_values.add(idf);
                }

            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        /**
     * This function indicates idf value of each word
     *
     * @throws IOException
     */
    public void calculateIDF() throws IOException {
        try {
            PrintWriter writer = new PrintWriter("idf.txt");
            PrintWriter writer2=new PrintWriter("word-num-mapping.txt");
            double count = 0;
            this.createDictionary();
            //this.filterWithUcubik();
            Zemberek zemberek = new Zemberek(new TurkiyeTurkcesi());
            for (int j = 0; j < dictionary.size(); j++) {
                if(dictionary.get(j)!=null) {

                    BufferedReader br_tasks = new BufferedReader(new FileReader(tasks_file_name));
                    String task1 ="";
                    for (task1 = br_tasks.readLine(); task1 != null; task1 = br_tasks.readLine()) {
                        String[] tsk = task1.split(";");
                        String task = tsk[0];
                        tsk[0] = tsk[0].replaceAll("[0-9]+", "");
                        tsk[0] = tsk[0].replaceAll("\\p{Punct}+", " ").toLowerCase();
                        String[] arr = tsk[0].replaceAll("((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)", "").split(" ");
                        for (int k = 0; k < arr.length; k++) {
                            if (task != null) {
                                if (task != null && dictionary.get(j) != null && arr[k].toLowerCase().startsWith(dictionary.get(j).toLowerCase())) {//task.toLowerCase().contains(dictionary.get(j).toLowerCase().substring(0,4))) {
                                    count++;
                                    break;
                                } else if (this.ucubik_with_appendix.containsKey(arr[k]) && this.ucubik_with_appendix.get(arr[k]).equals(dictionary.get(j))) {
                                    count++;
                                    break;
                                } else if (FirstStage.dictionary.get(j) != null && task != null && arr[k].length() > 3 && zemberek.kelimeDenetle(arr[k]) && zemberek.kelimeCozumle(arr[k]).length != 0) {
                                    String kok = zemberek.kelimeCozumle(arr[k])[0].kok().icerik();
                                    if (kok.toLowerCase() == FirstStage.dictionary.get(j).toLowerCase()) {
                                        count++;
                                        break;
                                    }
                                }
                                if (ucubik_kok.contains(dictionary.get(j))) {
                                    for (int i = 0; i < ucubik_kok.size(); i++) {
                                        if (dictionary.get(j).equals(ucubik_kok.get(i)) && arr[k].startsWith(ucubik_kok.get(i))) {
                                            count++;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    br_tasks.close();
                    double idf;
                    if(count>0){
                         idf = Math.log10(count_task / count);
                        writer.print(dictionary.get(j) + ",");
                        writer2.print("W" + j + ": " + dictionary.get(j) + ", ");
                        dictionary_updated.add(dictionary.get(j));
                        idf_values.add(idf);
                        writer.print(idf);
                        writer.println();
                        writer2.print(idf);
                        writer2.println();
                    }
                    else{
                        System.out.println(dictionary.get(j));
                        dictionary.set(j,"");
                    }
                    count = 0;
                }
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * This function is for reading frequency.txt
     */
    public static void createDictionary(){
        try {
            //This code block below is for litum data
            /*BufferedReader br_frequency = new BufferedReader(new FileReader(reduced_file_name));
            String word="";
            for (word=br_frequency.readLine();word != null; word = br_frequency.readLine()) {

                dictionary.add(word.split(";")[0]);
            }*/
            BufferedReader br_frequency = new BufferedReader(new FileReader("Train-Word-Count1.csv"));
            String word="";
            HashSet hs = new HashSet();
            word=br_frequency.readLine();
            for (word=br_frequency.readLine();word != null; word = br_frequency.readLine()) {

                hs.add(word.split(";")[1]);
            }
            br_frequency.close();
            br_frequency = new BufferedReader(new FileReader("Train-Word-Count2.csv"));
            word=br_frequency.readLine();
            for (word=br_frequency.readLine();word != null; word = br_frequency.readLine()) {

                hs.add(word.split(";")[1]);
            }
            br_frequency.close();
            br_frequency = new BufferedReader(new FileReader("Test-Word-Count.csv"));
            word=br_frequency.readLine();
            for (word=br_frequency.readLine();word != null; word = br_frequency.readLine()) {

                hs.add(word.split(";")[1]);
            }
            br_frequency.close();
            dictionary.addAll(hs);
            br_frequency = new BufferedReader(new FileReader("reduced_words_s_05.txt"));
            word = "";
            for (word=br_frequency.readLine(); word != null; word = br_frequency.readLine()) {
                dictionary_reduced.add(word.split(";")[0]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
