import java.io.IOException;
import java.util.ArrayList;

import clustering.Cluster;
import clustering.DBScan;
import clustering.MDL;
import data.DataSet;
import data.Point;
import filehandler.XMLFileHandler;
import generator.GDMain;
import misc.CsvWriter;
import misc.Plot2D;
import misc.Utils;

public class Main {

	public static void main(String[] args) throws IOException {
		//generateData();
		readXML();
	}

	public static void readXML(){
		long start= (long) 0.0;
		long end = (long) 0.0;
		XMLFileHandler handler = new XMLFileHandler();
		//DataSet data = 	handler.read("/home/mahmoud/uni/BACHELOR/clicot/big_data/Airport/airports_data.txt");
		//DataSet data = 	handler.read("/home/mahmoud/Schreibtisch/Teaching_Assistant_Evaluation/data.xml");
		DataSet data = 	handler.read("/home/mahmoud/uni/BACHELOR/clicot/big_data/Adlut_without_MissingValue.txt");
		int treelength = handler.getTree_deep() * handler.getTree_deep() ;
		ArrayList<Point> pointList = data.getDataset();
		int numericalValuesLength = pointList.get(0).getNumericalvalues().length;
		double[][] values = new double [numericalValuesLength][pointList.size()];
		double[] minRange = new double[numericalValuesLength];
		double[] maxRange = new double[numericalValuesLength];

		//save in array
		for(int j=0; j<numericalValuesLength; j++){
			for(int i=0; i<pointList.size();i++) {
				Point p = pointList.get(i);
				p.setOldnumericalvalues(p.getNumericalvalues());
				values[j][i] = p.getNumericalvalues()[j];
			}
		}
		//find max,min for every numerical attribute
		for(int j=0; j<numericalValuesLength; j++){
			minRange[j] = Utils.findMin(values[j]);
			maxRange[j] = Utils.findMax(values[j]);
		}

		// normalize
		double values2[] = new double[pointList.size()];
		for(int j=0; j<numericalValuesLength; j++){
			values2 = Utils.scaleBetween(values[j],0,treelength,minRange[j],maxRange[j]);
			values[j] = values2;
		}


		for(int i=0; i<pointList.size();i++) {
			double [] pointValue = new double[numericalValuesLength];
			for(int j=0; j<numericalValuesLength; j++){
				pointValue[j] = values[j][i];
			}
			Point p = pointList.get(i);
			p.setNumericalvalues(pointValue);
		}

		double minRadius = 0;
		double maxRadius = treelength;
		int iterration = 10;
		ArrayList<Cluster> endResult = new ArrayList<>();
		double minimumMDL = 0.0;
		boolean minMDL = false;
		double myRadius = 0.0;
		double stepIter= (treelength-minRadius)/iterration;
		double m = minRadius + stepIter;
		for( ; m <maxRadius; m+=stepIter){
			DBScan dbscan = new DBScan(data.getAttributeStructure(),m,4);
			//ArrayList<Point> pointList = data.getDataset();
			dbscan.setPointList(pointList);
			ArrayList<Cluster> result = dbscan.applyDbscan();
			MDL mdl = new MDL(result,data.getAttributeStructure());
			double costs =  Math.round(mdl.clusterCosts());
			System.out.println("out: "+costs + " radius: "+ m);
			CsvWriter csvWriter = new CsvWriter(result,data);
			csvWriter.writeToCsv("/home/mahmoud/uni/BACHELOR/DBScan_"+m+"_.csv");
			if (!minMDL ){
				System.out.println("doing first stage, radius: "+m);
				minimumMDL =costs;
				endResult = result;
				myRadius = m;
				minMDL = true;
			}
			else{
				if( minimumMDL > costs ){
					System.out.println("Min: "+ costs + " r: "+ m );
					minimumMDL = costs;
					endResult = result;
					myRadius = m;
				}

			}
			//break;
		}


	  /*DBScan dbscan = new DBScan(data.getAttributeStructure(),2.2,4);
	  ArrayList<Point> pointList = data.getDataset();
	  dbscan.setPointList(pointList);
	  ArrayList<Cluster> result = dbscan.applyDbscan();
	  MDL mdl = new MDL(result,data.getAttributeStructure());
	  mdl.clusterCosts();
		*/
		System.out.println("Minimum is: " + minimumMDL + ", radius: "+ myRadius);
		CsvWriter csvWriter = new CsvWriter(endResult,data);
		csvWriter.writeToCsv("/home/mahmoud/uni/BACHELOR/DBScan.csv");
		if(data.getAttributeStructure().getNumericalAttributes().size() == 2){
			Plot2D plot = new Plot2D("Result of DBScan" ,endResult);
			plot.createPlot();

		}
		System.out.println("DONE");
	}

	private static void generateData(){
		GDMain gd = new GDMain();
		gd.mainGenerate();
	}

}