/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compare;

import com.jmatio.io.MatFileWriter;
import com.jmatio.types.MLDouble;
import java.io.IOException;
import java.util.ArrayList;
import weka.core.Instances;
import com.jmatio.io.MatFileWriter;
import com.jmatio.types.MLDouble;
import java.io.IOException;
import java.util.ArrayList;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

/**
 *
 * @author claudia
 */
public class Cluster {

    int ID;
    //Instances data;
    int numObj;
    int numAttributes;
//    double[] means; //mean value of Gaussians for numeric Attributes
//    double[] vars; //variances of Gaussians for numeric Attributes
//    double[] entropy; //entropy of categorical and numerical variables
//    int[] indexNumerical; //index of numerical attributes as in dataset.
//    int[] indexCategorical; //index of numerical attributes as in dataset.
//    ContingencyTable[] ct; //contingency tables for categorical attributes
//    double[][] mi;
    SuperAttribute[] sa;
    double codingCost;
    double paramCost;
    double idCost;
    int splitTimes;
    public int lastLocalMinID; //ID of father cluster of the last local cost minimum
    Cluster lson;
    Cluster rson;

//    this constructor is used by k-means. KMeans.update with iter = 0; normalization required
    public Cluster(Instances data, int ID) {
        // Instances inst = normalize(data);
        numObj = data.numInstances();
        numAttributes = data.numAttributes() - 3; //second last attribute: classlabel, last attribute clusterid (created on the fly)
        this.ID = ID;
        sa = dendrogramMergeAttributes(data);
        setCost();
    }

