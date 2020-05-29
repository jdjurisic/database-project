package gui;

import app.AppCore;
import lombok.Data;
import observer.Notification;
import observer.Subscriber;
import observer.enums.NotificationCode;
import resource.implementation.InformationResource;
import tree.MyTree;
import tree.model.MyNode;
import tree.model.MyTreeModel;


import javax.swing.*;
import java.awt.*;
@Data
public class MainFrame  extends JFrame implements Subscriber {


    private static MainFrame instance = null;
    private AppCore appCore;
    private NorthPanel northPanel;
    private SouthPanel southPanel;

    private MyTree myTree;
    private MyTreeModel myTreeModel;


    private MainFrame() {

    }


    private void initialise() {

        /*System.out.println(getAppCore().loadTree());
       myTreeModel = new MyTreeModel(new MyNode(getAppCore().loadTree()));
        myTree = new MyTree(myTreeModel);*/
        setFrame();
    }

    private void setFrame() {
        setTitle("Baze podataka");
        setLayout(new BorderLayout());
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        setSize((int) (screenSize.width / 1.618), (int) (screenSize.height / 1.618));
        setLocationRelativeTo(null);


        MainMenu mainMenu = new MainMenu();
        setJMenuBar(mainMenu);

        /*MainToolBar mainToolBar = new MainToolBar();
        add(mainToolBar, BorderLayout.NORTH);*/

        JPanel myCenterPanel = new JPanel();
        myCenterPanel.setLayout(new BorderLayout());


        northPanel = new NorthPanel();
        northPanel.setVisible(true);
        myCenterPanel.add(northPanel, BorderLayout.NORTH);

        southPanel = new SouthPanel();
        southPanel.setVisible(true);
        myCenterPanel.add(southPanel, BorderLayout.SOUTH);

        myCenterPanel.setVisible(true);
        add(myCenterPanel, BorderLayout.CENTER);

        JScrollPane scrollTree = new JScrollPane(/*myTree*/);
        add(scrollTree);

        JSplitPane splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollTree, myCenterPanel);
        splitPane1.setDividerLocation(150);
        splitPane1.setOneTouchExpandable(true);
        add(splitPane1, BorderLayout.CENTER);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this); //osvjezavanje stabla
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }




    }

    public void setAppCore(AppCore appCore) {
        this.appCore = appCore;
        this.appCore.addSubscriber(this);
        //this.jTable.setModel(appCore.getTableModel());
    }



    public static MainFrame getInstance() {
        if (instance == null) {
            instance = new MainFrame();
            instance.initialise();
        }
        return instance;
    }

    @Override
    public void update(Notification notification) {
        if (notification.getCode() == NotificationCode.RESOURCE_LOADED){
            System.out.println((InformationResource)notification.getData());
            myTreeModel = new MyTreeModel(new MyNode((InformationResource)notification.getData()));
            myTree = new MyTree(myTreeModel);
            SwingUtilities.updateComponentTreeUI(this);
        }
    }


}
