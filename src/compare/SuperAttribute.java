/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compare;

import Jama.CholeskyDecomposition;
import Jama.Matrix;
import java.util.Vector;
import weka.core.Instance;
import weka.core.Instances;
import com.jmatio.io.MatFileWriter;
import com.jmatio.types.MLDouble;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author claudia
 */
public class SuperAttribute {

    Vector<Integer> attId; //id of attributes in original data set
    double dataCost; //per object
    double paramCost;
    int[] cont;
    double[] probCont;
    double[][] mean; //[sizeCont][counter_num];
    double[][][] covar; //[sizeCont][counter_num][counter_num];
    int[] indexDataSet_cat; //if several categorical variables: index in original dataset
    int[] num_cat; //number of categories
    int[] indexDataSet_num;
    static double singCheat = 0.000000000001;
    double[][][] covarL;
    double[] det;
    static int minNumObj = 10; //minimum number of objects in a cluster
    static double weightOriginal = 0.9;

    public SuperAttribute(SuperAttribute a, boolean copy) {
        if (copy) {
            this.attId = new Vector<Integer>();
            for (int i = 0; i < a.attId.size(); i++) {
                attId.add(a.attId.elementAt(i));
            }
            dataCost = a.dataCost;
            paramCost = a.paramCost;
            cont = new int[a.cont.length];
            for (int i = 0; i < a.cont.length; i++) {
                cont[i] = a.cont[i];
            }
            probCont = new double[a.cont.length];
            for (int i = 0; i < a.cont.length; i++) {
                probCont[i] = a.probCont[i];
            }
            indexDataSet_cat = new int[a.indexDataSet_cat.length];
            for (int i = 0; i < a.indexDataSet_cat.length; i++) {
                indexDataSet_cat[i] = a.indexDataSet_cat[i];
            }
            num_cat = new int[a.num_cat.length];
            for (int i = 0; i < a.num_cat.length; i++) {
                num_cat[i] = a.num_cat[i];
            }
            indexDataSet_num = new int[a.indexDataSet_num.length];
            for (int i = 0; i < a.indexDataSet_num.length; i++) {
                indexDataSet_num[i] = a.indexDataSet_num[i];
            }
            mean = new double[a.mean.length][a.mean[0].length];
            for (int i = 0; i < mean.length; i++) {
                for (int j = 0; j < mean[i].length; j++) {
                    mean[i][j] = a.mean[i][j];
                }
            }
            covar = new double[a.covar.length][a.covar[0].length][a.covar[0][0].length];
            for (int i = 0; i < a.covar.length; i++) {
                for (int j = 0; j < a.covar[0].length; j++) {
                    for (int k = 0; k < a.covar[0][0].length; k++) {
                        covar[i][j][k] = a.covar[i][j][k];
                    }
                }
            }

            covarL = new double[a.covarL.length][a.covarL[0].length][a.covarL[0][0].length];
            for (int i = 0; i < a.covarL.length; i++) {
                for (int j = 0; j < a.covarL[0].length; j++) {
                    for (int k = 0; k < a.covarL[0][0].length; k++) {
                        covarL[i][j][k] = a.covarL[i][j][k];
                    }
                }
            }
            det = new double[a.det.length];
            for (int i = 0; i < det.length; i++) {
                det[i] = a.det[i];
            }

        }
    }

    private void addComplexityCost(int overallNumAttributes) {
//DEBUG
//        if (attId.size() == 1) return ;
//        int numEntriesMIMatrix = overallNumAttributes * ((overallNumAttributes - 1) / 2);
//        int numEntriesThis = Math.max(1, attId.size() * ((attId.size() - 1) / 2));
//        double complexityCost = -(double) numEntriesThis / (double) numEntriesMIMatrix * log2((double) numEntriesThis / (double) numEntriesMIMatrix);
//        paramCost += complexityCost;
        //DEBUG

//        System.out.println("supervariable: ");
//        print();
//        System.out.println("complexity cost: " + complexityCost);
    }

