package actions;

import compiler.Query;
import gui.InTabPanel;
import gui.MainView;
import gui.Tab;
import gui.TextArea;
import gui.table.TableModel;
import main.Main;
import model.categories.Table;
import model.data.Row;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ExecuteAction extends AbstractAction {

    public ExecuteAction() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int indexTaba = Tab.getInstance().getTabbedPane().getSelectedIndex();
        if(indexTaba == -1){
            JOptionPane.showMessageDialog(null, "Izaberite tabelu nad kojom zelite da izvrsite upit!");
            return;
        }
        InTabPanel panel = (InTabPanel) Tab.getInstance().getTabbedPane().getComponent(indexTaba);
        Table trenutnaTabela = Tab.getInstance().getTabele().get(indexTaba);
        TableModel model= (TableModel) panel.getTabela().getModel();

        int queriesSize = MainView.getinstance().getGenerator().getAllqueries().size();
        if(queriesSize == 0){
            JOptionPane.showMessageDialog(null, "Nije generisan nijedan upit!");
            return;
        }
        List<Row> rows = MainView.getinstance().getAppCore().getDatabase().executeQ(MainView.getinstance().getGenerator().getAllqueries().get(queriesSize-1));
        if(rows.size() == 0){
            return;
        }else {
            model.setRows(rows);
        }
    }
}
