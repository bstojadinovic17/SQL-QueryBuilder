package actions;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;

import gui.InTabPanel;
import gui.MainView;
import gui.Tab;
import gui.table.TableModel;
import model.categories.Table;

public class SortAction extends AbstractAction{

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int indexTaba = Tab.getInstance().getTabbedPane().getSelectedIndex();
		InTabPanel panel = (InTabPanel) Tab.getInstance().getTabbedPane().getComponent(indexTaba);
		
		Table trenutnaTabela = Tab.getInstance().getTabele().get(indexTaba);
		TableModel model= (TableModel) panel.getTabela().getModel();
		JTable tabela = panel.getTabela();
		List<String> kolone = new ArrayList<String>();
		List<String> zaSort = new ArrayList<String>();
		JFrame frame = new JFrame();
		JFrame frame2 = new JFrame();
		frame.setSize(500, 500);
		frame.setLocationRelativeTo(null);
		JPanel mainPanel = new JPanel();			
		HashMap<String, JCheckBox> filterBoxovi = new HashMap<String, JCheckBox>();
		HashMap<String, JComboBox<String>> comboBoxovi = new HashMap<String, JComboBox<String>>();
		//JPanel filterPanel = new JPanel(new FlowLayout());
		for(int i=0;i<tabela.getColumnCount();i++) {
			kolone.add(tabela.getColumnName(i));
			filterBoxovi.put(tabela.getColumnName(i), new JCheckBox());
		}
		mainPanel.add(new JLabel("Kolone za filter:"));
		for(String s: kolone) {
			JLabel labela = new JLabel(s+":");
			mainPanel.add(labela);
			mainPanel.add(filterBoxovi.get(s));
		}
		JButton btnNext = new JButton("Next");
		btnNext.setSize(50,25);
		btnNext.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				for(String s: kolone) {
					if(filterBoxovi.get(s).isSelected()) {
						zaSort.add(s);
						JComboBox<String> ascdesc = new JComboBox<String>();
						ascdesc.addItem("ASC");
						ascdesc.addItem("DESC");
						comboBoxovi.put(s, ascdesc);
					}
				}
				if(zaSort.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Morate selektovati bar jednu kolonu!");
					return;
				}
				frame.setVisible(false);
				frame2.setSize(500,500);
				frame2.setLocationRelativeTo(null);
				JPanel panel2 = new JPanel();
				for(String s:zaSort) {
					JLabel labela = new JLabel(s);
					panel2.add(labela);
					panel2.add(comboBoxovi.get(s));
				}
				JButton btnSubmit = new JButton("Submit");
				btnSubmit.setSize(50,25);
				btnSubmit.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						HashMap<String, String> sortData = new HashMap<String, String>();
						for(String s:zaSort) {
							sortData.put(s, comboBoxovi.get(s).getSelectedItem().toString());
						}
						frame2.setVisible(false);
						model.setRows(MainView.getinstance().getAppCore().getDatabase().filterIsortData(trenutnaTabela.getName(), sortData));
					}
				});
				panel2.add(btnSubmit);
				frame2.add(panel2);
				frame2.setVisible(true);
			}
		});
		mainPanel.add(btnNext);
		frame.add(mainPanel);
		frame.setVisible(true);
	}

}