    public SuperAttribute(Instances data, int aId) {
        attId = new Vector<Integer>();
        attId.add(aId);
        if (data.attribute(aId).isNominal()) {
            cont = new int[data.attribute(aId).numValues()];
            probCont = new double[data.attribute(aId).numValues()];
            for (int i = 0; i < data.numInstances(); i++) {
                cont[(int) data.instance(i).value(aId)]++;
            }
            //fillProbCont(data.numInstances());
            num_cat = new int[1];
            num_cat[0] = data.attribute(aId).numValues();
            mean = new double[0][0];
            covar = new double[0][0][0];
            covarL = new double[0][][];
            det = new double[0];
            indexDataSet_cat = new int[1];
            indexDataSet_cat[0] = aId;
            indexDataSet_num = new int[0];
            check(weightOriginal, data);
            for (int i = 0; i < cont.length; i++) {
                if (cont[i] > 0) {
                    dataCost -= probCont[i] * log2(probCont[i]);
                }
            }
            paramCost = (double) data.attribute(aId).numValues() / 2.0 * log2(data.numInstances());
            addComplexityCost(data.numAttributes() - 3);
        } else {
            cont = new int[0];
            probCont = new double[0];
            mean = new double[1][1];
            covar = new double[1][1][1];
            covarL = new double[1][1][1];
            det = new double[1];
            for (int i = 0; i < data.numInstances(); i++) {
                mean[0][0] += data.instance(i).value(aId) / (double) data.numInstances();
            }
            for (int i = 0; i < data.numInstances(); i++) {
                covar[0][0][0] += Math.pow(data.instance(i).value(aId) - mean[0][0], 2) / (double) data.numInstances();
            }
            covarL[0][0][0] = Math.sqrt(covar[0][0][0]);
            det[0] = covar[0][0][0];
            indexDataSet_num = new int[1];
            indexDataSet_num[0] = aId;
            indexDataSet_cat = new int[0];
            num_cat = new int[0];
            check(weightOriginal, data);
            dataCost = entropySingleGaussian(mean[0][0], covar[0][0][0]);
            paramCost = log2(data.numInstances());
            addComplexityCost(data.numAttributes() - 3);

        }
    }

    public SuperAttribute(SuperAttribute a) {
        this.attId = a.attId; //id of attributes in original data set
        dataCost = 0.0; //per object
        paramCost = 0.0;
    }

