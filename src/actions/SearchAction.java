package actions;

import gui.InTabPanel;
import gui.MainView;
import gui.Tab;
import gui.table.TableModel;
import model.categories.Table;

import java.awt.event.ActionEvent;


import javax.swing.*;


public class SearchAction extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {


        int indexTaba = Tab.getInstance().getTabbedPane().getSelectedIndex();
        InTabPanel panel = (InTabPanel) Tab.getInstance().getTabbedPane().getComponent(indexTaba);


        Table tabela = Tab.getInstance().getTabele().get(indexTaba);
        TableModel model= (TableModel) panel.getTabela().getModel();

        model.setRows(MainView.getinstance().getAppCore().getDatabase().search(tabela.getName()));




    }
}
