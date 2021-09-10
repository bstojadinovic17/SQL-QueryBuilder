package compiler;

import java.util.ArrayList;

public class QueryFunction {

    private String name;
    private ArrayList<String> atributes;

    public QueryFunction(String name) {
        this.name = name;
        this.atributes = new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    public ArrayList<String> getAtributes() {
        return atributes;
    }

    public void setName(String name) {
        this.name = name;
    }
}
