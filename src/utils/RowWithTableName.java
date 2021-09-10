package utils;

import model.data.Row;

import java.util.ArrayList;
import java.util.List;

public class RowWithTableName {

    private List<Row> rows = new ArrayList<>();
    private String tableName;


    public List<Row> getRows() {
        return rows;
    }

    public String getTableName() {
        return tableName;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
