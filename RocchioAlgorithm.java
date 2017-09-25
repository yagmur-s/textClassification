import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Yaðmur on 22.5.2016.
 */
public class RocchioAlgorithm{
    public static ArrayList<String> normalized_train_attr=new ArrayList<>();
    public static ArrayList<String> centroid_class_attr =new ArrayList<>();
    public static LinkedHashMap<String,Integer> class_counter=new LinkedHashMap<>();
    public static LinkedHashMap<String,Double> euclidean_results=new LinkedHashMap<>();
    public static LinkedHashMap<String,String> rocchio_estimated_with_euclidean =new LinkedHashMap<>();
    public static LinkedHashMap<String,Double> cosine_results=new LinkedHashMap<>();
    public static LinkedHashMap<String,String> rocchio_estimated_with_cosine =new LinkedHashMap<>();
    public static LinkedHashMap<String,Double> mtsimV2_results=new LinkedHashMap<>();
    public static LinkedHashMap<String,Double> mtsimV4_results=new LinkedHashMap<>();

    public static LinkedHashMap<String,Double> hellinger_results=new LinkedHashMap<>();
    public static LinkedHashMap<String,String> rocchio_estimated_with_mtsimV2 =new LinkedHashMap<>();
    public static LinkedHashMap<String,String> rocchio_estimated_with_mtsimV4 =new LinkedHashMap<>();
    public static LinkedHashMap<String,String> rocchio_estimated_with_hellinger=new LinkedHashMap<>();
    public static String min_hellinger_class="",class_str1="", min_euclidean_class ="",max_cos_class="",max_mtV2_class="",max_mtV4_class="";
    public static double min_hellinger_value =10000000000.0,min_euclidean_value =999999999.9, max_cos_value =-999999.9,max_mtV4_value=-999999.9, max_mtV2_value =-999999.9;
    public static ArrayList<String> test=new ArrayList<>();
    private static double CONST_VALUE=1/(Math.sqrt(2));
    public static void execute(String train,int indexOfCurrTask) {
        normalizeAttr(train.replaceAll(",","."));
        for (int i = 0; i< CrossValidation.train_records_tf_idf.size(); i++){
            //bu fonksiyon için ondalikli sayilarin virgüllerini nokta ile degistirmis olman gerek
            CrossValidation.train_records_tf_idf.set(i,CrossValidation.train_records_tf_idf.get(i).replaceAll(",","."));
            //normalizeAttr(ApplicationOfAlgorithms.convertLILtoNormal(CrossValidation.train_records_tf_idf.get(i)));
            normalizeAttr(CrossValidation.train_records_tf_idf.get(i));
        }
        calculateCentroid(normalized_train_attr);
        //calculateEuclideanDist(indexOfCurrTask);
        //calculateCosineANDMtV2Dist(indexOfCurrTask);
        calculateMTSimV4(indexOfCurrTask);
        chooseClass(train);
        euclidean_results.clear();
        class_counter.clear();
        centroid_class_attr.clear();
        normalized_train_attr.clear();
        cosine_results.clear();
        mtsimV2_results.clear();
        hellinger_results.clear();
        mtsimV4_results.clear();
        //rocchio_estimated_with_euclidean.clear();
        //rocchio_estimated_with_cosine.clear();
        //rocchio_estimated_with_mtsimV2.clear();
    }

