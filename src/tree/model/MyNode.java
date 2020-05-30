package tree.model;


import resource.DBNode;
import resource.DBNodeComposite;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.Enumeration;


public class MyNode extends   DefaultMutableTreeNode {

    private DBNode dbNode;

    public MyNode(DBNode dbNode) {
       super(dbNode, true);
        this.dbNode = dbNode;
//        System.out.println("aa");
    }

/*
    @Override
    public void insert(MutableTreeNode child, int index) {
        System.out.println("ii");

    }

    @Override
    public void remove(int index) {
        System.out.println("2");
    }

    @Override
    public void remove(MutableTreeNode node) {
        System.out.println("3");
    }

    @Override
    public void setUserObject(Object object) {
        System.out.println("4");
    }

    @Override
    public void removeFromParent() {
        System.out.println("5");
    }

    @Override
    public void setParent(MutableTreeNode newParent) {
        if(newParent instanceof MyNode){
            DBNode nodeDb = ((MyNode) newParent).getDbNode();
            getDbNode().setParent(nodeDb);
        }
    }*/

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
           // System.out.println(dbNodeComposite.getChildren().size());

            return (dbNodeComposite.getChildren().size());
        }

        return 0;
    }

    @Override
    public String toString() {
        return getDbNode().getName();
    }

/*
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
        System.out.println("a");
        return null;
    }*/

    public DBNode getDbNode() {
        return dbNode;
    }
}
