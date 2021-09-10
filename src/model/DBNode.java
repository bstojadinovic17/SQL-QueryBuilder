package model;

import javax.swing.tree.TreeNode;

public abstract class DBNode implements TreeNode{

	private String name;
	private DBNode parent;
	
	public DBNode(String name, DBNode parent) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.parent = parent;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getName();
	}
	public TreeNode getparent() {
		// TODO Auto-generated method stub
		return parent;
	}
}
