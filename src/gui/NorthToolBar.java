package gui;

import javax.swing.*;
import java.awt.*;

public class NorthToolBar extends JToolBar {


    public NorthToolBar() {

        super(BorderLayout.NORTH);
        setFloatable(false);
        addSeparator();
        add(MainFrame.getInstance().getActionManager().getAddAction());
        addSeparator();
        add(MainFrame.getInstance().getActionManager().getUpdateAction());
        addSeparator();
        add(MainFrame.getInstance().getActionManager().getDeleteAction());
        addSeparator();
    }
}
