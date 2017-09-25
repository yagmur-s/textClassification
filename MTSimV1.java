import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dream on 18.06.2016.
 */
public class MTSimV1 extends Algorithms{
    static double distance;
    static double sim;
    @Override
    public double executeAlgorithm(ArrayList<Double> rec1, ArrayList<Double> rec2) {
        calculateDistance(rec1,rec2);
        sim=calculate_similarity(distance);
        return sim;
    }

    public static void calculateDistance(ArrayList<Double> rec1, ArrayList<Double> rec2){
        double sum=0,sub=0;
        for (int i=0;i<rec1.size();i++){
            double temp=rec1.get(i)-rec2.get(i);
            sum=Math.abs(temp);
            sub=(rec1.get(i)+rec2.get(i))/2;
            if(sub==0.0){
                distance=0+distance;
                continue;
            }
            distance=sum/sub+distance;
        }
        if(rec1.size()>5){
            distance=distance/rec1.size(); //normalized distance
        }
    }
    public static double calculate_similarity(double distance){
        double powerOfe;
        powerOfe=Math.pow(Math.E,-distance);
        return 2*powerOfe/(1+powerOfe);
    }
}