    public boolean updateParameters(Instances data) {
        int cat_a = 0;
        int num_a = 0;
        for (int i = 0; i <
                attId.size(); i++) {
            if (data.attribute(attId.elementAt(i)).isNominal()) {
                cat_a++;
            } else {
                num_a++;
            }

        }
        indexDataSet_cat = new int[cat_a];
        num_cat = new int[cat_a];
        indexDataSet_num = new int[num_a];
        int counter_cat = 0;
        int counter_num = 0;
        for (int i = 0; i < attId.size(); i++) {
            if (data.attribute(attId.elementAt(i)).isNominal()) {
                indexDataSet_cat[counter_cat] = attId.elementAt(i);
                num_cat[counter_cat] = data.attribute(attId.elementAt(i)).numValues();
                counter_cat++;

            } else {
                indexDataSet_num[counter_num] = attId.elementAt(i);
                counter_num++;

            }
        }
        int sizeCont = 1;
        for (int i = 0; i <
                num_cat.length; i++) {
            sizeCont *= num_cat[i];
        }
        this.cont = new int[sizeCont];                 //OK???
        probCont = new double[sizeCont];
        mean = new double[sizeCont][counter_num];
        covar = new double[sizeCont][counter_num][counter_num];
        if (indexDataSet_cat.length == 0) {
            cont = new int[1];
            cont[0] = data.numInstances();

        }
        for (int i = 0; i < data.numInstances(); i++) {
            int adr = 0;
            for (int j = 0; j < indexDataSet_cat.length; j++) {
                adr *= num_cat[j];
                int category = (int) data.instance(i).value(indexDataSet_cat[j]);
                adr += category;
            }
            if (indexDataSet_cat.length > 0) {
                cont[adr]++;
            }
            for (int j = 0; j < indexDataSet_num.length; j++) {
                mean[adr][j] += data.instance(i).value(indexDataSet_num[j]);
            }
        }
        for (int i = 0; i < cont.length; i++) {
            for (int j = 0; j < indexDataSet_num.length; j++) {
                mean[i][j] /= (double) cont[i];
            }

        }
        //fillProbCont(data.numInstances());

        for (int i = 0; i <
                data.numInstances(); i++) {
            int adr = 0;
            for (int j = 0; j <
                    indexDataSet_cat.length; j++) {
                adr *= num_cat[j];
                int category = (int) data.instance(i).value(indexDataSet_cat[j]);
                adr +=
                        category;
            }

            for (int k = 0; k <
                    indexDataSet_num.length; k++) {
                for (int l = k; l <
                        indexDataSet_num.length; l++) {
                    covar[adr][k][l] += ((data.instance(i).value(indexDataSet_num[l]) - mean[adr][l]) * (data.instance(i).value(indexDataSet_num[k]) - mean[adr][k])) / cont[adr];
                }

            }
        }
        for (int adr = 0; adr <
                cont.length; adr++) {
            for (int k = 0; k <
                    indexDataSet_num.length; k++) {
                for (int l = k + 1; l <
                        indexDataSet_num.length; l++) {
                    covar[adr][l][k] = covar[adr][k][l];
                }

            }
        }

        if(!check(weightOriginal, data))
            return false;
        for (int adr = 0; adr <
                cont.length; adr++) {
            double maxdiff = 0.0;
            for (int i = 0; i <
                    indexDataSet_num.length; i++) {
                double sum = 0.0;
                for (int j = 0; j <
                        indexDataSet_num.length; j++) {
                    sum += Math.abs(covar[adr][i][j]);
                }

                double diff = (1.0 + singCheat) * sum - (2.0 + singCheat) * covar[adr][i][i];
                if (diff > maxdiff) {
                    maxdiff = diff;
                }

            }

            if (maxdiff > 0.0) {
                for (int i = 0; i <
                        indexDataSet_num.length; i++) {
                    covar[adr][i][i] += maxdiff;
                }

            }
        }

        covarL = new double[cont.length][][];
        det =
                new double[cont.length];

        if (indexDataSet_num.length > 0) {
            for (int adr = 0; adr <
                    cont.length; adr++) {
                covarL[adr] = new CholeskyDecomposition(new Matrix(covar[adr])).getL().getArray();
                det[adr] = 1.0;
                for (int i = 0; i <
                        covarL[0].length; i++) {
//                //debug
//                System.out.println(adr + " " + " i " + i  + " covarL.length: " + covarL.length);
//                //debug
                    det[adr] *= covarL[adr][i][i];
                }

                det[adr] *= det[adr];
            //det[adr] = Math.max(det[adr], singCheat); //without this: costs -infinity
//                if (Double.isNaN(det[adr]) || det[adr] == 0) {
//                    det[adr] = singCheat;
//                }
            }

        }

        dataCost = 0.0;
        for (int adr = 0; adr <
                cont.length; adr++) {
            double phi = probCont[adr];
            if (indexDataSet_num.length > 0) {
                dataCost += phi * 0.5 * log2(Math.pow(2.0 * Math.PI * Math.E, indexDataSet_num.length) * det[adr]);
            //dataCost += phi * 0.5 * log2(Math.pow(2.0 * Math.PI ,indexDataSet_num.length) * det[adr]);
            //dataCost += phi * 0.5 * log2(Math.pow(2.0 * Math.PI * Math.E, covarL.length) * det[adr]);
            }

            if (phi > 0) {
                dataCost -= phi * log2(phi);
            }

        }
        int numParam = cont.length + cont.length * indexDataSet_num.length + cont.length * indexDataSet_num.length * (indexDataSet_num.length + 1) / 2;
        paramCost =
                (double) numParam / 2.0 * log2(data.numInstances());
        addComplexityCost(data.numAttributes() - 3);
//        if (dataCost == Double.NEGATIVE_INFINITY || Double.isInfinite(dataCost) || Double.isNaN(dataCost)) {
//            for (int i = 0; i <
//                    attId.size(); i++) {
//                System.out.print(attId.elementAt(i) + " ");
//            }
//
//            System.out.print(dataCost + " with update params");
//            System.out.println();
//        }
        return true;

    }

