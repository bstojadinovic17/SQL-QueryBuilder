package main;

import db.Database;
import db.DatabaseImpl;
import db.SQLrepositoryImpl;
import db.settings.Settings;
import db.settings.SettingsImpl;
import gui.table.TableModel;
import model.categories.Resourse;
import utils.DBdata;

public class AppCore {
	
	private Database database;
	private Settings settings;
	private TableModel tableModel;
	
	public AppCore() {
		// TODO Auto-generated constructor stub
		this.settings = initSettings();
		this.database = new DatabaseImpl(new SQLrepositoryImpl(this.settings));
		tableModel = new TableModel();
	}
	
	
	private Settings initSettings() {
		Settings settingsImpl = new SettingsImpl();
		 	settingsImpl.addParameter("mssql_ip", DBdata.MSSQL_IP);
		 	settingsImpl.addParameter("mssql_database", DBdata.MSSQL_DATABASE);
		 	settingsImpl.addParameter("mssql_username", DBdata.MSSQL_USERNAME);
		 	settingsImpl.addParameter("mssql_password", DBdata.MSSQL_PASSWORD);
		return settingsImpl;
	}
	
	public Resourse loadResource() {
		Resourse resource = (Resourse) this.database.loadResourse();
		return resource;
	}
	
	public void ReadDataFromTable(String fromTable) {
		tableModel.setRows(this.database.readDataFromTable(fromTable));
	}
	
	
	
	public Database getDatabase() {
		return database;
	}
	public Settings getSettings() {
		return settings;
	}
	
	public TableModel getTableModel() {
		return tableModel;
	}
	public void setTableModel(TableModel tableModel) {
		this.tableModel = tableModel;
	}
}
