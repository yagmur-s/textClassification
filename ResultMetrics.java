import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Yaðmur on 16.6.2016.
 */
public class ResultMetrics {
    public static HashMap<String,Double> recall_values =new HashMap<>();
    public static HashMap<String,Double> precision_values=new HashMap<>();
    public static HashMap<String,Double> fscore_values=new HashMap<>();
    public static double fp=0, tp =0, fn =0;
    public static void calculateRecallFscorePrecision(int fold_number,String algorithm){
        fold_number=fold_number;
        for (int i = 0; i< FirstStage.categories.size(); i++){
            //calculate_recall(FirstStage.categories.get(i),"cosine",CreateReport.estimated_class_cosine);
            //calculate_recall(FirstStage.categories.get(i),"cosine",CreateReport.estimated_class_pearson);
            calculate_recall(FirstStage.categories.get(i),algorithm,RocchioAlgorithm.rocchio_estimated_with_euclidean);
            //calculate_recall(FirstStage.categories.get(i),"cosine",RocchioAlgorithm.rocchio_estimated_with_cosine);
           // calculate_recall(FirstStage.categories.get(i),"cosine",RocchioAlgorithm.rocchio_estimated_with_mtsimV2);

            //rocchio_estimated_with_euclidean.clear();
            //rocchio_estimated_with_cosine.clear();
            //rocchio_estimated_with_mtsimV2.clear();
        }
        printMetrics(fold_number);
        recall_values.clear();
        precision_values.clear();
        fscore_values.clear();
        for (int i = 0; i< FirstStage.categories.size(); i++){
            //calculate_recall(FirstStage.categories.get(i),"cosine",CreateReport.estimated_class_cosine);
            //calculate_recall(FirstStage.categories.get(i),"cosine",CreateReport.estimated_class_pearson);
            //calculate_recall(FirstStage.categories.get(i),"cosine",RocchioAlgorithm.rocchio_estimated_with_euclidean);
            calculate_recall(FirstStage.categories.get(i),"rocchio with cosine",RocchioAlgorithm.rocchio_estimated_with_cosine);
            //calculate_recall(FirstStage.categories.get(i),"cosine",RocchioAlgorithm.rocchio_estimated_with_mtsimV2);
            //rocchio_estimated_with_euclidean.clear();
            //rocchio_estimated_with_cosine.clear();
            //rocchio_estimated_with_mtsimV2.clear();
        }
        printMetrics(fold_number);
        recall_values.clear();
        precision_values.clear();
        fscore_values.clear();
        for (int i = 0; i< FirstStage.categories.size(); i++){
            //calculate_recall(FirstStage.categories.get(i),"cosine",CreateReport.estimated_class_cosine);
            //calculate_recall(FirstStage.categories.get(i),"cosine",CreateReport.estimated_class_pearson);
            //calculate_recall(FirstStage.categories.get(i),"cosine",RocchioAlgorithm.rocchio_estimated_with_euclidean);
            //calculate_recall(FirstStage.categories.get(i),"cosine",RocchioAlgorithm.rocchio_estimated_with_cosine);
            calculate_recall(FirstStage.categories.get(i),"rocchio mtsimv2",RocchioAlgorithm.rocchio_estimated_with_mtsimV2);
            //rocchio_estimated_with_euclidean.clear();
            //rocchio_estimated_with_cosine.clear();
            //rocchio_estimated_with_mtsimV2.clear();
        }
        printMetrics(fold_number);
        recall_values.clear();
        precision_values.clear();
        fscore_values.clear();
        RocchioAlgorithm.rocchio_estimated_with_euclidean.clear();
        RocchioAlgorithm.rocchio_estimated_with_cosine.clear();
        RocchioAlgorithm.rocchio_estimated_with_mtsimV2.clear();
        CreateReport.original_class.clear();
    }
    /**
     * This function calculates recall= tp_cosine/(fn_cosine+tp_cosine) for each class according to each metric
     * And after calculation, directs program to precision calculation.
     */
    public static void calculate_recall(String class_name, String metric,LinkedHashMap<String,String> estimated_class){
        double recall=0.0;
        //for (Map.Entry<String, String> entry_org : CreateReport.original_class.entrySet()) {
            for (Map.Entry<String, String> entry_est : estimated_class.entrySet()) {
                String org_class=CreateReport.original_class.get(entry_est.getKey());
                if (entry_est.getValue().toUpperCase().equals(class_name) && org_class.toUpperCase().equals(class_name)) {
                    tp++;
                } else if (!entry_est.getValue().toUpperCase().equals(org_class) && org_class.toUpperCase().equals(class_name)) {
                    fn++;
                } else if (entry_est.getValue().toUpperCase().equals(class_name) && !org_class.toUpperCase().equals(class_name)) {
                    fp++;
                }
            }
        //}
        if((fn+tp)!=0){
            recall= tp /(fn + tp);
        }
        /*if(recall_values.containsKey(class_name+" for "+ metric)){
            double old_value=recall_values.get(class_name+" for "+ metric);
            double cummulative_value=old_value+recall;
            recall_values.replace(class_name+" for "+ metric,old_value,cummulative_value);
            //recall_values.put(class_name+" for "+ metric,cummulative_value);
        }
        else{*/
            recall_values.put(class_name+" for "+ metric,recall);
        //}
        double precision=calculate_precision(class_name,metric,tp,fp);
        calculate_fscore(class_name,metric,recall,precision);
        tp =0;fp=0;fn =0;
    }

