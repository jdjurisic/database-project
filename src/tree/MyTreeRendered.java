package tree;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

public class MyTreeRendered  extends DefaultTreeCellRenderer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MyTreeRendered() {

	}
	
	@Override
	public Component getTreeCellRendererComponent(JTree arg0, Object arg1, boolean arg2, boolean arg3, boolean arg4,
			int arg5, boolean arg6) {

		
		return super.getTreeCellRendererComponent(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
	}

}
