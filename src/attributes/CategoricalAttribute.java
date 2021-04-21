package attributes;

import tree.Node;

public class CategoricalAttribute extends Attribute {

	private String description;
	public Node rootNode;
	public Node Back_Tree;

	public CategoricalAttribute() {}
	
	public CategoricalAttribute(String _name, String _type, Node _node, String _desc, Node _BackNode) {
		super(_name,_type);
		this.rootNode = _node;
		this.description = _desc;
		this.Back_Tree= _BackNode;
	}

	public String getDescription(){
		return this.description;
	}
	
	public void setDescription(String _in){
		this.description = _in;
	}

}
