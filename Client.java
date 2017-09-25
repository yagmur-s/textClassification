import java.io.*;
import java.nio.file.FileSystemException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ya?gmur on 19.5.2016.
 */
public class Client {
    public static void main(String[] args) throws Exception {
        //categories rocchio içinde kullanýlýyor, indicatefrequency1 de otomatik dolduruluyor, elle doldurmaman lazým.
        FirstStage.categories.add("WL");
        FirstStage.categories.add("EKOL");
        FirstStage.categories.add("CAMIILA");
        FirstStage.categories.add("SK");
        //FirstStage.initCategoriesAndCounters();
        /*FirstStage.counter.add(0);
        FirstStage.counter.add(0);
        FirstStage.counter.add(0);
        FirstStage.counter.add(0);*/
        RocchioAlgorithm.class_counter.put("CAMIILA",0);
        RocchioAlgorithm.class_counter.put("WL",0);
        RocchioAlgorithm.class_counter.put("EKOL",0);
        RocchioAlgorithm.class_counter.put("SK",0);
        //ApplicationOfAlgorithms.executeFirstStage();
        ApplicationOfAlgorithms.reduceAttributesFromMatrixes();
        //FirstStage.createDictionary(); //bunu reduceAttributesFromMatrixes i çalýþtýrdýktan sonra dictionary i dosyadan okumak için çalýþtýr
        //getDataForDeneme();
        //StratifiedCrossValidationWithWeka.filterDataset();
        //CrossValidation.chooseTrainTestFor20NewsGroupData();
        //FirstStage.splitTFIDF();
        //FirstStage.convertNormalToLIL();
        CrossValidation.chooseTrainTestForRemaining();
        /*runForMTMetrics();
        runForCosineAndPearson();
        runForHellingerDist();
        ApplicationOfAlgorithms.executeForRocchio("tf-idf",CrossValidation.test_records_tf_idf,CrossValidation.train_records_tf_idf);*/
        CrossValidation.test_records_tf_idf.addAll(FirstStage.test_rec_tf_idf);
        CrossValidation.train_records_tf_idf.addAll(FirstStage.train_rec_tf_idf);
        //runForMTMetrics();
        //runForCosineAndPearson();
        //runForHellingerDist();
        //ApplicationOfAlgorithms.executeForRocchio("tf-idf",CrossValidation.test_records_tf_idf,CrossValidation.train_records_tf_idf);
        runForMTV4();
        //ResultMetrics.calculateRecallFscorePrecision(1,"cosine");
        //ResultMetrics.calculateRecallFscorePrecision(1,"pearson");
        RocchioAlgorithm.rocchio_estimated_with_mtsimV2.clear();
        RocchioAlgorithm.rocchio_estimated_with_cosine.clear();
        RocchioAlgorithm.rocchio_estimated_with_euclidean.clear();
        RocchioAlgorithm.rocchio_estimated_with_hellinger.clear();
        RocchioAlgorithm.rocchio_estimated_with_mtsimV4.clear();
        CrossValidation.train_records_tf_idf.clear();
        CrossValidation.test_records_tf_idf.clear();
        CrossValidation.test_records_tf_idf.addAll(CrossValidation.fold2);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold1);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold3);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold4);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold5);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold6);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold7);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold8);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold9);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold10);
        //runForMTMetrics();
        //runForCosineAndPearson();
        //runForHellingerDist();
        //ApplicationOfAlgorithms.executeForRocchio("tf-idf",CrossValidation.test_records_tf_idf,CrossValidation.train_records_tf_idf);
        //runForMTV4();
        //ResultMetrics.calculateRecallFscorePrecision(2,"cosine");
        RocchioAlgorithm.rocchio_estimated_with_mtsimV2.clear();
        RocchioAlgorithm.rocchio_estimated_with_cosine.clear();
        RocchioAlgorithm.rocchio_estimated_with_euclidean.clear();
        RocchioAlgorithm.rocchio_estimated_with_hellinger.clear();
        RocchioAlgorithm.rocchio_estimated_with_mtsimV4.clear();

        CrossValidation.train_records_tf_idf.clear();
        CrossValidation.test_records_tf_idf.clear();
        CrossValidation.test_records_tf_idf.addAll(CrossValidation.fold3);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold1);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold2);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold4);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold5);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold6);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold7);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold8);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold9);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold10);
        //runForMTMetrics();
        //runForCosineAndPearson();
        //runForHellingerDist();
        //ApplicationOfAlgorithms.executeForRocchio("tf-idf",CrossValidation.test_records_tf_idf,CrossValidation.train_records_tf_idf);
        //runForMTV4();
        //ResultMetrics.calculateRecallFscorePrecision(3,"cosine");
        RocchioAlgorithm.rocchio_estimated_with_mtsimV2.clear();
        RocchioAlgorithm.rocchio_estimated_with_cosine.clear();
        RocchioAlgorithm.rocchio_estimated_with_euclidean.clear();
        RocchioAlgorithm.rocchio_estimated_with_hellinger.clear();
        RocchioAlgorithm.rocchio_estimated_with_mtsimV4.clear();

        CrossValidation.train_records_tf_idf.clear();
        CrossValidation.test_records_tf_idf.clear();
        CrossValidation.test_records_tf_idf.addAll(CrossValidation.fold4);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold1);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold2);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold3);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold5);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold6);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold7);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold8);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold9);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold10);
        //runForMTMetrics();
        //runForCosineAndPearson();
        //runForHellingerDist();
        //runForMTV4();
        //ApplicationOfAlgorithms.executeForRocchio("tf-idf",CrossValidation.test_records_tf_idf,CrossValidation.train_records_tf_idf);
        //ResultMetrics.calculateRecallFscorePrecision(4,"cosine");
        RocchioAlgorithm.rocchio_estimated_with_mtsimV2.clear();
        RocchioAlgorithm.rocchio_estimated_with_cosine.clear();
        RocchioAlgorithm.rocchio_estimated_with_euclidean.clear();
        RocchioAlgorithm.rocchio_estimated_with_hellinger.clear();
        RocchioAlgorithm.rocchio_estimated_with_mtsimV4.clear();

        CrossValidation.train_records_tf_idf.clear();
        CrossValidation.test_records_tf_idf.clear();
        CrossValidation.test_records_tf_idf.addAll(CrossValidation.fold5);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold2);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold3);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold4);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold1);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold6);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold7);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold8);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold9);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold10);
        //runForMTMetrics();
        //runForCosineAndPearson();
        //runForHellingerDist();
        //runForMTV4();
        //ApplicationOfAlgorithms.executeForRocchio("tf-idf",CrossValidation.test_records_tf_idf,CrossValidation.train_records_tf_idf);
        //ResultMetrics.calculateRecallFscorePrecision(5,"cosine");
        RocchioAlgorithm.rocchio_estimated_with_mtsimV2.clear();
        RocchioAlgorithm.rocchio_estimated_with_cosine.clear();
        RocchioAlgorithm.rocchio_estimated_with_euclidean.clear();
        RocchioAlgorithm.rocchio_estimated_with_hellinger.clear();
        RocchioAlgorithm.rocchio_estimated_with_mtsimV4.clear();

        CrossValidation.train_records_tf_idf.clear();
        CrossValidation.test_records_tf_idf.clear();
        CrossValidation.test_records_tf_idf.addAll(CrossValidation.fold6);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold2);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold3);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold4);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold5);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold1);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold7);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold8);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold9);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold10);
        //runForMTMetrics();
        //runForCosineAndPearson();
        //runForMTV4();
        //ApplicationOfAlgorithms.executeForRocchio("tf-idf",CrossValidation.test_records_tf_idf,CrossValidation.train_records_tf_idf);
        //runForHellingerDist();
        //ResultMetrics.calculateRecallFscorePrecision(6,"cosine");
        RocchioAlgorithm.rocchio_estimated_with_mtsimV2.clear();
        RocchioAlgorithm.rocchio_estimated_with_cosine.clear();
        RocchioAlgorithm.rocchio_estimated_with_euclidean.clear();
        RocchioAlgorithm.rocchio_estimated_with_hellinger.clear();
        RocchioAlgorithm.rocchio_estimated_with_mtsimV4.clear();

        CrossValidation.train_records_tf_idf.clear();
        CrossValidation.test_records_tf_idf.clear();
        CrossValidation.test_records_tf_idf.addAll(CrossValidation.fold7);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold2);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold3);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold4);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold5);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold6);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold1);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold8);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold9);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold10);
        //runForMTMetrics();
        //runForCosineAndPearson();
        //runForHellingerDist();
        //runForMTV4();
        //ApplicationOfAlgorithms.executeForRocchio("tf-idf",CrossValidation.test_records_tf_idf,CrossValidation.train_records_tf_idf);
        //ResultMetrics.calculateRecallFscorePrecision(7,"cosine");
        RocchioAlgorithm.rocchio_estimated_with_mtsimV2.clear();
        RocchioAlgorithm.rocchio_estimated_with_cosine.clear();
        RocchioAlgorithm.rocchio_estimated_with_euclidean.clear();
        RocchioAlgorithm.rocchio_estimated_with_hellinger.clear();
        RocchioAlgorithm.rocchio_estimated_with_mtsimV4.clear();

        CrossValidation.train_records_tf_idf.clear();
        CrossValidation.test_records_tf_idf.clear();
        CrossValidation.test_records_tf_idf.addAll(CrossValidation.fold8);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold2);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold3);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold4);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold5);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold6);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold7);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold1);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold9);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold10);
        //runForMTMetrics();
        //runForCosineAndPearson();
        //runForHellingerDist();
        //runForMTV4();
        //ApplicationOfAlgorithms.executeForRocchio("tf-idf",CrossValidation.test_records_tf_idf,CrossValidation.train_records_tf_idf);
        //ResultMetrics.calculateRecallFscorePrecision(8,"cosine");
        RocchioAlgorithm.rocchio_estimated_with_mtsimV2.clear();
        RocchioAlgorithm.rocchio_estimated_with_cosine.clear();
        RocchioAlgorithm.rocchio_estimated_with_euclidean.clear();
        RocchioAlgorithm.rocchio_estimated_with_hellinger.clear();
        RocchioAlgorithm.rocchio_estimated_with_mtsimV4.clear();

        CrossValidation.train_records_tf_idf.clear();
        CrossValidation.test_records_tf_idf.clear();
        CrossValidation.test_records_tf_idf.addAll(CrossValidation.fold9);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold2);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold3);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold4);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold5);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold6);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold7);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold8);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold1);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold10);
        //runForMTMetrics();
        //runForCosineAndPearson();
        //runForHellingerDist();
        //runForMTV4();
        //ApplicationOfAlgorithms.executeForRocchio("tf-idf",CrossValidation.test_records_tf_idf,CrossValidation.train_records_tf_idf);
        //ResultMetrics.calculateRecallFscorePrecision(9,"cosine");
        RocchioAlgorithm.rocchio_estimated_with_mtsimV2.clear();
        RocchioAlgorithm.rocchio_estimated_with_cosine.clear();
        RocchioAlgorithm.rocchio_estimated_with_euclidean.clear();
        RocchioAlgorithm.rocchio_estimated_with_hellinger.clear();
        RocchioAlgorithm.rocchio_estimated_with_mtsimV4.clear();

        CrossValidation.train_records_tf_idf.clear();
        CrossValidation.test_records_tf_idf.clear();
        CrossValidation.test_records_tf_idf.addAll(CrossValidation.fold10);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold2);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold3);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold4);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold5);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold6);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold7);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold8);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold1);
        CrossValidation.train_records_tf_idf.addAll(CrossValidation.fold1);
        //runForMTMetrics();
        //runForCosineAndPearson();
        //runForHellingerDist();
        runForMTV4();
        //ApplicationOfAlgorithms.executeForRocchio("tf-idf",CrossValidation.test_records_tf_idf,CrossValidation.train_records_tf_idf);
        //ResultMetrics.calculateRecallFscorePrecision(10,"cosine");
        //ResultMetrics.calculateAverageResults();
        /*try {
            writer = new FileWriter("tf-report.txt", true);
            writer.write("Record,Original Class,Cosine Estimated Record,Cosine Value,Pearson Estimated Record,Pearson Value,Cosine Estimated Class,Pearson Estimated Class");
            writer.write(System.getProperty("line.separator"));
            writer.close();
            for (int i = 0; i < FirstStage.train_rec_tf.size(); i++) {
                String curr_task_tf = FirstStage.train_rec_tf.get(i);
                ApplicationOfAlgorithms.executeForPearsonAndCosine(i, FirstStage.train_rec_tf, ApplicationOfAlgorithms.convertLILtoNormal(curr_task_tf), "tf-report.txt");
            }
            writer = new FileWriter("tf-idf-report.txt", true);
            writer.write("Record,Original Class,Cosine Estimated Record,Cosine Value,Pearson Estimated Record,Pearson Value,Cosine Estimated Class,Pearson Estimated Class");
            writer.write(System.getProperty("line.separator"));
            writer.close();
            for (int i = 0; i < FirstStage.train_rec_tf_idf.size(); i++) {
                String curr_task_tf_idf = FirstStage.train_rec_tf_idf.get(i);
                ApplicationOfAlgorithms.executeForPearsonAndCosine(i, FirstStage.train_rec_tf_idf, ApplicationOfAlgorithms.convertLILtoNormal(curr_task_tf_idf), "tf-idf-report.txt");
                writer = new FileWriter("tf-idf-report.txt", true);
                String[] arr = curr_task_tf_idf.split(";");
                writer.write(arr[0] + ";" + arr[arr.length-1] +";"+ApplicationOfAlgorithms.key_cos +";"+ApplicationOfAlgorithms.value_cos +";"+ApplicationOfAlgorithms.key_pearson+";"+ApplicationOfAlgorithms.value_pearson);
                writer.write(System.getProperty("line.separator"));
                writer.close();
            }
        } catch (FileNotFoundException e) {
            e.getMessage();
        }*/
    }
    public static void runForHellingerDist(){
        FileWriter writer = null;
        try {
            /*writer = new FileWriter("tf-report.txt", true);
            writer.write("Record,Original Class,MTV1 Estimated Record,MTV1 Value,MTV2 Estimated Record,MTV2 Value,MTV1 Estimated Class,MTV2 Estimated Class");
            writer.write(System.getProperty("line.separator"));
            writer.close();
            for (int i = 0; i < FirstStage.train_rec_tf.size(); i++) {
                String curr_task_tf = FirstStage.train_rec_tf.get(i);
                ApplicationOfAlgorithms.executeForMTVersions(i, FirstStage.train_rec_tf, ApplicationOfAlgorithms.convertLILtoNormal(curr_task_tf), "tf-report.txt");
            }*/
            writer = new FileWriter("tf-idf-report-hellinger.txt", true);
            writer.write("Record;Original Class;Hellinger Estimated Record;Value");
            writer.write(System.getProperty("line.separator"));
            writer.close();
            for (int i = 0; i < CrossValidation.test_records_tf_idf.size(); i++) {
                CrossValidation.test_records_tf_idf.set(i,CrossValidation.test_records_tf_idf.get(i).replaceAll(",","."));
            }
            for (int i = 0; i < CrossValidation.test_records_tf_idf.size(); i++) {
                String curr_task_tf_idf = CrossValidation.test_records_tf_idf.get(i).replaceAll(",",".");
                //ApplicationOfAlgorithms.tasks.set(i,ApplicationOfAlgorithms.tasks.get(i).replaceAll(",","."));
                ApplicationOfAlgorithms.executeForMTVersions(i, CrossValidation.train_records_tf_idf, ApplicationOfAlgorithms.convertLILtoNormal(curr_task_tf_idf), "tf-idf-report-hellinger.txt","hellinger");
            }
        } catch (FileNotFoundException e) {
            e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void runForMTV4(){
        FileWriter writer = null;
        try {writer = new FileWriter("tf-idf-report-mtv4.txt", true);
            writer.write("Record;Original Class;MTV4 Estimated Record;Value");
            writer.write(System.getProperty("line.separator"));
            writer.close();
            for (int i = 0; i < CrossValidation.test_records_tf_idf.size(); i++) {
                CrossValidation.test_records_tf_idf.set(i,CrossValidation.test_records_tf_idf.get(i).replaceAll(",","."));
            }
            for (int i = 0; i < CrossValidation.test_records_tf_idf.size(); i++) {
                String curr_task_tf_idf = CrossValidation.test_records_tf_idf.get(i).replaceAll(",",".");
                ApplicationOfAlgorithms.executeForMTV4(i, CrossValidation.train_records_tf_idf, curr_task_tf_idf, "tf-idf-report-mtv4.txt","mtv4");
            }
        } catch (FileNotFoundException e) {
            e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void runForCosineAndPearson() throws IOException {
        FileWriter writer = null;
        writer = new FileWriter("tf-idf-report.txt", true);
        writer.write("Record;Original Class;Cosine Estimated Record;Value;Pearson Estimated Class;Value");
        writer.write(System.getProperty("line.separator"));
        writer.close();
        for (int i = 0; i < CrossValidation.test_records_tf_idf.size(); i++) {
            String curr_task_tf_idf = CrossValidation.test_records_tf_idf.get(i);
            try {
                ApplicationOfAlgorithms.executeForPearsonAndCosine(i, CrossValidation.train_records_tf_idf, ApplicationOfAlgorithms.convertLILtoNormal(curr_task_tf_idf), "tf-idf-report.txt");
                String[] arr = curr_task_tf_idf.split(";");
                writer = new FileWriter("tf-idf-report.txt", true);
                writer.write(arr[0] + ";" + arr[arr.length-1] +";"+ApplicationOfAlgorithms.key_cos +";"+ApplicationOfAlgorithms.value_cos +";"+ApplicationOfAlgorithms.key_pearson+";"+ApplicationOfAlgorithms.value_pearson);
                writer.write(System.getProperty("line.separator"));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    public static void runForMTMetrics(){
        FileWriter writer = null;
        try {
            /*writer = new FileWriter("tf-report.txt", true);
            writer.write("Record,Original Class,MTV1 Estimated Record,MTV1 Value,MTV2 Estimated Record,MTV2 Value,MTV1 Estimated Class,MTV2 Estimated Class");
            writer.write(System.getProperty("line.separator"));
            writer.close();
            for (int i = 0; i < FirstStage.train_rec_tf.size(); i++) {
                String curr_task_tf = FirstStage.train_rec_tf.get(i);
                ApplicationOfAlgorithms.executeForMTVersions(i, FirstStage.train_rec_tf, ApplicationOfAlgorithms.convertLILtoNormal(curr_task_tf), "tf-report.txt");
            }*/
            writer = new FileWriter("tf-idf-report-mtVersions.txt",true);
            writer.write("Record;Original Class;MTV2 Estimated Record;MTV2 Value;MTV2 Estimated Class");
            writer.write(System.getProperty("line.separator"));
            writer.close();
            /*for (int i = 0; i < FirstStage.test_rec_tf_idf.size(); i++) {
                FirstStage.test_rec_tf_idf.set(i,FirstStage.test_rec_tf_idf.get(i).replaceAll(",","."));
            }*/
            for (int i = 0; i < CrossValidation.test_records_tf_idf.size(); i++) {
                String curr_task_tf_idf = CrossValidation.test_records_tf_idf.get(i).replaceAll(",",".");
                ApplicationOfAlgorithms.executeForMTVersions(i, CrossValidation.train_records_tf_idf, ApplicationOfAlgorithms.convertLILtoNormal(curr_task_tf_idf), "tf-idf-report-mtVersions.txt","mtVersion");
            }
        } catch (FileNotFoundException e) {
            e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void getDataForDeneme() throws IOException {

        try {
            BufferedReader br_tf = new BufferedReader(new FileReader("tf-idf-matrix-normalized1_full_data_s_010.txt"));
            ArrayList<Integer> counts = new ArrayList<>();
            ArrayList<String> original_class=new ArrayList<>();
            int count = 1;
            String str_tf, word, prevWord;
            str_tf = br_tf.readLine();
            word = "";
            for (; str_tf != null; str_tf = br_tf.readLine()){
                String [] arr=str_tf.split(";");
                System.out.println(arr[0]+";"+arr[arr.length-1]);
                FirstStage.train_rec_tf_idf.add(str_tf);
                original_class.add(arr[arr.length-1]);
            }

            br_tf.close();

        } catch (@SuppressWarnings("Since15") FileSystemException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
