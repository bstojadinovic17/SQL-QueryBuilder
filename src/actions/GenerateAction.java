package actions;

import compiler.Query;
import gui.MainView;
import gui.TextArea;
import model.Message;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Queue;

public class GenerateAction extends AbstractAction {

    public GenerateAction() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String code = TextArea.getInstance().getCode().getText();
        Message messagesyntax = MainView.getinstance().getValidator().validateSyntax(code);
        if(messagesyntax.isOk()){
            Message messageLogic = MainView.getinstance().getValidator().validateLogic();
            if(messageLogic.isOk()){
                //TextArea.getInstance().getGenerated().setText(code);
                ArrayList<Query> allqueries = MainView.getinstance().getValidator().getSviUpiti();
                MainView.getinstance().getGenerator().setAllqueries(allqueries);
                MainView.getinstance().getGenerator().generateQuery(allqueries);
                StringBuilder sb = new StringBuilder();
                for(Query q: allqueries){
                    sb.append(q.getGeneratedSQL());
                    sb.append("\n");
                }
                TextArea.getInstance().getGenerated().setText(sb.toString());
                //TextArea.getInstance().getGenerated().setText(MainView.getinstance().getGenerator().generateQuery(allqueries));
            }else{
                StringBuilder sb = new StringBuilder();
                sb.append("Logic error:" + "\n");
                sb.append(messageLogic.isOk() + "\n");
                for(String s: messageLogic.getMessages()){
                    sb.append(s + "\n");
                    JOptionPane.showMessageDialog(null, s);
                }

                //TextArea.getInstance().getGenerated().setText(sb.toString());
            }
        }else{
            StringBuilder sb = new StringBuilder();
            sb.append("Syntax error:" + "\n");
            sb.append(messagesyntax.isOk() + "\n");
            for(String s: messagesyntax.getMessages()){
                sb.append(s + "\n");
                JOptionPane.showMessageDialog(null, s);
            }

            //TextArea.getInstance().getGenerated().setText(sb.toString());


        }
    }
}