    private void fillProbCont(int numObj) {
        for (int i = 0; i <
                probCont.length; i++) {
            probCont[i] = (double) cont[i] / (double) numObj;
        }

    }

    public double cost(Instance inst, int numObj) {
        int adr = 0;
        for (int j = 0; j <
                indexDataSet_cat.length; j++) {
            adr *= num_cat[j];
            int category = (int) inst.value(indexDataSet_cat[j]);
            adr +=
                    category;

        }
//double prob = 1.0;

        double logprob = 0.0;
        if (indexDataSet_cat.length > 0) {
            //prob = (double) cont[adr] / numObj;
            logprob = -log2(probCont[adr]);
        }
//prob = Math.max(prob, singCheat); //otherwise point cannot be assigned if it has this combination.

        double[] y = new double[indexDataSet_num.length];
        double norm = 0.0;
        for (int i = 0; i <
                indexDataSet_num.length; i++) {
            y[i] = inst.value(indexDataSet_num[i]) - mean[adr][i];
            for (int j = 0; j <
                    i; j++) {
                y[i] -= covarL[adr][i][j] * y[j];
            }

            y[i] /= covarL[adr][i][i];
            norm +=
                    y[i] * y[i];
        }

// TEST
//        if (indexDataSet_num.length > 0) {
//            double[] y1 = new double[indexDataSet_num.length];
//            for (int i = 0; i < indexDataSet_num.length; i++) {
//                y1[i] = inst.value(indexDataSet_num[i]) - mean[adr][i];
//            }
//
//            norm = rowVector(y1).times(new Matrix(covar[adr]).inverse()).times(columnVector(y1)).get(0, 0);
//        }
// END TEST OKAY for d=1
        if (indexDataSet_num.length > 0) {
            //prob *= Math.exp(-0.5 * norm) / Math.sqrt(Math.pow(2 * Math.PI, indexDataSet_num.length) * det[adr]);
            double phi = 1;
            if (indexDataSet_cat.length > 0) {
                phi = (double) probCont[adr];
            }

            logprob = (0.5 / Math.log(2.0) * norm) - log2(phi / Math.sqrt(Math.pow(2 * Math.PI, indexDataSet_num.length) * det[adr]));
        }
//double res = -log2(prob);
//System.out.println(res + " " + logprob);

        return logprob;
    }

