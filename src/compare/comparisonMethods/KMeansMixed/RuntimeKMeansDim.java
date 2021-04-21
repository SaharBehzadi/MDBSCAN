/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compare.comparisonMethods.KMeansMixed;

import compare.ArffFileReader;
import weka.core.Instances;
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
import weka.filters.unsupervised.attribute.Normalize;

/**
 *
 * @author claudia
 */
public class RuntimeKMeansDim {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int numClusters = 2;
        Random r = new Random();
        ArffFileReader af = new ArffFileReader();
        //Instances data = af.readFile("mushroomEd.arff");
        //
        //Instances data = af.readFile("hdNewNumeric.arff");
        Instances data = af.readFile("d10.arff");
        long startTime = System.currentTimeMillis();
        AddCluster ac = new AddCluster();
        SimpleKMeans km = new SimpleKMeans();
        try {
            km.setNumClusters(numClusters);
        } catch (Exception ex) {
            Logger.getLogger(RunKMeansWeka100Times.class.getName()).log(Level.SEVERE, null, ex);
        }
        km.setSeed(r.nextInt());
        ac.setClusterer(km);
        ac.setIgnoredAttributeIndices("last");
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
        double runtime = (double) endTime - startTime;
        System.out.println(runtime);

        data = af.readFile("d20.arff");
        startTime = System.currentTimeMillis();
        ac = new AddCluster();
        km = new SimpleKMeans();
        try {
            km.setNumClusters(numClusters);
        } catch (Exception ex) {
            Logger.getLogger(RunKMeansWeka100Times.class.getName()).log(Level.SEVERE, null, ex);
        }
        km.setSeed(r.nextInt());
        ac.setClusterer(km);
        ac.setIgnoredAttributeIndices("last");
        cl = new Instances(data);
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
        endTime = System.currentTimeMillis();
        runtime = (double) endTime - startTime;
        System.out.println(runtime);

        data = af.readFile("d30.arff");
        startTime = System.currentTimeMillis();
        ac = new AddCluster();
        km = new SimpleKMeans();
        try {
            km.setNumClusters(numClusters);
        } catch (Exception ex) {
            Logger.getLogger(RunKMeansWeka100Times.class.getName()).log(Level.SEVERE, null, ex);
        }
        km.setSeed(r.nextInt());
        ac.setClusterer(km);
        ac.setIgnoredAttributeIndices("last");
        cl = new Instances(data);
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
        endTime = System.currentTimeMillis();
        runtime = (double) endTime - startTime;
        System.out.println(runtime);

        data = af.readFile("d40.arff");
        startTime = System.currentTimeMillis();
        ac = new AddCluster();
        km = new SimpleKMeans();
        try {
            km.setNumClusters(numClusters);
        } catch (Exception ex) {
            Logger.getLogger(RunKMeansWeka100Times.class.getName()).log(Level.SEVERE, null, ex);
        }
        km.setSeed(r.nextInt());
        ac.setClusterer(km);
        ac.setIgnoredAttributeIndices("last");
        cl = new Instances(data);
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
        endTime = System.currentTimeMillis();
        runtime = (double) endTime - startTime;
        System.out.println(runtime);

        data = af.readFile("d50.arff");
        startTime = System.currentTimeMillis();
        ac = new AddCluster();
        km = new SimpleKMeans();
        try {
            km.setNumClusters(numClusters);
        } catch (Exception ex) {
            Logger.getLogger(RunKMeansWeka100Times.class.getName()).log(Level.SEVERE, null, ex);
        }
        km.setSeed(r.nextInt());
        ac.setClusterer(km);
        ac.setIgnoredAttributeIndices("last");
        cl = new Instances(data);
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
        endTime = System.currentTimeMillis();
        runtime = (double) endTime - startTime;
        System.out.println(runtime);


//        data = af.readFile("rt5000.arff");
//        startTime = System.currentTimeMillis();
//        ac = new AddCluster();
//        km = new SimpleKMeans();
//        try {
//            km.setNumClusters(numClusters);
//        } catch (Exception ex) {
//            Logger.getLogger(RunKMeansWeka100Times.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        km.setSeed(r.nextInt());
//        ac.setClusterer(km);
//        ac.setIgnoredAttributeIndices("last");
//        cl = new Instances(data);
//        try {
//            ac.setInputFormat(new Instances(cl));
//        } catch (Exception ex) {
//            Logger.getLogger(RunKMeansWeka100Times.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        try {
//            cl = Filter.useFilter(cl, ac);
//        } catch (Exception ex) {
//            Logger.getLogger(RunKMeansWeka100Times.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        endTime = System.currentTimeMillis();
//        runtime = (double) endTime - startTime;
//        System.out.println(runtime);
//
//        data = af.readFile("rt100000.arff");
//        startTime = System.currentTimeMillis();
//        ac = new AddCluster();
//        km = new SimpleKMeans();
//        try {
//            km.setNumClusters(numClusters);
//        } catch (Exception ex) {
//            Logger.getLogger(RunKMeansWeka100Times.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        km.setSeed(r.nextInt());
//        ac.setClusterer(km);
//        ac.setIgnoredAttributeIndices("last");
//        cl = new Instances(data);
//        try {
//            ac.setInputFormat(new Instances(cl));
//        } catch (Exception ex) {
//            Logger.getLogger(RunKMeansWeka100Times.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        try {
//            cl = Filter.useFilter(cl, ac);
//        } catch (Exception ex) {
//            Logger.getLogger(RunKMeansWeka100Times.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        endTime = System.currentTimeMillis();
//        runtime = (double) endTime - startTime;
//        System.out.println(runtime);

    }
}
