package clustering;
import java.util.ArrayList;

import attributes.CategoricalAttribute;
import attributes.NumericalAttribute;
import data.Point;

public class Cluster {
	private boolean noise=false;
	private ArrayList<Point> data_items;
	private ArrayList<NumericalAttribute> numericalAttributes= new ArrayList<NumericalAttribute>();
	private ArrayList<CategoricalAttribute> categoricalAttributes= new ArrayList<CategoricalAttribute>();

	public void setNumericalAttributes(ArrayList<NumericalAttribute> numericalAttributes) {
		this.numericalAttributes = numericalAttributes;
	}

	public void setCategoricalAttributes(ArrayList<CategoricalAttribute> categoricalAttributes) {
		this.categoricalAttributes = categoricalAttributes;
	}

	public Cluster () {}

	public boolean isNoise() {
		return noise;
	}

	public void setNoise(boolean _noise) {
		this.noise = _noise;
	}

	public Cluster (ArrayList<Point> data) {
		this.data_items = data;
	}
	
	public Cluster(Cluster _in){
		this.data_items = new ArrayList<Point>(_in.getDataItems());
		this.numericalAttributes = _in.numericalAttributes;
		this.categoricalAttributes = _in.categoricalAttributes;
	}

	public void setData_items(ArrayList<Point> in){
		
		this.data_items = in;
	}
	
	public ArrayList<Point> getDataItems(){
		
	return this.data_items;
	
	}
	
	public Point getDataItem (int idx) {
	
	return data_items.get(idx);
		
	}

	public void addNumericalAttribute(NumericalAttribute attr){
		this.numericalAttributes.add(attr);
	}

	public ArrayList<NumericalAttribute> getNumericalAttributes(){
		
		return this.numericalAttributes;
	}
	

	public ArrayList<CategoricalAttribute> getCategoricalAttributes(){
		
		return this.categoricalAttributes;
		
	}
	
	
	public void addCategoricalAttribute(CategoricalAttribute attr){
		this.categoricalAttributes.add(attr);
	}
	
	
	public void clearAttributes(){
		
		this.numericalAttributes =  new ArrayList<NumericalAttribute>();
		this.categoricalAttributes = new ArrayList<CategoricalAttribute>();
		
	}
	

}
