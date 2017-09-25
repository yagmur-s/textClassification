import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Yagmur on 15.03.2017.
 */
public class MTSimV4 extends Algorithms{
    public static HashMap<String,Double> mtsimv4_maps=new HashMap<>();
    static double distance;
    static double sim;
    static double nbOfcValue=0;
    public static ArrayList<Double> cValues=new ArrayList<>();
    @Override
    public double executeAlgorithm(ArrayList<Double> rec1, ArrayList<Double> rec2) {
        double distance=calculateDistance(rec1,rec2);
        double sim=calculate_similarity(distance,rec1.size());
        return sim;
    }

    public double calculate_similarity(double distance,int nbOfAttr) {
        double sim=0.0;
        sim=0.5 * ((nbOfcValue/nbOfAttr)+(nbOfcValue/(nbOfcValue+distance)));
        return sim;
    }

    public static double calculateDistance(ArrayList<Double> rec1,ArrayList<Double> rec2){
        double distance=0;nbOfcValue=0;
        for (int i=0;i<rec1.size();i++){
            double temp=rec1.get(i)-rec2.get(i);
            distance=Math.abs(temp)+distance;
            if(rec1.get(i)>0 && rec2.get(i)>0)
                nbOfcValue++;
        }
        return distance;
    }

}
