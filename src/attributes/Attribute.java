package attributes;

public class Attribute {
	
	private String name;
	private String type;

	public Attribute(){}

	public Attribute(String name, String type){
		this.name = name;
		this.type = type;
	}
	
	public void setName(String name){
		this.name = name;
	}

	public void setType(String type){
	    this.type = type;
	}

	public String getType(){
		return this.type;
	}
	public String getName(){
	    return this.name;
	}

}
