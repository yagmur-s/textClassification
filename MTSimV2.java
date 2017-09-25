import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * This class is for calculating MTSim algorithm with Bray Curtis dissimilarity
 * Created by dream on 18.06.2016.
 */
public class MTSimV2 extends Algorithms{
    static double distance;
    static double sim;
    public static HashMap<String,Double> mtsimv2_maps=new HashMap<>();
    @Override
    public double executeAlgorithm(ArrayList<Double> rec1, ArrayList<Double> rec2) {
        calculateDistance(rec1,rec2);
        sim=MTSimV1.calculate_similarity(distance);
        return  sim;
    }
    public static void calculateDistance(ArrayList<Double> rec1, ArrayList<Double> rec2){
        double sub=0,sum1=0,sum2=0;
        for (int i=0;i<rec1.size();i++){
            double temp=rec1.get(i)-rec2.get(i);
            sub=Math.abs(temp)+sub;
            sum1=rec1.get(i)+sum1;
            sum2=rec2.get(i)+sum2;
        }
        if(sum1+sum2==0){
            distance=0.0;
        }
        else {
            distance = sub / (sum1 + sum2);
        }
    }

}
