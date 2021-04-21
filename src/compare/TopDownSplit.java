/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compare;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

/**
 *
 * @author claudia
 */
public class TopDownSplit {

    Instances data;
    Vector<Cluster> currentClusters; //current clusters
    Vector<Cluster> bestClusters;
    Vector<Cluster> lastLocalMinClusters;
    int[] lastLocalMinIDs;
    boolean[] continueSplitting; //if cluster is already enough
    int[] currentID; //current cluster IDs;
    int[] bestID;
    double bestCost;
    int numObj;
    int dim;
    int maxClusterID;      //Max ID of the clusters (not noise!)
    int numTry;
    int clusterIDIndex;
    int objectIDIndex;
    static double ln10 = Math.log(10);
    static double ln2 = Math.log(2);
    static int minNumObj = 10; //minimum number of objects in a cluster
    boolean verbose = true;

    //numTry: how often to continue splitting if no improvement
    public TopDownSplit(Instances data, int numTry) {
        //insert attribute for cluster-id as second attribute. Expects classlabel to be the last attribute of input data
        //Normalize
        Normalize n = new Normalize();
        try {
            n.setInputFormat(data);
            data = Filter.useFilter(data, n);
        } catch (Exception ex) {
            Logger.getLogger(TopDownSplit.class.getName()).log(Level.SEVERE, null, ex);
        }
        Attribute clusterID = new Attribute("clusterID");
        data.insertAttributeAt(clusterID, data.numAttributes());
        for (int i = 0; i < data.numInstances(); i++) {
            data.instance(i).setValue(data.numAttributes() - 1, -1);
        }
        //insert attribute for object-id
        Attribute objectID = new Attribute("objectID");
        data.insertAttributeAt(objectID, data.numAttributes());
        for (int i = 0; i < data.numInstances(); i++) {
            data.instance(i).setValue(data.numAttributes() - 1, i);
        }
        this.data = data;
        numObj = data.numInstances();
        this.numTry = numTry;
        dim = data.numAttributes() - 2;
        maxClusterID = 0;
        bestCost = 0.0;
        currentClusters = new Vector<Cluster>();
        currentID = new int[numObj];
        bestClusters = new Vector<Cluster>();
        bestID = new int[numObj];
        lastLocalMinClusters = new Vector<Cluster>();
        lastLocalMinIDs = new int[numObj];
        clusterIDIndex = data.numAttributes() - 2;
        objectIDIndex = data.numAttributes() - 1;
    }

    //split clusters level-wise. each split resulting in an improvement is accepted. If no improvement: split that cluster which has the least cost increase. Try continue splitting for numTry times.
    public void run() {
        //determine baseline: cost without splitting
        KMeans km = new KMeans(new Instances(data), 1);
        Instances clustered = km.run();
        Cluster[] cl = km.getClusters();
        for (int i = 0; i < numObj; i++) {
            data.instance(i).setValue(clusterIDIndex, cl[0].ID);
        }
        currentClusters.add(cl[0]);
        setCurrentID(km.getIDs());
        bestCost = currentCost();
        bestClusters.add(cl[0]);
        for (int i = 0; i < numObj; i++) {
            bestID[i] = currentID[i];
        }
        boolean finished = false;
        int numSplit = 0;
        if (verbose) {
            System.out.println("numSplit: " + bestClusters.size() + " clusters. MDL:  " + bestCost());
        }
        while (!finished) {
            finished = splitClusters();
            numSplit++;
//            //DEBUG
//            if (numSplit == 41) {
//                System.out.println("m");
//            }
            //DEBUG
            if (verbose) {
                System.out.println("numSplit: " + numSplit + " currentClusters: " + currentClusters.size() + " currentMDL: " + currentCost() + " bestClusters: " + bestClusters.size() + " BestMDL:  " + bestCost());
            }

        }
        //relable ids for better presentation
        HashMap<Integer, Integer> rl = new HashMap<Integer, Integer>();
        for (int i = 0; i < bestClusters.size(); i++) {
            rl.put(bestClusters.elementAt(i).ID, i);
            bestClusters.elementAt(i).ID = i;
        }
        //set cluster id
        for (int i = 0; i < numObj; i++) {
            data.instance(i).setValue(clusterIDIndex, rl.get(bestID[i]));
        }
        if (verbose) {
            System.out.println(numSplit + " split" + " MDL " + bestCost());
//            VisuInfo vs = new VisuInfo(data, "TopDownSplit");
//            vs.setSize(600, 600);
//            vs.setLocation(200, 0);
//            vs.setVisible(true);

        }
        ArffFileWriter af = new ArffFileWriter();
        af.saveFile("/home/mahmoud/uni/BACHELOR/compersion/resultTopDown.arff", data);

    }