    private Instances normalize(Instances inst) {
        Normalize n = new Normalize();
        Instances instn = new Instances(inst);
        try {
            n.setInputFormat(inst);
            instn = Filter.useFilter(inst, n);
        } catch (Exception ex) {
            //  Logger.getLogger(TopDownSplit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return instn;
    }

    //used by TopDownSplit: no normalization required
    public Cluster(Cluster c) {
        this.ID = c.ID;
        this.numObj = c.numObj;
        this.numAttributes = c.numAttributes;
        this.splitTimes = c.splitTimes;
        this.sa = new SuperAttribute[c.sa.length];
        for (int i = 0; i < sa.length; i++) {
            if (sa[i] != null) {
                sa[i] = new SuperAttribute(c.sa[i], true);
            }
        }
        this.codingCost = c.codingCost;
        this.paramCost = c.paramCost;
        this.lastLocalMinID = c.lastLocalMinID;

    }

    private void setCost() {
        for (int i = 0; i < sa.length; i++) {
            if (sa[i] != null) {
                codingCost += sa[i].dataCost * numObj;
                paramCost += sa[i].paramCost;
            }
        }
    }

    public void printPattern(Instances data) {
        double[][] p = new double[numAttributes][numAttributes];
        double num_num = 1.0;
        double cat_cat = 2.0;
        double mixed = 3.0;
        for (int i = 0; i < sa.length; i++) {
            if (sa[i] != null) {
                if (sa[i].attId.size() > 1) {
                    for (int j = 0; j < sa[i].attId.size(); j++) {
                        for (int k = 0; k < sa[i].attId.size(); k++) {
                            if (data.attribute(sa[i].attId.elementAt(j)).isNumeric() && data.attribute(sa[i].attId.elementAt(k)).isNumeric()) {
                                p[sa[i].attId.elementAt(j)][sa[i].attId.elementAt(k)] = num_num;
                                p[sa[i].attId.elementAt(k)][sa[i].attId.elementAt(j)] = num_num;
                            }
                            if (data.attribute(sa[i].attId.elementAt(j)).isNominal() && data.attribute(sa[i].attId.elementAt(k)).isNominal()) {
                                p[sa[i].attId.elementAt(j)][sa[i].attId.elementAt(k)] = cat_cat;
                                p[sa[i].attId.elementAt(k)][sa[i].attId.elementAt(j)] = cat_cat;
                            }
                            if ((data.attribute(sa[i].attId.elementAt(j)).isNominal() && data.attribute(sa[i].attId.elementAt(k)).isNumeric()) || (data.attribute(sa[i].attId.elementAt(k)).isNominal() && data.attribute(sa[i].attId.elementAt(j)).isNumeric())) {
                                p[sa[i].attId.elementAt(j)][sa[i].attId.elementAt(k)] = mixed;
                                p[sa[i].attId.elementAt(k)][sa[i].attId.elementAt(j)] = mixed;
                            }


                        }
                    }

                }
            }
        }
        for (int i = 0; i < p.length; i++) {
            p[i][i] = 0.0;
        }
        MLDouble t = new MLDouble("pattern", p);
        ArrayList ll = new ArrayList();
        ll.add(t);
        MatFileWriter mw = new MatFileWriter();
        try {
            mw.write("pattern" + this.ID + ".mat", ll);
        } catch (IOException ex) {
        }
    }

    public void printSAInfo() {
        for (int i = 0; i < sa.length; i++) {
            if (sa[i] != null) {
            }
        }
    }

    //used in KMeans.update
    public Cluster(Instances data, int ID, Cluster c) {
        //Instances inst = normalize(data);
        double dataCostUpdated = 0.0;
        double paramCostUpdated = 0.0;
        numAttributes = c.numAttributes;
        this.ID = ID;
        numObj = data.numInstances();
        this.sa = new SuperAttribute[c.sa.length];
        boolean updatePossible = true;
        double costUpdated = 0.0;
        for (int i = 0; i < c.sa.length; i++) {
            if (c.sa[i] != null) {
                sa[i] = new SuperAttribute(c.sa[i]);
                if (!sa[i].updateParameters(data)) {
                    updatePossible = false;
                }
                dataCostUpdated += sa[i].dataCost;
                paramCostUpdated += sa[i].paramCost;
            }
        }
        if (updatePossible) {
            costUpdated = data.numInstances() * dataCostUpdated + paramCostUpdated;
        } else {
            costUpdated = Double.MAX_VALUE;
        }

        // System.out.println("cost with updated params: " + costUpdated);
        //DEBUG
//        ArffFileWriter af = new ArffFileWriter();
//        af.saveFile("test.arff", data);
        //DEBUG

        SuperAttribute[] restructured = dendrogramMergeAttributes(data);
        double dataCostRest = 0.0;
        double paramCostRest = 0.0;
        for (int i = 0;
                i < restructured.length;
                i++) {
            if (restructured[i] != null) {
                dataCostRest += restructured[i].dataCost;
                paramCostRest += restructured[i].paramCost;
            }
        }
        double costRestructured = data.numInstances() * dataCostRest + paramCostRest;
        //  System.out.println("cost with restructuring: " + costRestructured);
        if (costRestructured < costUpdated) {
            sa = new SuperAttribute[c.sa.length];
            // System.out.println("restructuring cluster: " + ID);
            for (int i = 0; i < sa.length; i++) {
                sa[i] = restructured[i];
            }
        }

        setCost();
    }

    public void normalizeAndUpdateSA(Instances data) {
        Instances inst = normalize(data);
        SuperAttribute[] updated = dendrogramMergeAttributes(inst);
        for (int i = 0; i < sa.length; i++) {
            sa[i] = updated[i];
        }
        for (int i = 0; i < sa.length; i++) {
            if (sa[i] != null) {
                for (int j = 0; j < data.numInstances(); j++) {
                    sa[i].dataCost += sa[i].cost(data.instance(j), data.numInstances());
                    
                }
                
            }     
        }
        setCost();


    }

    //used for initialization in K-means. no normalization required
    public Cluster(Instances dataset, int repID, int clID) {
        this.ID = clID;
        //this.data = dataset;
        numObj =
                dataset.numInstances();
        numAttributes =
                dataset.numAttributes() - 3; //last 3 attributes are: classID, clusterID, number
        sa =
                new SuperAttribute[numAttributes];
        for (int i = 0; i
                < sa.length; i++) {
            sa[i] = new SuperAttribute(dataset, i, repID);
        }

        setCost();
    }

    public void printInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("Cluster " + ID + " " + numObj + " ");
        sb.append("SA (");
        for (int i = 0; i
                < sa.length; i++) {
            if (sa[i] != null) {
                sb.append("(");
                for (int j = 0; j
                        < sa[i].attId.size(); j++) {
                    sb.append(sa[i].attId.elementAt(j).toString() + " ");
                }

                sb.append(")");
            }

        }
        sb.append((")"));
        System.out.println(sb.toString());

    }

//    public void mutualInformation() {
//        mi = new double[numAttributes][numAttributes];
//        double num_num = -1.0;
//        double cat_cat = 1.0;
//        double num_cat = 0.0;
//        double[][] kind = new double[numAttributes][numAttributes];
//        for (int i = 0; i < mi.length; i++) {
//            for (int j = 0; j < mi.length; j++) {
//                if (i < j) {
//                    if (data.attribute(i).isNominal() && data.attribute(j).isNominal()) {
//                        mi[i][j] = mi_cat(i, j);
//                        mi[j][i] = mi[i][j];
//                        kind[i][j] = cat_cat;
//                        kind[j][i] = kind[i][j];
//                    }
//                    if (data.attribute(i).isNumeric() && data.attribute(j).isNumeric()) {
//                        mi[i][j] = mi_num(i, j);
//                        mi[j][i] = mi[i][j];
//                        kind[i][j] = num_num;
//                        kind[j][i] = num_num;
//                    }
//                    if (data.attribute(i).isNumeric() && data.attribute(j).isNominal() || data.attribute(j).isNumeric() && data.attribute(i).isNominal()) {
//                        mi[i][j] = mi_mixedWithIntegral(i, j);
//                        kind[i][j] = num_cat;
//                    }
//                }
//            }
//        }
//        dataCheck(mi, "mi");
//        dataCheck(kind, "kind");
//
//    }
    public SuperAttribute[] dendrogramMergeAttributes(Instances data) {
        SuperAttribute[] curr = new SuperAttribute[numAttributes];
        for (int i = 0; i
                < numAttributes; i++) {
            curr[i] = new SuperAttribute(data, i);
        }

        int currNumSa = numAttributes;
        double maxSaved;
        SuperAttribute[][] future = new SuperAttribute[numAttributes][numAttributes];
        for (int i = 0; i
                < currNumSa; i++) {
            for (int j = 0; j
                    < currNumSa; j++) {
                if (i != j) {
//                    //DEBUG
//                    System.out.println("i: " + i + " j: " + j);
//                    //DEBUG
                    future[i][j] = new SuperAttribute(curr[i], curr[j], data);
                }

            }
        }
        int iter = 0;
        do {
            //DEBUB System.out.println("m"); -> ok
            maxSaved = -Double.MAX_VALUE;
            int max_i = -1;
            int max_j = -1;
            for (int i = 0; i
                    < currNumSa; i++) {
                for (int j = i + 1; j
                        < currNumSa; j++) {
                    if (i != j) {
                        double costInd = curr[i].dataCost * data.numInstances() + curr[i].paramCost + curr[j].dataCost * data.numInstances() + curr[j].paramCost;
                        double costDep = future[i][j].dataCost * data.numInstances() + future[i][j].paramCost;
                        double saved = costInd - costDep;
                        if (saved > maxSaved) {
                            max_i = i;
                            max_j =
                                    j;
                            maxSaved =
                                    saved;
                        }

                    }
                }
            }
//            //merge
//            System.out.println("iter: " + iter + " " + maxSaved);
//            System.out.println("i ");
//            print(curr[max_i]);
//            System.out.println("j ");
//            print(curr[max_j]);
//            System.out.println(" merged ");
            //  print(future[max_i][max_j]);
            if (maxSaved > 0) {
                iter++;
                curr[max_i] = future[max_i][max_j];
                curr[max_j] = curr[currNumSa - 1];
                curr[currNumSa - 1] = null;
                for (int i = 0; i
                        < currNumSa - 1; i++) {
                    //System.out.println(i);
                    if (i != max_i) {
                        future[max_i][i] = new SuperAttribute(curr[max_i], curr[i], data);
                        future[i][max_i] = future[max_i][i];
                        future[max_j][i] = future[currNumSa - 1][i];
                        future[i][max_j] = future[i][currNumSa - 1];
                        future[currNumSa - 1][i] = null;
                        future[i][currNumSa - 1] = null;
                    }

                }
                currNumSa--;

            }

        } while (maxSaved > 0);
        return curr;

    }

