package clustering;

import attributes.AttributeStructure;
import attributes.CategoricalAttribute;
import attributes.NumericalAttribute;
import data.Point;
import misc.Utils;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.jfree.data.general.Dataset;

import javax.rmi.CORBA.Util;
import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by mahmoud on 17.12.17.
 */
public class MDL {

    private  ArrayList<Cluster> clusterListe;
    private  ArrayList<NumericalAttribute> numericalAts;
    private  ArrayList<CategoricalAttribute> categoricalAts;

    private double totalNumCosts = 0.0;
    private double totalCatCosts = 0.0;
    private double totalParamCosts = 0.0;
    private double totalIDCosts = 0.0;
    private double parameterCostCat = 0.0;
    private double totalCost  = 0.0;



    public MDL( ArrayList<Cluster> result, AttributeStructure dataset) {
        this.clusterListe = result;
        numericalAts = dataset.getNumericalAttributes();
        categoricalAts = dataset.getCategoricalAttributes();
    }


    public double clusterCosts(){
        int totalNumerofPointsInCLuster = 0;
        int clusterNumber = clusterListe.size();
        for(int x = 0; x<clusterNumber; x++){
            totalNumerofPointsInCLuster += clusterListe.get(x).getDataItems().size();
        }

        int numAttributesNumber = numericalAts.size();
        for (int i = 0; i < clusterNumber; i++) {
            ArrayList<Point> clusterPoints = clusterListe.get(i).getDataItems();
            int clusterPointsNumber = clusterPoints.size();
            if(clusterListe.get(i).getDataItems().size()>0) {
                //Coding Numerical Data
                totalNumCosts = numCosts(clusterPoints);
                // Coding Categorical Data
                totalCatCosts = catCosts(clusterPoints);

                //Parameter Cost
                totalParamCosts = ((parameterCostCat + numAttributesNumber * 2) / 2) * (Utils.log2(clusterPointsNumber));

                // ID Cost
                double idTmpCost = (double) totalNumerofPointsInCLuster / clusterPointsNumber;
                totalIDCosts = Utils.log2(idTmpCost);
                //System.out.println("idcost: " + totalIDCosts);
            /*System.out.println("totalNumCosts: "+totalNumCosts +" , totalCatCosts: "+totalCatCosts
                + " , totalParamCosts: "+ totalParamCosts +", idTmpCost: "+idTmpCost );*/
                totalCost += totalNumCosts + totalCatCosts + totalParamCosts + totalIDCosts;
            }

        }
       // System.out.println("TOTAL: "+ totalCost);

        return totalCost;
    }

    private double catCosts (ArrayList<Point> clusterPoints){
        int clusterPointsNumber = clusterPoints.size();
        double clasterCatCosts = 0.0;
        double pcostCat = 0.0;
        int catAttributesNumber = categoricalAts.size();
        for(int m=0; m<catAttributesNumber;m++){
            ArrayList<String> catValues = new ArrayList<>();
            int catValueCounter = 0; //types
            double columnCatCosts = 0.0;
            //see how many different Categorical values are there. and add them to list
            for(int k=0; k<clusterPointsNumber; k++){
                String des = clusterPoints.get(k).getCategoricalvalues()[m];
                if(!catValues.contains(des)){
                    catValues.add(des);
                    catValueCounter++;
                }
            }
            pcostCat = catValueCounter -1;
            int[] catTypesNumber = new int[catValueCounter];
            for(int x=0; x<catValueCounter;x++) catTypesNumber[x] = 0;

            //  Counter of every Categorical values
            for(int k=0; k<clusterPointsNumber; k++){
                for(int l=0; l<catValueCounter; l++){
                    String des = catValues.get(l);
                    if(clusterPoints.get(k).getCategoricalvalues()[m]!=null && clusterPoints.get(k).getCategoricalvalues()[m].equals(des)){
                        catTypesNumber[l] +=1 ;
                    }
                }
            }

            double[] pdfCat = new double[catValueCounter];
            //Probability of every Categorical value.
            for(int l=0; l<catValueCounter; l++){
                pdfCat[l] = (double)catTypesNumber[l]/(double)clusterPointsNumber ;
            }

            // costs
            for(int k=0; k<clusterPointsNumber; k++){
                for(int l=0; l<catValueCounter; l++){
                    String des = catValues.get(l);
                    if(clusterPoints.get(k).getCategoricalvalues()[m]!=null && clusterPoints.get(k).getCategoricalvalues()[m].equals(des)){
                        columnCatCosts +=  pdfCat[l] *((Utils.log2(pdfCat[l]))*-1);
                    }
                }
            }
            clasterCatCosts += columnCatCosts;
            parameterCostCat += pcostCat;
        }

        return clasterCatCosts;
    }




    public double numCosts( ArrayList<Point> clusterPoints) {
        int numAttributesNumber = numericalAts.size();
        int clusterPointsNumber = clusterPoints.size();
        double clasterNumericalCosts = 0.0;
        double columnNumericalCosts  = 0.0;
        for(int m = 0; m < numAttributesNumber; m++) {
            double cluster_mean = 0;
            double cluster_variance = 0;
            double[] values = new double[clusterPointsNumber];
            for (int j = 0; j < clusterPointsNumber; j++) {
                values[j] = clusterPoints.get(j).getNumericalvalues()[m];
            }

            /*double[] normaizedValues = new double[clusterPointsNumber];
            double min = Utils.findMin(values);
            double max = Utils.findMax(values);
            for (int j = 0; j < clusterPointsNumber; j++) {
                normaizedValues[j]  = (values[j] - min) / (max -min) ;
            }*/

            cluster_mean = Utils.calculateMean(values);
            cluster_variance = Utils.calculateVariance(values, cluster_mean);

            //NormalDistribution dist = new NormalDistribution(cluster_mean,Math.sqrt(cluster_variance));

            columnNumericalCosts = 0.0;
            for (int k = 0; k < clusterPointsNumber; k++) {
                double cost = 0.0;
               // double val = normaizedValues[k];
                double val = values[k];


                double pdfvalue = Utils.pdf(val,cluster_mean, Math.sqrt(cluster_variance));
                    //System.out.println("pdf: "+ xxx);
                if (pdfvalue == 0) pdfvalue = 10E-7;
                cost = pdfvalue * (Utils.log2(pdfvalue) * -1) ;
               // cost = pdfvalue;
                columnNumericalCosts += cost;
            }
            //System.out.println("column NUM Cost: "+ columnNumericalCosts);
            clasterNumericalCosts += columnNumericalCosts;
        }
        return clasterNumericalCosts;
    }
}
