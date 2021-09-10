package gui;

import model.DBNode;
import model.categories.Attribute;
import model.categories.AttributeConstraints;
import model.categories.Table;
import model.enums.ConstraintType;
import observer.Observable;
import observer.Observer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class InTabPanel extends JPanel implements Observable {
	
	private JTable tabela;
	private JToolBar toolbar;
	private List<Observer> observerList = new ArrayList<>();

	public InTabPanel(JTable table) {
		addObserver(TabDole.getInstance());
		// TODO Auto-generated constructor stub
		this.tabela = table;
		InTabPanel panelaaaa = this;

		tabela.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent event) {

				// do some actions here, for example
				// print first column value from selected row
				int indexTaba = Tab.getInstance().getTabbedPane().getSelectedIndex();
				boolean pomBool = false;
				Table myTable = Tab.getInstance().getTabele().get(indexTaba);
				ArrayList<DBNode> atributi = (ArrayList<DBNode>) myTable.getChildren();
				List<String> columnNames = new ArrayList<>();
				List<String> pom = new ArrayList<>();
				HashMap<String, String> hashMap = new HashMap<>();

				for(DBNode node:atributi) {
					Attribute a = (Attribute) node;
					for(DBNode ogr:a.getChildren()) {
						AttributeConstraints ogranicenje = (AttributeConstraints) ogr;
						if(ogranicenje.getConstraintType().equals(ConstraintType.FOREIGN_KEY)) {
							columnNames.add(ogranicenje.getParent().toString());

						}
					}
				}
				int columnIndex = 0;
				for(int i=0;i<tabela.getModel().getColumnCount();i++) {
					if(columnNames.contains(tabela.getColumnName(i))) {
						columnIndex = i;
						pom.add(tabela.getColumnName(i));
						try{
							hashMap.put(tabela.getColumnName(i), (String) tabela.getModel().getValueAt(tabela.getSelectedRow(), columnIndex));
						}catch (Exception e){
							pomBool = true;
							TabDole.getInstance().getTabbedPane().removeAll();
							//System.out.println((String)tabela.getModel().getValueAt(tabela.getSelectedRow(), columnIndex));
							//return;
						}


					}
				}

				if(pomBool){
					return;
				}

				if(pom.contains("manager_id") && pom.contains( "employee_id")){
					hashMap.replace("employee_id" , hashMap.get("manager_id"));
					hashMap.remove("manager_id");
				}

				System.out.println(hashMap);



				panelaaaa.notify(myTable.toString(), hashMap, 1);
			}
		});

		toolbar = new JToolBar();
		toolbar.setSize(700,80);
		setSize(new Dimension(700, 350));
		setLayout(new BorderLayout());
		JScrollPane scrollTable = new JScrollPane(tabela);
		scrollTable.setMinimumSize(new Dimension(700, 270));
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setSize(new Dimension(40,25));
		btnAdd.addActionListener(MainView.getinstance().getActionManager().getAddAction());
		toolbar.add(btnAdd);
		toolbar.addSeparator();
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setSize(new Dimension(40,25));
		btnDelete.addActionListener(MainView.getinstance().getActionManager().getDeleteAction());
		toolbar.add(btnDelete);
		toolbar.addSeparator();
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setSize(new Dimension(40,25));
		btnUpdate.addActionListener(MainView.getinstance().getActionManager().getUpdateAction());
		toolbar.add(btnUpdate);
		toolbar.addSeparator();
		
		
		JButton btnSort = new JButton("Filter & Sort");
		btnSort.setSize(new Dimension(50,25));
		btnSort.addActionListener(MainView.getinstance().getActionManager().getSortAction());
		toolbar.add(btnSort);
		toolbar.addSeparator();
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setSize(new Dimension(40,25));
		btnSearch.addActionListener(MainView.getinstance().getActionManager().getSearchAction());
		toolbar.add(btnSearch);
		toolbar.addSeparator();
		
		JButton btnCount = new JButton("Count");
		btnCount.setSize(new Dimension(40,25));
		btnCount.addActionListener(MainView.getinstance().getActionManager().getCountAction());
		toolbar.add(btnCount);
		toolbar.addSeparator();
		
		JButton btnAverage = new JButton("Average");
		btnAverage.setSize(new Dimension(40,25));
		btnAverage.addActionListener(MainView.getinstance().getActionManager().getAverageAction());
		toolbar.add(btnAverage);
		toolbar.addSeparator();
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setSize(new Dimension(40,25));
		btnRefresh.addActionListener(MainView.getinstance().getActionManager().getRefreshAction());
		toolbar.add(btnRefresh);
		toolbar.addSeparator();
		
		JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollTable, toolbar);
		add(split, BorderLayout.CENTER);
		setVisible(true);
	}
	
	public JTable getTabela() {
		return tabela;
	}


	@Override
	public void addObserver(Observer o) {
		if( o != null && !observerList.contains(o)){
			observerList.add(o);
		}
	}

	@Override
	public void notify(Object o, Object hashMap,  int where) {
		for(Observer obs : observerList){
			obs.update(o, hashMap, where);
		}
	}
}
