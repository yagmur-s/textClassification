import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;

import java.io.*;
import java.util.*;

/**
 * Created by Yagmur on 22.06.2016.
 */
public class TFIDFnormalization {
    public static ArrayList<String> normalized1_tf_idf_values = new ArrayList<>();
    public static ArrayList<String> normalized2_tf_idf_values = new ArrayList<>();

    public static void normalizeAndCreate_tf_idf_matrix_for_20GroupsNewsData(String file_name,String indicator) throws IOException {
        String tf1 = "", tf2 = "";
        double to_be_normalized1 = 0;
        double to_be_normalized2 = 0;
        //FirstStage.createDictionary(); //buna gerek yok cunku idfleri okurken attr ları da okuyoruz, filtered dan daha az sozcuk oluyor bir cümlede geçenlerin idf ini hesaplamadığımız için
        //FirstStage.filterWithUcubik(); //buna gerek yok cunku idfleri okurken attr ları da okuyoruz
        ArrayList<Double> counts = new ArrayList<>();
        boolean first_time=true;
        String[] tsk = null;
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        String curr_doc_id="";String prev_doc_id="";
        ArrayList<String> task=new ArrayList<>();
        ArrayList<String> task_words=new ArrayList<>();
        for (;line != null;) {
            prev_doc_id=curr_doc_id;
            line=br.readLine();
            curr_doc_id=line;
            String[] arr=null;
            if(line!=null){
                arr = line.split(";");
                curr_doc_id=arr[0];
                /*if(arr[0].equals("7505")){
                    int a=5;
                }*/
            }
            if(prev_doc_id.equals(curr_doc_id) || first_time){
                task.add(line);
                task_words.add(arr[1]);
                first_time=false;
            }
            else{
                for (int j = 0; j < FirstStage.dictionary_updated.size(); j++) {
                    if (task_words.contains(FirstStage.dictionary_updated.get(j))) {
                            int index=task_words.indexOf(FirstStage.dictionary_updated.get(j));
                            counts.add(Double.parseDouble(task.get(index).split(";")[2]));
                    }else{
                        counts.add(0.0);
                    }
                }
                String frqs1 = "", frqs2 = "";
                double norm1, norm2;
                String index;
                boolean new_check = false;

                for (int i = 0; i < counts.size(); i++) {
                    if (counts.get(i) != 0) {
                        index = FirstStage.dictionary_updated.get(i);
                        norm1 = 0.5 + (0.5 * (counts.get(i) / Collections.max(counts)));
                        norm1 = FirstStage.idf_values.get(i) * norm1;
                        norm2 = 0.5 + (0.6 * (counts.get(i) / Collections.max(counts)));
                        norm2 = FirstStage.idf_values.get(i) * norm2;
                        new_check = true;
                    } else {
                        index = "-1";
                        norm1 = 0;
                        norm2 = 0;
                    }
                    //frqs1 = frqs1 + "," + norm1;
                    //frqs2 = frqs2 + "," + norm2;
                    if (index != "-1") {
                        frqs1 = frqs1 + ";" + index + ";" + norm1;
                        frqs2 = frqs2 + ";" + index + ";" + norm2;
                    }

                }
                //This line below is for sending a record to be normalized
                if (new_check) {
                    TFIDFnormalization.normalized1_tf_idf_values.add(indicator + (task.get(0).split(";")[0]) + frqs1);
                    TFIDFnormalization.normalized2_tf_idf_values.add(indicator+ (task.get(0).split(";")[0]) + frqs2);
                }
                counts.clear();
                task.clear();
                task_words.clear();
                first_time=true;
                if(line!=null){
                    task.add(line);
                    task_words.add(arr[1]);
                }

            }

        }
        br.close();
        printNormalizedValues("tf-idf-matrix-normalized1.txt",TFIDFnormalization.normalized1_tf_idf_values);
        printNormalizedValues("tf-idf-matrix-normalized2.txt",TFIDFnormalization.normalized2_tf_idf_values);
        TFIDFnormalization.normalized1_tf_idf_values.clear();
        TFIDFnormalization.normalized2_tf_idf_values.clear();
    }
    /**
     * This function constructs normalize tf-idf matrix -calculates tf then normalize then multiplies with idf-
     * Words we are inspecting are reduced from reduced_words by idf value which is 1 is removed again.
     * @throws IOException
     */
    public static void normalizeAndCreate_Tf_idf_Matrix() throws IOException {
        String tf1 = "", tf2 = "";
        double to_be_normalized1 = 0;
        double to_be_normalized2 = 0;
        //FirstStage.createDictionary(); //buna gerek yok cunku idfleri okurken attr ları da okuyoruz, filtered dan daha az sozcuk oluyor bir cümlede geçenlerin idf ini hesaplamadığımız için
        //FirstStage.filterWithUcubik(); //buna gerek yok cunku idfleri okurken attr ları da okuyoruz
        BufferedReader br_tasks = new BufferedReader(new FileReader(FirstStage.tasks_file_name));
        //PrintWriter writer2 = new PrintWriter("tf-matrix.txt");
        Zemberek zemberek = new Zemberek(new TurkiyeTurkcesi());
        ArrayList<Double> counts = new ArrayList<>();
        double count = 1,count_term=0;
        String task;
        task = br_tasks.readLine();
        String[] tsk=null;
        while (task != null) {
            task = br_tasks.readLine();
            if (task != null) {
                tsk = task.split(";");
                tsk[0] = tsk[0].replaceAll("\\p{Punct}+", " ").toLowerCase();
                tsk[0] = tsk[0].replaceAll("[0-9]+", "");
                String[] arr = tsk[0].replaceAll("((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)", "").split(" ");
                List<String> list = new LinkedList<String>(Arrays.asList(arr));
                for (int j = 0; j < FirstStage.dictionary_updated.size(); j++) {
                    count = 0;
                    for (int i = 0; i < list.size(); i++) {
                        if (task != null && list.get(i) != "" && FirstStage.dictionary.get(j) != null && list.get(i).toLowerCase().startsWith(FirstStage.dictionary_updated.get(j))) {
                            count++;
                        } else if (FirstStage.dictionary_updated.get(j) != null && task != null && list.get(i).length() > 3 && zemberek.kelimeDenetle(list.get(i)) && zemberek.kelimeCozumle(list.get(i)).length != 0) {
                            String kok = zemberek.kelimeCozumle(list.get(i))[0].kok().icerik();
                            if (kok.toLowerCase() == FirstStage.dictionary_updated.get(j).toLowerCase()) {
                                count++;
                            }
                        } else if (FirstStage.ucubik_with_appendix.containsKey(list.get(i)) && FirstStage.ucubik_with_appendix.get(list.get(i)).equals(FirstStage.dictionary_updated.get(j))) {
                            count++;
                        }
                        else if (FirstStage.ucubik_kok.contains(FirstStage.dictionary_updated.get(j))) {
                            for (int k = 0; k < FirstStage.ucubik_kok.size(); k++) {
                                if (FirstStage.dictionary_updated.get(j).equals(FirstStage.ucubik_kok.get(k)) && list.get(i).startsWith(FirstStage.ucubik_kok.get(k))) {
                                    count++;
                                }
                            }
                        }
                    }
                    counts.add(count);
                }


            String frqs1 = "", frqs2 = "";
            double norm1, norm2;
                int index;
                boolean new_check=false;
            for (int i = 0; i < counts.size(); i++) {
                if (counts.get(i) != 0) {
                    index=i;
                    norm1 = 0.5 + (0.5 * (counts.get(i) / Collections.max(counts)));
                    norm1 = FirstStage.idf_values.get(i) * norm1;
                    norm2 = 0.5 + (0.6 * (counts.get(i) / Collections.max(counts)));
                    norm2 = FirstStage.idf_values.get(i) * norm2;
                    //omitted_zero1= (i+1)+";"+norm1; //this lines for preventing sparse matrix by creating list of lists (LIL)
                    //omitted_zero2= (i+1)+";"+norm2;
                    new_check=true;
                } else {
                    index=-1;
                    norm1 = 0;
                    norm2 = 0;
                }
                if(index!=-1){
                    frqs1 = frqs1 + ";" +index+";"+norm1;
                    frqs2 = frqs2 + ";" +index+";"+norm2;
                }

            }
            //This line below is for sending a record to be normalized
                if(new_check){
                    TFIDFnormalization.normalized1_tf_idf_values.add("T" + (count_term + 1) + frqs1 + ";" + tsk[1]);
                    TFIDFnormalization.normalized2_tf_idf_values.add("T" + (count_term + 1) + frqs2 + ";" + tsk[1]);
                }
            }
            count_term++;
            counts.clear();
        }
        printNormalizedValues("tf-idf-matrix-normalized1.txt",TFIDFnormalization.normalized1_tf_idf_values);
        printNormalizedValues("tf-idf-matrix-normalized2.txt",TFIDFnormalization.normalized2_tf_idf_values);



    }
    public static  String convertLILtoNormal(String recordTypeOfLIL){
        String normalRecordWithZeros="";
        String [] arr=recordTypeOfLIL.split(";");
        int prevIndex=1;
        for (int i=1;i<arr.length;i++){
            if(i%2==1){
                for (int j=prevIndex;j<i;j++){
                    normalRecordWithZeros=normalRecordWithZeros+";0";
                }
            }
            normalRecordWithZeros=normalRecordWithZeros+arr[i];
            prevIndex=i;
        }
        normalRecordWithZeros=arr[0]+normalRecordWithZeros;
        return normalRecordWithZeros;
    }
    public static void printNormalizedValues(String fileName, ArrayList<String> normalized_values) {
        PrintWriter writer;
        try {
            String word = "";
            FileWriter fw = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(fw);
            writer  = new PrintWriter(bw,true);
            /*writer.write("    ");
            for (int i = 0; i < FirstStage.dictionary.size(); i++) {
                word = word + "     " + "W" + (i + 1);
            }
            writer.write(word);
            writer.write("\n");*/
            for (int i=0;i<normalized_values.size();i++){
                if(!normalized_values.get(i).equals(""))
                    writer.write(normalized_values.get(i));
                    writer.write("\n");
            }

            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
