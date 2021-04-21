package data;
import java.util.ArrayList;

import attributes.AttributeStructure;
import attributes.NumericalAttribute;
import misc.Utils;
public class DataSet extends ArrayList<Point> {

	private AttributeStructure attributes = null;
	
	public DataSet(){}
	
	public DataSet(String data,AttributeStructure _attributes, String format){
		  this.attributes = _attributes;
          String lines [] = data.split("\n");
          for(int i=0;i<lines.length;i++){
			  double[] numericalvalues = new double[attributes.getNumOfNumericals()];
			  String[] categoricalvalues = new String[attributes.getNumOfCategoricals()];
			  int dataLength = attributes.getNumOfNumericals() + attributes.getNumOfCategoricals();
			  if(lines[i].length()==0) continue;
			  String parts [] = lines[i].split("\t");
			  Point item = new Point(i);
			  int numcount = 0;
			  int catcount = 0;
			   for(int j=0;j<dataLength ;j++){
				   if(attributes.get(j).getType().equals("Numerical")) {
						  if(!parts[j].equals("?")){
						  Double x1 = new Double(parts[j]);
						  numericalvalues[numcount] = x1;
						  }
						  else numericalvalues[numcount] = 0;
						  numcount ++;
				   }
				   else {
					   String s1 = new String(parts[j]);
						 categoricalvalues[catcount] = s1;
						 catcount ++;
				   }

			   } //for j

			  item.setNumericalvalues(numericalvalues);
			  item.setCategoricalvalues(categoricalvalues);
			  this.add(item);
          }//for i

          setGolbalMeans();
          setGlobalVariances();
          setGlobalStandardDeviation();
	}//DataSet
	
	public void setGolbalMeans(){
		ArrayList<NumericalAttribute> numericalAts = attributes.getNumericalAttributes();
		for(int i=0; i<numericalAts.size();i++){
			NumericalAttribute at = numericalAts.get(i);
			double [] values = new double[this.size()];
			for(int j=0;j<this.size();j++){
				values[j] = this.get(j).getNumericalvalues()[i];
			}
			double golbalmean = Utils.calculateMean(values);
			at.setGlobalMean(golbalmean);
		}
 }
	
  public void setGlobalVariances(){
		ArrayList<NumericalAttribute> numericalAts = attributes.getNumericalAttributes();
		for(int i=0; i<numericalAts.size();i++){
			NumericalAttribute at = numericalAts.get(i);
			double [] values = new double[this.size()];
			for(int j=0;j<this.size();j++){
				values[j] = this.get(j).getNumericalvalues()[i];
			}
			double globalvar = Utils.calculateVariance(values, ((NumericalAttribute)at).getGlobalMean());
			at.setGlobalVariance(globalvar);
		}
  }
 
 public void setGlobalStandardDeviation(){
	 ArrayList<NumericalAttribute> numericalAts = attributes.getNumericalAttributes();
	 for(int i=0; i<numericalAts.size();i++){
		 NumericalAttribute at = numericalAts.get(i);
		 double [] values = new double[this.size()];
		 for(int j=0;j<this.size();j++){
			 values[j] = this.get(j).getNumericalvalues()[i];
		 }
		 double golbalsd = Math.sqrt(at.getGlobalVariance());
		 at.setGlobalMean(golbalsd);
	 }
 }

	
 public AttributeStructure getAttributeStructure(){
	return this.attributes;
 }

 public void setAttributeStructure(AttributeStructure _attributes){
 	this.attributes = _attributes;
 }

 public ArrayList<Point> getDataset(){
 	return this;
 }

}//class
