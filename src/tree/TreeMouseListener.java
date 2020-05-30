package tree;


import app.Main;
import gui.MainFrame;
import resource.DBNodeComposite;
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
						}
					//novo
					if(!alreadyThere){
						MainFrame.getInstance().getNorthTab().addTabWithTable(entity.getName(),
								MainFrame.getInstance().getAppCore().readTableModelFromTable(entity.getName()));
						MainFrame.getInstance().getNorthTab().setSelectedIndex(MainFrame.getInstance().getNorthTab().getTabCount()-1);
					}

				}



			}
		}
	}

}
