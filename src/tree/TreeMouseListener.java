package tree;


import gui.MainFrame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class TreeMouseListener extends MouseAdapter {

	@Override
	public void mousePressed(MouseEvent e) {
		MyTree tree = MainFrame.getInstance().getMyTree();


		super.mousePressed(e);

		if ((e.getClickCount() == 2)
			//	&& (tree.getLastSelectedPathComponent() != null)
		 ) {
			/*
			if (tree.getLastSelectedPathComponent() instanceof ProjectNode) {
				// System.out.println(tree.getLastSelectedPathComponent().toString());



			}*/
		}
	}

}