    /**
     *
     * @param train_record
     */
    public static void normalizeAttr(String train_record){
        String [] attr=train_record.split(";");
        double sumOfsqr=0.0;
        double [] rec_double=new double[attr.length];
        ArrayList<Double> rec_double_list=new ArrayList<>();
        for (int i=1;i<attr.length-1;i++){
            rec_double_list.add(Double.parseDouble(attr[i]));
        }
        rec_double=rec_double_list.stream().mapToDouble(Double::doubleValue).toArray();
        for (int i=0;i<rec_double_list.size();i++){
            sumOfsqr+=rec_double_list.get(i)*rec_double_list.get(i);
        }
        double normalized_value=Math.sqrt(sumOfsqr);
        String norm="";
        //end of 1.1 stage
        for (int i=0;i<rec_double_list.size();i++){
            double normalized;
            if(normalized_value==0)
                normalized=0;
            else{
                normalized=rec_double_list.get(i)/normalized_value;
            }
            norm=norm+";"+normalized;
        }
        norm=attr[0]+norm+";"+attr[attr.length-1];
        normalized_train_attr.add(norm);

    }
    public static void calculateCentroid(ArrayList<String> train_record){
        for (int i=0;i<FirstStage.categories.size();i++){
            class_counter.put(FirstStage.categories.get(i),0);
        }
        for (int i = 0; i< CrossValidation.train_records_tf_idf.size(); i++){
            String [] rec= CrossValidation.train_records_tf_idf.get(i).split(";");
            int counter=class_counter.get(rec[rec.length-1].toUpperCase());
            counter++;
            class_counter.put(rec[rec.length-1].toUpperCase(),counter);
        }
        String cent="";
        double centroid_value=0.0;
        for (int k=0;k<FirstStage.categories.size();k++) {
            for (int j = 1; j < train_record.get(0).split(";").length - 1; j++) {
                for (int i = 0; i < train_record.size(); i++) {
                    String[] normalized_values = train_record.get(i).split(";");
                    if(normalized_values[normalized_values.length-1].toUpperCase().equals(FirstStage.categories.get(k))) {
                        double centroid = (Double.parseDouble(normalized_values[j]) / class_counter.get(normalized_values[normalized_values.length-1].toUpperCase()));
                        centroid_value += centroid;
                    }
                }
                cent += ";" + centroid_value;

                centroid_value = 0.0;
            }
            cent=FirstStage.categories.get(k).toUpperCase()+cent;centroid_class_attr.add(cent);
            cent="";
        }
    }
    public static void calculateHellingerDist(String curr_class,ArrayList<Double> rec1, ArrayList<Double> rec2){
        double result=0.0;
        for (int i=0;i<rec1.size();i++){
            double sqrt1=Math.sqrt(rec1.get(i));
            double sqrt2=Math.sqrt(rec2.get(i));
            double diff=sqrt1-sqrt2;
            double sqr=diff*diff;
            result=result+sqr;
        }
        result=Math.sqrt(result);
        hellinger_results.put(curr_class,CONST_VALUE*result) ;
    }
    public static void calculateEuclideanDist(int index){

        String [] attr_values=ApplicationOfAlgorithms.convertLILtoNormal(CrossValidation.test_records_tf_idf.get(index)).split(";");
        double sum=0.0;
        for (int i=0;i<centroid_class_attr.size();i++){
            String [] class_values=centroid_class_attr.get(i).split(";");
            for (int j=1;j<attr_values.length-1;j++){
                double sub=(Double.parseDouble(class_values[j])-Double.parseDouble(attr_values[j]));
                sum+=sub*sub;
            }
           euclidean_results.put(FirstStage.categories.get(i).toUpperCase(),Math.sqrt(sum));
           sum=0.0;
        }
    }
    public static void calculateCosineANDMtV2Dist(int index){
        CosineSimilarity cos_obj=new CosineSimilarity();
        //String [] attr_values=ApplicationOfAlgorithms.convertLILtoNormal(CrossValidation.test_records_tf_idf.get(index)).split(";");
        String [] attr_values=CrossValidation.test_records_tf_idf.get(index).split(";");
        String [] cent_attr_str;
        double res_cos;
        ArrayList<Double> train_attr=new ArrayList<>();
        ArrayList<Double> cent_attr=new ArrayList<>();
        for (int k = 1; k < attr_values.length-1; k++) {
            train_attr.add(Double.parseDouble(attr_values[k]));
        }
        for (int i=0;i<centroid_class_attr.size();i++){
            cent_attr_str=centroid_class_attr.get(i).split(";");
            for (int j=1;j<cent_attr_str.length;j++)
            {
                cent_attr.add(Double.parseDouble(cent_attr_str[j]));
            }
            res_cos=cos_obj.executeAlgorithm(train_attr,cent_attr);
            //calculateMTSimV2(FirstStage.categories.get(i).toUpperCase(),train_attr,cent_attr);
            //calculateHellingerDist(FirstStage.categories.get(i).toUpperCase(),train_attr,cent_attr);
            cosine_results.put(FirstStage.categories.get(i).toUpperCase(),res_cos);
            cent_attr.clear();
         }
    }
    public static void calculateMTSimV4(int index){
        String [] attr_values=CrossValidation.test_records_tf_idf.get(index).split(";");
        String [] cent_attr_str;
        ArrayList<Double> train_attr=new ArrayList<>();
        ArrayList<Double> cent_attr=new ArrayList<>();
        for (int k = 1; k < attr_values.length-1; k++) {
            train_attr.add(Double.parseDouble(attr_values[k]));
        }
        for (int i=0;i<centroid_class_attr.size();i++){
            cent_attr_str=centroid_class_attr.get(i).split(";");
            for (int j=1;j<cent_attr_str.length;j++)
            {
                cent_attr.add(Double.parseDouble(cent_attr_str[j]));
            }
            calculateMTSimV4(FirstStage.categories.get(i).toUpperCase(),train_attr,cent_attr);
            cent_attr.clear();
        }
    }

