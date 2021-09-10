package actions;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar.Separator;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.TableView.TableRow;

import gui.InTabPanel;
import gui.MainView;
import gui.Tab;
import model.categories.Table;
import model.data.Row;

public class AddAction extends AbstractAction{
	
	
	public AddAction() {
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int indexTaba = Tab.getInstance().getTabbedPane().getSelectedIndex();
		InTabPanel panel = (InTabPanel) Tab.getInstance().getTabbedPane().getComponent(indexTaba);
		
		DefaultTableModel model = (DefaultTableModel) panel.getTabela().getModel();
		
		Table tabela = Tab.getInstance().getTabele().get(indexTaba);
		MainView.getinstance().getAppCore().getDatabase().addData(tabela);
		
	}

	
	
}
