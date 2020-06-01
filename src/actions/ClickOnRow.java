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
                String primaryK = "2";
                Attribute attribute = (Attribute) dbNode;
                for(DBNode dbNodeInAttribute:attribute.getChildren()){
                    if(dbNodeInAttribute instanceof AttributeConstraint){
                        AttributeConstraint attributeConstraint = (AttributeConstraint) dbNodeInAttribute;
                        if(attributeConstraint.getConstraintType()== ConstraintType.FOREIGN_KEY) {
                            foreigK = attribute.getName().toString();
                            break;
                        }
                        /*if(attributeConstraint.getConstraintType()== ConstraintType.PRIMARY_KEY) {
                            primaryK = attribute.getName().toString();
                            break;
                        }*/

                    }
                }

                if(attribute.getInRelationWith()!=null) {
                    Attribute atrVeza = attribute.getInRelationWith();
                    NorthTablePanel ntp = (NorthTablePanel)MainFrame.getInstance().getNorthTab().getSelectedComponent();
                    Row currentRow = ntp.getTableModel().getRows().get(ntp.getjTable().getSelectedRow());



                    if(atrVeza.getParent() instanceof  Entity) {
                        Entity tableStraniKljuc = (Entity) atrVeza.getParent();
                        String foreigValue =(String) currentRow.getFields().get(foreigK);
                        if(dbNode.getParent().getName().equals(tableStraniKljuc.getName())) {

                            for (DBNode dbNode1:entity.getChildren()) {

                                if (dbNode1 instanceof Attribute) {

                                    Attribute attribute1 = (Attribute) dbNode1;
                                    for (DBNode dbNodeInAttribute1 : attribute1.getChildren()) {
                                        if (dbNodeInAttribute1 instanceof AttributeConstraint) {
                                            AttributeConstraint attributeConstraint1 = (AttributeConstraint) dbNodeInAttribute1;

                                            if(attributeConstraint1.getConstraintType()== ConstraintType.PRIMARY_KEY) {
                                                foreigK = attribute1.getName().toString();
                                                break;
                                            }

                                        }
                                    }
                                }
                            }


                        }
                        String query = foreigK+"='"+foreigValue+"'";
                        //System.out.println(query);
                        if (foreigValue == null)
                            continue;


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
