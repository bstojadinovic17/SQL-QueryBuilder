package db;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import compiler.Query;
import model.DBNode;
import model.categories.Attribute;
import model.categories.Table;
import model.data.Row;
import observer.Observer;
import utils.RowWithTableName;

public class DatabaseImpl implements Database{
	
	private Repository repository;
	
	public DatabaseImpl(SQLrepositoryImpl sqLrepositoryImpl) {
		// TODO Auto-generated constructor stub
		this.repository = sqLrepositoryImpl;
	}

	@Override
	public DBNode loadResourse() {
		return repository.getSchema();
	}

	@Override
	public List<Row> readDataFromTable(String from) {
		return repository.get(from);
	}

	
	public Repository getRepository() {
		return repository;
	}

	@Override
	public void deleteData(String from, String columnName, String value) {
		// TODO Auto-generated method stub
		repository.delete(from, columnName, value);
	}

	@Override
	public void addData(Table toTableName) {
		// TODO Auto-generated method stub
		repository.add(toTableName);
	}
	
	
	

	@Override
	public List<Row> countData(String from, String countColumn, List<String> data) {
		// TODO Auto-generated method stub
		return repository.count(from,countColumn, data);
	}

	@Override
	public List<Row> averageData(String from, String averageColumn, List<String> data) {
		// TODO Auto-generated method stub
		return repository.average(from,averageColumn, data);
	}

	@Override
	public List<Row> search(String from) {
		return repository.search(from);
	}

	@Override
	public void updateData(Table tabela, String pk, HashMap<String, String> rowData) {
		// TODO Auto-generated method stub
		repository.update(tabela, pk, rowData);
	}

	@Override
	public List<String> getTableModelFromRow(String tableName){
		return repository.getTableModelFromRow(tableName);
	}

	@Override
	public List<Row> filterIsortData(String from, HashMap<String, String> data) {
		// TODO Auto-generated method stub
		return repository.filterIsort(from,data);
	}

	@Override
	public RowWithTableName getTableModelFromTableName(String main, HashMap<String,String> map, int iterate){
		return repository.getTableModelFromTableName(main, map, iterate);
	}

	@Override
	public List<Row> executeQ(Query query) {
		return repository.executeQ(query);
	}

}
