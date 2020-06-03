package actions;

import gui.MainFrame;
import gui.NorthTablePanel;
import resource.DBNode;
import resource.enums.AttributeType;
import resource.implementation.Attribute;
import resource.implementation.Entity;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class Average extends MyAbstractAction{
    public Average() {
        putValue(NAME, "Average");
        putValue(SHORT_DESCRIPTION, "Average table ");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Entity nt = ((NorthTablePanel) MainFrame.getInstance().getNorthTab().getSelectedComponent()).getEntity();

        ArrayList<JRadioButton> columnButtons = new ArrayList<>();
        ArrayList<JCheckBox> checkBoxes = new ArrayList<>();

        ButtonGroup group = new ButtonGroup();

        for(DBNode a:nt.getChildren()){
            checkBoxes.add(new JCheckBox(a.getName()));
            if(((Attribute)a).getAttributeType().equals(AttributeType.NUMERIC) || ((Attribute)a).getAttributeType().equals(AttributeType.FLOAT)){
                //System.out.println(((Attribute) a).getAttributeType().toString());
                JRadioButton jradbut = new JRadioButton(a.getName());
                jradbut.setActionCommand(a.getName());
                columnButtons.add(jradbut);
                group.add(jradbut);
            }
        }

        Border raisedbevel = BorderFactory.createRaisedBevelBorder();
        Border loweredbevel = BorderFactory.createLoweredBevelBorder();
        Border blackline = BorderFactory.createCompoundBorder(
                raisedbevel, loweredbevel);

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BorderLayout());

        JPanel colPanel = new JPanel();
        colPanel.setBorder(blackline);
        colPanel.setLayout(new GridLayout(0,2,5,5));
        for (JRadioButton jrb:columnButtons){
            colPanel.add(jrb);
        }
        myPanel.add(colPanel,BorderLayout.NORTH);

        JPanel p =new JPanel();
        p.setBorder(blackline);
        p.setBorder(new EmptyBorder(10, 10, 5, 10));
        p.add(new JLabel("Group by "));
        myPanel.add(p,BorderLayout.CENTER);

        JPanel groupPanel = new JPanel();
        groupPanel.setBorder(blackline);
        groupPanel.setLayout(new GridLayout(0,2,5,5));
        for (JCheckBox jrb:checkBoxes){
            groupPanel.add(jrb);
        }
        myPanel.add(groupPanel,BorderLayout.SOUTH);

        ArrayList<String> grpBy = new ArrayList<>();

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please Select Average Column", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {

            for(JCheckBox j:checkBoxes){
                if(j.isSelected()){
                    grpBy.add(j.getText());
                }
            }
            if(group.getSelection() != null){
                MainFrame.getInstance().getAppCore().avgColumn(nt.getName(),group.getSelection().getActionCommand(),grpBy,
                        ((NorthTablePanel) MainFrame.getInstance().getNorthTab().getSelectedComponent()).getTableModel());
            }else{
                JOptionPane.showMessageDialog(null,
                        "Select column!",
                        "Input error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        ChangeSouthPanel.change();
    }
}
