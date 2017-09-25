import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is for calculating cosine similarity between two sentences.
 * Created by Yaðmur on 8.5.2016.
 */
public class CosineSimilarity extends Algorithms {
public static HashMap<String,Double> cosine_maps=new HashMap<>();
    @Override
    public double executeAlgorithm(ArrayList<Double> rec1, ArrayList<Double> rec2) {
        double pay=0,payda=0;
        pay= calculatePAY(rec1,rec2);
        payda=calculatePAYDA(rec1,rec2);
        if(pay == 0 && payda==0){
            return 0;
        }
        else  return pay/payda;
    }
    public double calculatePAY(ArrayList<Double> rec1, ArrayList<Double> rec2){
        return calculateMulAndSum(rec1,rec2);
    }
    public double calculatePAYDA(ArrayList<Double> rec1, ArrayList<Double> rec2){
        double sumOfSQR1=calculateSquareToplama(rec1);
        double sumOfSQR2=calculateSquareToplama(rec2);
        return Math.sqrt(sumOfSQR1)*Math.sqrt(sumOfSQR2);
    }
    public double calculateMulAndSum(ArrayList<Double> rec1,ArrayList<Double> rec2){
        double result=0;
        for (int i=0;i<rec1.size();i++){
            result=result+(rec1.get(i)*rec2.get(i));
        }
        return result;
    }
    public double calculateSquareToplama(ArrayList<Double> rec){
        double result=0;
        for (int i=0;i<rec.size();i++){
            double x=rec.get(i)*rec.get(i);
            result=result+x;
        }
        return result;
    }
}
