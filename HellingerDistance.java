import sun.awt.image.ImageWatched;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

/**
 * Created by Yagmur on 2.10.2016.
 */
public class HellingerDistance {
    public static LinkedHashMap<String,Double> hellinger_maps=new LinkedHashMap<>();
    private static double result=0.0;
    private static double CONST_VALUE=1/(Math.sqrt(2));
    public static double calculateHellingerDist(ArrayList<Double> rec1,ArrayList<Double> rec2){
        for (int i=0;i<rec1.size();i++){
            double sqrt1=Math.sqrt(rec1.get(i));
            double sqrt2=Math.sqrt(rec2.get(i));
            double diff=sqrt1-sqrt2;
            double sqr=diff*diff;
            result=result+sqr;
        }
        result=Math.sqrt(result);
        return CONST_VALUE*result;
    }
}
