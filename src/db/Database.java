package db;

import java.util.HashMap;
import java.util.List;

import compiler.Query;
import model.DBNode;
import model.categories.Table;
import model.data.Row;
import utils.RowWithTableName;

public interface Database {
	
	DBNode loadResourse();
	
	List<Row> readDataFromTable(String tableName);
	
	void deleteData(String from, String column, String value);
	
	void addData(Table tabela);
	
	void updateData(Table tabela, String pk ,HashMap<String, String> rowData);
	
	List<Row> filterIsortData(String from, HashMap<String, String> data);
	
	List<Row> countData(String from, String countColumn, List<String> data);
	
	List<Row> averageData(String from, String averageColumn, List<String> data);

	List<Row> search(String from);

	public List<String> getTableModelFromRow(String tableName);

	public RowWithTableName getTableModelFromTableName(String main, HashMap<String,String> map, int iterate);

	List<Row> executeQ(Query query);
}
