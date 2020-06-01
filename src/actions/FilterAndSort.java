package actions;

import gui.MainFrame;
import gui.NorthTablePanel;
import resource.DBNode;
import resource.implementation.Entity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FilterAndSort extends MyAbstractAction{

    public FilterAndSort() {
        putValue(NAME, "Filter&Sort");
        putValue(SHORT_DESCRIPTION, "filter and sort table ");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //System.out.println("filter");
        Entity nt = ((NorthTablePanel) MainFrame.getInstance().getNorthTab().getSelectedComponent()).getEntity();

        ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
        ArrayList<JComboBox> comboBoxes = new ArrayList<>();
        String[] ascdesc = { "ASC", "DESC" };

        JPanel myPanel = new JPanel();
        GridLayout gr = new GridLayout(nt.getChildren().size(),2,2,2);
        myPanel.setLayout(gr);

        for(DBNode a: nt.getChildren()){
            checkBoxes.add(new JCheckBox(a.getName()));
            comboBoxes.add(new JComboBox(ascdesc));
        }

        for(int i=0;i<checkBoxes.size();i++){
            myPanel.add(checkBoxes.get(i));
            myPanel.add(comboBoxes.get(i));
        }

        LinkedHashMap<String ,String> sortValues = new LinkedHashMap<>();
        StringBuilder cols = new StringBuilder();
        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please Select Filter Columns", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            for(JCheckBox j:checkBoxes){
                if(j.isSelected()){
                    cols.append(j.getText());
                    cols.append(",");
                    //System.out.println("Indeks izabranog :"+checkBoxes.indexOf(j)+comboBoxes.get(checkBoxes.indexOf(j)).getSelectedItem());
                    sortValues.put(j.getText(),comboBoxes.get(checkBoxes.indexOf(j)).getSelectedItem().toString());
                }
            }
            if(cols.length() >0) {
                cols.deleteCharAt(cols.length()-1);
                //System.out.println(cols.toString());

             // mapa sa vrednostima za sort
//                for (HashMap.Entry<String,String> entry : sortValues.entrySet()){
//                    System.out.println("Izlaz:"+entry.getKey()+entry.getValue());
//                }
                MainFrame.getInstance().getAppCore().filterAndSort(sortValues,cols.toString(),
                        nt.getName(),
                        ((NorthTablePanel) MainFrame.getInstance().getNorthTab().getSelectedComponent()).getTableModel());



            }
            /*MainFrame.getInstance().getNorthTab().addTabWithTable(nt,
                    MainFrame.getInstance().getAppCore().filterAndSort(null,cols.toString(),nt.getName()));*/

        }
        //System.out.println("aa");
        ChangeSouthPanel.change();
    }
}
