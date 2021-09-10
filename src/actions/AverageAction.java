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
import model.DBNode;
import model.categories.Attribute;
import model.categories.Table;
import model.enums.AttributeType;

public class AverageAction extends AbstractAction{

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
		for(DBNode a: trenutnaTabela.getChildren()) {
			Attribute attribute = (Attribute) a;
			if(attribute.getAttributeType().toString().contains("INT") || attribute.getAttributeType().equals(AttributeType.NUMERIC)
				|| attribute.getAttributeType().equals(AttributeType.FLOAT) || attribute.getAttributeType().equals(AttributeType.DECIMAL)
				|| attribute.getAttributeType().equals(AttributeType.REAL)
			) {
				kolone.add(attribute.getName());
			}
			boxovi.put(attribute.getName(), new JCheckBox());
		}
		
		JLabel labela1 = new JLabel("Kolona za avg:");
		JComboBox<String> box = new JComboBox<String>();
		for(String s: kolone) {
			box.addItem(s);
		}
		box.setSelectedIndex(0);
		mainPanel.add(labela1);
		mainPanel.add(box);
		JLabel labela2 = new JLabel("Kolone za grupisanje:");
		mainPanel.add(labela2);
		for(int i=0;i<tabela.getColumnCount();i++) {
			JLabel labela = new JLabel(tabela.getColumnName(i));
			JCheckBox checkBox = boxovi.get(tabela.getColumnName(i));
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
				for(int i=0;i<tabela.getColumnCount();i++) {
					JCheckBox trenutniBox = boxovi.get(tabela.getColumnName(i));
					if(trenutniBox.isSelected()) {
						zaGroupBy.add(tabela.getColumnName(i));
					}
				}
				model.setRows(MainView.getinstance().getAppCore().getDatabase().averageData(trenutnaTabela.getName(), box.getSelectedItem().toString(), zaGroupBy));
				frame.setVisible(false);
			}
		});
		mainPanel.add(btnSubmit);
		frame.add(mainPanel);
		frame.setVisible(true);
	}

}
