package gui;

import javax.swing.*;

public class MainMenu extends JMenuBar{


    public MainMenu() {
        super();
        JMenu file = new JMenu("file");
       /* file.add(MainFrame.getInstance().getActionManager().getNewNode());
        file.add(MainFrame.getInstance().getActionManager().getRemoveNode());
        file.add(MainFrame.getInstance().getActionManager().getRenameNode());
        file.addSeparator();*/




        add(file);

        JMenu tools = new JMenu("tools");
       /* tools.add(MainFrame.getInstance().getActionManager().getCloseThisTabDocument());
        tools.add(MainFrame.getInstance().getActionManager().getCloseAllTabDocument());*/

        add(tools);





    }
}
