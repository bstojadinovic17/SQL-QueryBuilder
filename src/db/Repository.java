package db;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import compiler.Query;
import model.DBNode;
import model.categories.Attribute;
import model.categories.Table;
import model.data.Row;
import utils.RowWithTableName;

public interface Repository {
	
	DBNode getSchema();
	
	List<Row> get(String from);

	void delete(String from, String column, String value);
	
	void add(Table toTableName);
	

	List<Row> count(String from, String countColumn, List<String> data);

	List<Row> average(String from, String averageColumn, List<String> data);

	List<String> getTableModelFromRow(String tableName);

	void update(Table tabela, String pk, HashMap<String, String> rowData);

	List<Row> filterIsort(String from, HashMap<String, String> data);

	List<Row> search(String from);

	public RowWithTableName getTableModelFromTableName(String main, HashMap<String,String> map, int iterate);

    List<Row> executeQ(Query query);
}
