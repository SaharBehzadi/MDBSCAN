/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compare.comparisonMethods.KMeansMixed;

import compare.ArffFileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import compare.comparisonMethods.EvaluationUtils;
import weka.core.Instances;

/**
 *
 * @author claudia
 */
public class RunKMMixed {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int numClusters = 2;
        try {
            // TODO code application logic here
            ArffFileReader af = new ArffFileReader();
            //Instances data = af.readFile("mushroomEd.arff");
            //
            Instances data = af.readFile("mixed.arff");
            Wrapper w = new Wrapper(data);
            w.writeInputFile();
            RanCluster r = new RanCluster();
            r.run(numClusters);
            dclusteringnew2 c = new dclusteringnew2();
            c.run();
            w.readOutputFileAndSetClusterIDs();
            EvaluationUtils e = new EvaluationUtils(w.data.numAttributes()-2, w.data.numAttributes()-1, w.data);
            e.outputQuality();
            double d = e.averageFMeasure();
            System.out.println("Fmeasure: " + d);
            e.precisionRecall();
            e.saveResultAsArff("resultkmmmixed.arff");

        } catch (IOException ex) {
            Logger.getLogger(RunKMMixed.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
