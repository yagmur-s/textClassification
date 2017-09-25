import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.math3.stat.*;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

/**
 * Created by Yaðmur on 8.5.2016.
 */
public class Pearson extends Algorithms {
    public static HashMap<String,Double> pearson_maps=new HashMap<>();

    @Override
    public double executeAlgorithm(ArrayList<Double> rec1,ArrayList<Double> rec2) {

        double cov=0.0,std1=0.0,std2=0.0;
        double[] rec1_arr=new double[rec1.size()];
        double [] rec2_arr=new double[rec2.size()];
        rec1_arr=rec1.stream().mapToDouble(Double::doubleValue).toArray();
        rec2_arr=rec2.stream().mapToDouble(Double::doubleValue).toArray();
        cov=Stats.covar(rec1_arr,rec2_arr);
        std1=Stats.sdev(rec1_arr);
        std2=Stats.sdev(rec2_arr);
        if(cov==0 || std1==0 || std2==0)
            return 0.0;
        return cov/(std1*std2);
    }
}
