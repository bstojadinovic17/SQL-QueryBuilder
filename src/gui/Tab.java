package gui;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import gui.table.TableModel;
import model.categories.Table;
import observer.Observable;
import observer.Observer;

public class Tab extends JTabbedPane implements Observable {
	
	private static Tab instance = null;
	private JTabbedPane tabbedPane;
	private static ArrayList<Table> tabele;
	private JButton closeButton;

	private List<Observer> observerList = new ArrayList<>();

	private Tab() {
		// TODO Auto-generated constructor stub
		addObserver(TabDole.getInstance());
		tabele = new ArrayList<>();
		tabbedPane = new JTabbedPane();
		tabbedPane.setSize(new Dimension(700,350));
		tabbedPane.setVisible(true);
	}
	
	public void dodajTab(Object arg, Object o, List<String> columnNames, List<String> values, int where) {
		if(arg instanceof Table) {
			Table t = (Table) arg;
			
			boolean ima=false;
			for(int i=0;i<tabbedPane.getTabCount();i++) {
				if(tabbedPane.getTitleAt(i).equals(t.getName())) {
					tabbedPane.setSelectedIndex(i);
					ima = true;
					notify(o, null, where);
					break;
				}
				
			}
			
			if(ima == false) {
				
				JTable tabela = new JTable();
				TableModel model = new TableModel();
				model.setRows(MainView.getinstance().getAppCore().getDatabase().readDataFromTable(t.getName()));
				tabela.setModel(model);
				tabela.setRowSelectionAllowed(true);
				InTabPanel panel = new InTabPanel(tabela);
				tabbedPane.addTab(t.getName(), panel);
				tabbedPane.setFocusable(true);
				tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
				notify(o, null, 0);
			}
		}
	}
	
	public static Tab getInstance() {
		if(instance==null) instance=new Tab();
		return instance;
	}
	
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}
	
	public static ArrayList<Table> getTabele() {
		return tabele;
	}

	@Override
	public void addObserver(Observer o) {
		if( o != null && !observerList.contains(o)){
			observerList.add(o);
		}
	}

	@Override
	public void notify(Object o,  Object columnValues, int where) {
		for(Observer obs : observerList){
			obs.update(o, columnValues, where);
		}
	}
}
