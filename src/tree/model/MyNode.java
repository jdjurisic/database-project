package tree.model;


import resource.DBNode;
import resource.DBNodeComposite;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.Enumeration;


public class MyNode implements MutableTreeNode {

    private DBNode dbNode;

    public MyNode(DBNode dbNode) {
        this.dbNode = dbNode;
    }


    @Override
    public void insert(MutableTreeNode child, int index) {


    }

    @Override
    public void remove(int index) {

    }

    @Override
    public void remove(MutableTreeNode node) {

    }

    @Override
    public void setUserObject(Object object) {

    }

    @Override
    public void removeFromParent() {

    }

    @Override
    public void setParent(MutableTreeNode newParent) {
        if(newParent instanceof MyNode){
            DBNode nodeDb = ((MyNode) newParent).getDbNode();
            getDbNode().setParent(nodeDb);
        }
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        if(getDbNode() instanceof  DBNodeComposite){
            DBNodeComposite dbNodeComposite = (DBNodeComposite) getDbNode();
            return (new MyNode(dbNodeComposite.getChildren().get(childIndex)));


        }
        return null;
    }

    @Override
    public int getChildCount() {
        if(getDbNode() instanceof  DBNodeComposite){
            DBNodeComposite dbNodeComposite = (DBNodeComposite) getDbNode();
            return (dbNodeComposite.getChildren().size());
        }

        return 0;
    }

    @Override
    public TreeNode getParent() {

        return new MyNode(getDbNode().getParent());
    }

    @Override
    public int getIndex(TreeNode node) {
        if(getDbNode() instanceof  DBNodeComposite){
            DBNodeComposite dbNodeComposite = (DBNodeComposite) getDbNode();
            if(node instanceof MyNode){
                DBNode nodeDb = ((MyNode) node).getDbNode();
                return dbNodeComposite.getChildren().indexOf(nodeDb);
            }
        }
        return 0;
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public boolean isLeaf() {
        if(getDbNode() instanceof DBNodeComposite)
            return false;
        return true;
    }

    @Override
    public Enumeration children() {
        return null;
    }

    public DBNode getDbNode() {
        return dbNode;
    }
}
