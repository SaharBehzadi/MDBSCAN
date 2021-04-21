/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compare.comparisonMethods.KMeansMixed;

import compare.ArffFileReader;
import compare.ArffFileWriter;
import compare.comparisonMethods.EvaluationUtils;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.AddCluster;
import com.jmatio.io.MatFileWriter;
import com.jmatio.types.MLDouble;
import java.io.IOException;
import java.util.ArrayList;
import weka.core.Attribute;
import weka.filters.unsupervised.attribute.Normalize;

/**
 *
 * @author claudia
 */
public class RunKMeans100Times {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        boolean normalize = false;
        ArffFileReader af = new ArffFileReader();
        //Instances data = af.readFile("mushroomEd.arff");
        //
        //Instances data = af.readFile("hdNewNumeric.arff");
        Instances data = af.readFile("rex_6cl.arff");
//        Wrapper w = new Wrapper(new Instances(data));
//            w.writeInputFile();
        //remove instances with missing values
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
        if (normalize) {
            Normalize n = new Normalize();
            try {
                n.setInputFormat(data);
                data = Filter.useFilter(data, n);
            } catch (Exception ex) {
                //Logger.getLogger(TopDownSplit.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        DataObject[] dd = new DataObject[data.numInstances()];
        int dim = data.numAttributes() - 1; //last attribute is class label
        for (int i = 0; i < data.numInstances(); i++) {
            double[] coord = new double[dim];
            for (int j = 0; j < dim; j++) {
                coord[j] = data.instance(i).value(j);
            }
            dd[i] = new DataObject(coord, i);
        }
        DB db = new DB(dd);
        int numRun = 100;
        int numClusters = 7;
        double averageRuntime = 0.0;
        double averagefMeasure = 0.0;
        double[][] runtime = new double[numRun][1];
        double[][] fMeasure = new double[numRun][1];
        double[][] classIds = new double[numRun][data.numInstances()];
        double[][] clusterIds = new double[numRun][data.numInstances()];
        Random r = new Random();
        for (int i = 0; i < numRun; i++) {
            long startTime = System.currentTimeMillis();
            KMeans km = new KMeans(numClusters, db);
            DataObject[] clustered = km.run();
            Instances ev = new Instances(data);
            Attribute clusterid = new Attribute("clusterid");
            ev.insertAttributeAt(clusterid, ev.numAttributes());
            for (int j = 0; j < ev.numInstances(); j++) {
                ev.instance(j).setValue(ev.numAttributes()-1, clustered[j].clusterID);
            }
            long endTime = System.currentTimeMillis();
            EvaluationUtils e = new EvaluationUtils(ev.numAttributes() - 2, ev.numAttributes() - 1, ev);
            runtime[i][0] = (double) endTime - startTime;
            averageRuntime += runtime[i][0];
            fMeasure[i][0] = e.averageFMeasure();
            averagefMeasure += fMeasure[i][0];
            e.classIdsClusterIds();
            classIds[i] = e.classIds;
            clusterIds[i] = e.clusterIds;
            System.out.println("run: " + i + " F: " + fMeasure[i][0] + " time: " + runtime[i][0] + " ms.");
            //save clustering result
            ArffFileWriter aw = new ArffFileWriter();
            String id = new Integer(i).toString();
            aw.saveFile("res_" + id + ".arff", ev);
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
            mw.write("ev_mult_km_debug.mat", ll);
        } catch (IOException ex) {
        }

    }
}
