/**
 * Created by Ya�mur on 9.5.2016.
 */

import java.io.*;
import java.util.*;

/**
 * Created by Ya�mur on 7.5.2016.
 */
public class ApplicationOfAlgorithms {
    public static HashMap<String, Double> cosine_results = new HashMap<>();
    public static HashMap<String, Double> pearson_results = new HashMap<>();
    public static HashMap<String, Double> mtv1_results = new HashMap<>();
    public static HashMap<String, Double> mtv2_results = new HashMap<>();
    public static HashMap<String, Double> mtv4_results = new HashMap<>();
    static FirstStage obj = new FirstStage();
    public static String key_cos; //cosine estimated class
    public static double value_cos;
    public static String key_pearson;
    public static double value_pearson;
    public static String key_roc;
    public static double value_roc;
    public static String key; //temp variable for key
    public static double value;
    public static String key_mtv2;
    public static double value_mtv2;
    public static String current_task_id;
    public static String tf_idf_test_file_name="TF-IDF-NORM1-TEST-20-NEWS-GROUPS.csv";
    public static String tf_idf_train_file_name="tf-idf-norm2-cmp2.csv";
    public static ArrayList<String> tasks = new ArrayList<>(); //temp arraylist for train set depends on being tf or tf-idf values
    public static int total_word_count=3409;
    public static void executeFirstStage() throws IOException {

        try {
            /*FirstStage.categories.add("CAMIILA");
            FirstStage.categories.add("SK");
            FirstStage.categories.add("WL");
            FirstStage.categories.add("EKOL");*/
            //obj.calculateIDF(); //litum datası için idf hesaplaması
            obj.calculateIDFFor20NewsGroupsData();
            FirstStage.readIDFAndAttrFromFile(); //eğer baştan idf hesaplamayıp direk okucaksak bu fonk. çağırıcaz
            //TFIDFnormalization.normalizeAndCreate_tf_idf_matrix_for_20GroupsNewsData("Train-Word-Count1.csv","Train");
            //TFIDFnormalization.normalizeAndCreate_tf_idf_matrix_for_20GroupsNewsData("Train-Word-Count2.csv","Train");
            TFIDFnormalization.normalizeAndCreate_tf_idf_matrix_for_20GroupsNewsData("Test-Word-Count.csv","Test");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void reduceAttributesFromMatrixes(){
        try {
            BufferedReader read_tf_idf=new BufferedReader(new FileReader(tf_idf_train_file_name));
            String task="";
            task = read_tf_idf.readLine(); //bu silinicek
            for (task = read_tf_idf.readLine(); task != null; task = read_tf_idf.readLine()){
                FirstStage.train_rec_tf_idf.add(task);
            }
            read_tf_idf.close();
            /*read_tf_idf=new BufferedReader(new FileReader(tf_idf_test_file_name));
            for (task = read_tf_idf.readLine(); task != null; task = read_tf_idf.readLine()){
                FirstStage.test_rec_tf_idf.add(task);
            }*/

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void executeForRocchio(String indicator,ArrayList<String> test,ArrayList<String> train) {
        FileWriter writer = null;
        try {
            FileWriter writer2 = new FileWriter(indicator + "-report-roc-mtv4.txt", true);
            //writer2.write("Record;Original Class;Rocchio Estimated Hellinger Class;Value;Rocchio Estimated Euclidian Class;Value;Rocchio Estimated Cosine Class;Value;Rocchio Estimated MTV2 Class;Value");
            writer2.write("Record;Original Class;Rocchio Estimated MTV4 Class;Value");
            writer2.write(System.getProperty("line.separator"));
            writer2.close();
            for (int i = 0; i < test.size(); i++) {
                 //writer = new FileWriter(indicator + "-report-roc.txt", true);
                writer = new FileWriter(indicator + "-report-roc-mtv4.txt", true);
                String curr_task = test.get(i);
                //convertLILtoNormal(curr_task);
                //RocchioAlgorithm.execute(convertLILtoNormal(curr_task),i);
                RocchioAlgorithm.execute(curr_task,i);
                String[] arr = curr_task.split(";");
                CreateReport.original_class.put(arr[0],arr[arr.length-1]);
                //writer.write(arr[0] + ";" + arr[arr.length-1] +";"+RocchioAlgorithm.min_hellinger_class+";"+RocchioAlgorithm.min_hellinger_value +";"+RocchioAlgorithm.min_euclidean_class +";"+RocchioAlgorithm.min_euclidean_value +";"+RocchioAlgorithm.max_cos_class+";"+RocchioAlgorithm.max_cos_value +";"+RocchioAlgorithm.max_mtV2_class+";"+RocchioAlgorithm.max_mtV2_value);
                writer.write(arr[0] + ";" + arr[arr.length-1] +";"+RocchioAlgorithm.max_mtV4_class+";"+RocchioAlgorithm.max_mtV4_value);
                writer.write(System.getProperty("line.separator"));
                writer.close();
            }

        } catch (FileNotFoundException e) {
            e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String convertLILtoNormal(String curr_task) {
        String [] arr= curr_task.split(";");
        ArrayList<String> arr_list=new ArrayList<>(Arrays.asList(arr));
        String frq=arr[0];
        for (int i=1;i<total_word_count;i++){
            if(arr_list.contains(Integer.toString(i))){
                int ind=arr_list.indexOf(Integer.toString(i));
                frq=frq+";"+arr_list.get(ind+1);
            }
            else{
                frq=frq+";"+"0";
            }
        }
        frq=frq+";"+arr_list.get(arr_list.size()-1);
        return frq;
    }
    public static void executeForPearsonAndCosine(int index, ArrayList<String> test, String train, String indicator) throws IOException {
        ArrayList<Double> rec_train_list = new ArrayList<>();
        ArrayList<Double> rec_test_list = new ArrayList<>();
        CosineSimilarity cos_obj = new CosineSimilarity();
        Pearson pear_obj = new Pearson();
        ArrayList<String> temp = new ArrayList<>();
        String [] train_arr=train.split(";");
            for (int j = 0; j < test.size(); j++) {
                String curr_test=ApplicationOfAlgorithms.convertLILtoNormal(test.get(j));
                String [] curr_arr=curr_test.split(";");

                if (!CosineSimilarity.cosine_maps.containsKey(train_arr[0] + ";" + curr_arr[0]+";"+curr_arr[curr_arr.length-1]) && !Pearson.pearson_maps.containsKey(train_arr[0] + ";" + curr_arr[0]+";"+curr_arr[curr_arr.length-1])) {
                        String[] rec_train = train.split(";");
                        String[] rec_test = curr_test.split(";");
                        rec_train_list.clear();
                        rec_test_list.clear();
                        for (int k = 1; k < rec_train.length - 1; k++) {
                            rec_train_list.add(Double.parseDouble(rec_train[k]));
                            rec_test_list.add(Double.parseDouble(rec_test[k]));
                        }
                        double result_cos = cos_obj.executeAlgorithm(rec_train_list, rec_test_list);
                        double result_pearson = pear_obj.executeAlgorithm(rec_train_list, rec_test_list);
                        if (CosineSimilarity.cosine_maps.containsKey((rec_train[0]) + ";" + (rec_test[0])) && Pearson.pearson_maps.containsKey((rec_train[0]) + ";" + (rec_test[0]))) {
                            temp.add((rec_train[0]) + ";" + (rec_test[0]));
                        }
                        //Maps lere train taski,test taski ve test taskinin classı, benzerlik sonucunu koyuyoruz.
                        CosineSimilarity.cosine_maps.put((rec_train[0]) + ";" + (rec_test[0]) + ";" + rec_test[rec_test.length - 1], result_cos);
                        Pearson.pearson_maps.put((rec_train[0]) + ";" + (rec_test[0]) + ";" + rec_test[rec_test.length - 1], result_pearson);
                }
            }
        ArrayList<String> pearson_estimated = new ArrayList<>();
        ArrayList<String> cosine_estimated = new ArrayList<>();
        boolean check = true;
        //for (Map.Entry<String, Double> entry : CosineSimilarity.cosine_maps.entrySet()) {
            //Map.Entry maks_entry = null;
            //check = true;
            //if (entry != null) {
               NearestNeighbourhoodAlgorithm.findNearest(train, CosineSimilarity.cosine_maps);
                key_cos=key;
                value_cos=value;
           // }
            //KEY DIREK CLASS ADI OLUYOR.
           /* if (key_cos != null) {
                Set<String> keys_list = new HashSet<>();
                keys_list = cosine_results.keySet();
                for (String s : keys_list) {
                    String[] s_arr = s.split(";");
                    if (current_task_id.equals(s_arr[0])) { //eger bir taskin classina karar verilmişse, tekrar bakmamak icin
                        check = false;
                    }
                }
                if (check == true) {
                    cosine_results.put(key, value);
                    cosine_estimated.add(key);
                }
                if (cosine_results.size() == 1)
                    break;
            }
        }*/
        //for (Map.Entry<String, Double> entry : Pearson.pearson_maps.entrySet()) {
            check = true;
            NearestNeighbourhoodAlgorithm.findNearest(train, Pearson.pearson_maps);
            key_pearson=key;
            value_pearson=value;
            /*if (key != null) {
                Set<String> keys_list = new HashSet<>();
                keys_list = pearson_results.keySet();
                for (String s : keys_list) {
                    String[] s_arr = s.split(";");
                    if (current_task_id.equals(s_arr[0])) {
                        check = false;
                    }
                }
                if (check == true) {
                    pearson_results.put(key, value);
                    pearson_estimated.add(key);
                }
                if (pearson_results.size() == 1)
                    break;
            }
        }*/
        /*CreateReport.indicateOriginalClass(train,test);
        String [] arr_pearson=key_pearson.split(";");
        String[] arr_cos=key_cos.split(";");
        CreateReport.createResultString(indicator, train,arr_cos[arr_cos.length-1],arr_pearson[arr_pearson.length-1],cosine_results,pearson_results);*/
        CosineSimilarity.cosine_maps.clear();
        Pearson.pearson_maps.clear();
        cosine_results.clear();
        pearson_results.clear();
    }
    public static void executeForMTV4(int index, ArrayList<String> test, String train, String indicator,String nameOfAlgorithm) throws IOException {
        ArrayList<Double> rec_train_list = new ArrayList<>();
        ArrayList<Double> rec_test_list = new ArrayList<>();
        MTSimV4 mtv4_obj = new MTSimV4();
        ArrayList<String> temp = new ArrayList<>();
        for (int j = 0; j < test.size(); j++) {
            String curr_test=test.get(j);
            if (!MTSimV2.mtsimv2_maps.containsKey((index + 1) + ";" + (j + 1))) {
                String[] rec_train = train.split(";");
                String[] rec_test = curr_test.split(";");
                rec_train_list.clear();
                rec_test_list.clear();
                for (int k = 1; k < rec_train.length - 1; k++) {
                    rec_train_list.add(Double.parseDouble(rec_train[k]));
                    rec_test_list.add(Double.parseDouble(rec_test[k]));
                    //System.out.println(k);
                }
                double result_mtv4 = mtv4_obj.executeAlgorithm(rec_train_list, rec_test_list);
                if (MTSimV2.mtsimv2_maps.containsKey((rec_train[0]) + ";" + (rec_test[0]))) {
                    temp.add((rec_train[0]) + ";" + (rec_test[0]));
                }
                MTSimV4.mtsimv4_maps.put((rec_train[0]) + ";" + (rec_test[0]) + ";" + rec_test[rec_test.length - 1], result_mtv4);
            }
        }
        ArrayList<String> mtv4_estimated = new ArrayList<>();
        NearestNeighbourhoodAlgorithm.findNearest(train, MTSimV4.mtsimv4_maps);
        key_mtv2=key;
        value_mtv2=value;
        FileWriter writer = new FileWriter(indicator, true);
        CreateReport.indicateOriginalClass(train,test);
        String[] arr_mtv2=key_mtv2.split(";");
        String[] arr_train=train.split(";");
        String result=arr_train[0]+";"+arr_train[arr_train.length-1]+";"+arr_mtv2[1]+";"+value_mtv2+";"+arr_mtv2[2];
        writer.write(result);
        writer.write("\n");
        writer.close();
        MTSimV4.mtsimv4_maps.clear();
        mtv4_results.clear();
    }
    public static void executeForMTVersions(int index, ArrayList<String> test, String train, String indicator,String nameOfAlgorithm) throws IOException {
        ArrayList<Double> rec_train_list = new ArrayList<>();
        ArrayList<Double> rec_test_list = new ArrayList<>();
        MTSimV1 mtv1_obj = new MTSimV1();
        MTSimV2 mtv2_obj = new MTSimV2();
        ArrayList<String> temp = new ArrayList<>();
        for (int j = 0; j < test.size(); j++) {
            String curr_test=ApplicationOfAlgorithms.convertLILtoNormal(test.get(j));
            if (/*!MTSimV1.mtsimv1_maps.containsKey((index + 1) + "," + (j + 1)) && */!MTSimV2.mtsimv2_maps.containsKey((index + 1) + ";" + (j + 1))) {

                String[] rec_train = train.split(";");
                    String[] rec_test = curr_test.split(";");
                    rec_train_list.clear();
                    rec_test_list.clear();
                    for (int k = 1; k < rec_train.length - 1; k++) {
                        rec_train_list.add(Double.parseDouble(rec_train[k]));
                        rec_test_list.add(Double.parseDouble(rec_test[k]));
                    }
                    //double result_mtv1 = mtv1_obj.executeAlgorithm(rec_train_list, rec_test_list);
                if(nameOfAlgorithm.equals("mtVersion")){
                    double result_mtv2 = mtv2_obj.executeAlgorithm(rec_train_list, rec_test_list);
                    if (/*MTSimV1.mtsimv1_maps.containsKey((rec_train[0]) + "," + (rec_test[0])) && */MTSimV2.mtsimv2_maps.containsKey((rec_train[0]) + ";" + (rec_test[0]))) {
                        temp.add((rec_train[0]) + ";" + (rec_test[0]));
                    }
                    //Maps lere train taski,test taski ve test taskinin classı, benzerlik sonucunu koyuyoruz.
                    //MTSimV1.mtsimv1_maps.put((rec_train[0]) + "," + (rec_test[0]) + "," + rec_test[rec_test.length - 1], result_mtv1);
                    MTSimV2.mtsimv2_maps.put((rec_train[0]) + ";" + (rec_test[0]) + ";" + rec_test[rec_test.length - 1], result_mtv2);
                }
                if(nameOfAlgorithm.equals("hellinger")){
                    double result_hellinger=HellingerDistance.calculateHellingerDist(rec_train_list,rec_test_list);
                    HellingerDistance.hellinger_maps.put((rec_train[0]) + ";" + (rec_test[0]) + ";" + rec_test[rec_test.length - 1], result_hellinger);
                }

            }
        }
        //ArrayList<String> mtv1_estimated = new ArrayList<>();
        ArrayList<String> mtv2_estimated = new ArrayList<>();
        boolean check = true;
       /* for (Map.Entry<String, Double> entry : MTSimV1.mtsimv1_maps.entrySet()) {
            Map.Entry maks_entry = null;
            check = true;
            if (entry != null) {
                NearestNeighbourhoodAlgorithm.findNearest(entry, MTSimV1.mtsimv1_maps);
                key_mtv1=key;
                value_mtv1=value;
            }
            //KEY DIREK CLASS ADI OLUYOR.
            if (key_mtv1 != null) {
                Set<String> keys_list = new HashSet<>();
                keys_list = mtv1_results.keySet();
                for (String s : keys_list) {
                    String[] s_arr = s.split(",");
                    if (current_task_id.equals(s_arr[0])) { //eger bir taskin classina karar verilmişse, tekrar bakmamak icin
                        check = false;
                    }
                }
                if (check == true) {
                    mtv1_results.put(key, value);
                    mtv1_estimated.add(key);
                }
                if (mtv1_results.size() == 1)
                    break;
            }
        }*/
        //for (Map.Entry<String, Double> entry : MTSimV2.mtsimv2_maps.entrySet()) {
            check = true;
        if(nameOfAlgorithm.equals("mtVersion"))
            NearestNeighbourhoodAlgorithm.findNearest(train, MTSimV2.mtsimv2_maps);
            //NearestNeighbourHoodForDistance.findNearest(train,HellingerDistance.hellinger_maps);
        if(nameOfAlgorithm.equals("hellinger"))
            NearestNeighbourHoodForDistance.findNearest(train,HellingerDistance.hellinger_maps);
        key_mtv2=key;
        value_mtv2=value;
         /*   if (key != null) {
                Set<String> keys_list = new HashSet<>();
                keys_list = mtv2_results.keySet();
                for (String s : keys_list) {
                    String[] s_arr = s.split(";");
                    if (current_task_id.equals(s_arr[0])) {
                        check = false;
                    }
                }
                if (check == true) {
                    mtv2_results.put(key, value);
                    mtv2_estimated.add(key);
                }
                if (mtv2_results.size() == 1)
                    break;
            }
        }*/
        FileWriter writer = new FileWriter(indicator, true);
        CreateReport.indicateOriginalClass(train,test);
        //String [] arr_mtv1=key_mtv1.split(",");
        String[] arr_mtv2=key_mtv2.split(";");
        String[] arr_train=train.split(";");
        String result=arr_train[0]+";"+arr_train[arr_train.length-1]+";"+arr_mtv2[1]+";"+value_mtv2+";"+arr_mtv2[2];
        //MTSimV2.mtsimv2_estimated.put(arr_train[0],arr_mtv2[2]);
        writer.write(result);
        writer.write("\n");
        writer.close();
        //MTSimV1.mtsimv1_maps.clear();
        MTSimV2.mtsimv2_maps.clear();
        HellingerDistance.hellinger_maps.clear();
        //mtv1_results.clear();
        mtv2_results.clear();
    }
}

