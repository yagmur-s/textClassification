import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Yaðmur on 9.5.2016.
 */
public class RandomSample {
    public FirstStage obj= new FirstStage();
    public ArrayList<String> train_rec_tf=new ArrayList<>();
    public ArrayList<String> train_rec_tf_idf=new ArrayList<>();

    public static String file_name_tf="tf-yeni-2.csv";
    public static String file_name_tf_idf="tf-idf-yeni-2.csv";
    public static String line_rand_tf, line_rand_tf_idf,line_train_tf,line_train_tf_idf;
    public void selectRandSample(ArrayList<String> rand_records_tf_idf,ArrayList<String> rand_records_tf,ArrayList<String> train_rec_tf_idf,ArrayList<String> train_rec_tf ) throws IOException {
        int b=0;
        boolean check=true;
        /** Random choose_test for test **/
        while(b<500) {
            Random rand=new Random();
            int randomInt=rand.nextInt(1136)+1;
            choose_test(randomInt);
            if(!(rand_records_tf_idf.contains(line_rand_tf_idf)) && !(rand_records_tf.contains(line_rand_tf))){
                rand_records_tf_idf.add(line_rand_tf_idf);
                rand_records_tf.add(line_rand_tf);
                b++;
            }

        }
        int a=0;
        /** Random choose_test for train **/
        while(a<50) {
            Random rand=new Random();
            int randomInt=rand.nextInt(1136)+1;
            choose_train(randomInt);
            String [] s_arr=line_train_tf_idf.split(";");
            if(!(rand_records_tf_idf.contains(line_train_tf_idf)) && !(train_rec_tf_idf.contains(line_train_tf_idf))){
                check=true;
                train_rec_tf_idf.add(line_train_tf_idf);
                train_rec_tf.add(line_train_tf);
                a++;
                }
         }

    }

    public static void choose_test(int randomInt) throws IOException
    {
        BufferedReader reader1=new BufferedReader(new FileReader(file_name_tf));
        BufferedReader reader2=new BufferedReader(new FileReader(file_name_tf_idf));
        line_rand_tf = reader1.readLine();
        line_rand_tf_idf =reader2.readLine();
        String line=" ";
        for(int i=0;i<randomInt;i++){
            line=reader1.readLine();
         }
        line_rand_tf=line;
        line=" ";
        String[] tf_arr=line_rand_tf.split(";");
        while(line_rand_tf_idf!=null && line!=null){
            line=reader2.readLine();
            if(line!=null) {
                String[] line_arr = line.split(";");
                if (line_arr[0].equals(tf_arr[0])) {
                    line_rand_tf_idf = line;
                    break;
                }
            }
        }
        reader1.close();
        reader2.close();
    }
    public static void choose_train(int randomInt) throws IOException
    {
        BufferedReader reader1=new BufferedReader(new FileReader(file_name_tf));
        BufferedReader reader2=new BufferedReader(new FileReader(file_name_tf_idf));
        line_train_tf = reader1.readLine();
        line_train_tf_idf =reader2.readLine();
        String line=" ";
        for(int i=0;i<randomInt;i++){
            line=reader1.readLine();

        }
        line_train_tf=line;
        String[] tf_arr=line_train_tf.split(";");
        line=" ";
        while(line_train_tf_idf!=null && line!=null){
            line=reader2.readLine();
            if(line!=null) {
                String[] line_arr = line.split(";");
                if (line_arr[0].equals(tf_arr[0])) {
                    line_train_tf_idf = line;
                    break;
                }
            }
        }
        reader1.close();
        reader2.close();
    }
    public static String choose_tf() throws IOException {
        String result = null;
        BufferedReader reader=new BufferedReader(new FileReader(file_name_tf));
        String line = null;
        try {
            line = reader.readLine();
            Random rand=new Random();
            int randomInt=rand.nextInt(1136);
            for (int i = 0; i < randomInt + 1; i++) {
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
         reader.close();
        return line;
    }
}
