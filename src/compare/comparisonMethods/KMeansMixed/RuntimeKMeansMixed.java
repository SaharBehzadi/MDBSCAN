/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compare.comparisonMethods.KMeansMixed;

import compare.ArffFileReader;
import com.jmatio.io.MatFileWriter;
import com.jmatio.types.MLDouble;
import compare.comparisonMethods.EvaluationUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.core.Instances;

/**
 *
 * @author claudia
 */
public class RuntimeKMeansMixed {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int numClusters = 2;
        Vector<Double> rt = new Vector<Double>();
        ArffFileReader af = new ArffFileReader();
        Instances data = af.readFile("rt1000.arff");
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
        rt.add(r.runtime + c.runtime);



        data = af.readFile("rt3000.arff");
        w = new Wrapper(new Instances(data));
        w.writeInputFile();
        r = new RanCluster();
        try {
            r.run(numClusters);
        } catch (IOException ex) {
            Logger.getLogger(RunKMMixed100Times.class.getName()).log(Level.SEVERE, null, ex);
        }
        c = new dclusteringnew2();
        try {
            c.run();
        } catch (IOException ex) {
            Logger.getLogger(RunKMMixed100Times.class.getName()).log(Level.SEVERE, null, ex);
        }
        rt.add(r.runtime + c.runtime);



        data = af.readFile("rt5000.arff");
        w = new Wrapper(new Instances(data));
        w.writeInputFile();
        r = new RanCluster();
        try {
            r.run(numClusters);
        } catch (IOException ex) {
            Logger.getLogger(RunKMMixed100Times.class.getName()).log(Level.SEVERE, null, ex);
        }
        c = new dclusteringnew2();
        try {
            c.run();
        } catch (IOException ex) {
            Logger.getLogger(RunKMMixed100Times.class.getName()).log(Level.SEVERE, null, ex);
        }
        rt.add(r.runtime + c.runtime);


        data = af.readFile("rt7000.arff");
        w = new Wrapper(new Instances(data));
        w.writeInputFile();
        r = new RanCluster();
        try {
            r.run(numClusters);
        } catch (IOException ex) {
            Logger.getLogger(RunKMMixed100Times.class.getName()).log(Level.SEVERE, null, ex);
        }
        c = new dclusteringnew2();
        try {
            c.run();
        } catch (IOException ex) {
            Logger.getLogger(RunKMMixed100Times.class.getName()).log(Level.SEVERE, null, ex);
        }
        rt.add(r.runtime + c.runtime);

//        data = af.readFile("rt10000.arff");
//        w = new Wrapper(new Instances(data));
//        w.writeInputFile();
//        r = new RanCluster();
//        try {
//            r.run(numClusters);
//        } catch (IOException ex) {
//            Logger.getLogger(RunKMMixed100Times.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        c = new dclusteringnew2();
//        try {
//            c.run();
//        } catch (IOException ex) {
//            Logger.getLogger(RunKMMixed100Times.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        rt.add(r.runtime + c.runtime);


//        data = af.readFile("rt50000.arff");
//        w = new Wrapper(new Instances(data));
//        w.writeInputFile();
//        r = new RanCluster();
//        try {
//            r.run(numClusters);
//        } catch (IOException ex) {
//            Logger.getLogger(RunKMMixed100Times.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        c = new dclusteringnew2();
//        try {
//            c.run();
//        } catch (IOException ex) {
//            Logger.getLogger(RunKMMixed100Times.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        rt.add(r.runtime + c.runtime);
//
//        data = af.readFile("rt100000.arff");
//        w = new Wrapper(new Instances(data));
//        w.writeInputFile();
//        r = new RanCluster();
//        try {
//            r.run(numClusters);
//        } catch (IOException ex) {
//            Logger.getLogger(RunKMMixed100Times.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        c = new dclusteringnew2();
//        try {
//            c.run();
//        } catch (IOException ex) {
//            Logger.getLogger(RunKMMixed100Times.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        rt.add(r.runtime + c.runtime);
//
//
        System.out.println("runtimes:");
        for (int i = 0; i < rt.size(); i++) {
            System.out.println(rt.elementAt(i));
        }



    }
}
