/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compare;

import weka.core.Instances;

/**
 *
 * @author claudia
 */
public class MainChol {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ArffFileReader af = new ArffFileReader();
        //Instances data = af.readFile("mushroomEd.arff");
        //Instances data = af.readFile("heartWithoutMissing.arff");
        Instances data = af.readFile("/home/mahmoud/uni/BACHELOR/compersion/rex._ok.arff");
        Cluster c = new Cluster(data, 0);
        c.printInfo();
        //c.mutualInformation();
       // c.dendrogramMergeAttributes();

    }
}
