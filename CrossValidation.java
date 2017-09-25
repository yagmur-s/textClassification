import sun.awt.image.ImageWatched;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.BooleanSupplier;

/**
 * This class is for selecting subsample for train and test with the method of cross validation.
 * Created by Yaðmur on 22.5.2016.
 */
public class CrossValidation {
    public static ArrayList<String> class_names=new ArrayList<>();
    public static ArrayList<String> test_records_tf_idf=new ArrayList<>();
    public static ArrayList<String> train_records_tf_idf=new ArrayList<>();
    public static ArrayList<String> fold1=new ArrayList<>();
    public static ArrayList<String> fold2=new ArrayList<>();
    public static ArrayList<String> fold3=new ArrayList<>();
    public static ArrayList<String> fold4=new ArrayList<>();
    public static ArrayList<String> fold5=new ArrayList<>();
    public static ArrayList<String> fold6=new ArrayList<>();
    public static ArrayList<String> fold7=new ArrayList<>();
    public static ArrayList<String> fold8=new ArrayList<>();
    public static ArrayList<String> fold9=new ArrayList<>();
    public static ArrayList<String> fold10=new ArrayList<>();
    public static HashMap<String, Integer> fold_number_mapping=new HashMap<>();
    public static LinkedHashMap<String,Double> fold1_counters=new LinkedHashMap<>();
    public static LinkedHashMap<String,Double> fold2_counters=new LinkedHashMap<>();
    public static LinkedHashMap<String,Double> fold3_counters=new LinkedHashMap<>();
    public static LinkedHashMap<String,Double> fold4_counters=new LinkedHashMap<>();
    public static LinkedHashMap<String,Double> fold5_counters=new LinkedHashMap<>();
    public static LinkedHashMap<String,Double> fold6_counters=new LinkedHashMap<>();
    public static LinkedHashMap<String,Double> fold7_counters=new LinkedHashMap<>();
    public static LinkedHashMap<String,Double> fold8_counters=new LinkedHashMap<>();
    public static LinkedHashMap<String,Double> fold9_counters=new LinkedHashMap<>();
    public static LinkedHashMap<String,Double> fold10_counters=new LinkedHashMap<>();
    public static LinkedHashMap<String,Boolean> check_0_5_round_fact_for_each_class=new LinkedHashMap<>();
    public static LinkedHashMap<Integer,Boolean> check_0_5_round_fact_for_each_fold=new LinkedHashMap<>();
    static double total_task_count=0;
    static double number_of_fold=10;
    public static int count1=0,count2=0,count3=0,count4=0,count5=0,count6=0,count7=0,count8=0,count9=0,count10=0;
    public static void initMapping(){
        fold_number_mapping.put("fold1",1);
        fold_number_mapping.put("fold2",2);
        fold_number_mapping.put("fold3",3);
        fold_number_mapping.put("fold4",4);
        fold_number_mapping.put("fold5",5);
        fold_number_mapping.put("fold6",6);
        fold_number_mapping.put("fold7",7);
        fold_number_mapping.put("fold8",8);
        fold_number_mapping.put("fold9",9);
        fold_number_mapping.put("fold10",10);
    }
    public static ArrayList<String> getFold(int number_of_fold){
        switch (number_of_fold){
            case 1:
                return fold1;
            case 2:
                return fold2;
            case 3:
                return fold3;
            case 4:
                return fold4;
            case 5:
                return fold5;
            case 6:
                return fold6;
            case 7:
                return fold7;
            case 8:
                return fold8;
            case 9:
                return fold9;
            case 10:
                return fold10;
            default:
                return null;
        }
    }
    public static LinkedHashMap<String,Double> getFoldClassCounter(int number_of_fold){
        switch (number_of_fold){
            case 1:
                return fold1_counters;
            case 2:
                return fold2_counters;
            case 3:
                return fold3_counters;
            case 4:
                return fold4_counters;
            case 5:
                return fold5_counters;
            case 6:
                return fold6_counters;
            case 7:
                return fold7_counters;
            case 8:
                return fold8_counters;
            case 9:
                return fold9_counters;
            case 10:
                return fold10_counters;
            default:
                return null;
        }
    }
    public static int getFoldCounter(int number_of_fold){
        switch (number_of_fold){
            case 1:
                return count1;
            case 2:
                return count2;
            case 3:
                return count3;
            case 4:
                return count4;
            case 5:
                return count5;
            case 6:
                return count6;
            case 7:
                return count7;
            case 8:
                return count8;
            case 9:
                return count9;
            case 10:
                return count10;
            default:
                return 0;
        }
    }
    public static void setCount(int count,int new_value){
        count=new_value;
    }
    public static void chooseTrainTestFor20NewsGroupData(){
        int count_for_reduced_words=0,count_for_all_words_containing=0;
        for (int i=0;i<FirstStage.test_rec_tf_idf.size();i++){
            String [] arr=FirstStage.test_rec_tf_idf.get(i).split(";");
            for(int j=1;j<arr.length;j+=2){
                if(FirstStage.dictionary_reduced.contains(arr[j]))
                    count_for_reduced_words++;
                count_for_all_words_containing++;
            }
            if(count_for_reduced_words>((count_for_all_words_containing*count_for_reduced_words)/FirstStage.dictionary.size())){
                FirstStage.test_rec_tf_idf_reduced.add(FirstStage.test_rec_tf_idf.get(i));
            }
            count_for_reduced_words=0;count_for_all_words_containing=0;
        }
        count_for_reduced_words=0;count_for_all_words_containing=0;
        FirstStage.test_rec_tf_idf.removeAll(FirstStage.train_rec_tf_idf_reduced);
        for (int i=0;i<FirstStage.train_rec_tf_idf.size();i++){
            String [] arr=FirstStage.train_rec_tf_idf.get(i).split(";");
            for(int j=1;j<arr.length;j+=2){
                if(FirstStage.dictionary_reduced.contains(arr[j]))
                    count_for_reduced_words++;
                count_for_all_words_containing++;
            }
            if(count_for_reduced_words>((count_for_all_words_containing*count_for_reduced_words)/FirstStage.dictionary.size())){
                FirstStage.train_rec_tf_idf_reduced.add(FirstStage.train_rec_tf_idf.get(i));
            }
            count_for_reduced_words=0;count_for_all_words_containing=0;
        }
        //FirstStage.train_rec_tf_idf.removeAll(FirstStage.train_rec_tf_idf_reduced);
    }
    public static void chooseTrainTestForRemaining() {

        try {
            BufferedReader read_tf_idf=new BufferedReader(new FileReader("toanalyse.csv"));
            String task="";
            task = read_tf_idf.readLine(); //bu silinicek
            ArrayList<String> toAnalyseName=new ArrayList<>();
            ArrayList<String> tempTest=new ArrayList<>();
            for (task = read_tf_idf.readLine(); task != null; task = read_tf_idf.readLine()){
                toAnalyseName.add(task);
            }
            read_tf_idf.close();
            //FirstStage.test_rec_tf_idf=FirstStage.train_rec_tf_idf;
            for(int i=0;i<FirstStage.train_rec_tf_idf.size();i++){
                for(int j=0;j<toAnalyseName.size();j++){
                    if(FirstStage.train_rec_tf_idf.get(i).contains(toAnalyseName.get(j))){
                        tempTest.add(FirstStage.train_rec_tf_idf.get(i));
                    }
                }
            }
            FirstStage.test_rec_tf_idf.clear();
            FirstStage.test_rec_tf_idf.addAll(tempTest);
            FirstStage.train_rec_tf_idf.removeAll(tempTest);
            tempTest.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int a=5;
    }

    public static void chooseTrainTest() throws IOException {
        calculateCounters();
        double count_must_be=Math.round(total_task_count/number_of_fold);
        String mediator = Double.valueOf(total_task_count/number_of_fold).toString();
        mediator = mediator.substring(mediator.indexOf('.') + 1);
        int remained_after_dot = Integer.valueOf(mediator);
        ArrayList<String> temp_train=new ArrayList<>();
        LinkedHashMap<String,Integer> temp_class_counter=new LinkedHashMap<>();
        temp_class_counter.putAll(RocchioAlgorithm.class_counter);
        temp_train.addAll(FirstStage.train_rec_tf_idf);

        for (int i=1;i<(11-remained_after_dot);i++){
            int curr_fold_count=0;
            int a=5;
            for (int j=0;j<temp_train.size();j++){
                if(temp_train.get(j)!=""){
                    String [] arr=temp_train.get(j).split(";");
                    double curr_count_must_be=Math.round((RocchioAlgorithm.class_counter.get(arr[arr.length-1].toUpperCase())*(int)count_must_be)/(total_task_count));
                    if(remained_after_dot==5){
                        check_0_5_round_fact_for_each_class.put(arr[arr.length-1].toUpperCase(),true);
                    }
                    if(!(getFoldClassCounter(i).get(arr[arr.length-1].toUpperCase())==curr_count_must_be)){
                        if(getFold(i).size()<(int)count_must_be){
                            if(check_0_5_round_fact_for_each_class.containsKey(arr[arr.length-1])){
                                check_0_5_round_fact_for_each_fold.put(i,true);
                            }
                            getFold(i).add(temp_train.get(j));
                            double m=getFoldClassCounter(i).get(arr[arr.length-1].toUpperCase());
                            m++;
                            curr_fold_count++;
                            getFoldClassCounter(i).put(arr[arr.length-1].toUpperCase(),m);
                            temp_train.set(j,"");
                            int counter=temp_class_counter.get(arr[arr.length-1].toUpperCase());
                            counter--;
                            temp_class_counter.put(arr[arr.length-1].toUpperCase(),counter);
                        }

                    }
                }

            }
        }
        double remained_count=0;
        if(remained_after_dot!=0){
            remained_count=Math.round(temp_class_counter.get("CAMIILA")/remained_after_dot)+Math.round(temp_class_counter.get("EKOL")/remained_after_dot)+Math.round(temp_class_counter.get("SK")/remained_after_dot)+Math.round(temp_class_counter.get("WL")/remained_after_dot);
            ArrayList<String> class_whose_count_is_not_divisible_by_remained=new ArrayList<>();
            ArrayList<String> class_whose_count_is_not_divisible_by_remained_used=new ArrayList<>();
            LinkedHashMap<String,Integer> temp_class_counter2=new LinkedHashMap<>();
            temp_class_counter2.putAll(temp_class_counter);
            for(int i=11-remained_after_dot;i<11;i++){
                int curr_fold_count=0;
                for (int j=0;j<temp_train.size();j++){
                    if(temp_train.get(j)!=""){
                        String [] arr=temp_train.get(j).split(";");
                        double curr_count_must_be=Math.round(temp_class_counter.get(arr[arr.length-1].toUpperCase())/remained_after_dot);
                        mediator = Double.valueOf((double)temp_class_counter.get(arr[arr.length-1].toUpperCase())/remained_after_dot).toString();
                        mediator = mediator.substring(mediator.indexOf('.') + 1);
                        if(Long.valueOf(mediator)!=0.0 && !class_whose_count_is_not_divisible_by_remained.contains(arr[arr.length-1].toUpperCase())){
                            class_whose_count_is_not_divisible_by_remained.add(arr[arr.length-1].toUpperCase());
                        }
                        if((getFoldClassCounter(i).get(arr[arr.length-1].toUpperCase())<curr_count_must_be) && !temp_train.get(j).equals("") && curr_fold_count<remained_count){
                            getFold(i).add(temp_train.get(j));
                            double m=getFoldClassCounter(i).get(arr[arr.length-1].toUpperCase());
                            m++;
                            getFoldClassCounter(i).put(arr[arr.length-1].toUpperCase(),m);
                            temp_train.set(j,"");
                            curr_fold_count++;
                            int cnt=temp_class_counter2.get(arr[arr.length-1].toUpperCase());
                            cnt--;
                            temp_class_counter2.put(arr[arr.length-1].toUpperCase(),cnt);
                        }
                    }
                }

            }
            int fold_number,stop_fold_number,start_fold_number;
            if(count_must_be<remained_count){
                start_fold_number=1;
                fold_number=1;
                stop_fold_number=11-remained_after_dot;
                remained_count=count_must_be;
            }else{
                start_fold_number=11-remained_after_dot;
                fold_number=11-remained_after_dot;
                stop_fold_number=11;
            }
            remained_count=remained_count+Math.round((double)(temp_class_counter2.get("CAMIILA")+(double)temp_class_counter2.get("EKOL")+(double)temp_class_counter2.get("SK")+(double)temp_class_counter2.get("WL"))/(double)remained_after_dot);
             class_whose_count_is_not_divisible_by_remained_used.clear();
                for (int m=0;m<class_whose_count_is_not_divisible_by_remained.size();m++){
                        for (int k=0;k<temp_train.size();k++){
                            String [] arr=temp_train.get(k).split(";");
                            if(getFold(fold_number).size()!=remained_count && temp_train.get(k)!="" && class_whose_count_is_not_divisible_by_remained.get(m).equals(arr[arr.length-1].toUpperCase())
                                    && !class_whose_count_is_not_divisible_by_remained_used.contains(arr[arr.length-1].toUpperCase())){

                                getFold(fold_number).add(temp_train.get(k));
                                double n=getFoldClassCounter(fold_number).get(class_whose_count_is_not_divisible_by_remained.get(m));
                                n++;
                                getFoldClassCounter(fold_number).put(class_whose_count_is_not_divisible_by_remained.get(m).toUpperCase(),n);
                                int cnt=temp_class_counter2.get(arr[arr.length-1].toUpperCase());
                                cnt--;
                                temp_class_counter2.put(arr[arr.length-1].toUpperCase(),cnt);
                                fold_number++;
                                if(fold_number==stop_fold_number){
                                    fold_number=start_fold_number;
                                }
                                if(cnt==0){
                                    class_whose_count_is_not_divisible_by_remained_used.add(arr[arr.length-1]);
                                    break;
                                }

                                temp_train.set(k,"");
                            }
                        }
                }
            class_whose_count_is_not_divisible_by_remained.clear();
            class_whose_count_is_not_divisible_by_remained_used.clear();
            temp_class_counter.clear();
            temp_class_counter2.clear();
        }

            boolean check_for_remained_zero_but_fraction_is_not_okey=false;
            for (int i=1;i<11;i++){
                if(getFold(i).size()!=count_must_be && remained_after_dot==0){ //10 a tam bölünüyor ama oranlar sýkýntýlý olduðu için eksik kalan foldlar oluyorsa
                    remained_after_dot=i;
                    check_for_remained_zero_but_fraction_is_not_okey=true;
                }
            }
            if(check_for_remained_zero_but_fraction_is_not_okey){
                int i=remained_after_dot;
                while(i<11&& getFold(i).size()<count_must_be){
                    for(int k=0;k<temp_train.size();k++){
                        if(temp_train.get(k)!=""){
                            getFold(i).add(temp_train.get(k));
                            break;
                        }
                    }
                    if(getFold(i).size()==count_must_be){
                        i++;
                    }
                }
            }
            temp_train.clear();
        temp_class_counter.clear();
    }

    public static void calculateCounters(){
            int count=0;
            for(int i=0;i<FirstStage.train_rec_tf_idf.size();i++) {
                String[] arr = FirstStage.train_rec_tf_idf.get(i).split(";");
                String class_of_record=arr[arr.length-1];

                int m = RocchioAlgorithm.class_counter.get(class_of_record.toUpperCase());
                m++;
                RocchioAlgorithm.class_counter.put(arr[arr.length - 1].toUpperCase(), m);
                total_task_count = total_task_count + 1;
            }
            fold1_counters.put("CAMIILA",0.0);
            fold1_counters.put("WL",0.0);
            fold1_counters.put("EKOL",0.0);
            fold1_counters.put("SK",0.0);
            fold2_counters.put("CAMIILA",0.0);
            fold2_counters.put("WL",0.0);
            fold2_counters.put("EKOL",0.0);
            fold2_counters.put("SK",0.0);
            fold3_counters.put("CAMIILA",0.0);
            fold3_counters.put("WL",0.0);
            fold3_counters.put("EKOL",0.0);
            fold3_counters.put("SK",0.0);
            fold4_counters.put("CAMIILA",0.0);
            fold4_counters.put("WL",0.0);
            fold4_counters.put("EKOL",0.0);
            fold4_counters.put("SK",0.0);
            fold5_counters.put("CAMIILA",0.0);
            fold5_counters.put("WL",0.0);
            fold5_counters.put("EKOL",0.0);
            fold5_counters.put("SK",0.0);
            fold6_counters.put("CAMIILA",0.0);
            fold6_counters.put("WL",0.0);
            fold6_counters.put("EKOL",0.0);
            fold6_counters.put("SK",0.0);
            fold7_counters.put("CAMIILA",0.0);
            fold7_counters.put("WL",0.0);
            fold7_counters.put("EKOL",0.0);
            fold7_counters.put("SK",0.0);
            fold8_counters.put("CAMIILA",0.0);
            fold8_counters.put("WL",0.0);
            fold8_counters.put("EKOL",0.0);
            fold8_counters.put("SK",0.0);
            fold9_counters.put("CAMIILA",0.0);
            fold9_counters.put("WL",0.0);
            fold9_counters.put("EKOL",0.0);
            fold9_counters.put("SK",0.0);
            fold10_counters.put("CAMIILA",0.0);
            fold10_counters.put("WL",0.0);
            fold10_counters.put("EKOL",0.0);
            fold10_counters.put("SK",0.0);

    }
}