    private void setCurrentID(int[] ids) {
        for (int i = 0; i < numObj; i++) {
            currentID[i] = ids[i];
        }
    }

    //Performs split with the maximum saved cost and returns if splitting is finished.
    private boolean splitClusters() {
        double[] savedCost = new double[currentClusters.size()];
        double[] costSplit = new double[currentClusters.size()];
        for (int i = 0; i < currentClusters.size(); i++) {
            savedCost[i] = -Double.MAX_VALUE;
        }
        Cluster[] clustersUnsplit = new Cluster[currentClusters.size()];
        for (int i = 0; i < clustersUnsplit.length; i++) {
            clustersUnsplit[i] = new Cluster(currentClusters.elementAt(i));
        }
        double maxSavedCost = -Double.MAX_VALUE;
        int maxIndex = -1;
        Cluster[] bestSplit = new Cluster[2];
        int[] bestSplitIDs = new int[1];
        int[] objectNumber = new int[1];
        int[] bestSplitObjNumber = new int[1];
//        Vector<Cluster> lastLocalMinClusters = new Vector<Cluster>();
//        int[] lastLocalMinIDs = new int[numObj];

        for (int i = 0; i < clustersUnsplit.length; i++) {
            if (clustersUnsplit[i].splitTimes <= numTry && clustersUnsplit[i].numObj > minNumObj) {
                Instances obj = getObjects(clustersUnsplit[i].ID);
                checkIDs();
                objectNumber = new int[obj.numInstances()];
                for (int j = 0; j < objectNumber.length; j++) {
                    objectNumber[j] = (int) obj.instance(j).value(objectIDIndex);
                }
                //run KMeans with one initialization only
                KMeans km = new KMeans(obj, 2);
                Instances clustered = km.run();
                Cluster[] cl = km.getClusters();
                //run KMeans with different random initializations


                //cluster is to small; should not be split further
                if (cl[0].numObj < minNumObj || cl[1].numObj < minNumObj) {
                    clustersUnsplit[i].splitTimes = numTry + 1;

                } //check for cost improvement
                else {
                    double costUnsplit = currentCost();
                    maxClusterID++;
                    cl[0].ID = maxClusterID;
                    maxClusterID++;
                    cl[1].ID = maxClusterID;
                    Vector<Cluster> splitClusters = new Vector<Cluster>();
                    splitClusters.add(cl[0]);
                    splitClusters.add(cl[1]);
                    for (int j = 0; j < currentClusters.size(); j++) {
                        if (currentClusters.elementAt(j).ID != clustersUnsplit[i].ID) {
                            splitClusters.add(currentClusters.elementAt(j));
                        }
                    }
//                    currentClusters.remove(currentClusters.elementAt(i));
//                    currentClusters.add(cl[0]);
//                    currentClusters.add(cl[1]);
                    currentClusters = splitClusters;
                    costSplit[i] = currentCost();
                    savedCost[i] = costUnsplit - costSplit[i];
                    if (savedCost[i] > maxSavedCost) {
                        maxSavedCost = savedCost[i];
                        maxIndex = i;
                        bestSplit = cl;
                        bestSplitIDs = km.getIDs();
                        bestSplitObjNumber = objectNumber;
                    }
                    //restore currentClusters;
                    currentClusters = new Vector<Cluster>();
                    for (int j = 0; j < clustersUnsplit.length; j++) {
                        currentClusters.add(clustersUnsplit[j]);
                    }
                    maxClusterID = maxClusterID - 2;
                } //else
            }//if
        }//i
        //perform split; modify currentClusters and currentIDs
        if (maxIndex != -1) {
            maxClusterID++;
            bestSplit[0].ID = maxClusterID;
            maxClusterID++;
            bestSplit[1].ID = maxClusterID;
            maxClusterID++;
            for (int i = 0; i < bestSplitIDs.length; i++) {
                if (bestSplitIDs[i] == 0) {
                    currentID[bestSplitObjNumber[i]] = bestSplit[0].ID;
                } else {
                    currentID[bestSplitObjNumber[i]] = bestSplit[1].ID;
                }
            }
            Vector<Cluster> splitClusters = new Vector<Cluster>();
            splitClusters.add(bestSplit[0]);
            splitClusters.add(bestSplit[1]);
            for (int j = 0; j < clustersUnsplit.length; j++) {
                if (clustersUnsplit[j].ID != clustersUnsplit[maxIndex].ID) {
                    splitClusters.add(clustersUnsplit[j]);
                }
            }
            currentClusters = splitClusters;
//            currentClusters.remove(currentClusters.elementAt(maxIndex));//das stimmt nicht hier!!!
//            currentClusters.add(bestSplit[0]);
//            currentClusters.add(bestSplit[1]);

            currentClusters.elementAt(0).lastLocalMinID = clustersUnsplit[maxIndex].lastLocalMinID;
            currentClusters.elementAt(1).lastLocalMinID = clustersUnsplit[maxIndex].lastLocalMinID;
            //improvement. 
            if (maxSavedCost > 0) {
                //the new two clusters can be split further
                currentClusters.elementAt(0).splitTimes = 0;
                currentClusters.elementAt(1).splitTimes = 0;

                //store clusters and ids of local cost minimum

//                for (int i = 0; i < currentClusters.size(); i++) {
//                    currentClusters.elementAt(i).lastLocalMinID = currentClusters.elementAt(i).ID;
//                }


                //new global cost minimum
                if (costSplit[maxIndex] < bestCost) {
                    System.out.println("new global cost minimum.");
                    //last global cost minimum is local cost minimum
                    lastLocalMinClusters = new Vector<Cluster>();
                    for (int i = 0; i < bestClusters.size(); i++) {
                        lastLocalMinClusters.add(new Cluster(bestClusters.elementAt(i)));
                    }
                    for (int i = 0; i < bestID.length; i++) {
                        lastLocalMinIDs[i] = bestID[i];
                    }


                    //restore other branches to last local minimum. with exception of clustersUnsplit[maxIndex].lastLocalMinID
                    int splittedBranch = clustersUnsplit[maxIndex].lastLocalMinID;
                    //clusters
                    bestClusters = new Vector<Cluster>();
                    for (int i = 0; i < currentClusters.size(); i++) {
                        if (currentClusters.elementAt(i).lastLocalMinID == splittedBranch) {
                            bestClusters.add(currentClusters.elementAt(i));
                        }
                    }
                    for (int i = 0; i < lastLocalMinClusters.size(); i++) {
                        if (lastLocalMinClusters.elementAt(i).ID != splittedBranch) {
                            bestClusters.add(lastLocalMinClusters.elementAt(i));
                        }
                    }
                    currentClusters = bestClusters;
//                    currentClusters = new Vector<Cluster>();
//                    for(int i = 0; i < bestClusters.size(); i++)
//                        currentClusters.addElement(new Cluster(bestClusters.elementAt(i)));
                    //ids
                    for (int i = 0; i < numObj; i++) {
                        if (lastLocalMinIDs[i] == splittedBranch) {
                            bestID[i] = currentID[i];
                        } else {
                            bestID[i] = lastLocalMinIDs[i];
                        }
                    }
                    for (int i = 0; i < numObj; i++) {
                        currentID[i] = bestID[i];
                    }

                    for (int i = 0; i < currentClusters.size(); i++) {
                        currentClusters.elementAt(i).lastLocalMinID = currentClusters.elementAt(i).ID;
                    }

//                    currentClusters.elementAt(0).lastLocalMinID = currentClusters.elementAt(0).ID;
//                    currentClusters.elementAt(1).lastLocalMinID = currentClusters.elementAt(1).ID;


                    for (int i = 0; i < bestClusters.size(); i++) {
                        bestClusters.elementAt(i).lastLocalMinID = bestClusters.elementAt(i).ID;
                    }

//                    bestClusters = currentClusters;
//                    for (int i = 0; i < bestID.length; i++) {
//                        bestID[i] = currentID[i];
//                    }
                    //bestCost = costSplit[maxIndex];
                    bestCost = currentCost();

                }
            } else {
//jeder cluster wird noch numTry mal weitergesplittet nachdem nicht erfolgreicher Split erkannt
                currentClusters.elementAt(0).splitTimes = clustersUnsplit[maxIndex].splitTimes + 1;
                currentClusters.elementAt(1).splitTimes = clustersUnsplit[maxIndex].splitTimes + 1;



            //oder: es werden noch numTry erfolglose Splits insgesamt durchgeführt.
//                for(int i = 0; i < currentClusters.size(); i++)
//                    currentClusters.elementAt(i).splitTimes++;


            }
            //set ids to currentids
            for (int i = 0; i < numObj; i++) {
                data.instance(i).setValue(clusterIDIndex, currentID[i]);
            }
            //DEBUG

            System.out.println("splitting cluster " + clustersUnsplit[maxIndex].ID + " improvement: " + maxSavedCost);
            System.out.println("old clusters: ");
            for (int j = 0; j < clustersUnsplit.length; j++) {
                System.out.println("ID:  " + clustersUnsplit[j].ID + " numObj: " + clustersUnsplit[j].numObj + " splitTimes: " + clustersUnsplit[j].splitTimes + " lastLocalMinID: " + clustersUnsplit[j].lastLocalMinID);
            }
            System.out.println("new clusters: ");
            for (int j = 0; j < currentClusters.size(); j++) {
                System.out.println("ID:  " + currentClusters.elementAt(j).ID + " numObj: " + currentClusters.elementAt(j).numObj + " splitTimes: " + currentClusters.elementAt(j).splitTimes + " lastLocalMinID: " + currentClusters.elementAt(j).lastLocalMinID);
            }
            System.out.println();
            //DEBUG

            return false;
        } else {
            return true;
        }




    }

