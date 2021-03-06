package actions;

import app.Main;
import gui.MainFrame;
import gui.NorthTablePanel;
import resource.DBNode;
import resource.enums.AttributeType;
import resource.implementation.Attribute;
import resource.implementation.Entity;
import sun.security.pkcs11.Secmod;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Search extends MyAbstractAction {
    public Search() {

        putValue(NAME, "Search");
        putValue(SHORT_DESCRIPTION, "Search table ");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //System.out.println("Search");
        Entity nt = ((NorthTablePanel) MainFrame.getInstance().getNorthTab().getSelectedComponent()).getEntity();
        JDialog jDialog = new JDialog(MainFrame.getInstance(), "Create your custom search query", true);
        //String[] ops = { "Equal", "Greater than","Less than","Greater than or equal", "Less than or equal","Like"};
        String[] ops = { "=", ">","<"," Like "};
        ArrayList<JRadioButton> columnButtons = new ArrayList<>();
        ArrayList<JRadioButton> operationButtons = new ArrayList<>();
        JButton andButton = new JButton("And");
        JButton orButton = new JButton("Or");
        ButtonGroup group = new ButtonGroup();
        ButtonGroup opgrupa = new ButtonGroup();

        StringBuilder upit = new StringBuilder();

        for(String s:ops){
            JRadioButton jradbut = new JRadioButton(s);
            operationButtons.add(jradbut);
            jradbut.setActionCommand(s);
            opgrupa.add(jradbut);
        }

        for(DBNode a:nt.getChildren()){
            if(a instanceof Attribute){
                Attribute attribute = (Attribute) a;
                JRadioButton jradbut = new JRadioButton(attribute.getName());
                group.add(jradbut);
                jradbut.setActionCommand(a.getName());
                columnButtons.add(jradbut);
                jradbut.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(attribute.getAttributeType().equals(AttributeType.CHAR) || attribute.getAttributeType().equals(AttributeType.VARCHAR)){
                            for (JRadioButton jRadioButton:operationButtons) {
                                if(jRadioButton.getActionCommand().equals("=")||jRadioButton.getActionCommand().equals(">")||jRadioButton.getActionCommand().equals("<")){
                                    jRadioButton.setEnabled(false);
                                    opgrupa.clearSelection();
                                }else {
                                    jRadioButton.setEnabled(true);

                                }
                            }
                        } else if(attribute.getAttributeType().equals(AttributeType.NUMERIC) || attribute.getAttributeType().equals(AttributeType.FLOAT )
                                || attribute.getAttributeType().equals(AttributeType.DATE) || attribute.getAttributeType().equals(AttributeType.DATETIME)){
                            for (JRadioButton jRadioButton:operationButtons) {
                                if(jRadioButton.getActionCommand().equals(" Like ")){
                                    jRadioButton.setEnabled(false);
                                    opgrupa.clearSelection();
                                }else {
                                    jRadioButton.setEnabled(true);

                                }
                            }
                        }

                    }
                });
            }

        }


        Border raisedbevel = BorderFactory.createRaisedBevelBorder();
        Border loweredbevel = BorderFactory.createLoweredBevelBorder();
        Border blackline = BorderFactory.createCompoundBorder(
                raisedbevel, loweredbevel);

        JLabel qLabel = new JLabel("        Enter value");
        JTextField query = new JTextField();
        query.setEnabled(false);
        query.setBorder(blackline);

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS ));

        JPanel colPanel = new JPanel();
        GridLayout gr = new GridLayout(0,4,5,5);
        colPanel.setLayout(gr);
        for(JRadioButton jbtn:columnButtons){
            colPanel.add(jbtn);
        }
        colPanel.setBorder(blackline);
        myPanel.add(colPanel);

        JPanel opPanel = new JPanel();
        GridLayout gr2 = new GridLayout(0,4,13,13);
        opPanel.setLayout(gr2);
        for(JRadioButton jbtn2:operationButtons){
            opPanel.add(jbtn2);
        }
        opPanel.setBorder(blackline);
        myPanel.add(opPanel);

        JPanel valuePanel = new JPanel();
        GridLayout gr3 = new GridLayout(0,2,5,5);
        JTextField queryValue = new JTextField();
        valuePanel.setLayout(gr3);
        valuePanel.setBorder(blackline);
        valuePanel.add(qLabel);
        valuePanel.add(queryValue);
        valuePanel.add(andButton);
        valuePanel.add(orButton);
        myPanel.add(valuePanel);
        myPanel.add(query);
        // novo
        JButton finishQuery = new JButton(" Ok ");
        JButton cancel = new JButton("Cancel");
        JPanel okCancelPanel = new JPanel();
        okCancelPanel.setLayout(new GridLayout(0,2,5,5));
        finishQuery.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        cancel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        okCancelPanel.add(finishQuery);
        okCancelPanel.add(cancel);

        myPanel.add(okCancelPanel);

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jDialog.dispose();
            }
        });

        finishQuery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(queryValue.getText().trim().length() > 0 && group.getSelection()!=null && opgrupa.getSelection()!=null){

                    upit.append(group.getSelection().getActionCommand());
                    upit.append(opgrupa.getSelection().getActionCommand());
                    upit.append("'");
                    upit.append(queryValue.getText());
                    upit.append("'");
                    upit.append(" ;");

                    query.setText(upit.toString());
                    queryValue.setText("");
                    group.clearSelection();
                    opgrupa.clearSelection();

                    for(JRadioButton jbtn13:operationButtons){
                        jbtn13.setEnabled(false);
                    }
                    for(JRadioButton jbtn10:columnButtons){
                        jbtn10.setEnabled(false);
                    }
                    andButton.setEnabled(false);
                    orButton.setEnabled(false);
                    finishQuery.setEnabled(false);
                    queryValue.setEnabled(false);
                    if(upit.lastIndexOf(";") == upit.length()-1){
                        //System.out.println("Query:"+nt.getName()+" WHERE "+upit.toString());
                        MainFrame.getInstance().getAppCore().readDataFromTable(nt.getName()+" WHERE "+upit.toString(),
                                ((NorthTablePanel) MainFrame.getInstance().getNorthTab().getSelectedComponent()).getTableModel());
                        jDialog.dispose();

                    }else{
                        JOptionPane.showMessageDialog(null,
                                "Your query wasnt finished!",
                                "Input error",
                                JOptionPane.ERROR_MESSAGE);
                    }


                }else{
                    JOptionPane.showMessageDialog(null,
                            "Select columns and enter the value!",
                            "Input error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });



        // novo

        andButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(queryValue.getText().trim().length() > 0 && group.getSelection()!=null && opgrupa.getSelection()!=null){
                    upit.append(group.getSelection().getActionCommand());
                    upit.append(opgrupa.getSelection().getActionCommand());
                    upit.append("'");
                    upit.append(queryValue.getText());
                    upit.append("'");
                    upit.append(" ");
                    upit.append(" AND ");

                    query.setText(upit.toString());
                    queryValue.setText("");
                    group.clearSelection();
                    opgrupa.clearSelection();


                }else{
                    JOptionPane.showMessageDialog(null,
                            "Input field mustn't be empty!",
                            "Input error",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        orButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(queryValue.getText().trim().length() > 0 && group.getSelection()!=null && opgrupa.getSelection()!=null){
                    upit.append(group.getSelection().getActionCommand());
                    upit.append(opgrupa.getSelection().getActionCommand());
                    upit.append("'");
                    upit.append(queryValue.getText());
                    upit.append("'");
                    upit.append(" ");
                    upit.append(" OR ");

                    query.setText(upit.toString());
                    queryValue.setText("");
                    group.clearSelection();
                    opgrupa.clearSelection();




                }else{
                    JOptionPane.showMessageDialog(null,
                            "Input field mustn't be empty!",
                            "Input error",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        });



        jDialog.add(myPanel);
        jDialog.pack();
        jDialog.setLocationRelativeTo(MainFrame.getInstance());
        jDialog.setVisible(true);
        /*
        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Create your custom search query", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            if(upit.lastIndexOf(";") == upit.length()-1){
                //System.out.println("Query:"+nt.getName()+" WHERE "+upit.toString());
                MainFrame.getInstance().getAppCore().readDataFromTable(nt.getName()+" WHERE "+upit.toString(),
                        ((NorthTablePanel) MainFrame.getInstance().getNorthTab().getSelectedComponent()).getTableModel());


            }else{
                JOptionPane.showMessageDialog(null,
                        "Your query wasnt finished!",
                        "Input error",
                        JOptionPane.ERROR_MESSAGE);
            }

        }*/




    }
}
