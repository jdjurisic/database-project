package actions;

import app.Main;
import gui.MainFrame;
import gui.NorthTablePanel;
import resource.data.Row;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

public class DeleteAction extends MyAbstractAction {

    public DeleteAction(){
        putValue(NAME, "Delete row");
        putValue(SHORT_DESCRIPTION, " Delete currently selected row ");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //System.out.println(" DEL ");
        //TODO greska ako nije selektovano


        NorthTablePanel ntp = (NorthTablePanel)MainFrame.getInstance().getNorthTab().getSelectedComponent();
        if(ntp.getjTable().getSelectedRow() == -1){
            JOptionPane.showMessageDialog(null, "Row not Selected", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int x = JOptionPane.showConfirmDialog(MainFrame.getInstance(), "Confirm the action delete");
        if(x>0)
            return;
        Row currentRow = ntp.getTableModel().getRows().get(ntp.getjTable().getSelectedRow());


        /*
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DELETE FROM ");
        stringBuilder.append(currentRow.getName());
        stringBuilder.append(" WHERE ");
        boolean firstTime=false;
        for(Map.Entry<String,Object> entry : currentRow.getFields().entrySet()){
            if(firstTime==false){
                firstTime=true;

            }else{
                stringBuilder.append(" AND ");
            }
            stringBuilder.append(entry);
            stringBuilder.append(entry.getKey()+"=");
            stringBuilder.append("'"+entry.getValue().toString()+"'");

        }
        stringBuilder.append(";");
        System.out.println(stringBuilder);*/
        String konst = "DELETE FROM REGIONS WHERE region_id='6.0' AND region_name='Westeros';";
        //MainFrame.getInstance().getAppCore().executeQuery(konst,null,((NorthTablePanel) MainFrame.getInstance().getNorthTab().getSelectedComponent()).getTableModel(),ntp.getEntity().getName());
        MainFrame.getInstance().getAppCore().deleteToTable((HashMap<String, Object>) currentRow.getFields(),
                ((NorthTablePanel) MainFrame.getInstance().getNorthTab().getSelectedComponent()).getTableModel(),
                ntp.getEntity().getName());


    }
}