    private void checkIDs() {
        Vector<Integer> ids = new Vector<Integer>();
        for (int i = 0; i < data.numInstances(); i++) {
            if (!ids.contains((int) data.instance(i).value(clusterIDIndex))) {
                ids.add((int) data.instance(i).value(clusterIDIndex));
            }
        }
        System.out.println(ids.size() + " clusters in ids");
        for (int i = 0; i < ids.size(); i++) {
            int counter = 0;
            for (int j = 0; j < data.numInstances(); j++) {
                if (data.instance(j).value(clusterIDIndex) == ids.elementAt(i)) {
                    counter++;
                }
            }
            System.out.println("cluster " + ids.elementAt(i) + " numObj " + counter);
        }

    }

    private void writeTest(Instances inst) {
        ArffFileWriter af = new ArffFileWriter();
        af.saveFile("test.arff", inst);
    }

    private Instances getObjects(int clId) {
        Instances obj = new Instances(data);
        obj.delete();
        for (int i = 0; i < data.numInstances(); i++) {
            if ((int) data.instance(i).value(clusterIDIndex) == clId) {
                obj.add((Instance) data.instance(i).copy());
            }
        }
        return obj;
    }

    //determine cost of current clusters: add id-cost here
    private double bestCost() {
        double cost = 0.0;
        for (int i = 0; i < bestClusters.size(); i++) {
            cost += bestClusters.elementAt(i).codingCost;
            cost += bestClusters.elementAt(i).paramCost;
            double costID = bestClusters.elementAt(i).numObj * Math.log((double) numObj / (double) bestClusters.elementAt(i).numObj) / Math.log(2);
            bestClusters.elementAt(i).idCost = costID;
            cost += costID;
        }
        return cost;
    }

    //determine cost of current clusters: add id-cost here
    private double currentCost() {
        double cost = 0.0;
        for (int i = 0; i < currentClusters.size(); i++) {
            cost += currentClusters.elementAt(i).codingCost;
            cost += currentClusters.elementAt(i).paramCost;
            double costID = currentClusters.elementAt(i).numObj * Math.log((double) numObj / (double) currentClusters.elementAt(i).numObj) / Math.log(2);
            currentClusters.elementAt(i).idCost = costID;
            cost += costID;
        }
        return cost;
    }
}