    public static void calculateMTSimV4(String curr_class,ArrayList<Double> rec1, ArrayList<Double> rec2){
        MTSimV4 mt=new MTSimV4();
        double res_mtSimV4=mt.executeAlgorithm(rec1,rec2);
        mtsimV4_results.put(curr_class,res_mtSimV4);
    }
    public static void calculateMTSimV2(String curr_class,ArrayList<Double> rec1, ArrayList<Double> rec2){
        MTSimV2 mt=new MTSimV2();
        double res_mtSimV2=mt.executeAlgorithm(rec1,rec2);
        mtsimV2_results.put(curr_class,res_mtSimV2);
    }
    public static void chooseClass(String rec){
        min_euclidean_value =999999999.9;
        max_cos_value =-999999.9;
        max_mtV2_value =-999999.9;
        min_hellinger_value = 99999999.9;
        String [] rec_arr=rec.split(";");
        for (int i=0;i<FirstStage.categories.size();i++){
            class_str1=FirstStage.categories.get(i);
            /*if(euclidean_results.containsKey(class_str1)&&euclidean_results.get(class_str1)< min_euclidean_value) {
                min_euclidean_value = euclidean_results.get(FirstStage.categories.get(i));
                min_euclidean_class =class_str1;
            }*/
            /*if(cosine_results.containsKey(class_str1)&&cosine_results.get(class_str1)> max_cos_value){
                max_cos_value =cosine_results.get(FirstStage.categories.get(i));
                max_cos_class=class_str1;
            }*/
            /*if(mtsimV2_results.containsKey(class_str1)&&mtsimV2_results.get(class_str1)> max_mtV2_value){
                max_mtV2_value =mtsimV2_results.get(FirstStage.categories.get(i));
                max_mtV2_class=class_str1;
            }*/
            if(mtsimV4_results.containsKey(class_str1)&&mtsimV4_results.get(class_str1)> max_mtV4_value){
                max_mtV4_value =mtsimV4_results.get(FirstStage.categories.get(i));
                max_mtV4_class=class_str1;
            }
            /*if(hellinger_results.containsKey(class_str1)&&hellinger_results.get(class_str1)< min_hellinger_value){
                min_hellinger_value =hellinger_results.get(FirstStage.categories.get(i));
                min_hellinger_class=class_str1;
            }*/
        }
        //ApplicationOfAlgorithms.key_roc= min_euclidean_class;
        //ApplicationOfAlgorithms.value_roc= min_euclidean_value;
        //rocchio_estimated_with_euclidean.put(rec_arr[0], min_euclidean_class);
        //rocchio_estimated_with_cosine.put(rec_arr[0],max_cos_class);
        //rocchio_estimated_with_hellinger.put(rec_arr[0],min_hellinger_class);
        //rocchio_estimated_with_mtsimV1.put(rec_arr[0],max_mtV1_class);
        //rocchio_estimated_with_mtsimV2.put(rec_arr[0],max_mtV2_class);
        rocchio_estimated_with_mtsimV4.put(rec_arr[0],max_mtV4_class);

    }
}
