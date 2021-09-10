package model.tree;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import gui.MainView;
import main.AppCore;
import model.categories.Resourse;
import model.categories.Table;

public class TreeModel extends DefaultTreeModel{

	public TreeModel() {
		super(new AppCore().loadResource());
		// TODO Auto-generated constructor stub
	}

	public void addNode(Table t) {
		((TreeModel) getRoot()).addNode(t);
	}

	
	
	
	
	
}
