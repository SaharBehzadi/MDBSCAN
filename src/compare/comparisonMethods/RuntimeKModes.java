/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compare.comparisonMethods;

import compare.ArffFileReader;
import java.util.Vector;
import weka.core.Instances;

/**
 *
 * @author claudia
 */
public class RuntimeKModes {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Vector<Double> rt = new Vector<Double>();
        ArffFileReader af = new ArffFileReader();
        Instances data = af.readFile("rt1000cat.arff");
        KModes km = new KModes(new Instances(data));
        rt.add(km.runtime);
        //System.out.println("1000 " + km.runtime);

        data = af.readFile("rt3000cat.arff");
        km = new KModes(new Instances(data));
        //System.out.println("3000 " + km.runtime);
        rt.add(km.runtime);

        data = af.readFile("rt5000.arff");
        km = new KModes(new Instances(data));
        //System.out.println("5000 " + km.runtime);
        rt.add(km.runtime);

        data = af.readFile("rt7000.arff");
        km = new KModes(new Instances(data));
        // System.out.println("7000 " + km.runtime);
        rt.add(km.runtime);

        data = af.readFile("rt10000.arff");
        km = new KModes(new Instances(data));
        //System.out.println("10000 " + km.runtime);
        rt.add(km.runtime);


        data = af.readFile("rt50000.arff");
        km = new KModes(new Instances(data));
        //System.out.println("50000 " + km.runtime);
        rt.add(km.runtime);


        data = af.readFile("rt100000.arff");
        km = new KModes(new Instances(data));
        // System.out.println("100000 " + km.runtime);
        rt.add(km.runtime);

        System.out.println("runtimes:");
        for (int i = 0; i < rt.size(); i++) {
            System.out.println(rt.elementAt(i));
        }



    }
}
