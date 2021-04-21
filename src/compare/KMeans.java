/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compare;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.RemoveWithValues;

/**
 *
 * @author claudia
 */
public class KMeans {

    int k;
    Instances data;
    int numObj;
    Cluster[] cl;
    int clusterIDIndex;
    int iter;
    public static Random r = new Random (178);
    boolean verbose = false;

    public KMeans() {
    }

    public KMeans(Instances data, int k) {
        this.k = k;
        this.data = data;
        numObj = data.numInstances();
        clusterIDIndex = data.numAttributes() - 2;
        //r = new Random();
    }

    public KMeans(int k, Instances data, Cluster[] cl) {
        this.k = k;
        this.data = data;
        this.cl = cl;
        numObj = data.numInstances();
        clusterIDIndex = data.numAttributes() - 2;
    }

    public Cluster[] getClusters() {
        return this.cl;
    }

    public int[] getIDs() {
        int[] ids = new int[numObj];
        for (int i = 0; i < numObj; i++) {
            ids[i] = (int) data.instance(i).value(clusterIDIndex);
        }
        return ids;
    }

    public double checkInit() {
        initialize();
        assignPoints();
        update();
        double sumCost = 0.0;
        for (int i = 0; i < cl.length; i++) {
            sumCost += cl[i].codingCost + cl[i].paramCost;
        }
        if (verbose) {
            System.out.println(sumCost);
        }
        return sumCost;
    }

    public Instances terminate() {
        boolean clusterChanged = true;
        iter = 0;
        boolean isEmpty = false;
        while (clusterChanged && !isEmpty && iter < 100) {
            // try{
            clusterChanged = assignPoints();
            if (cl.length > 1 && (cl[0].numObj == 0 || cl[1].numObj == 0)) {
                isEmpty = true;
            }
            //}
//            catch(java.lang.ArrayIndexOutOfBoundsException e){
//                writeTest(data);
//                System.out.println("done");
//            }
            //System.out.println("after assign: obj 0 " + data.instance(0).value(clusterIDIndex));
            isEmpty = update();
            if (verbose) {
                for (int i = 0; i < k; i++) {
                    cl[i].printInfo();
                }
            }
            iter++;
            //System.out.println(iter);
            if (!isEmpty) {
                double overallCost = 0.0;
                for (int i = 0; i < k; i++) {
                    for (int j = 0; j < cl[i].sa.length; j++) {
                        if (cl[i].sa[j] != null) {
                            overallCost += cl[i].sa[j].dataCost * cl[i].numObj; // + cl[i].sa[j].paramCost;
                        }
                    }
                }

                if (verbose) {
                    //DEBUG
                    if(iter == 13)
                        System.out.println("m");
                    //DEBUG
                    System.out.println("iter: " + iter + " overallCost after update: " + overallCost);
                }
            }
        }

        // System.out.println("m");
        return data;
    }

    public Instances run() {
        initialize();
        //System.out.println("after init: obj 0 " + data.instance(0).value(clusterIDIndex));
        boolean clusterChanged = true;
        iter = 0;
        boolean isEmpty = false;
        while (clusterChanged && !isEmpty && iter < 100) {
            // try{
            clusterChanged = assignPoints();
            if (cl.length > 1 && (cl[0].numObj == 0 || cl[1].numObj == 0)) {
                isEmpty = true;
            }
            //}
//            catch(java.lang.ArrayIndexOutOfBoundsException e){
//                writeTest(data);
//                System.out.println("done");
//            }
            //System.out.println("after assign: obj 0 " + data.instance(0).value(clusterIDIndex));
            isEmpty = update();
            if (!isEmpty) {
                if (verbose) {
                    for (int i = 0; i < k; i++) {
                        cl[i].printInfo();
                    }
                }
                iter++;
                //System.out.println(iter);
                double overallCost = 0.0;
                for (int i = 0; i < k; i++) {
                    for (int j = 0; j < cl[i].sa.length; j++) {
                        if (cl[i].sa[j] != null) {
                            overallCost += cl[i].sa[j].dataCost * cl[i].numObj; // + cl[i].sa[j].paramCost;
                        }
                    }
                }
                if (verbose) {
                    System.out.println("iter: " + iter + " overallCost after update: " + overallCost);
                }
            }
        }
        // System.out.println("m");
        return data;
    }

