package model.categories;

import java.util.Enumeration;

import javax.swing.tree.TreeNode;

import model.DBNode;
import model.DBNodeComposite;
import model.enums.ConstraintType;


public class Resourse extends DBNodeComposite {

	
	
	public Resourse(String name) {
		super(name, null);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addChild(DBNode child) {
		// TODO Auto-generated method stub
		if (child != null && child instanceof Table){
            Table table = (Table) child;
            this.getChildren().add(table);
        }
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

}
