/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compare.comparisonMethods;

import compare.ArffFileReader;
import compare.ArffFileWriter;
import com.jmatio.io.MatFileWriter;
import com.jmatio.types.MLDouble;
import java.io.IOException;
import java.util.ArrayList;
import weka.core.Instances;

/**
 *
 * @author claudia
 */
public class RunKModes100Times {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ArffFileReader af = new ArffFileReader();
        //Instances data = af.readFile("mushroomEd.arff");
        //
        //Instances data = af.readFile("hdNewCategorical.arff");
        Instances data = af.readFile("rex_6cl.arff");
        Instances dataWMissing = new Instances(data);
        dataWMissing.delete();
        for (int i = 0; i < data.numInstances(); i++) {
            boolean missing = false;
            for (int j = 0; j < data.numAttributes(); j++) {
                if (data.instance(i).isMissing(j)) {
                    missing = true;
                }

            }
            if (!missing) {
                dataWMissing.add(data.instance(i));
            }
        }
        data = new Instances(dataWMissing);
        int numRun = 10;
        double averageRuntime = 0.0;
        double averagefMeasure = 0.0;
        double[][] runtime = new double[numRun][1];
        double[][] fMeasure = new double[numRun][1];
        double[][] classIds = new double[numRun][data.numInstances()];
        double[][] clusterIds = new double[numRun][data.numInstances()];
        for (int i = 0; i < numRun; i++) {
            Instances inst = new Instances(data);
            KModes km = new KModes(inst);
            runtime[i][0] = km.runtime;
            averageRuntime += km.runtime;
            EvaluationUtils e = new EvaluationUtils(km.classIndex, km.clusterIDIndex, km.data);
            fMeasure[i][0] = e.averageFMeasure();
            averagefMeasure += fMeasure[i][0];
            e.classIdsClusterIds();
            classIds[i] = e.classIds;
            clusterIds[i] = e.clusterIds;
            System.out.println("run: " + i + " F: " + fMeasure[i][0] + " time: " + runtime[i][0] + " ms.");
            ArffFileWriter aw = new ArffFileWriter();
            String id = new Integer(i).toString();
            aw.saveFile("res_" + id + ".arff", km.data);
        }
        averageRuntime = averageRuntime / (double) numRun;
        averagefMeasure = averagefMeasure / (double) numRun;
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
        MatFileWriter mw = new MatFileWriter();
        try {
            mw.write("ev_mult_kmodes.mat", ll);
        } catch (IOException ex) {
        }

    }
}
