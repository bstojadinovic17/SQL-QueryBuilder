package gui;

import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

public class ToolBar extends JToolBar{
	
	public ToolBar() {
		// TODO Auto-generated constructor stub
		
		super(SwingConstants.HORIZONTAL);
		
		add(new JLabel("File"));
		addSeparator();
		
		add(new JLabel("Analize"));
		addSeparator();

		
		add(new JLabel("Window"));
		addSeparator();

		
		add(new JLabel("Help"));
		addSeparator();

	}
}
