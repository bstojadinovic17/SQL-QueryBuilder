package actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import gui.InTabPanel;
import gui.MainView;
import gui.Tab;
import gui.table.TableModel;
import model.DBNode;
import model.categories.Attribute;
import model.categories.AttributeConstraints;
import model.categories.Table;
import model.enums.ConstraintType;

public class UpdateAction extends AbstractAction{

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		int indexTaba = Tab.getInstance().getTabbedPane().getSelectedIndex();
		InTabPanel panel = (InTabPanel) Tab.getInstance().getTabbedPane().getComponent(indexTaba);
		
		int selektovanRed = panel.getTabela().getSelectedRow();
		if(selektovanRed == -1) {
			JOptionPane.showMessageDialog(null, "Niste selektovali red za izmenu!");
			return;
		}else {
			TableModel model = (TableModel) panel.getTabela().getModel();
			
			Table tabela = Tab.getInstance().getTabele().get(indexTaba);
			List<String> rowData = new ArrayList<String>();
			List<String> headeri = new ArrayList<String>();
			for(int i=0;i<panel.getTabela().getColumnCount();i++) {
				String value = (String) panel.getTabela().getModel().getValueAt(selektovanRed, i);
				headeri.add(panel.getTabela().getColumnName(i));
				rowData.add(value);
			}
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
			pkValue=pkColumnName+":";
			pkValue += (String) panel.getTabela().getModel().getValueAt(selektovanRed, columnIndex);
			HashMap<String, String> data = new HashMap<String, String>();
			for(int i=0;i<headeri.size();i++) {
				data.put(headeri.get(i), rowData.get(i));
			}
			
			//System.out.println(pkValue);
			
			MainView.getinstance().getAppCore().getDatabase().updateData(tabela, pkValue, data);
		}
		
	}

}
