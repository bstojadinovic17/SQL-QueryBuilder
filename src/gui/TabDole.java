package gui;

import db.SQLrepositoryImpl;
import gui.table.TableModel;
import model.categories.Table;
import model.data.Row;
import observer.Observer;
import utils.RowWithTableName;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class TabDole extends JTabbedPane implements Observer {

    private static TabDole instance = null;
    private JTabbedPane tabbedPane;
    private static ArrayList<Table> tabele;
    private JButton closeButton;

    private TabDole() {
        // TODO Auto-generated constructor stub

        tabele = new ArrayList<>();
        tabbedPane = new JTabbedPane();
        tabbedPane.setSize(new Dimension(700, 350));
        tabbedPane.setVisible(true);
    }



    public static TabDole getInstance() {
        if (instance == null) instance = new TabDole();
        return instance;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public static ArrayList<Table> getTabele() {
        return tabele;
    }

    @Override
    public void update(Object o, Object columnValues, int where) {
        TabDole.getInstance().getTabbedPane().removeAll();
        if(where == 1){
            HashMap<String, String> map = (HashMap<String, String>) columnValues;
            System.out.println(map);
            //System.out.println(o.toString() + " " + where);
            for (int i = 0; i<map.size(); i++){
                RowWithTableName rows = MainView.getinstance().getAppCore().getDatabase().getTableModelFromTableName(o.toString(), map, i);
                if(rows.getRows()!= null){
                    JTable tabela = new JTable();
                    TableModel model = new TableModel();
                    model.setRows(rows.getRows());
                    tabela.setModel(model);
                    tabela.setRowSelectionAllowed(true);
                    InTabPanel panel = new InTabPanel(tabela);
                    tabbedPane.addTab(rows.getTableName(), panel);
                    tabbedPane.setFocusable(true);
                    tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
                }

            }
            System.out.println("----------------------------------");
        }else{
            //System.out.println(MainView.getinstance().getAppCore().getDatabase().getTableModelFromRow(o.toString()));
            for(String s : MainView.getinstance().getAppCore().getDatabase().getTableModelFromRow(o.toString())){
                JTable tabela = new JTable();
                TableModel model = new TableModel();
                model.setRows(MainView.getinstance().getAppCore().getDatabase().readDataFromTable(s));
                tabela.setModel(model);
                tabela.setCellSelectionEnabled(true);
                tabela.setRowSelectionAllowed(false);
                InTabPanel panel = new InTabPanel(tabela);
                tabbedPane.addTab(s, panel);
                tabbedPane.setFocusable(true);
                tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
            }

           // System.out.println(o.toString());
           // System.out.println(where);
        }
        //System.out.println("----------------------------------------");

    }

}