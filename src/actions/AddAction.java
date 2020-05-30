package actions;

import gui.MainFrame;
import gui.NorthTablePanel;
import resource.DBNode;
import resource.implementation.Attribute;
import resource.implementation.Entity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class AddAction extends MyAbstractAction {

    public AddAction(){
        putValue(NAME, "Add row");
        putValue(SHORT_DESCRIPTION, "Add new row to the selected table ");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Add action");
        Entity nt = ((NorthTablePanel)MainFrame.getInstance().getNorthTab().getSelectedComponent()).getEntity();
        System.out.println(nt);

        ArrayList<JTextField> fields = new ArrayList<>();
        ArrayList<JLabel> labels = new ArrayList<>();

        JPanel myPanel = new JPanel();
        GridLayout gr = new GridLayout(nt.getChildren().size(),2,2,2);
        myPanel.setLayout(gr);

        for(DBNode a: nt.getChildren()){
            fields.add(new JTextField(5));
            labels.add(new JLabel(a.getName()));
        }

        for(int i=0;i<labels.size();i++){
            myPanel.add(labels.get(i));
            myPanel.add(fields.get(i));
        }

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please Enter Values", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            System.out.println("OK");
            for(int i=0;i<labels.size();i++){
                System.out.println(labels.get(i).getText()+" "+fields.get(i).getText());
            }
        }

    }
}