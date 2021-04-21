package generator;

import clustering.Cluster;
import data.Point;
import misc.Utils;
import tree.Node;
import tree.Tree;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by mahmoud on 15.11.17.
 */

public class GenerateData {
    private Random r = new Random();
    int dimension ;
    int ncategroy ;
    double [] mean;
    double [] sd;
    int npoints;
    ArrayList<Node> nodesListe;
    private  ArrayList<Point>  hset  = new ArrayList<Point>();
    private String[] hset2;
    private String[] nodeSpecific;
    private boolean specific=false;

    private Cluster cluster;

    public GenerateData(Cluster _cluster, int _dimension, int _ncat, int _npoints, double[] _mean, double[] _sd,  ArrayList<Node> _nodesListe){
        this.dimension = _dimension;
        this.ncategroy = _ncat;
        this.npoints = _npoints;
        this.mean = _mean;
        this.sd = _sd;
        this.nodesListe = _nodesListe;
        this.cluster = _cluster;
    }

    public GenerateData(Cluster _cluster, int _dimension, int _ncat, int _npoints, double[] _mean, double[] _sd,  ArrayList<Node> _nodesListe, String[] nodeSpecific){
        this.dimension = _dimension;
        this.ncategroy = _ncat;
        this.npoints = _npoints;
        this.mean = _mean;
        this.sd = _sd;
        this.nodesListe = _nodesListe;
        this.cluster = _cluster;
        this.specific = true;
        this.nodeSpecific = nodeSpecific;
    }

    private double [] generateNumbers(){
        double [] coordinates = new double[this.dimension];
        for(int i=0; i<this.dimension; i++){
            //The nextGaussian() method returns random numbers with a mean of 0 and a standard deviation of 1.
            /* to change the maen (average) of the distribution, we add the required value;
                to change the standard deviation, we multiply the value
            */
            coordinates[i] = r.nextGaussian()*this.sd[i] + this.mean[i];
        }
        return coordinates;
    }

    public void generatePoints(){
        for(int i=0; i< this.npoints; i++){
            Point p = new Point(i);
            double [] coordinates = generateNumbers();
            p.setNumericalvalues(coordinates);
            p.setCategoricalvalues(generateCategory());
            hset.add(p);
        }
        this.cluster.setData_items(hset);
    }

    private String [] generateCategory(){
        hset2 = new String[this.ncategroy] ;
        int k=0;
        for(int i=0; i<this.ncategroy; i++){
            String child = null;
            Node root = this.nodesListe.get(i);
            ArrayList<Node> children = new ArrayList<>();
            if (this.specific) {
                int randomNum = Utils.getRandomInt(r, 0, nodeSpecific.length - 1);
                child = nodeSpecific[randomNum];
            }
            else {
                Tree.getDescendants(root, children);
                int randomNum = Utils.getRandomInt(r, 0, children.size() - 1);
                child = children.get(randomNum).getDescription();
            }
            hset2[k] = child;
            k++;
        }
        return hset2;
    }

}
