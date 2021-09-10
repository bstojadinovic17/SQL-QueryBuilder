package actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import gui.InTabPanel;
import gui.MainView;
import gui.Tab;
import main.Main;
import model.DBNode;
import model.categories.Attribute;
import model.categories.AttributeConstraints;
import model.categories.Table;
import model.enums.ConstraintType;

public class DeleteAction extends AbstractAction{

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		int indexTaba = Tab.getInstance().getTabbedPane().getSelectedIndex();
		InTabPanel panel = (InTabPanel) Tab.getInstance().getTabbedPane().getComponent(indexTaba);
		
		int selektovanRed = panel.getTabela().getSelectedRow();
		if(selektovanRed == -1) {
			JOptionPane.showMessageDialog(null, "Niste selektovali red za brisanje!");
		}else {
			
			DefaultTableModel model = (DefaultTableModel) panel.getTabela().getModel();
			
			Table tabela = Tab.getInstance().getTabele().get(indexTaba);
			
			ArrayList<DBNode> atributi = (ArrayList<DBNode>) tabela.getChildren();
			String pkColumnName = null;
			String pkValue = null;
			for(DBNode node:atributi) {
				Attribute a = (Attribute) node;
				for(DBNode ogr:a.getChildren()) {
					AttributeConstraints ogranicenje = (AttributeConstraints) ogr;
					if(ogranicenje.getConstraintType().equals(ConstraintType.PRIMARY_KEY)) {
						pkColumnName = ogranicenje.getParent().toString();
					}
				}
			}
			int columnIndex = 0;
			for(int i=0;i<panel.getTabela().getModel().getColumnCount();i++) {
				if(panel.getTabela().getColumnName(i).equals(pkColumnName)) {
					columnIndex = i;
				}
			}
			pkValue = (String) panel.getTabela().getModel().getValueAt(selektovanRed, columnIndex);
			
			MainView.getinstance().getAppCore().getDatabase().deleteData(tabela.getName(), pkColumnName, pkValue);
			model.removeRow(selektovanRed);
		}
	}

}
