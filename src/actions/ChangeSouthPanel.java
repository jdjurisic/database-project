package actions;

import gui.MainFrame;
import gui.NorthTablePanel;
import resource.DBNode;
import resource.implementation.Attribute;
import resource.implementation.Entity;

public interface ChangeSouthPanel {

    static void change(){
        NorthTablePanel northTablePanel= (NorthTablePanel) MainFrame.getInstance().getNorthTab().getSelectedComponent();
        Entity entity = northTablePanel.getEntity();
        MainFrame.getInstance().getSouthTab().removeAll();
        for (DBNode dbNode:entity.getChildren()) {
            if(dbNode instanceof Attribute){
                Attribute attribute = (Attribute) dbNode;
                if(attribute.getInRelationWith()!=null) {
                    Attribute atrVeza = attribute.getInRelationWith();
                    if(atrVeza.getParent() instanceof Entity) {
                        Entity tableStraniKljuc = (Entity) atrVeza.getParent();
                        //System.out.println(tableStraniKljuc.getName().toString());

                        MainFrame.getInstance().getSouthTab().addTabWithTable(tableStraniKljuc,
                                MainFrame.getInstance().getAppCore().readTableModelFromTable(tableStraniKljuc.getName()));
                    }
                }
            }
        }
    }

}
