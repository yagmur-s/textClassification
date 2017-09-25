/**
 * Created by Yagmur on 23.10.2016.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yaðmur on 10.5.2016.
 */
public class NearestNeighbourHoodForDistance {

    public static ArrayList<Map.Entry<String, Double>> selected_maks_values =new ArrayList<>();
    public static HashMap<String,Integer> counter_for_max1_classes=new HashMap<>();
    public static HashMap<String,Integer> counter_for_max2_classes=new HashMap<>();
    public static ArrayList<Map.Entry<String, Double>> selected_2nd_maks_values =new ArrayList<>();
    public static void findNearest(String rec,HashMap<String,Double> records) throws IOException {
        Map.Entry<String, Double> maxEntry = null;
        Map.Entry<String, Double> second_maxEntry = null;
        String [] key_rec=rec.split(";");
        String [] key_current_2=null;
        ApplicationOfAlgorithms.current_task_id=key_rec[0];
        try {
            double max_value,second_max_value=0;
            for (Map.Entry<String, Double> entry : records.entrySet())
            {
                String [] key_current=entry.getKey().split(";");
                if(maxEntry==null){
                    maxEntry=entry;
                }

                if (( entry.getValue()<maxEntry.getValue()  && !(maxEntry.getKey().equals(entry.getKey())) /*&& !(key_current[1].equals(key_rec[0]))*/))
                {
                    second_maxEntry=maxEntry;
                    maxEntry = entry;
                }
                else if(second_maxEntry!=null && entry.getValue()<second_maxEntry.getValue()  && maxEntry.getValue()<entry.getValue()&& !(maxEntry.getKey().equals(entry.getKey()))){
                    second_maxEntry=entry;
                }
            }
            int a=5;
            if(second_maxEntry!=null) {
                selected_2nd_maks_values.add(second_maxEntry);
                second_max_value = second_maxEntry.getValue();
            }
            selected_maks_values.add(maxEntry);
            max_value=maxEntry.getValue();
            for (Map.Entry<String, Double> entry : records.entrySet())
            {

                double temp_value=entry.getValue();
                key_current_2=entry.getKey().split(";");
                if(maxEntry==null){
                    maxEntry=entry;
                }
                //This 'if' for exposing other records with max similarity found above
                if (temp_value==max_value && !(key_current_2[1].equals(maxEntry.getKey().split(";")[1])) /*&& key_rec[0].equals(key_current_2[0]) && !(key_rec[1].equals(key_current_2[0]))*/)
                {
                    selected_maks_values.add(entry);
                }
                if(second_maxEntry!=null){
                    if (second_maxEntry!= null && temp_value==second_max_value && !(key_current_2[1].equals(second_maxEntry.getKey().split(";")[1])) /* && key_rec[0].equals(key_current_2[0]) && !(key_rec[1].equals(key_current_2[1]))*/)
                    {
                        selected_2nd_maks_values.add(entry);
                    }
                }

            }
            int count=0;
            //This outer loop below is for counting the number of same classes whose values are selected max1 and max2
            for (int i=0;i<selected_maks_values.size();i++){
                String [] arr=selected_maks_values.get(i).getKey().split(";");
                count=0;
                for (int j=0;j<selected_maks_values.size();j++){
                    String [] arr_curr=selected_maks_values.get(j).getKey().split(";");
                    if(arr[2].equals(arr_curr[2])){
                        count++;
                    }
                }
                counter_for_max1_classes.put(arr[2],count);
                count=0;
            }
            int maxValueInMap=(Collections.max(counter_for_max1_classes.values()));
            String class_decided="";
            for (Map.Entry<String, Integer> entry : counter_for_max1_classes.entrySet()) {
                if (entry.getValue()==maxValueInMap) {
                    count++;
                    class_decided=entry.getKey();

                }
            }
            if(count==1){
                ApplicationOfAlgorithms.key=maxEntry.getKey();
                ApplicationOfAlgorithms.value=maxEntry.getValue();
            }
            else {
                //This outer loop below is for counting the number of same classes whose values are selected max2

                for (int i = 0; i < selected_2nd_maks_values.size(); i++) {

                    String[] arr = selected_2nd_maks_values.get(i).getKey().split(";");
                    count = 0;
                    for (int j = 0; j < selected_2nd_maks_values.size(); j++) {
                        String[] arr_curr = selected_2nd_maks_values.get(j).getKey().split(";");
                        if (arr[2].equals(arr_curr[2])) {
                            count++;
                        }
                    }
                    counter_for_max2_classes.put(arr[2], count);
                    count = 0;
                }
                //To sum first max values and second max values whose keys are the same for getting just 1 max value
                for (Map.Entry<String, Integer> entry1 : counter_for_max1_classes.entrySet()) {
                    for (Map.Entry<String, Integer> entry2 : counter_for_max2_classes.entrySet()) {
                        if (entry1.getKey().equals(entry2.getKey())) {
                            counter_for_max1_classes.replace(entry1.getKey(), entry1.getValue(), entry1.getValue() + entry2.getValue());
                        }
                    }
                }
                maxValueInMap=(Collections.max(counter_for_max1_classes.values()));
                for (Map.Entry<String, Integer> entry : counter_for_max1_classes.entrySet()) {
                    if (entry.getValue()==maxValueInMap) {
                        class_decided=entry.getKey();
                        ApplicationOfAlgorithms.key=key_rec[0]+";"+"MULTIPLE"+";"+class_decided;
                        ApplicationOfAlgorithms.value=1; // eðer ikinci en büyüklere bakýlýyorsa maksimum bulunan deðeri direk 1 atýyoruz çünkü önemli olan sýnýf.
                        break;
                    }
                }
            }
            //BURDA COUNTERMAX1 VE MAX2 YI BIRLESTIRIP TEK HASHMAP YAPMAYI DENE.


        }
        catch (NullPointerException e){
            //System.out.println("keycurr 0:" +key_current_2[0]+" keycur 1:" + key_current_2[1]);
        }

    }
}