    private boolean check(double weightOr, Instances data) {
        //re-weight category probabilities
        int numObj = data.numInstances();
        if(numObj == 0)
            return false;
        double weightUniform = 1.0 - weightOr;
        boolean emptyGaussians = false;
        boolean[] isNull = new boolean[cont.length];
        for (int i = 0; i < cont.length; i++) {
            if (cont[i] < minNumObj) {
                isNull[i] = true;
                emptyGaussians = true;
            }

        }
        // if (indexDataSet_cat.length > 0) {
        probCont = new double[cont.length];
        for (int i = 0; i < probCont.length; i++) {
            probCont[i] = (double) cont[i] / (double) numObj;
        }
        if (weightUniform > 0 && indexDataSet_cat.length > 0) {
            double uniform = (double) numObj / (double) cont.length;
            double probUniform = uniform / (double) numObj;
            for (int i = 0; i < cont.length; i++) {
                //probCont[i] = (weightOriginal * probCont[i]) + (weightUniform * probCont[i]) / 2.0;
                probCont[i] = (weightOr * probCont[i]) + (weightUniform * probUniform);
            }

            //sanity check
            boolean isNullp = false;
            double sum = 0.0;
            for (int i = 0; i < probCont.length; i++) {
                if (probCont[i] == 0) {
                    isNullp = true;
                }
                sum += probCont[i];

            }
            if (Math.abs(1.0 - sum) > 0.1 || isNullp) {
                System.out.println("error ");
            }
        }
        //ok
        if (emptyGaussians && indexDataSet_num.length > 0) {
            //determine overall cluster gaussian
            double[] overallMean = new double[indexDataSet_num.length];
            double[][] overallCovar = new double[indexDataSet_num.length][indexDataSet_num.length];
            for (int i = 0; i <
                    indexDataSet_num.length; i++) {
                overallMean[i] = data.meanOrMode(indexDataSet_num[i]);
            }

            for (int i = 0; i <
                    data.numInstances(); i++) {
                for (int k = 0; k <
                        indexDataSet_num.length; k++) {
                    for (int l = k; l <
                            indexDataSet_num.length; l++) {
                        overallCovar[k][l] += ((data.instance(i).value(indexDataSet_num[l]) - overallMean[l]) * (data.instance(i).value(indexDataSet_num[k]) - overallMean[k])) / (double) numObj;
                    }

                }
            }
//            counter = 0;
//            while (counter < isNull.length) {
//                while (isNull[counter] == false) {
//                    counter++;
//                }
//                int adr = 0;
//                while (adr < counter) {
//                    for (int i = 0; i < num_cat.length; i++) {
//                        adr *= num_cat[i];
//                    }
//                }
//                adr += (counter - adr);
//
//
//
//            }
            for (int adr = 0; adr <
                    isNull.length; adr++) {
                if (isNull[adr]) {
                    covar[adr] = overallCovar.clone();
                    for (int i = 0; i <
                            covar[adr].length; i++) {
                        covar[adr][i] = covar[adr][i].clone();
                    }
                    if (cont[adr] == 0) {
                        mean[adr] = overallMean.clone();
                    }
                }

            }

        } //empty gaussians
        //numerical sanity check
//        if (indexDataSet_num.length > 0) {
//            for (int i = 0; i < det.length; i++) {
//                det[i] = Math.max(det[i], singCheat);
//            }

        for (int i = 0; i <
                covar.length; i++) {
            for (int j = 0; j <
                    covar[i].length; j++) {
                for (int k = 0; k <
                        covar[i][j].length; k++) {
                    covar[i][j][k] = Math.max(covar[i][j][k], singCheat);
                }

            }
        }
        for (int i = 0; i <
                mean.length; i++) {
            for (int j = 0; j <
                    mean[0].length; j++) {
                if ((Double.isNaN(mean[i][j]) || Double.isInfinite(mean[i][j]) || Double.isInfinite(-mean[i][j]) && data.numInstances() > 0)) {
                    System.out.println("error: ");
                    print();

                }






            }
        }
        return true;
    }

    private void sanityCheck(double[] probCont) {
        boolean isNull = false;
        boolean sumNotOne = false;
        double sum = 0.0;
        for (int i = 0; i < probCont.length; i++) {
            if (probCont[i] == 0) {
                isNull = true;
            }
            sum += probCont[i];

        }
        if (sum != 1.0 || isNull) {
            System.out.println("error ");
        }

    }

    public void print() {
        StringBuffer sb = new StringBuffer();
        sb.append("SA (");
        for (int i = 0; i <
                attId.size(); i++) {
            sb.append(attId.elementAt(i).toString() + " ");
        }

        sb.append((")"));
        System.out.println(sb.toString());

    }

