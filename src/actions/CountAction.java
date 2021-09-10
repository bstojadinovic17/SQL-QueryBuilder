package actions;

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
import javax.swing.JPanel;
import javax.swing.JTable;

import gui.InTabPanel;
import gui.MainView;
import gui.Tab;
import gui.table.TableModel;
import model.categories.Table;

public class CountAction extends AbstractAction{

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int indexTaba = Tab.getInstance().getTabbedPane().getSelectedIndex();
		InTabPanel panel = (InTabPanel) Tab.getInstance().getTabbedPane().getComponent(indexTaba);
		
		Table trenutnaTabela = Tab.getInstance().getTabele().get(indexTaba);
		TableModel model= (TableModel) panel.getTabela().getModel();
		JTable tabela = panel.getTabela();
		JFrame frame = new JFrame();
		frame.setLocationRelativeTo(null);
		frame.setSize(500, 500);
		JPanel mainPanel = new JPanel();
		List<String> kolone = new ArrayList<>();
		HashMap<String, JCheckBox> boxovi = new HashMap<>();
		for(int i=0;i<tabela.getColumnCount();i++) {
			kolone.add(tabela.getColumnName(i));
			boxovi.put(tabela.getColumnName(i), new JCheckBox());
		}
		JLabel labela1 = new JLabel("Kolona za count:");
		JComboBox<String> box = new JComboBox<String>();
		for(String s:kolone) {
			box.addItem(s);
		}
		box.setSelectedIndex(0);
		mainPanel.add(labela1);
		mainPanel.add(box);
		JLabel labela2 = new JLabel("Kolone za grupisanje:");
		mainPanel.add(labela2);
		for(String s: kolone) {
			JLabel labela = new JLabel(s);
			JCheckBox checkBox = boxovi.get(s);
			mainPanel.add(labela);
			mainPanel.add(checkBox);
		}
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setSize(50,25);
		btnSubmit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				List<String> zaGroupBy = new ArrayList<>();
				for(String s:kolone) {
					JCheckBox trenutniBox = boxovi.get(s);
					if(trenutniBox.isSelected()) {
						zaGroupBy.add(s);
					}
				}
				model.setRows(MainView.getinstance().getAppCore().getDatabase().countData(trenutnaTabela.getName(), box.getSelectedItem().toString(), zaGroupBy));
				frame.setVisible(false);
			}
			
		});
		mainPanel.add(btnSubmit);
		frame.add(mainPanel);
		frame.setVisible(true);
	}

}
