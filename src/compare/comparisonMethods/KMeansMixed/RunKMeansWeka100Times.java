/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compare.comparisonMethods.KMeansMixed;

import compare.ArffFileReader;
import compare.ArffFileWriter;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import compare.comparisonMethods.EvaluationUtils;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.AddCluster;
import com.jmatio.io.MatFileWriter;
import com.jmatio.types.MLDouble;
import java.io.IOException;
import java.util.ArrayList;
import weka.filters.unsupervised.attribute.Normalize;

/**
 *
 * @author claudia
 */
public class RunKMeansWeka100Times {

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
        int numRun = 10;
        int numClusters = 7;
        double averageRuntime = 0.0;
        double averagefMeasure = 0.0;
        double[][] runtime = new double[numRun][1];
        double[][] fMeasure = new double[numRun][1];
        double[][] classIds = new double[numRun][data.numInstances()];
        double[][] clusterIds = new double[numRun][data.numInstances()];
        Random r = new Random();
//        double[] groundTruth = new double[data.numInstances()];
//        for(int i = 0; i < groundTruth.)
        for (int i = 0; i < numRun; i++) {
            long startTime = System.currentTimeMillis();
            AddCluster ac = new AddCluster();
            ac.setIgnoredAttributeIndices("last");
            SimpleKMeans km = new SimpleKMeans();
            try {
                km.setNumClusters(numClusters);
            } catch (Exception ex) {
                Logger.getLogger(RunKMeansWeka100Times.class.getName()).log(Level.SEVERE, null, ex);
            }
            km.setSeed(r.nextInt(data.numInstances()));

//            //debug
//            System.out.println("seed " + km.getSeed());
//            //debug
            ac.setClusterer(km);


            Instances cl = new Instances(data);
            try {

                ac.setInputFormat(new Instances(cl));
            } catch (Exception ex) {
                Logger.getLogger(RunKMeansWeka100Times.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                cl = Filter.useFilter(cl, ac);
            } catch (Exception ex) {
                Logger.getLogger(RunKMeansWeka100Times.class.getName()).log(Level.SEVERE, null, ex);
            }
            long endTime = System.currentTimeMillis();
            ArffFileWriter aw = new ArffFileWriter();
            String id = new Integer(i).toString();
            aw.saveFile("res_" + id + ".arff", cl);

            EvaluationUtils e = new EvaluationUtils(cl.numAttributes() - 2, cl.numAttributes() - 1, cl);
            runtime[i][0] = (double) endTime - startTime;
            averageRuntime += runtime[i][0];
            fMeasure[i][0] = e.averageFMeasure();
            averagefMeasure += fMeasure[i][0];
            e.classIdsClusterIds();
            classIds[i] = e.classIds;
            clusterIds[i] = e.clusterIds;
            System.out.println("run: " + i + " F: " + fMeasure[i][0] + " time: " + runtime[i][0] + " ms.");

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