    public Matrix columnVector(
            double[] v) {
        return new Matrix(v, v.length);
    }

    public Matrix rowVector(
            double[] v) {
        return new Matrix(v, 1);
    }

//for initialization
    public SuperAttribute(Instances data, int aId, int repId) {
        attId = new Vector<Integer>();
        attId.add(aId);
        if (data.attribute(aId).isNominal()) {
            cont = new int[data.attribute(aId).numValues()];
            // for (int i = 0; i < data.numInstances(); i++) {
            cont[(int) data.instance(repId).value(aId)]++;
            //}

            for (int i = 0; i <
                    cont.length; i++) {
                if (cont[i] == 1) {
                    cont[i] = data.numInstances();
                }
//                    cont[i] = (int) ((2.0 / (double) (cont.length + 1)) * data.numInstances());
//                } else {
//                    cont[i] = (int) ((1.0 / (double) (cont.length + 1)) * data.numInstances());
//                }
            }

//            int roundDev = data.numInstances() - counter;
//            cont[(int) data.instance(repId).value(aId)] += roundDev;
//            probCont = new double[cont.length];
            //fillProbCont(data.numInstances());

            num_cat =
                    new int[1];
            num_cat[0] = data.attribute(aId).numValues();
            mean =
                    new double[0][0];
            covar =
                    new double[0][0][0];
            covarL =
                    new double[0][][];
            det =
                    new double[0];
            indexDataSet_cat =
                    new int[1];
            indexDataSet_cat[0] = aId;
            indexDataSet_num =
                    new int[0];
//            for (int i = 0; i < cont.length; i++) {
//                dataCost -= cont[i] / (double) data.numInstances() * log2(cont[i] / (double) data.numInstances());
//            }
//            paramCost = (double) data.attribute(aId).numValues() / 2.0 * log2(data.numInstances());
            check(0.5, data);
        } else {
            cont = new int[0];
            probCont =
                    new double[0];
            mean =
                    new double[1][1];
            covar =
                    new double[1][1][1];
            covarL =
                    new double[1][1][1];
            det =
                    new double[1];
            // for (int i = 0; i < data.numInstances(); i++) {
            mean[0][0] = data.instance(repId).value(aId);
            //}
            //for (int i = 0; i < data.numInstances(); i++) {
            covar[0][0][0] = 1.0;
            covarL[0][0][0] = 1.0;
            det[0] = 1.0;
            //}
            indexDataSet_num =
                    new int[1];
            indexDataSet_num[0] = aId;
            indexDataSet_cat =
                    new int[0];
            num_cat =
                    new int[0];
            //dataCost = entropySingleGaussian(mean[0][0], covar[0][0][0]);
            //paramCost = log2(data.numInstances());
            check(0.5, data);
        }

    }

    private double entropySingleGaussian(double mean, double var) {
        return 0.5 * log2(2 * Math.PI * Math.E * var);
    }

    private double entropyMultivariateGaussian(double[] mean, double[][] covar) {
        // double detSigma = Math.max(singCheat, new Matrix(covar).det());
        double detSigma = new Matrix(covar).det();
        return 0.5 * log2(Math.pow(2.0 * Math.PI * Math.E, mean.length) * detSigma);
    }

    private double entropyMultivariateGaussian(double[][] covarL) {
        double detSigma = 1.0;
        for (int i = 0; i <
                covarL.length; i++) {
            detSigma *= covarL[i][i];
        }

        detSigma *= detSigma;
        return 0.5 * log2(Math.pow(2.0 * Math.PI * Math.E, covarL.length) * detSigma);
    }

