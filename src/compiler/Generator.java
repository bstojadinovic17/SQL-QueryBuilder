package compiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Generator {

    private ArrayList<Query> allqueries;
    public Generator(){
        allqueries = new ArrayList<>();
    }

    public void generateQuery(ArrayList<Query> allqueries){
        StringBuilder sb = new StringBuilder();
        StringBuilder generatedSQL = new StringBuilder();
        for(Query query: allqueries){
            sortQuery(query);
            ArrayList<QueryFunction> functions = query.getAllFunctions();
            //System.out.println("---------------- " + query.getName());
            for(QueryFunction qf: functions){
                //System.out.println(qf.getName());
                if(qf.getName().equalsIgnoreCase("query")){
                    query.setTableName(qf.getAtributes().get(0));
                }
                if(qf.getName().equalsIgnoreCase("join") || qf.getName().equalsIgnoreCase("on")){
                    String toJoin = "";
                    if(!qf.getName().equalsIgnoreCase("join")){
                        sb.append(parseJoin(qf, toJoin));
                    }else{
                        toJoin = qf.getAtributes().get(0);
                        continue;
                    }
                }else {
                    sb.append(transformFunction(qf));
                }
                //sb.append("\n");
            }

            query.setGeneratedSQL(sb.toString());
            sb.setLength(0);
        }

    }

    public void sortQuery(Query query){
        Query toReturn = new Query(query.getName());
        List<QueryFunction> select = query.getFunctionByName("select");
        List<QueryFunction> agr = query.getFunctionByName("agr");

        for(QueryFunction fun : agr){
            if(select.get(0).getAtributes().contains(fun.getAtributes().get(0))){
                int index = select.get(0).getAtributes().indexOf(fun.getAtributes().get(0));
                select.get(0).getAtributes().set(index, transformFunction(fun));

            }
            else if(fun.getAtributes().size() == 2){
                if(select.get(0).getAtributes().contains(fun.getAtributes().get(1))){
                    int index = select.get(0).getAtributes().indexOf(fun.getAtributes().get(1));
                    select.get(0).getAtributes().set(index, transformFunction(fun));
                }
            }
        }



        toReturn.getAllFunctions().add(select.get(0));

        List<QueryFunction> from = query.getFunctionByName("query");
        toReturn.getAllFunctions().add(from.get(0));

        List<QueryFunction> where = query.getFunctionByName("where");
        if(where.size() == 1){
            toReturn.getAllFunctions().add(where.get(0));
            query.getAllFunctions().remove(where.get(0));

        }

        List<QueryFunction> whereBetween = query.getFunctionByName("wherebetween");
        if(whereBetween.size() == 1){
            toReturn.getAllFunctions().add(whereBetween.get(0));
            query.getAllFunctions().remove(whereBetween.get(0));
        }
        List<QueryFunction> wherestartswith = query.getFunctionByName("wherestartswith");
        if(wherestartswith.size() == 1){
            toReturn.getAllFunctions().add(wherestartswith.get(0));
            query.getAllFunctions().remove(wherestartswith.get(0));
        }
        List<QueryFunction> whereendswith = query.getFunctionByName("whereendswith");
        if(whereendswith.size() == 1){
            toReturn.getAllFunctions().add(whereendswith.get(0));
            query.getAllFunctions().remove(whereendswith.get(0));
        }
        List<QueryFunction> wherecontains = query.getFunctionByName("wherecontains");
        if(wherecontains.size() == 1){
            toReturn.getAllFunctions().add(wherecontains.get(0));
            query.getAllFunctions().remove(wherecontains.get(0));
        }

        List<QueryFunction> whereIn = query.getFunctionByName("wherein");
        if(whereIn.size() == 1){
            toReturn.getAllFunctions().add(whereIn.get(0));
            toReturn.getAllFunctions().add(query.getFunctionByName("parametarlist").get(0));
            query.getAllFunctions().remove(whereIn.get(0));
        }



        for(QueryFunction qf: query.getAllFunctions()){
            if(qf.getName().contains("where") && !(qf.getName().equals("whereinq") || qf.getName().equals("whereeqq"))){
                toReturn.getAllFunctions().add(qf);
                //query.getAllFunctions().remove(qf);
            }
        }


        List<QueryFunction> podupiti = query.getFunctionByName("whereinq");
        podupiti.addAll(query.getFunctionByName("whereeqq"));
        for(QueryFunction qf: podupiti){
            toReturn.getAllFunctions().add(qf);
        }

        List<QueryFunction> groupBys = query.getFunctionByName("groupby");
        if(groupBys.size() == 1) toReturn.getAllFunctions().add(groupBys.get(0));


        List<QueryFunction> having = query.getFunctionByName("having");
        if(having.size() == 1){
            for (QueryFunction qf : agr){
                if(qf.getAtributes().size()==1){
                    if(qf.getAtributes().get(0).equalsIgnoreCase(having.get(0).getAtributes().get(0))){
                        having.get(0).getAtributes().add(0, transformFunction(qf));
                    }else if(qf.getAtributes().get(0).equalsIgnoreCase(having.get(0).getAtributes().get(2))){
                        having.get(0).getAtributes().add(2, transformFunction(qf));
                    }
                }
            }
            toReturn.getAllFunctions().add(having.get(0));
            query.getAllFunctions().remove(having.get(0));
        }

        for(QueryFunction qf: query.getAllFunctions()){
            if(qf.getName().contains("having")){
                for (QueryFunction ag : agr){
                    if(ag.getAtributes().size()==1){
                        if(ag.getAtributes().get(0).equalsIgnoreCase(qf.getAtributes().get(0))){
                            qf.getAtributes().add(0, transformFunction(ag));
                        }else if(ag.getAtributes().get(0).equalsIgnoreCase(qf.getAtributes().get(2))){
                            qf.getAtributes().add(2, transformFunction(ag));
                        }
                    }
                }
                toReturn.getAllFunctions().add(qf);
            }
        }
        int cnt = 0;
        for(QueryFunction qf: query.getAllFunctions()){
            if(qf.getName().contains("order")){
                toReturn.getAllFunctions().add(qf);
                cnt++;
                if(cnt > 1){
                    qf.setName(qf.getName()+"-");
                }
            }
        }
        query.setName(toReturn.getName());
        query.setAllFunctions(toReturn.getAllFunctions());
    }

    public String transformFunction(QueryFunction queryFunction){
        StringBuilder toReturn = new StringBuilder();
        if(queryFunction.getName().equalsIgnoreCase("query")){
            toReturn.append(" FROM ");
            toReturn.append(writeParameters(queryFunction.getAtributes()));
        }
        else if(queryFunction.getName().equalsIgnoreCase("select")){
            toReturn.append(" SELECT ");
            toReturn.append(writeParameters(queryFunction.getAtributes()));
        }
        else if(queryFunction.getName().contains("orderby")){
            if(queryFunction.getName().contains("-")){
                toReturn.append(", ");
                toReturn.append(writeParameters(queryFunction.getAtributes()));
                toReturn.append(" "+ queryFunction.getName().substring(7, queryFunction.getName().length()-1).toUpperCase());
            }else{
                toReturn.append(" ORDER BY ");
                toReturn.append(writeParameters(queryFunction.getAtributes()));
                if(queryFunction.getName().contains("sc")){
                    toReturn.append(" "+ queryFunction.getName().substring(7).toUpperCase());
                }
            }

        }
        else if(queryFunction.getName().endsWith("where")){
            if(queryFunction.getName().length() > 6){
                String operator = queryFunction.getName().substring(0,queryFunction.getName().indexOf("w"));
                toReturn.append(operator + " ");
            }else{
                toReturn.append(" WHERE ");
            }

            toReturn.append(writeExpression(queryFunction.getAtributes()));
        }
        else if(queryFunction.getName().startsWith("where") && queryFunction.getName().length() > 6 && !queryFunction.getName().endsWith("q")){
            toReturn.append(" WHERE " + queryFunction.getAtributes().get(0));
            if(queryFunction.getName().contains("starts")){
                toReturn.append(" LIKE '" + queryFunction.getAtributes().get(1) + "%' ");
            }else if(queryFunction.getName().contains("ends")){
                toReturn.append(" LIKE '%" + queryFunction.getAtributes().get(1) + "' ");
            }else if(queryFunction.getName().contains("contains")){
                toReturn.append(" LIKE '" + "%"+queryFunction.getAtributes().get(1)+"%' ");
            }else if(queryFunction.getName().contains("in")){
                toReturn.append(" IN ");
            }else{
                toReturn.append(" BETWEEN " + queryFunction.getAtributes().get(1) + " AND " + queryFunction.getAtributes().get(2));
            }
        }
        else if(queryFunction.getName().startsWith("m") || queryFunction.getName().startsWith("c") || queryFunction.getName().equalsIgnoreCase("avg")){
            String agregator = queryFunction.getName().toUpperCase();
            toReturn.append(" "+agregator+"("+ queryFunction.getAtributes().get(0)+")");
            if (queryFunction.getAtributes().size() > 1){
                toReturn.append(" AS "+"'"+queryFunction.getAtributes().get(1)+"'");
            }
        }
        else if(queryFunction.getName().equalsIgnoreCase("groupby")){
            toReturn.append(" GROUP BY ");
            toReturn.append(writeParameters(queryFunction.getAtributes()));
        }
        else if(queryFunction.getName().contains("having")){
            if(!queryFunction.getName().startsWith("having")){
                String operator = queryFunction.getName().substring(0, queryFunction.getName().indexOf("h"));
                toReturn.append(operator+ " ");
            }else{
                toReturn.append(" HAVING ");
            }

            toReturn.append(writeExpression(queryFunction.getAtributes()));
        }
        else if(queryFunction.getName().endsWith("q")){
            toReturn.append(" WHERE " + queryFunction.getAtributes().get(0));
            if(queryFunction.getName().contains("qq")){
                toReturn.append(" = ");
            }else{
                toReturn.append(" IN (");
            }
            String name = queryFunction.getAtributes().get(1);
            String podupit = "";
            for(Query q: this.getAllqueries()){
                if (q.getName().equals(name)){
                    podupit = q.getGeneratedSQL();
                }
            }

            toReturn.append(podupit);
            toReturn.append(")");
        }
        else if(queryFunction.getName().contains("parametar")){
            toReturn.append(writeParametarList(queryFunction.getAtributes()));
        }
        else{
            toReturn.append(" JOIN ");
        }
        return toReturn.toString();
    }

    public String writeParameters(ArrayList<String> parameters){
        StringBuilder toReturn = new StringBuilder();
        int cnt = 0;
        if (parameters.size() > 1){
            //toReturn.append("(");
        }
        for(String p: parameters){
            if(cnt == parameters.size()-1){
                toReturn.append(p);
            }else{
                toReturn.append(p + ", ");
            }
            cnt++;
        }
        if (parameters.size() > 1){
            //toReturn.append(")");
        }
        return toReturn.toString();
    }
    public String writeExpression(ArrayList<String> parameters){
        StringBuilder toReturn = new StringBuilder();

        for(int i=0; i<parameters.size(); i++){
            if(i == 2){
                toReturn.append(isNumeric(parameters.get(i)) + " ");
            }else{
                toReturn.append(parameters.get(i) + " ");
            }

        }


        return toReturn.toString();
    }
    public String writeParametarList(ArrayList<String> parameters){
        StringBuilder toReturn = new StringBuilder();
        toReturn.append("(");
        for(int i=0; i<parameters.size(); i++){
            if(i == parameters.size()-1){
                toReturn.append(isNumeric(parameters.get(i)) + " ");
            }else{
                toReturn.append(isNumeric(parameters.get(i)) + ", ");
            }

        }
        toReturn.append(") ");

        return toReturn.toString();
    }
    public String parseJoin(QueryFunction queryFunction, String fromJoin){
        StringBuilder toReturn = new StringBuilder();
        String toJoin = queryFunction.getAtributes().get(2).split("\\.")[0];
        String usingJoin = queryFunction.getAtributes().get(0).split("\\.")[1];
        toReturn.append(" JOIN "+ toJoin + " USING "+ usingJoin);
        return toReturn.toString();
    }

    public ArrayList<Query> getAllqueries() {
        return allqueries;
    }

    public String isNumeric(String s){
        //System.out.println(s);
        try {
            Integer.parseInt(s);
            return s;
        } catch (NumberFormatException e){
            return "'" + s +"'";
        }
    }

    public void setAllqueries(ArrayList<Query> allqueries) {
        this.allqueries = allqueries;
    }
}
