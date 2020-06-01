package actions;

import gui.MainFrame;
import gui.NorthTablePanel;
import resource.DBNode;
import resource.data.Row;
import resource.enums.ConstraintType;
import resource.implementation.Attribute;
import resource.implementation.AttributeConstraint;
import resource.implementation.Entity;

import java.util.ArrayList;
import java.util.List;

public interface ChangeSouthPanel {

    static void change(){
        NorthTablePanel northTablePanel= (NorthTablePanel) MainFrame.getInstance().getNorthTab().getSelectedComponent();
        Entity entity = northTablePanel.getEntity();
        MainFrame.getInstance().getSouthTab().removeAll();
        int i=4;
        for (DBNode dbNode:entity.getChildren()) {
            if(dbNode instanceof Attribute){
                Attribute attribute = (Attribute) dbNode;
                if(attribute.getInRelationWith()!=null) {
                    Attribute atrVeza = attribute.getInRelationWith();
                    if(atrVeza.getParent() instanceof Entity) {
                        Entity tableStraniKljuc = (Entity) atrVeza.getParent();
                        //System.out.println(tableStraniKljuc.getName().toString());
                        NorthTablePanel ntp = (NorthTablePanel)MainFrame.getInstance().getNorthTab().getSelectedComponent();
                        List<Row> currentRows = ntp.getTableModel().getRows();
                        i++;
                        boolean nemojPrekidati = false;
                        Object[] stringArrayList =currentRows.get(0).getFields().keySet().toArray();
                        for (int x = 0; x <stringArrayList.length; x++){

                            if(attribute.getName().equals(stringArrayList[x].toString())){
                                nemojPrekidati = true;
                            }
                        }
                        if(!nemojPrekidati)
                            continue;

                        MainFrame.getInstance().getSouthTab().addTabWithTable(tableStraniKljuc,
                                MainFrame.getInstance().getAppCore().readTableModelFromTable(tableStraniKljuc.getName()));
                    }
                }
            }
        }
    }

}
