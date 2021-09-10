package gui.table;


import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import model.data.Row;

public class TableModel extends DefaultTableModel{
	
	private List<Row> rows;
	
	private void updateModel() {
		int columnCount = rows.get(1).getFields().keySet().size();
		
		Vector columnVector = DefaultTableModel.convertToVector(rows.get(1).getFields().keySet().toArray());
        Vector dataVector = new Vector(columnCount);

        for (int i=0; i<rows.size(); i++){
            dataVector.add(DefaultTableModel.convertToVector(rows.get(i).getFields().values().toArray()));
        }
        setDataVector(dataVector, columnVector);
    }

    public void setRows(List<Row> list) {
	    if(list.size() == 1){
	        list.add(list.get(0));
        }
        this.rows = list;
        updateModel();
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
    	// TODO Auto-generated method stub
    	return true;
    }
    
    
	}

