package compiler;

import model.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Validator {


    Map<String, Integer> dozvoljeneFunkcije = new HashMap<>();
    private Message message;
    private Message messageLogic;
    private ArrayList<Query> sviUpiti;
    private ArrayList<String> declarations;
    public Validator() {

        dozvoljeneFunkcije.put("query", 99);
        dozvoljeneFunkcije.put("select", 99);
        dozvoljeneFunkcije.put("orderby", 99);
        dozvoljeneFunkcije.put("orderbydesc", 99);
        dozvoljeneFunkcije.put("orderbyasc", 99);
        dozvoljeneFunkcije.put("where", 3);
        dozvoljeneFunkcije.put("orwhere", 3);
        dozvoljeneFunkcije.put("andwhere", 3);
        dozvoljeneFunkcije.put("wherebetween", 3);
        dozvoljeneFunkcije.put("wherein", 1);
        dozvoljeneFunkcije.put("parametarlist", 99);
        dozvoljeneFunkcije.put("join", 1);
        dozvoljeneFunkcije.put("on", 3);
        dozvoljeneFunkcije.put("wherestartswith", 2);
        dozvoljeneFunkcije.put("whereendswith", 2);
        dozvoljeneFunkcije.put("wherecontains", 2);
        dozvoljeneFunkcije.put("avg", 21);
        dozvoljeneFunkcije.put("count", 21);
        dozvoljeneFunkcije.put("min", 21);
        dozvoljeneFunkcije.put("max", 21);
        dozvoljeneFunkcije.put("groupby", 99);
        dozvoljeneFunkcije.put("having", 3);
        dozvoljeneFunkcije.put("andhaving", 3);
        dozvoljeneFunkcije.put("orhaving", 3);
        dozvoljeneFunkcije.put("whereinq", 2);
        dozvoljeneFunkcije.put("whereeqq", 2);


    }

    public Message validate(String code){
        //System.out.println(code);
        return message;
    }
    public Message validateSyntax(String code){
        declarations = new ArrayList<>();
        code = code.replace("\n","");
        code = code.replace("\t","");
        String[] queries = code.split("var");
        this.message = new Message();
        message.setOk(true);
        if(!code.startsWith("var")){
            message.addMessage("Niste definisali tip promenljive!");
            message.setOk(false);
            return message;
        }
        sviUpiti = new ArrayList<>();
        for(int i = 1;i<queries.length; i++) {
            String q = queries[i];
            int jednakoIndex = q.indexOf("=");
            if (jednakoIndex == -1){
                message.addMessage("Nema znaka jednako!");
                message.setOk(false);
                return message;
            }
            String queryName = q.substring(0,jednakoIndex).trim();
            declarations.add(queryName);
            Query upit = new Query(queryName);
            String query = "var"+ q;
            String[] jednako = query.split("=");
            String pom = "";
            try {
                pom = jednako[1];
            } catch (Exception e){
                message.addMessage("Niste inicijalizovali upit!");
                message.setOk(false);
                return message;
            }

            if (jednako.length > 2) {
                for (int j = 2; j < jednako.length; j++) {
                    //System.out.println(jednako[i]);
                    pom += "=" + jednako[j];
                }
            }
            //System.out.println(pom);
            jednako[1] = pom;
            //System.out.println(jednako[1]);
            // delimo upit na levi i desni deo po znaku jednako

            String[] leviSpace = new String[2];
            String[] desniSpace = new String[2];

            //ako nema jednako baza error za jednako[1]
            try {
                leviSpace = jednako[0].trim().split(" ");
                int indexnew = jednako[1].indexOf("new");
                if (indexnew != -1) {
                    desniSpace = new String[]{"new ", jednako[1].substring(indexnew + 4)};
                    //System.out.println(desniSpace[0]);
                    //System.out.println(desniSpace[1]);
                } else {
                    message.addMessage("Greska u inicijalizaciji promenljive (nema new)!");
                    message.setOk(false);
                    return message;
                }
            } catch (Exception e) {
                message.addMessage("Nema  query");
                message.setOk(false);
                return message;
            }

            //ako levi ne pocinjke sa var greska
            if (!(leviSpace[0].equals("var"))) {
                message.addMessage("Niste definisali tip promenljive!");
                message.setOk(false);
                return message;
            }

            // da li posle vara ima ime promenljive
            try {
                String check = leviSpace[1];
            } catch (Exception e) {
                message.addMessage("Niste definisali ime promenljive!");
                message.setOk(false);
                return message;

            }


            try {
                desniSpace[1].startsWith("Query");
            } catch (Exception e) {
                message.addMessage("Nepostoji Query");
                message.setOk(false);
                return message;
            }

            // da li ide query posle new
            if (!desniSpace[1].startsWith("Query(")) {
                message.addMessage("Greska u inicijalizaciji promenljive (nema Query)!");
                message.setOk(false);
                return message;

            }

            //splitujemo funkcije po tacki
            String[] funkcije = desniSpace[1].split("\\)\\.");

            if (funkcije.length == 1) {
                message.addMessage("Nije dovoljno napisati samo query funkciju");
                message.setOk(false);
                return message;
            }
            int cnt = 0;
            for (String s : funkcije) {
                cnt++;
                if (!(cnt == funkcije.length)) {
                    s = s + ")";
                }

                StringBuilder sb = new StringBuilder();
                //trazimo ime funkcije

                int funNameIndex = s.indexOf("(");
                if (funNameIndex == -1) {
                    message.addMessage("Sintaksna greska: Ocekivana '(' posle naziva funkcije");
                    message.setOk(false);
                    continue;
                }
                String funName = s.substring(0, funNameIndex);
                funName = funName.toLowerCase();
                QueryFunction func = new QueryFunction(funName);
                ArrayList<String> attributes = new ArrayList<>();
                sb.append(funName + " ->");

                if (dozvoljeneFunkcije.containsKey(funName)) {
                    //System.out.println(funName);
                    int numOfAtr = dozvoljeneFunkcije.get(funName);
                    int atrEndIndex = s.indexOf(")");
                    sb.append("atrNum:" + numOfAtr + " | ");


                    if (atrEndIndex == -1) {
                        message.addMessage("Sintaksna greska: " + funName + " Ocekivana ')' posle liste atributa");
                        message.setOk(false);
                        continue;
                    }

                    if (atrEndIndex != s.length() - 1) {
                        message.addMessage("Sintaksna greska: " + funName + " Ocekivana ')' posle liste atributa");
                        message.setOk(false);
                        continue;
                    }

                    String[] atrs = s.substring(funNameIndex + 1, atrEndIndex).split(",");

                    // ako nema broj parametra koji treba da ima ide u proveru
                    if (atrs.length != numOfAtr) {

                        if (numOfAtr != 99 && numOfAtr != 21) {
                            message.addMessage("Sintaksna greska: " + funName + " moze da sadrzi samo " + numOfAtr + "atributa");
                            message.setOk(false);
                            continue;
                        }

                        if (numOfAtr == 21) {
                            if (atrs.length != 1 && atrs.length != 2) {
                                System.out.println(atrs.length + " " + funName);
                                message.addMessage("Sintaksna greska: " + funName + " moze da sadrzi 1 ili 2 atributa");
                                message.setOk(false);
                                continue;

                            }
                        }


                    }
                    sb.append("atrs: ");
                    for (String art : atrs) {
                        sb.append(art + ", ");
                        art = art.trim();
                        if (art.startsWith("\"") && art.endsWith("\"")) {
                            attributes.add(art.substring(1,art.length()-1));

                            if (art.length() == 2) {
                                message.addMessage("Sintaksna greska: " + funName + " Atributi moraja da sadrzi ime '\"'");
                                message.setOk(false);

                            }
                        } else {
                            if (!(funName.equalsIgnoreCase("whereinq") || funName.equalsIgnoreCase("whereeqq")) && !isNumeric(art)) {
                                message.addMessage("Sintaksna greska:" + funName + " Atributi moraju da pocnu i da zavrse sa '\"'");
                                message.setOk(false);
                            }

                            if(funName.equalsIgnoreCase("whereinq") || funName.equalsIgnoreCase("whereeqq")){
                                if(art.equals(queryName)){
                                    message.addMessage("Ne moze sam sebi da bude podupit!");
                                    message.setOk(false);
                                }
                                else if(!declarations.contains(art)){
                                    message.addMessage("Promenjiva koja se koristi za podupit mora biti deklarisana!");
                                    message.setOk(false);
                                }
                            }
                            attributes.add(art);

                        }
                    }
                    func.getAtributes().addAll(attributes);
                    if(funName.contains("having") || funName.equalsIgnoreCase("where") || funName.endsWith("where")){
                        if(!(func.getAtributes().get(1).equals("=") || func.getAtributes().get(1).equals("<") || func.getAtributes().get(1).equals(">") || func.getAtributes().get(1).equalsIgnoreCase("like"))){
                            message.addMessage("Niste uneli operator kod funkcije "+funName+" koja to zahteva!");
                            message.setOk(false);
                        }
                        if(func.getAtributes().get(1).equalsIgnoreCase("like") && !(func.getAtributes().get(2).startsWith("%") || func.getAtributes().get(2).endsWith("%"))){
                            message.addMessage("Ne moze se koristiti operator LIKE, a da se ne navodi % na pocetku/kraju sledeceg parametra!");
                            message.setOk(false);
                        }
                    }


                } else {
                    message.addMessage(funName + " -> Funkcija ne postoji!");
                    message.setOk(false);

                }
                upit.getAllFunctions().add(func);
                sb.append("\n");
                //System.out.println(sb);
            }

            sviUpiti.add(upit);
        }
        return message;

    }

    public Message validateLogic(){
        messageLogic = new Message();
        messageLogic.setOk(true);
        for(Query q:sviUpiti){

            // SELECT mora da postoji, u suptornom se podrazumeva da se selektuje sve iz funkcije query
            if(q.getFunctionByName("select").size() == 0){
                QueryFunction select = new QueryFunction("select");
                select.getAtributes().add("*");
                q.getAllFunctions().add(select);
                //messageLogic.addMessage("Ne moze bez selecta upit!");
                //messageLogic.setOk(false);
            }
            // SELECT GROUPBY
            if(!selectGroupBy(q)){
                messageLogic.addMessage("Sve sto je u SELECT, a nije agregacija mora u GROUP BY!");
                messageLogic.setOk(false);
            }


            // JOIN i ON
            if(q.getFunctionByName("join").size() != q.getFunctionByName("on").size()){
                messageLogic.addMessage("Upit mora da ima jednak broj JOIN i ON funkcija!");
                messageLogic.setOk(false);
            }

            // JOIN sam sebe
            if(!checkJoin(q)){
                messageLogic.addMessage("Ne moze da se join-uje tabela iz querija!");
                messageLogic.setOk(false);
            }
            if(!checkSelectJoin(q)){
                messageLogic.addMessage("Ne moze se selektovati polje iz tabele koja nije selektovana ili join-ovana!");
                messageLogic.setOk(false);
            }

            if(!checkJoinOn(q)){
                messageLogic.addMessage("Ne poklapaju se JOIN i ON");
                messageLogic.setOk(false);
            }

            //Ne moze imati vise groupby-ova

            if(q.getFunctionByName("groupby").size() > 1){
                messageLogic.addMessage("Ne moze imati vise GroupBy-ova!");
                messageLogic.setOk(false);
            }


            if(q.getFunctionByName("having").size() > 1){
                messageLogic.addMessage("Ne moze imati vise Having-a!");
                messageLogic.setOk(false);
            }


            //Ne moze vise havinga
            if(q.getFunctionByName("having").size() > 1){
                messageLogic.addMessage("Ne moze imati vise Having-a!");
                messageLogic.setOk(false);
            }

            // Ne moze da ima or ili and ako nije imao where
            int i = 0;
            if((q.getFunctionByName("where").size() == 0 && q.getFunctionByName("wherein").size() == 0 && q.getFunctionByName("wherebetween").size() == 0 && q.getFunctionByName("wherecontains").size() == 0 && q.getFunctionByName("wherestartswith").size() == 0 &&  q.getFunctionByName("whereendswith").size() == 0) && (q.getFunctionByName("orwhere").size() > 0 || q.getFunctionByName("andwhere").size() > 0)){
                messageLogic.addMessage("Ne moze imati and/or ako nema where funkciju!");
                messageLogic.setOk(false);
            }

            //Ne moze da ima or ili and ako nije imao having
            if(q.getFunctionByName("having").size() == 0 && (q.getFunctionByName("orhaving").size() > 0 || q.getFunctionByName("andhaving").size() > 0)){
                messageLogic.addMessage("Ne moze imati and/or ako nema having funkciju!");
                messageLogic.setOk(false);
            }

            // Where in i Parameter list moraju ici u paru
            if(q.getFunctionByName("wherein").size() != q.getFunctionByName("parametarlist").size()){
                messageLogic.addMessage("Broj wherein i parametarlist funkcija mora biti jednak!");
                messageLogic.setOk(false);
            }

            // ne moze vise razlicitih where-ova
            int cnt = 0;
            for(QueryFunction qf: q.getAllFunctions()){
                if(qf.getName().startsWith("where") && !(qf.getName().equalsIgnoreCase("whereinq") || qf.getName().equalsIgnoreCase("whereeqq"))){
                    cnt++;
                }
            }
            if(cnt > 1){
                messageLogic.addMessage("Ne moze imati vise razlicitih where-ova koji nisu and ili or funkcije!");
                messageLogic.setOk(false);
            }

            // provera za having da li je u groupby
            if(q.getFunctionByName("having").size()>0){
                ArrayList<QueryFunction> havings = q.getFunctionByName("having");
                if(q.getFunctionByName("andhaving").size() > 0){
                    ArrayList<QueryFunction> andh = q.getFunctionByName("andhaving");
                    for(QueryFunction qf: andh){
                        havings.add(qf);
                    }
                }
                if(q.getFunctionByName("orhaving").size() > 0){
                    ArrayList<QueryFunction> orh = q.getFunctionByName("ordhaving");
                    for(QueryFunction qf: orh){
                        havings.add(qf);
                    }
                }
                int brHav = havings.size();
                int hasGroupby = q.getFunctionByName("groupby").size();
                if(hasGroupby == 0){
                    int hasSelect = q.getFunctionByName("select").size();
                    if(hasSelect == 0){
                        messageLogic.addMessage("Sve sto je u having-u mora biti ukljuceno ili u select ili u group by ako nije agregacija!");
                        messageLogic.setOk(false);
                    }else{
                        for(QueryFunction hav: havings){
                            for(String a:q.getFunctionByName("select").get(0).getAtributes()){
                                if(hav.getAtributes().get(0).equalsIgnoreCase(a)){
                                    brHav = brHav - 1;
                                }
                            }
                        }
                    }
                }else{
                    for(QueryFunction hav: havings){
                        for(String a: q.getFunctionByName("groupby").get(0).getAtributes()){
                            if(hav.getAtributes().get(0).equalsIgnoreCase(a)){
                                brHav = brHav - 1;
                            }
                        }
                    }
                }

                if(brHav != 0){
                    messageLogic.addMessage("Prvi atribut u having-u mora biti ukljucen u group by ako nije pod agregacijom!");
                    messageLogic.setOk(false);
                }
            }

        }

        return messageLogic;
    }

    public boolean checkSelectJoin(Query q){
        boolean toReturn = true;

        ArrayList<QueryFunction> joins = q.getFunctionByName("join");
        ArrayList<QueryFunction> selects = q.getFunctionByName("select");
        ArrayList<QueryFunction> queries = q.getFunctionByName("query");

        for(QueryFunction select: selects){
            ArrayList<String> selectAtr = select.getAtributes();
            for(String s: selectAtr){
                if(s.contains(".")){
                    String tableName = s.split("\\.")[0];

                    if(!queries.get(0).getAtributes().get(0).equalsIgnoreCase(tableName)){
                        for(QueryFunction join: joins){
                            if(!join.getAtributes().get(0).equalsIgnoreCase(tableName)){
                                toReturn = false;
                            }

                        }
                    }else{
                        toReturn = true;
                    }

                }
            }

        }
        return toReturn;
    }

    public boolean checkJoinOn(Query q){
        ArrayList<QueryFunction> joins = q.getFunctionByName("join");
        ArrayList<QueryFunction> ons = q.getFunctionByName("on");
        int cnt = 0;

        for(QueryFunction join: joins){
            for(QueryFunction on: ons){
                int index = on.getAtributes().get(2).indexOf(".");
                int index2 = on.getAtributes().get(0).indexOf(".");
                if(index == -1 || index2 == -1) return false;
                if(on.getAtributes().get(2).substring(0, index).equals(join.getAtributes().get(0))){
                    if(!on.getAtributes().get(2).substring(index+1).equals(on.getAtributes().get(0).substring(index2+1))){
                        return false;
                    }
                    QueryFunction from = q.getFunctionByName("query").get(0);
                    from.getAtributes().add(join.getAtributes().get(0));
                    QueryFunction select = q.getFunctionByName("select").get(0);
                    select.getAtributes().add(on.getAtributes().get(0));
                    if(q.getFunctionByName("groupby").size() > 0){
                        q.getFunctionByName("groupby").get(0).getAtributes().add(on.getAtributes().get(0));
                    }else{
                        q.getFunctionByName("groupby").add(new QueryFunction(on.getAtributes().get(0)));
                    }
                    cnt++;
                }
            }
            if(cnt != 1 ){
                return false;
            }
            cnt = 0;
        }
        return true;

    }

    public boolean checkJoin(Query q){
        ArrayList<QueryFunction> joins = q.getFunctionByName("join");
        ArrayList<QueryFunction> queries = q.getFunctionByName("query");

        for(QueryFunction join: joins){
            for(QueryFunction qf: queries){
                if(join.getAtributes().get(0).equalsIgnoreCase(qf.getAtributes().get(0))){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean selectGroupBy(Query q){
        ArrayList<QueryFunction> selects = q.getFunctionByName("select");
        ArrayList<QueryFunction> groupBys = q.getFunctionByName("groupby");
        ArrayList<QueryFunction> agregations = q.getFunctionByName("agr");

        if(selects.size() > 0 && agregations.size() == 0 && groupBys.size() == 0){
            return false;
        }
        if(selects.size() == 0){
            return true;
        }else{
            for(String s:selects.get(0).getAtributes()){
                if(groupBys.size() > 0){
                    if(!groupBys.get(0).getAtributes().contains(s)){
                        return false;
                    }
                }else{
                    int cn = agregations.size();
                    for(QueryFunction qf:agregations){
                        if(qf.getAtributes().contains(s)){
                            cn = cn-1;
                        }
                    }
                    if(cn > 0){
                        return false;
                    }
                }
            }
        }


        return true;
    }

    public boolean isNumeric(String s){
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    public ArrayList<Query> getSviUpiti() {
        return sviUpiti;
    }
}
