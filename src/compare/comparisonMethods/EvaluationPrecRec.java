/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compare.comparisonMethods;

import compare.ArffFileReader;
import weka.core.Instances;

/**
 *
 * @author claudia
 */
public class EvaluationPrecRec {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ArffFileReader af = new ArffFileReader();
        //Instances data = af.readFile("mushroomEd.arff");
        //
       //Instances data = af.readFile("abalone.arff");
         //Instances data = af.readFile("hdNew.arff");
         Instances data = af.readFile("res_Ev.arff");
         EvaluationUtils u = new EvaluationUtils(34, 35, data);
         u.precisionRecall();


    }

}
