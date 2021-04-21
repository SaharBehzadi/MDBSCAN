package attributes;

public class NumericalAttribute extends Attribute {
	
	private double globalMean = 0.0;
	private double globalVariance = 0.0;
	private double standardDeviation = 0.0;
	public int id;

	public NumericalAttribute() {}

	public NumericalAttribute(String _name, String _type,double globalmean,double globalvariance){
		super(_name,_type);
		this.globalMean = globalmean;
		this.globalVariance = globalvariance;
	}

	public void setGlobalMean(double _in) {
		this.globalMean = _in;
	}
	
		
	public double getGlobalMean () {
		return this.globalMean;
	}

	public void setGlobalVariance(double _in) {
		this.globalVariance = _in;
	}
	
	public double getGlobalVariance() {
		return this.globalVariance;
	}

	public double getStandardDeviation() {
		return standardDeviation;
	}

	public void setStandardDeviation(double standardDeviation) {
		this.standardDeviation = standardDeviation;
	}
}
