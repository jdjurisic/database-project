package actions;

import gui.MainFrame;
import gui.NorthTablePanel;
import resource.DBNode;
import resource.data.Row;
import resource.enums.ConstraintType;
import resource.implementation.Attribute;
import resource.implementation.AttributeConstraint;
import resource.implementation.Entity;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClickOnRow extends MouseAdapter {

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        NorthTablePanel northTablePanel= (NorthTablePanel) MainFrame.getInstance().getNorthTab().getSelectedComponent();
        Entity entity = northTablePanel.getEntity();
        MainFrame.getInstance().getSouthTab().removeAll();
        for (DBNode dbNode:entity.getChildren()) {

            if(dbNode instanceof Attribute){
                String foreigK="1";
                Attribute attribute = (Attribute) dbNode;
                for(DBNode dbNodeInAttribute:attribute.getChildren()){
                    if(dbNodeInAttribute instanceof AttributeConstraint){
                        AttributeConstraint attributeConstraint = (AttributeConstraint) dbNodeInAttribute;
                        if(attributeConstraint.getConstraintType()== ConstraintType.FOREIGN_KEY) {
                            foreigK = attribute.getName().toString();
                            break;
                        }

                    }
                }

                if(attribute.getInRelationWith()!=null) {

                    NorthTablePanel ntp = (NorthTablePanel)MainFrame.getInstance().getNorthTab().getSelectedComponent();
                    Row currentRow = ntp.getTableModel().getRows().get(ntp.getjTable().getSelectedRow());
                    String foreigValue =(String) currentRow.getFields().get(foreigK);
                    String query = foreigK+"='"+foreigValue+"'";
                    //System.out.println(query);
                    if (foreigValue == null)
                        continue;

                    Attribute atrVeza = attribute.getInRelationWith();
                    if(atrVeza.getParent() instanceof  Entity) {

                        Entity tableStraniKljuc = (Entity) atrVeza.getParent();
                        //System.out.println(tableStraniKljuc.getName().toString());

                        MainFrame.getInstance().getSouthTab().addTabWithTable(tableStraniKljuc,
                                MainFrame.getInstance().getAppCore().clickOnRow(query,
                                        tableStraniKljuc.getName()));
                    }
                }
            }
        }
    }
}
