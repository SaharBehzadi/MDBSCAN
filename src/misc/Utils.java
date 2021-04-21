package misc;


import java.util.Random;
import attributes.AttributeStructure;
import data.Point;
import static java.lang.Math.log;
import tree.Node;
import tree.Tree;


public class Utils {
	public static double MIN_VAR = 1E-2;

	public static double calculateMean(double[] values) {
		double mean = 0;
		for (int j = 0; j < values.length; j++) {
			mean += values[j];
		}
		mean /= values.length;
		return mean;
	}

	public static double calculateVariance(double[] values, double mean) {
		double var = 0;
		for (int j = 0; j < values.length; j++) {
			var += Math.pow(values[j] - mean, 2);
		}
		var /= values.length;
		return var;
	}

	public static double log2(double x) {
		return log(x) / log(2);
	}

	public static String trim(String str) {
		return str.trim().replaceAll("_", " ");
	}

	public static int getRandomInt(Random random, int min, int max) {
		return random.nextInt(max - min + 1) + min;
	}

	public static double calculateDistance(AttributeStructure att, Point p, Point q) {
		double sum = 0.0;
		int numbericalDimension = p.getNumericalvalues().length;
		int catgeoryNumber = p.getCategoricalvalues().length;
		for(int i=0; i<numbericalDimension;i++){
			double df = p.getNumericalvalues()[i] - q.getNumericalvalues()[i];
			double df_square = df * df;
			sum+=df_square;
		}
		for(int i=0; i<catgeoryNumber; i++){
			Node root = att.getCategoricalAttributes().get(i).rootNode;
			if(p.getCategoricalvalues()[i]!=null || q.getCategoricalvalues()[i]!=null){
			Node node1 = Tree.getNodeByDesc( root, p.getCategoricalvalues()[i].toString());
			Node node2 = Tree.getNodeByDesc(root,q.getCategoricalvalues()[i].toString());
			double distance = Tree.getNodesDistance(node1,node2,root);
			sum+= (distance * distance);
			}
		}
		return Math.sqrt(sum);
	}

	// return pdf(x) = standard Gaussian pdf
	public static double pdf(double x) {
		return Math.exp(-x*x / 2) / Math.sqrt(2 * Math.PI);
	}

	// return pdf(x, mu, signma) = Gaussian pdf with mean mu and stddev sigma
	public static double pdf(double x, double mu, double sigma) {
		return pdf((x - mu) / sigma) / sigma;
	}


	public static double findMin(double values[]) {
		double min = values[0];
		for(int i=0; i<values.length; i++){
			if (values[i] < min) {
				min = values[i];
			}
		}
		return min;
	}

	public static double findMax(double values[]) {
		double max = values[0];
		for(int i=0; i<values.length; i++){
			if (values[i] > max) {
				max = values[i];
			}
		}
		return max;
	}

	public static double[] scaleBetween(double[] unscaledNum, double minAllowed, double maxAllowed, double min, double max){
		double[] values = new double[unscaledNum.length];
			for(int i=0; i<unscaledNum.length; i++){
				values[i] = (maxAllowed - minAllowed) * (unscaledNum[i] - min) / (max - min) + minAllowed;
				//System.out.println("value: "+values[i]);
			}
		return values;
	}
}
