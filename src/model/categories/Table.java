package model.categories;

import java.util.Enumeration;

import javax.swing.tree.TreeNode;

import model.DBNode;
import model.DBNodeComposite;

public class Table extends DBNodeComposite{

	public Table(String name, DBNode parent) {
		super(name, parent);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addChild(DBNode child) {
		// TODO Auto-generated method stub
		if (child != null && child instanceof Attribute){
            Attribute attribute = (Attribute) child;
            this.getChildren().add(attribute);
        }
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.getName();
	}

}
