package model;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;


import model.categories.AttributeConstraints;



public  class DBNodeComposite extends DBNode{
	
	private List<DBNode> children;
	public DBNodeComposite(String name, DBNode parent) {
		super(name, parent);
		// TODO Auto-generated constructor stub
		this.children = new ArrayList<>();
	}
	
	public void addChild(DBNode child) {
	}
	
	public DBNode getChildByName(String name) {
		for (DBNode child: this.getChildren()) {
			if(name.equals(child.getName())) {
				return child;
			}
		}
		
		return null;
	}
	
	public List<DBNode> getChildren() {
		return children;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	@Override
	public Enumeration children() {
		// TODO Auto-generated method stub
		return (Enumeration) getChildren();
	}

	@Override
	public boolean getAllowsChildren() {
		
		return true;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		// TODO Auto-generated method stub
		return getChildren().get(childIndex);
	}

	@Override
	public int getChildCount() {
		// TODO Auto-generated method stub
		return getChildren().size();
	}

	@Override
	public int getIndex(TreeNode node) {
		// TODO Auto-generated method stub
		return getChildren().indexOf(node);
	}

	@Override
	public TreeNode getParent() {
		// TODO Auto-generated method stub
		return getparent();
	}

	@Override
	public boolean isLeaf() {
		// TODO Auto-generated method stub
		
		return false;
	}

}