    /**
     * This function calculates precision= TP/(TP+FP) for each class according to each metric
     */
    public static double calculate_precision(String class_name,String metric,double tp,double fp){
        double precision=0.0;
        if((tp+fp!=0)){
            precision=tp/(tp+fp);
        }
        /*if(precision_values.containsKey(class_name+" for "+ metric)){
            double old_value=precision_values.get(class_name+" for "+ metric);
            double cummulative_value=old_value+precision;
            precision_values.replace(class_name+" for "+ metric,old_value,cummulative_value);
        }else{*/
            precision_values.put(class_name+" for "+ metric,precision);
        //}
        //precision_pearson=tp_pearson/(tp_pearson+fp_pearson);
        //precision_values.put(class_name+" in pearson",precision_pearson);
        return precision;

    }

    /**
     * This function calculates fscore value for each class according to each metric by using recall and precision value belonging them.
     * @param class_name
     * @param metric
     * @param recall
     * @param precision
     */
    public static void calculate_fscore(String class_name,String metric,double recall,double precision){
        double fscore=0.0;
        if((recall+precision!=0)){
            fscore=(recall*precision)/((recall+precision)/2);
        }
       /* if(fscore_values.containsKey(class_name+" for "+ metric)){
            double old_value=fscore_values.get(class_name+" for "+ metric);
            double cummulative_value=old_value+fscore;
            fscore_values.replace(class_name+" for "+metric,old_value,cummulative_value);
        }else{*/
            fscore_values.put(class_name+" for "+metric,fscore);
        //}
    }
    public static void calculateAverageResults(){
        int fold_nb=10;
        for (Map.Entry<String, Double> entry : recall_values.entrySet()){
            String key=entry.getKey();
            double value=entry.getValue();
            recall_values.replace(key,value,(value/fold_nb));
        }
        for (Map.Entry<String, Double> entry : precision_values.entrySet()){
            String key=entry.getKey();
            double value=entry.getValue();
            precision_values.replace(key,value,(value/fold_nb));
        }
        for (Map.Entry<String, Double> entry : fscore_values.entrySet()){
            String key=entry.getKey();
            double value=entry.getValue();
            fscore_values.replace(key,value,(value/fold_nb));
        }
        printMetrics(-1);
    }
    public static void printMetrics(int fold_number){
        try {
            PrintWriter writer = new PrintWriter(new FileOutputStream(
                    new File("results.txt"),
                    true));
            if(fold_number==-1){
                writer.println("AVERAGE VALUES");
            }else{
                writer.println("For Fold "+fold_number+" :");
            }
            for (Map.Entry<String, Double> entry : recall_values.entrySet()){
                String key=entry.getKey();
                double value=entry.getValue();
                writer.println(key+";"+value);
            }
            for (Map.Entry<String, Double> entry : fscore_values.entrySet()){
                String key=entry.getKey();
                double value=entry.getValue();
                writer.println(key+";"+value);
            }
            for (Map.Entry<String, Double> entry : precision_values.entrySet()){
                String key=entry.getKey();
                double value=entry.getValue();
                writer.println(key+";"+value);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
