package tree;


import app.Main;
import gui.MainFrame;
import resource.DBNode;
import resource.DBNodeComposite;
import resource.implementation.Attribute;
import resource.implementation.Entity;
import tree.model.MyNode;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class TreeMouseListener extends MouseAdapter {

	@Override
	public void mousePressed(MouseEvent e) {
		MyTree tree = MainFrame.getInstance().getMyTree();


		super.mousePressed(e);

		if ((e.getClickCount() == 2) && (tree.getLastSelectedPathComponent() != null)) {


			if (tree.getLastSelectedPathComponent() instanceof MyNode) {
				MyNode myNode = (MyNode) tree.getLastSelectedPathComponent();
				if(myNode.getDbNode() instanceof Entity){
					Entity entity = (Entity) myNode.getDbNode();
					//novo
					boolean alreadyThere = false;
					for(int i=0; i< MainFrame.getInstance().getNorthTab().getTabCount();i++)
						if(entity.getName().equals(MainFrame.getInstance().getNorthTab().getComponentAt(i).getName())){
							MainFrame.getInstance().getNorthTab().setSelectedIndex(i);
							//System.out.println("VEC SAM TU");
							alreadyThere = true;
							break;
						}
					//novo
					if(!alreadyThere){
						MainFrame.getInstance().getNorthTab().addTabWithTable(entity,
								MainFrame.getInstance().getAppCore().readTableModelFromTable(entity.getName()));
						MainFrame.getInstance().getNorthTab().setSelectedIndex(MainFrame.getInstance().getNorthTab().getTabCount()-1);
					}
					MainFrame.getInstance().getSouthTab().removeAll();
					for (DBNode dbNode:entity.getChildren()) {
						if(dbNode instanceof Attribute){
							Attribute attribute = (Attribute) dbNode;
							if(attribute.getInRelationWith()!=null) {
								Attribute atrVeza = attribute.getInRelationWith();
								if(atrVeza.getParent() instanceof  Entity) {
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
		}
	}

}
