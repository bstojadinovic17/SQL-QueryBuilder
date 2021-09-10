package gui;

import javax.swing.*;
import java.awt.*;

public class TextArea extends JPanel {

    private static TextArea instance = null;
    private JTextArea code = new JTextArea();
    private JTextArea generated = new JTextArea();
    private TextArea(){
        //setSize(new Dimension(700, 350));
        setPreferredSize(new Dimension(700, 350));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        code.setPreferredSize(new Dimension(200,350));
        generated.setPreferredSize(new Dimension(200,350));
        generated.setEditable(false);
        JLabel label = new JLabel("Query Builder");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setPreferredSize(new Dimension(100,50));
        add(label);
        add(code);
        JButton btnGenerate = new JButton("Generate");
        JButton btnExecute = new JButton("Execute");
        btnGenerate.setSize(new Dimension(40,25));
        btnGenerate.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnGenerate.addActionListener(MainView.getinstance().getActionManager().getGenerateAction());

        btnExecute.setSize(new Dimension(40,25));
        btnExecute.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnExecute.addActionListener(MainView.getinstance().getActionManager().getExecuteAction());
        add(btnGenerate);
        add(generated);
        add(btnExecute);
        setVisible(true);
    }



    public JTextArea getCode() {
        return code;
    }

    public JTextArea getGenerated() {
        return generated;
    }

    public static TextArea getInstance() {
            if(instance==null) {
                instance=new TextArea();
            }
            return instance;
        }


}
