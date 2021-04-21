/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compare.comparisonMethods.KMeansMixed;
import compare.ArffFileReader;
import compare.ArffFileWriter;
import com.jmatio.io.MatFileWriter;
import com.jmatio.types.MLDouble;
import compare.comparisonMethods.EvaluationUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.core.Instances;


/**
 *
 * @author claudia
 */
public class RunKMMixed100Times {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ArffFileReader af = new ArffFileReader();
        //Instances data = af.readFile("mushroomEd.arff");
        //
        Instances data = af.readFile("/home/mahmoud/uni/BACHELOR/clicot/big_data/Airport/airports_data.arff");
//        Wrapper w = new Wrapper(new Instances(data));
//            w.writeInputFile();
        int numRun = 10;
        int numClusters = 24;
        double averageRuntime = 0.0;
        double averagefMeasure = 0.0;
        double[][] runtime = new double[numRun][1];
        double[][] fMeasure = new double[numRun][1];
        double[][] classIds = new double[numRun][data.numInstances()];
        double[][] clusterIds = new double[numRun][data.numInstances()];
        for (int i = 0; i < numRun; i++) {
            Wrapper w = new Wrapper(new Instances(data));
                  w.writeInputFile();
            RanCluster r = new RanCluster();
            try {
                r.run(numClusters);
            } catch (IOException ex) {
                Logger.getLogger(RunKMMixed100Times.class.getName()).log(Level.SEVERE, null, ex);
            }
            dclusteringnew2 c = new dclusteringnew2();
            try {
                c.run();
            } catch (IOException ex) {
                Logger.getLogger(RunKMMixed100Times.class.getName()).log(Level.SEVERE, null, ex);
            }
            w.readOutputFileAndSetClusterIDs();
            EvaluationUtils e = new EvaluationUtils(w.data.numAttributes()-2, w.data.numAttributes()-1, w.data);
            runtime[i][0] = r.runtime + c.runtime;
            averageRuntime += runtime[i][0];
           // fMeasure[i][0] = e.averageFMeasure();
            averagefMeasure += fMeasure[i][0];
            e.classIdsClusterIds();
            classIds[i] = e.classIds;
            clusterIds[i] = e.clusterIds;
            System.out.println("run: " + i + " F: " + fMeasure[i][0] + " time: " + runtime[i][0] + " ms.");
             ArffFileWriter aw = new ArffFileWriter();
            String id = new Integer(i).toString();
            if (i == 9)
                aw.saveFile("/home/mahmoud/uni/BACHELOR/compersion/res_" + id + ".arff", w.data);
        }
        averageRuntime = averageRuntime/(double)numRun;
        averagefMeasure = averagefMeasure/(double)numRun;
        System.out.println("averageRuntime: " + averageRuntime + " averageFMeasure " + averagefMeasure);
        //write results to matlab
        MLDouble t = new MLDouble("runtime", runtime);
        MLDouble t1 = new MLDouble("fMeasure", fMeasure);
        MLDouble t2 = new MLDouble("classLabels", classIds);
        MLDouble t3 = new MLDouble("clusterIds", clusterIds);
        ArrayList ll = new ArrayList();
        ll.add(t);
        ll.add(t1);
        ll.add(t2);
        ll.add(t3);
       /* MatFileWriter mw = new MatFileWriter();
        try {
            mw.write("/home/mahmoud/uni/BACHELOR/compersion/kkmeans.mat", ll);
        } catch (IOException ex) {
        }
*/
    }
    

}
