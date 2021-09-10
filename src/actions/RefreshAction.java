package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import gui.InTabPanel;
import gui.MainView;
import gui.Tab;
import gui.table.TableModel;
import model.categories.Table;

public class RefreshAction extends AbstractAction{

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int indexTaba = Tab.getInstance().getTabbedPane().getSelectedIndex();
		InTabPanel panel = (InTabPanel) Tab.getInstance().getTabbedPane().getComponent(indexTaba);
		
		Table tabela = Tab.getInstance().getTabele().get(indexTaba);
		TableModel model= (TableModel) panel.getTabela().getModel();
		model.setRows(MainView.getinstance().getAppCore().getDatabase().readDataFromTable(tabela.getName()));
		
	}

}
