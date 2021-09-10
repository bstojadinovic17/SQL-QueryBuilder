package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import actions.ActionManager;
import gui.tree.Tree;
import main.AppCore;
import compiler.Generator;
import compiler.Validator;
import model.tree.TreeModel;
import observer.Observer;


public class MainView extends JFrame  {
	
	private static MainView instance=null;
	
	private AppCore appCore;
	
	private ActionManager actionManager;
	private ToolBar toolbar;
	private DesnoDole desnoDole;
	private TextArea textarea;
	private TreeModel treeModel;
	private Tree tree;
	
	private JTable table;
	private Validator validator = new Validator();
	private Generator generator = new Generator();
	private List<Observer> observerList = new ArrayList<>();
	
	private MainView() {
		
	}
	
	
	private void initialize() {

		actionManager = new ActionManager();
		initializeTree();
		initializeGUI();
	}


	private void initializeGUI() {
		setTitle("DB projekat");
		setSize(1000, 800);
		setLocationRelativeTo(null);
        setLayout(new BorderLayout());
		setVisible(false);
		//setResizable(false);
		toolbar = new ToolBar();
		add(toolbar, BorderLayout.NORTH);
		
		
		JScrollPane scrollTree = new JScrollPane(tree);
		scrollTree.setMinimumSize(new Dimension(250,750));
		
		JPanel desno = new JPanel(new BorderLayout());
        Tab tabovi = Tab.getInstance();
        TabDole tabovi2 = TabDole.getInstance();

		JPanel panelTab = new JPanel(new BorderLayout());
        panelTab.setPreferredSize(new Dimension(700, 350));
        panelTab.add(tabovi.getTabbedPane(), BorderLayout.CENTER);
		desnoDole = DesnoDole.getInstance();
		desnoDole.add(tabovi2.getTabbedPane(), BorderLayout.CENTER);
		desnoDole.setPreferredSize(new Dimension(700, 350));
		//JTextArea textarea = new JTextArea();
		//textarea.setPreferredSize(new Dimension(700, 350));
		textarea = TextArea.getInstance();
		textarea.setPreferredSize(new Dimension(700, 350));
		JSplitPane splitVer=new JSplitPane(JSplitPane.VERTICAL_SPLIT,textarea,panelTab);
		desno.add(splitVer,BorderLayout.SOUTH);
		JSplitPane splitHor=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,scrollTree,desno);
		add(splitHor,BorderLayout.CENTER);
		
	}
	
	private void initializeTree() {
		tree = new Tree();
		treeModel = new TreeModel();
		tree.setModel(treeModel);
	}
	public static MainView getinstance() {
		if(instance==null) {
			instance=new MainView();
		    instance.initialize();
		}
		return instance;
	}
	
	public AppCore getAppCore() {
		return appCore;
	}
	public void setAppCore(AppCore appCore) {
		this.appCore = appCore;
		//initializeTree();
	}
	
	public Tree getTree() {
		return tree;
	}
	public TreeModel getTreeModel() {
		return treeModel;
	}
	
	public JTable getTable() {
		return table;
	}
	public ActionManager getActionManager() {
		return actionManager;
	}

	public Validator getValidator() {
		return validator;
	}

	public Generator getGenerator() {
		return generator;
	}
}
