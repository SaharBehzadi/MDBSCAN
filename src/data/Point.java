package data;

import clustering.Cluster;

public class Point {

	private int id;
	private double[] numericalvalues;
	private double[] oldnumericalvalues;
	private String[] categoricalvalues;
	private Cluster cluster;
	private int ClusterID;


	public Point(int _id){
		this.id = _id;
	}
	public Point(double[] numericalvalues, String[]categoricalvalues) {
		this.numericalvalues = numericalvalues;
		this.categoricalvalues = categoricalvalues;
	}


	public double[] getOldnumericalvalues() {
		return oldnumericalvalues;
	}

	public void setOldnumericalvalues(double[] oldnumericalvalues) {
		this.oldnumericalvalues = oldnumericalvalues;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double[] getNumericalvalues() {
		return numericalvalues;
	}

	public void setNumericalvalues(double[] numericalvalues) {
		this.numericalvalues = numericalvalues;
	}

	public String[] getCategoricalvalues() {
		return categoricalvalues;
	}

	public void setCategoricalvalues(String[] categoricalvalues) {
		this.categoricalvalues = categoricalvalues;
	}

	public Cluster getCluster() {
		return cluster;
	}

	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}

	public int getClusterID() {
		return ClusterID;
	}

	public void setClusterID(int clusterID) {
		ClusterID = clusterID;
	}
}