    private double log2(double d) {
        return Math.log(d) / Math.log(2);
    }

//     private void dataCheck(double[][] d, String filename) {
//        MLDouble q = new MLDouble("test", d);
//        ArrayList ll = new ArrayList();
//        ll.add(q);
//        MatFileWriter mw = new MatFileWriter();
//        try {
//            String name = filename + ".mat";
//            mw.write(name, ll);
//        } catch (IOException ex) {
//            //Logger.getLogger(VI.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }


    public SuperAttribute(SuperAttribute a, SuperAttribute b, Instances data) {
//        //DEBUG
//        ArffFileWriter af = new ArffFileWriter();
//        af.saveFile("testCov.arff", data);
//        //DEBUG

        this.attId = (Vector<Integer>) a.attId.clone();
        this.attId.addAll(b.attId);
//        //DEBUG
//        if (attId.size() == 3 && attId.elementAt(0) == 0 && attId.elementAt(1) == 6 && attId.elementAt(2) == 3) {
//            System.out.println("m");
//
//        }
//        //DEBUG
        //set up contingency table
        int cat_a = 0;
        int num_a = 0;
        for (int i = 0; i <
                a.attId.size(); i++) {
            if (data.attribute(a.attId.elementAt(i)).isNominal()) {
                cat_a++;
            } else {
                num_a++;
            }

        }
        int cat_b = 0;
        int num_b = 0;
        for (int i = 0; i <
                b.attId.size(); i++) {
            if (data.attribute(b.attId.elementAt(i)).isNominal()) {
                cat_b++;
            } else {
                num_b++;
            }

        }
        indexDataSet_cat = new int[cat_a + cat_b];
        num_cat =
                new int[cat_a + cat_b];
        indexDataSet_num =
                new int[num_a + num_b];
        int counter_cat = 0;
        int counter_num = 0;
        for (int i = 0; i <
                a.attId.size(); i++) {
            if (data.attribute(a.attId.elementAt(i)).isNominal()) {
                indexDataSet_cat[counter_cat] = a.attId.elementAt(i);
                num_cat[counter_cat] = data.attribute(a.attId.elementAt(i)).numValues();
                counter_cat++;

            } else {
                indexDataSet_num[counter_num] = a.attId.elementAt(i);
                counter_num++;

            }






        }
        for (int i = 0; i <
                b.attId.size(); i++) {
            if (data.attribute(b.attId.elementAt(i)).isNominal()) {
                indexDataSet_cat[counter_cat] = b.attId.elementAt(i);
                num_cat[counter_cat] = data.attribute(b.attId.elementAt(i)).numValues();
                counter_cat++;

            } else {
                indexDataSet_num[counter_num] = b.attId.elementAt(i);
                counter_num++;

            }






        }
        int sizeCont = 1;
        for (int i = 0; i <
                num_cat.length; i++) {
            sizeCont *= num_cat[i];
        }

        this.cont = new int[sizeCont];                 //OK???
        probCont =
                new double[sizeCont];
        mean =
                new double[sizeCont][counter_num];
        covar =
                new double[sizeCont][counter_num][counter_num];
        for (int i = 0; i <
                data.numInstances(); i++) {
            int adr = 0;
            for (int j = 0; j <
                    indexDataSet_cat.length; j++) {
                adr *= num_cat[j];
                int category = (int) data.instance(i).value(indexDataSet_cat[j]);
                adr +=
                        category;
            }

            cont[adr]++;
            for (int j = 0; j <
                    counter_num; j++) {
                mean[adr][j] += data.instance(i).value(indexDataSet_num[j]);
            }

        }
        for (int i = 0; i <
                sizeCont; i++) {
            for (int j = 0; j <
                    counter_num; j++) {
                mean[i][j] /= (double) cont[i];
            }

        }
        //fillProbCont(data.numInstances());

        for (int i = 0; i <
                data.numInstances(); i++) {
            int adr = 0;
            for (int j = 0; j <
                    indexDataSet_cat.length; j++) {
                adr *= num_cat[j];
                int category = (int) data.instance(i).value(indexDataSet_cat[j]);
                adr +=
                        category;
            }

            for (int k = 0; k <
                    counter_num; k++) {
                for (int l = k; l <
                        counter_num; l++) {
                    covar[adr][k][l] += ((data.instance(i).value(indexDataSet_num[l]) - mean[adr][l]) * (data.instance(i).value(indexDataSet_num[k]) - mean[adr][k])) / cont[adr];
                }

            }
        }
        for (int adr = 0; adr <
                sizeCont; adr++) {
            for (int k = 0; k <
                    counter_num; k++) {
                for (int l = k + 1; l <
                        counter_num; l++) {
                    covar[adr][l][k] = covar[adr][k][l];
                }

            }
        }

        check(weightOriginal, data);


//        for (int adr = 0; adr <
//                sizeCont; adr++) {
//            double maxdiff = 0.0;
//            for (int i = 0; i <
//                    indexDataSet_num.length; i++) {
//                double sum = 0.0;
//                for (int j = 0; j <
//                        indexDataSet_num.length; j++) {
//                    sum += Math.abs(covar[adr][i][j]);
//                }
//
//                double diff = (1.0 + singCheat) * sum - (2.0 + singCheat) * covar[adr][i][i];
//                if (diff > maxdiff) {
//                    maxdiff = diff;
//                }
//
//            }
//
//            if (maxdiff > 0.0) {
//                for (int i = 0; i <
//                        indexDataSet_num.length; i++) {
//                    covar[adr][i][i] += maxdiff;
//                }
//
//            }
//        }

         for (int adr = 0; adr < sizeCont; adr++) {
              for (int i = 0; i < indexDataSet_num.length; i++) {
                    covar[adr][i][i] += singCheat;
                }
         }

        covarL = new double[sizeCont][][];
        det =
                new double[sizeCont];

        if (indexDataSet_num.length > 0) {
            for (int adr = 0; adr <
                    sizeCont; adr++) {
                covarL[adr] = new CholeskyDecomposition(new Matrix(covar[adr])).getL().getArray();
                det[adr] = 1.0;
                for (int i = 0; i <
                        covarL[0].length; i++) {
//                //debug
//                System.out.println(adr + " " + " i " + i  + " covarL.length: " + covarL.length);
//                //debug
                    det[adr] *= covarL[adr][i][i];
                }

                det[adr] *= det[adr];
                det[adr] = Math.max(det[adr], singCheat); //without this: costs -infinity
//                if (Double.isNaN(det[adr]) || det[adr] == 0) {
//                    det[adr] = singCheat;
//                }
            }

        }

        dataCost = 0.0;
        for (int adr = 0; adr <
                sizeCont; adr++) {
            double phi = probCont[adr];
            if (counter_num > 0) {
                dataCost += phi * 0.5 * log2(Math.pow(2.0 * Math.PI * Math.E, indexDataSet_num.length) * det[adr]);
            //dataCost += phi * 0.5 * log2(Math.pow(2.0 * Math.PI ,indexDataSet_num.length) * det[adr]);
            //dataCost += phi * 0.5 * log2(Math.pow(2.0 * Math.PI * Math.E, covarL.length) * det[adr]);
            }

            if (phi > 0) {
                dataCost -= phi * log2(phi);
            }

        }
        int numParam = sizeCont + sizeCont * counter_num + sizeCont * counter_num * (counter_num + 1) / 2;
        paramCost =
                (double) numParam / 2.0 * log2(data.numInstances());
        addComplexityCost(data.numAttributes() - 3);
        if (data.numInstances() > 0) {
            if (dataCost == Double.NEGATIVE_INFINITY || Double.isInfinite(dataCost) || Double.isNaN(dataCost)) {
                for (int i = 0; i <
                        attId.size(); i++) {
                    System.out.print(attId.elementAt(i) + " ");
                }

                System.out.print(dataCost + " with dendrogramm");
                System.out.println();
            }
        }
    }
}
