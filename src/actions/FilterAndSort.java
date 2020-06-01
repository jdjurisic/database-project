package actions;

import gui.MainFrame;
import gui.NorthTablePanel;
import resource.DBNode;
import resource.implementation.Entity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class FilterAndSort extends MyAbstractAction{

    public FilterAndSort() {
        putValue(NAME, "Filter&Sort");
        putValue(SHORT_DESCRIPTION, "filter and sort table ");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("filter");
        Entity nt = ((NorthTablePanel) MainFrame.getInstance().getNorthTab().getSelectedComponent()).getEntity();

        ArrayList<JCheckBox> checkBoxes = new ArrayList<>();

        JPanel myPanel = new JPanel();
        GridLayout gr = new GridLayout(nt.getChildren().size(),2,2,2);
        myPanel.setLayout(gr);

        for(DBNode a: nt.getChildren()){
            checkBoxes.add(new JCheckBox(a.getName()));
        }

        for(int i=0;i<checkBoxes.size();i++){
            myPanel.add(checkBoxes.get(i));
        }

        StringBuilder cols = new StringBuilder();
        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please Select Filter Columns", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            for(JCheckBox j:checkBoxes){
                if(j.isSelected()){
                    cols.append(j.getText());
                    cols.append(",");
                }
            }
            cols.deleteCharAt(cols.length()-1);
            System.out.println(cols.toString());

            MainFrame.getInstance().getAppCore().filterAndSort(null,cols.toString(),
                    nt.getName(),
                    ((NorthTablePanel) MainFrame.getInstance().getNorthTab().getSelectedComponent()).getTableModel());
            /*MainFrame.getInstance().getNorthTab().addTabWithTable(nt,
                    MainFrame.getInstance().getAppCore().filterAndSort(null,cols.toString(),nt.getName()));*/
        }

    }
}
