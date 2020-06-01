package actions;

import gui.MainFrame;
import gui.NorthTablePanel;
import resource.DBNode;
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
        System.out.println("Search");
        Entity nt = ((NorthTablePanel) MainFrame.getInstance().getNorthTab().getSelectedComponent()).getEntity();

        String[] ops = { "Equal", "Greater than","Less than","Greater than or equal", "Less than or equal","Like"};
        ArrayList<JRadioButton> columnButtons = new ArrayList<>();
        ArrayList<JRadioButton> operationButtons = new ArrayList<>();
        JButton andButton = new JButton("And");
        JButton orButton = new JButton("Or");
        ButtonGroup group = new ButtonGroup();
        ButtonGroup opgrupa = new ButtonGroup();

        for(DBNode a:nt.getChildren()){
            JRadioButton jradbut = new JRadioButton(a.getName());
            group.add(jradbut);
            columnButtons.add(jradbut);
        }
        for(String s:ops){
            JRadioButton jradbut = new JRadioButton(s);
            operationButtons.add(jradbut);
            opgrupa.add(jradbut);
        }

        Border raisedbevel = BorderFactory.createRaisedBevelBorder();
        Border loweredbevel = BorderFactory.createLoweredBevelBorder();
        Border blackline = BorderFactory.createCompoundBorder(
                raisedbevel, loweredbevel);

        JLabel qLabel = new JLabel("        Enter value");
        JTextField query = new JTextField();
        query.setEnabled(false);

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BorderLayout());

        JPanel colPanel = new JPanel();
        GridLayout gr = new GridLayout(0,4,5,5);
        colPanel.setLayout(gr);
        for(JRadioButton jbtn:columnButtons){
            colPanel.add(jbtn);
        }
        colPanel.setBorder(blackline);
        myPanel.add(colPanel,BorderLayout.NORTH);

        JPanel opPanel = new JPanel();
        GridLayout gr2 = new GridLayout(0,4,2,2);
        colPanel.setLayout(gr2);
        for(JRadioButton jbtn2:operationButtons){
            opPanel.add(jbtn2);
        }
        opPanel.setBorder(blackline);
        myPanel.add(opPanel,BorderLayout.CENTER);

        JPanel valuePanel = new JPanel();
        GridLayout gr3 = new GridLayout(0,2,5,5);
        JTextField queryValue = new JTextField();
        valuePanel.setLayout(gr3);
        valuePanel.setBorder(blackline);
        valuePanel.add(qLabel);
        valuePanel.add(queryValue);
        valuePanel.add(andButton);
        valuePanel.add(orButton);
        valuePanel.add(new JButton("        Query preview"));
        valuePanel.add(query);
        myPanel.add(valuePanel,BorderLayout.SOUTH);



        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Create your custom search query", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            System.out.println("Test");
        }




    }
}
