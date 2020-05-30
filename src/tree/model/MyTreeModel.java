package tree.model;


import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;


public class MyTreeModel extends DefaultTreeModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MutableTreeNode root;
	
	public MyTreeModel(MyNode root) {
		super(root);
		this.root=root;

	}

	public MutableTreeNode getRoot() {
		return root;
	}

	public void setRoot(MutableTreeNode root) {
		this.root = root;
	}

	

}
