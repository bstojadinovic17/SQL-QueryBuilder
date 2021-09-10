package gui.tree;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import model.categories.Attribute;
import model.categories.AttributeConstraints;
import model.categories.Resourse;
import model.categories.Table;

public class TreeCellRenderer extends DefaultTreeCellRenderer{
	
	public TreeCellRenderer() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public Component getTreeCellRendererComponent(
			JTree tree,
			Object value,
			boolean sel,
			boolean expanded,
			boolean leaf,
			int row,
			boolean hasFocus) {
				super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
				
				if (value instanceof Resourse) {
					setIcon(new ImageIcon("icons/database.png"));
				}
				if (value instanceof Table) {
					setIcon(new ImageIcon("icons/table.png"));
				}
				if (value instanceof Attribute) {
					setIcon(new ImageIcon("icons/attribute.png"));
				}
				if (value instanceof AttributeConstraints) {
					setIcon(new ImageIcon("icons/constraints.png"));
				}
		return this;
	}

}
