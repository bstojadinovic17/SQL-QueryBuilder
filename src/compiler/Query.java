package compiler;

import java.util.ArrayList;

public class Query {

    private String name;
    private ArrayList<QueryFunction> allFunctions;
    private String generatedSQL;
    private String tableName;
    public Query(String name) {
        this.name = name;
        this.allFunctions = new ArrayList<>();
        this.generatedSQL = "";
        this.tableName = "";
    }

    public String getName() {
        return name;
    }

    public ArrayList<QueryFunction> getAllFunctions() {
        return allFunctions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("------------------------");
        sb.append("\n");
        sb.append(name);
        sb.append("\n");
        for(QueryFunction qf: allFunctions){
            sb.append(qf.getName());
            sb.append("->");
            for(String atr: qf.getAtributes()){
                sb.append(atr);
                sb.append(" , ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public ArrayList<QueryFunction> getFunctionByName(String name){
        ArrayList<QueryFunction> returnFunc = new ArrayList<>();

        for(QueryFunction qf: allFunctions){
            if(qf.getName().equalsIgnoreCase(name)){
                returnFunc.add(qf);
            }

            if(name.equals("agr")){
                if (qf.getName().equalsIgnoreCase("min") || qf.getName().equalsIgnoreCase("max")
                    || qf.getName().equalsIgnoreCase("avg") || qf.getName().equalsIgnoreCase("count")){
                    returnFunc.add(qf);
                }
            }
        }
        return returnFunc;
    }

    public String getGeneratedSQL() {
        return generatedSQL;
    }

    public void setGeneratedSQL(String generatedSQL) {
        this.generatedSQL = generatedSQL;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAllFunctions(ArrayList<QueryFunction> allFunctions) {
        this.allFunctions = allFunctions;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