    private void print(SuperAttribute s) {
        StringBuffer sb = new StringBuffer();
        sb.append("SA (");
        for (int i = 0; i
                < s.attId.size(); i++) {
            sb.append(s.attId.elementAt(i).toString() + " ");
        }

        sb.append((")"));
        System.out.println(sb.toString());

    }

    private void dataCheck(double[][] d, String filename) {
        MLDouble q = new MLDouble(filename, d);
        ArrayList ll = new ArrayList();
        ll.add(q);
        MatFileWriter mw = new MatFileWriter();
        try {
            String name = filename + ".mat";
            mw.write(name, ll);
        } catch (IOException ex) {
            // Logger.getLogger(VI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    private double mi_cat(int i, int j) {
//        //look for correct contingency table
//        int index = -1;
//        int counter = 0;
//        while (index == -1) {
//            if ((ct[counter].rowAtt == i && ct[counter].colAtt == j) || (ct[counter].rowAtt == j && ct[counter].colAtt == i)) {
//                index = counter;
//            }
//            counter++;
//        }
//        double m = 0.0;
//        for (int ii = 0; ii < ct[index].values.length; ii++) {
//            for (int jj = 0; jj < ct[index].values[ii].length; jj++) {
//                double pij = (double) ct[index].values[ii][jj] / (double) numObj;
//                double pi = (double) ct[index].rowSum[ii] / (double) numObj;
//                double pj = (double) ct[index].colSum[jj] / (double) numObj;
//                if (pij > 0) {
//                    m += pij * log2(pij / (pi * pj));
//                }
//            }
//        }
//        return m;
//    }
//    private double savedCost(SuperAttribute a, SuperAttribute b) {
//        double costIndependent = a.dataCost + a.paramCost + b.dataCost + b.paramCost;
//        SuperAttribute merged = new SuperAttribute(a, b, data);
//
//        return 0.0;
//    }
//
//    private double savedCost(int i, int j) {
//        double dataCostIndependent = entropy[i] + entropy[j];
//        int numParamIndependent = 0;
//        int numParamDependent = 0;
//        if (data.attribute(i).isNominal() && data.attribute(j).isNominal()) {
//            numParamIndependent = data.attribute(i).numValues() + data.attribute(j).numValues();
//            numParamDependent = data.attribute(i).numValues() * data.attribute(j).numValues();
//        }
//        if (data.attribute(i).isNumeric() && data.attribute(j).isNumeric()) {
//            numParamIndependent = 2 * 2; //for each mean and variance
//            numParamDependent = numParamIndependent + 4; //additional covariance matrix
//        }
//        if (data.attribute(i).isNominal() && data.attribute(j).isNumeric()) {
//            numParamIndependent = data.attribute(i).numValues() + 2;
//            numParamDependent = data.attribute(i).numValues() * 2;
//        }
//        if (data.attribute(i).isNumeric() && data.attribute(j).isNominal()) {
//            numParamIndependent = data.attribute(j).numValues() + 2;
//            numParamDependent = data.attribute(j).numValues() * 2;
//        }
//        double additionalParamCost = ((double) numParamDependent / 2.0 * log2(numObj)) - ((double) numParamIndependent / 2.0 * log2(numObj));
//        return dataCostIndependent - 2 * mi[i][j] + additionalParamCost;
//
//    }
//    private double mi_mixedWithIntegral(int i, int j) {
//        int catIndexDataSet = 0;
//        int numIndexDataSet = 0;
//        if (data.attribute(i).isNominal()) {
//            catIndexDataSet = i;
//            numIndexDataSet = j;
//        } else {
//            catIndexDataSet = j;
//            numIndexDataSet = i;
//        }
//        //look for correct contingency table
//        int index_ct = -1;
//        int counter = 0;
//        boolean row = true;
//        while (index_ct == -1) {
//            if (ct[counter].rowAtt == catIndexDataSet || ct[counter].colAtt == catIndexDataSet) {
//                index_ct = counter;
//                if (ct[counter].colAtt == catIndexDataSet) {
//                    row = false;
//                }
//            }
//            counter++;
//        }
//        //compute mean and variance of Gaussian for each category
//        double[] catMeans = new double[data.attribute(catIndexDataSet).numValues()];
//        double[] catVars = new double[data.attribute(catIndexDataSet).numValues()];
//        for (int jj = 0; jj < numObj; jj++) {
////                //DEBUG
////                System.out.println("instance: " + jj);
////                System.out.println("index categorical: " + catIndexDataSet);
////                System.out.println("index numerical: " + numIndexDataSet);
////                System.out.println("numObj of this category: "+ ct[index_ct].rowSum[(int) data.instance(jj).value(catIndexDataSet)]);
////                //DEBUG -> ok
//            if (row) {
//                catMeans[(int) data.instance(jj).value(catIndexDataSet)] += (data.instance(jj).value(numIndexDataSet)) / (double) ct[index_ct].rowSum[(int) data.instance(jj).value(catIndexDataSet)];
//            } else {
//                catMeans[(int) data.instance(jj).value(catIndexDataSet)] += (data.instance(jj).value(numIndexDataSet)) / (double) ct[index_ct].colSum[(int) data.instance(jj).value(catIndexDataSet)];
//            }
//        }
//        for (int jj = 0; jj < numObj; jj++) {
//            if (row) {
//                catVars[(int) data.instance(jj).value(catIndexDataSet)] += Math.pow((data.instance(jj).value(numIndexDataSet) - catMeans[(int) data.instance(jj).value(catIndexDataSet)]), 2) / (double) ct[index_ct].rowSum[(int) data.instance(jj).value(catIndexDataSet)];
//            } else {
//                catVars[(int) data.instance(jj).value(catIndexDataSet)] += Math.pow((data.instance(jj).value(numIndexDataSet) - catMeans[(int) data.instance(jj).value(catIndexDataSet)]), 2) / (double) ct[index_ct].colSum[(int) data.instance(jj).value(catIndexDataSet)];
//            }
////                //DEBUG
////                System.out.println("arrayIndex: " + data.instance(jj).value(catIndexDataSet));
////                //DEBUG -> ok
//        }
//        int index_num = 0;
//        //search for correct position in indexNumerical
//        double[] mean = new double[2];
//        for (int ii = 0; ii < numNum; ii++) {
//            if (numIndexDataSet == indexNumerical[ii]) {
//                index_num = ii;
//            }
//        }
//        double mi = 0.0;
//        double p_y = 0.0;
//        for (int ii = 0; ii < data.attribute(catIndexDataSet).numValues(); ii++) {
//            if (row) {
//                p_y = ct[index_ct].rowSum[ii] / (double) numObj;
//            } else {
//                p_y = ct[index_ct].colSum[ii] / (double) numObj;
//            }
//            double term1 = -2.0 * Math.log(Math.sqrt(vars[index_num])) * vars[index_num];
//            double term2 = 2.0 * vars[index_num] * Math.log(p_y);
//            double term3 = 2.0 * Math.log(Math.sqrt(catVars[ii])) * vars[index_num];
//            double term4 = 2.0 * catMeans[ii] * means[index_num];
//            double upper = term1 + term2 + term3 + term4 - Math.pow(catMeans[ii], 2) + vars[index_num] - Math.pow(means[index_num], 2) - catVars[ii];
//            double lower = Math.log(2.0) * vars[index_num];
//            double curr = -0.5 * (upper / lower);
//            mi += curr;
//
//        }
//        double p_x = gaussIntegral(means[index_num], vars[index_num]);
//        for(int ii = 0; ii < data.attribute(catIndexDataSet).numValues(); ii++){
//            double p_xy = gaussIntegral(catMeans[ii], catVars[ii]);
//            double p_y = 0.0;
//            if(row){
//                p_y = ct[index_ct].rowSum[ii]/(double)numObj;
//            }
//            else{
//                p_y = ct[index_ct].colSum[ii]/(double)numObj;
//            }
//            mi += p_xy * log2(p_xy/(p_x * p_y));
//        }
//        return mi;
//    }
//
//    private double mi_mixed(int i, int j) {
//        int catIndexDataSet = 0;
//        int numIndexDataSet = 0;
//        if (data.attribute(i).isNominal()) {
//            catIndexDataSet = i;
//            numIndexDataSet = j;
//        } else {
//            catIndexDataSet = j;
//            numIndexDataSet = i;
//        }
//        //look for correct contingency table
//        int index_ct = -1;
//        int counter = 0;
//        boolean row = true;
//        while (index_ct == -1) {
//            if (ct[counter].rowAtt == catIndexDataSet || ct[counter].colAtt == catIndexDataSet) {
//                index_ct = counter;
//                if (ct[counter].colAtt == catIndexDataSet) {
//                    row = false;
//                }
//            }
//            counter++;
//        }
//
//        //compute mean and variance of Gaussian for each category
//        double[] catMeans = new double[data.attribute(catIndexDataSet).numValues()];
//        double[] catVars = new double[data.attribute(catIndexDataSet).numValues()];
//        for (int jj = 0; jj < numObj; jj++) {
////                //DEBUG
////                System.out.println("instance: " + jj);
////                System.out.println("index categorical: " + catIndexDataSet);
////                System.out.println("index numerical: " + numIndexDataSet);
////                System.out.println("numObj of this category: "+ ct[index_ct].rowSum[(int) data.instance(jj).value(catIndexDataSet)]);
////                //DEBUG -> ok
//            if (row) {
//                catMeans[(int) data.instance(jj).value(catIndexDataSet)] += (data.instance(jj).value(numIndexDataSet)) / (double) ct[index_ct].rowSum[(int) data.instance(jj).value(catIndexDataSet)];
//            } else {
//                catMeans[(int) data.instance(jj).value(catIndexDataSet)] += (data.instance(jj).value(numIndexDataSet)) / (double) ct[index_ct].colSum[(int) data.instance(jj).value(catIndexDataSet)];
//            }
//        }
//        for (int jj = 0; jj < numObj; jj++) {
//            if (row) {
//                catVars[(int) data.instance(jj).value(catIndexDataSet)] += Math.pow((data.instance(jj).value(numIndexDataSet) - catMeans[(int) data.instance(jj).value(catIndexDataSet)]), 2) / (double) ct[index_ct].rowSum[(int) data.instance(jj).value(catIndexDataSet)];
//            } else {
//                catVars[(int) data.instance(jj).value(catIndexDataSet)] += Math.pow((data.instance(jj).value(numIndexDataSet) - catMeans[(int) data.instance(jj).value(catIndexDataSet)]), 2) / (double) ct[index_ct].colSum[(int) data.instance(jj).value(catIndexDataSet)];
//            }
////                //DEBUG
////                System.out.println("arrayIndex: " + data.instance(jj).value(catIndexDataSet));
////                //DEBUG -> ok
//        }
//        //find index of categorical attribute
//        int index_cat = 0;
//        int index_num = 0;
//        //search for correct position in indexNumerical
//        double[] mean = new double[2];
//        for (int ii = 0; ii < numNum; ii++) {
//            if (numIndexDataSet == indexNumerical[ii]) {
//                index_num = ii;
//            }
//        }
//        for (int ii = 0; ii < numCat; ii++) {
//            if (catIndexDataSet == indexCategorical[ii]) {
//                index_cat = ii;
//            }
//        }
//        double entropyCat = entropy[index_cat];
//        double entropyNum = entropySingleGaussian(means[index_num], vars[index_num]);
//        double entropyCombined = 0.0;
//        for (int ii = 0; ii < catMeans.length; ii++) {
//            entropyCombined += entropySingleGaussian(catMeans[ii], catVars[ii]);
//        }
//        return entropyCat + entropyNum - entropyCombined;
//
//
//    }
    private double entropySingleGaussian(double mean, double var) {
        return 0.5 * log2(2 * Math.PI * Math.E * var);
    }

//    private double mi_num(int i, int j) {
//        //look for correct means and variances
//        int index_i = 0;
//        int index_j = 0;
//        //search for correct position in indexNumerical
//        double[] mean = new double[2];
//        for (int ii = 0; ii < numNum; ii++) {
//            if (indexNumerical[ii] == i) {
//                index_i = ii;
//            }
//            if (indexNumerical[ii] == j) {
//                index_j = ii;
//            }
//        }
//        mean[0] = means[index_i];
//        mean[1] = means[index_j];
//        double[][] coord = new double[data.numInstances()][2];
//        for (int ii = 0; ii < data.numInstances(); ii++) {
//            coord[ii][0] = data.instance(ii).value(i);
//            coord[ii][1] = data.instance(ii).value(j);
//        }
//
//
//        double[][] cc = covar(coord, mean);
//        double ccDet = new Matrix(cc).det();
//        double logvalue = (vars[index_i] * vars[index_j]) / ccDet;
//        return 0.5 * log2(logvalue);
//    }
//
//   
//compute covarance matrix: data are row vectors
    private double[][] covar(double[][] data, double[] mean) {
        double[][] cov = new double[mean.length][mean.length];
        for (int l = 0; l
                < data.length; l++) {
            for (int i = 0; i
                    < mean.length; i++) {
                for (int j = 0; j
                        < mean.length; j++) {
                    cov[i][j] += (data[l][j] - mean[j]) * (data[l][i] - mean[i]);
                }

            }
        }
        for (int i = 0; i
                < mean.length; i++) {
            for (int j = 0; j
                    < mean.length; j++) {
                cov[i][j] /= data.length;
            }

        }
        return cov;
    }

//    private double gaussIntegral(double mean, double var) {
//        double factor = 1.0 / (Math.sqrt(2 * Math.PI) * var);
//        double a = 1.0 / (2 * var);
//        double int0 = Math.pow(a, -0.5) * gammaf;
//        return int0 * 2;
//    }
    private double log2(double d) {
        return Math.log(d) / Math.log(2);
    }
//    private double entropy(int i){
//        double sum = 0;
//        for(int i = 0; i < data.attribute(i).numValues(); i++)
//            sum += d
//    }
//
//    private void setUpContingencyTables() {
//        for (int i = 0; i < numAttributes; i++) {
//            if (data.attribute(i).isNominal()) {
//                numCat++;
//            }
//        }
//        int counter = 0;
//        indexCategorical = new int[numCat];
//        for (int i = 0; i < numAttributes; i++) {
//            if (data.attribute(i).isNominal()) {
//                indexCategorical[counter] = i;
//                counter++;
//            }
//        }
//        entropy = new double[numAttributes];
//        int[][] count = new int[numCat][50];
//        for (int i = 0; i < numObj; i++) {
//            for (int j = 0; j < numCat; j++) {
//                count[j][(int) data.instance(i).value(indexCategorical[j])]++;
//            }
//        }
//        for (int i = 0; i < numCat; i++) {
//            for (int j = 0; j < data.attribute(indexCategorical[i]).numValues(); j++) {
//                entropy[i] += count[i][j] / (double) numObj * log2(count[i][j] / (double) numObj);
//            }
//            entropy[indexCategorical[i]] = entropy[i] * (-1);
//        }
//
//        ct = new ContingencyTable[(numCat * numCat - numCat) / 2];
//        counter = 0;
//        for (int i = 0; i < numAttributes; i++) {
//            for (int j = 0; j < numAttributes; j++) {
//                if (i < j && data.attribute(i).isNominal() && data.attribute(j).isNominal()) {
//                    int[][] values = new int[data.attribute(i).numValues()][data.attribute(j).numValues()];
//                    for (int k = 0; k < data.numInstances(); k++) {
//                        values[(int) data.instance(k).value(i)][(int) data.instance(k).value(j)]++;
//                    }
//                    ct[counter] = new ContingencyTable(i, j, values);
//                    counter++;
//                }
//            }
//        }
//    }
//    private void setUpNumerical() {
//        for (int i = 0; i < numAttributes; i++) {
//            if (data.attribute(i).isNumeric()) {
//                numNum++;
//            }
//        }
//        int counter = 0;
//        indexNumerical = new int[numNum];
//        for (int i = 0; i < numAttributes; i++) {
//            if (data.attribute(i).isNumeric()) {
//                indexNumerical[counter] = i;
//                counter++;
//            }
//        }
//        means = new double[numNum];
//        vars = new double[numNum];
//        for (int i = 0; i < data.numInstances(); i++) {
//            for (int j = 0; j < indexNumerical.length; j++) {
//                means[j] += (data.instance(i).value(indexNumerical[j])) / numObj;
//            }
//        }
//        for (int i = 0; i < data.numInstances(); i++) {
//            for (int j = 0; j < indexNumerical.length; j++) {
//                vars[j] += Math.pow(data.instance(i).value(indexNumerical[j]) - means[j], 2) / numObj;
//            }
//        }
//        //compute entropy
//        for (int i = 0; i < indexNumerical.length; i++) {
//            entropy[indexNumerical[i]] = entropySingleGaussian(means[i], vars[i]);
//        }
//    }
}