    private void writeTest(Instances inst) {
        ArffFileWriter af = new ArffFileWriter();
        af.saveFile("test.arff", inst);
    }

    private boolean assignPoints() {
        double overallCost = 0.0; //check if overall cost for all points including parameter costs = sum clusters.datacost + paramcost
        boolean clusterChanged = false;
        for (int j = 0; j < numObj; j++) {
            double bestcost = Double.MAX_VALUE;
            int bestcluster = -1;
            for (int i = 0; i < k; i++) {
                double cost = -log2((double) cl[i].numObj / (double) numObj);
                for (int l = 0; l < cl[i].sa.length; l++) {
                    if (cl[i].sa[l] != null) {
                        cost += cl[i].sa[l].cost(data.instance(j), cl[i].numObj);
                    // cost += cl[i].sa[l].paramCost / (double) cl[i].numObj; //consider paramcosts
                    }
                }
                // Insert here maybe parameter cost, distributed over all cl[i].numObj objects
//                if (Double.isNaN(cost) || Double.isInfinite(cost) || Double.isInfinite(-cost)) {
//                    System.out.println("object: " + j + " cost: " + cost);
//                }
                if (cost < bestcost) {
                    bestcost = cost;
                    bestcluster = i;
                }
            }
            if (bestcluster == -1) {
                System.out.println(j);
            }
//            if(j == 0)
            overallCost += bestcost;
//            System.out.println("obj " + " j " + " cost cluster 0 " );
            if ((int) data.instance(j).value(clusterIDIndex) != cl[bestcluster].ID) {
                data.instance(j).setValue(clusterIDIndex, cl[bestcluster].ID);
                clusterChanged = true;
            }
        }
        if (verbose) {
            System.out.println("overallCost after assignment: " + overallCost);
        }
        return clusterChanged;
    }

    private double log2(double d) {
        return Math.log(d) / Math.log(2);
    }

    private void initialize() {
        //select k random representants
//        //DEBUG
//        if(numObj == 0)
//            System.out.println("m");
//        //DEBUG
        // Random r = new Random(); //Random 1 bei hd perfekt, 2 schlecht.
        int[] centerID = new int[k];
        boolean[] visited = new boolean[numObj];
        cl = new Cluster[k];
        for (int i = 0; i < centerID.length; i++) {
            int id = r.nextInt(numObj);
            while (visited[id]) {
                id = r.nextInt(numObj);
            }
            visited[id] = true;
            centerID[i] = id;
            cl[i] = new Cluster(data, centerID[i], i);

        }


    }

    //returns if empty
    private boolean update() {
        Instances[] clObj = new Instances[k];
        //System.out.println("in update 0 " + data.instance(0).value(clusterIDIndex));
        for (int i = 0; i < k; i++) {
            clObj[i] = new Instances(data);
            clObj[i].delete();
        }
        for (int i = 0; i < data.numInstances(); i++) {
            clObj[(int) data.instance(i).value(clusterIDIndex)].add((Instance) data.instance(i).copy());
        }
        for (int i = 0; i < k; i++) {
            if (cl[i].numObj > 0) {
                int currID = cl[i].ID;
                if (iter > 0) {
                    if (clObj[currID].numInstances() == 0) {
                        cl[i].numObj = 0;
                        return true;
                    } else {
                        cl[i] = new Cluster(clObj[currID], currID, cl[i]);
                    }
                } else {
                    if (clObj[currID].numInstances() == 0) {
                        cl[i].numObj = 0;
                        return true;
                    } else {
                        cl[i] = new Cluster(clObj[currID], currID);
                    }
                }
            }
        }
        return false;
    //System.out.print("m");
    }
}
