package gui;

import javax.swing.*;
import java.awt.*;

public class NorthToolBar extends JToolBar {


    public NorthToolBar() {

        super(BorderLayout.NORTH);
        setFloatable(true);


       /* add(MainFrame.getInstance().getActionManager().getNewNode());
        add(MainFrame.getInstance().getActionManager().getRemoveNode());
        add(MainFrame.getInstance().getActionManager().getRenameNode());*/
        addSeparator();
    }
}
