package tree;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import tree.model.MyTreeModel;

public class MyTree extends JTree{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public MyTree(MyTreeModel myTreeModel) {
		setModel(myTreeModel);
		addTreeSelectionListener(new MyTreeSelectionListener());
		setCellEditor(new MyEditTree(this, new DefaultTreeCellRenderer()));
		setCellRenderer(new MyTreeRendered());
		setEditable(false);
		addMouseListener(new TreeMouseListener());//dodavanje popUPmenija
		
	}



	
	
}
