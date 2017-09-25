import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class is for creating a report of analysis result
 * Created by Yaðmur on 13.5.2016.
 */
public class CreateReport {
    public static LinkedHashMap<String, String> original_class = new LinkedHashMap<>();
    public static HashMap<String, String> estimated_class_pearson = new HashMap<>();
    public static HashMap<String, String> estimated_class_cosine = new HashMap<>();

    public static String createResultString(String record, String estimated_rec, HashMap<String,Double> results) {
            String line = "";
            String[] rec = record.split(";");
            line = rec[0];
            line = line + ";" + rec[rec.length-1];
            String best_result_rec = "",best_result_assigned_class="";
            double best_value = 0.0;
            for (Map.Entry<String, Double> entry : results.entrySet()) {
                String[] current_res = entry.getKey().split(",");
                if (current_res[0].equals(rec[0])) {
                    if(current_res.length==2){
                        best_result_rec="MULTIPLE";
                    }
                    else{
                        best_result_rec = current_res[1];
                    }
                    best_result_assigned_class=current_res[current_res.length-1];
                    best_value = entry.getValue();
                    break;
                }
            }
            /*
            for (Map.Entry<String, Double> entry : results2.entrySet()) {
                String[] current_pearson = entry.getKey().split(";");
                if (current_pearson[0].equals(rec[0])) {
                    if(current_pearson.length==2){
                        best_result_pearson="MULTIPLE";
                    }
                    else{
                        best_result_pearson = current_pearson[1];
                    }

                    best_pearson = entry.getValue();
                    break;
                }
            }*/
            line = line + ";" +best_result_rec+";" + best_value + ";"+best_result_assigned_class;
            line.replaceAll(" ", "");
            //writer.write(line);
            //writer.write(System.getProperty("line.separator"));
            //writer.close();
        return line;
    }

    public static void indicateOriginalClass(String record,ArrayList<String> test) {
            int count = 0;
            String task = "task";
            //for (int i=0;i<rand_records.size();i++){
            String[] rand = record.split(";");
            task = "task";
            for (int i=0;i<test.size();i++) {
                String[] task_arr = test.get(i).split(";");
                    if ((task_arr[0]).equals(rand[0])) {
                        original_class.put((rand[0]), task_arr[task_arr.length-1]);
                        break;
                    }
            }
    }

    public static void indicateEstimatedClass(ArrayList<String> rand_records, String indicator) {
        try {

            int count = 0;
            String task = "task";
            for (int i = 0; i < rand_records.size(); i++) {
                BufferedReader br = new BufferedReader(new FileReader("tf-idf-norm1-final-delimited.csv"));
                String[] rand = rand_records.get(i).split(",");
                task = "task";
                count = 0;
                task = br.readLine();
                System.out.println(rand_records.get(i));
                while (task != null && !rand.equals(null)) {
                    task = br.readLine();
                    if (task != null) {
                        String[] task_arr = task.split(";");
                        if ((task_arr[0]).equals(rand[1])) {

                            if (indicator.equals("pearson")) {
                                estimated_class_pearson.put((rand[1]), task_arr[task_arr.length-1]);
                                break;
                            } else {
                                estimated_class_cosine.put((rand[1]), task_arr[task_arr.length-1]);
                                break;
                            }
                        }
                    }
                }
                br.close();

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
