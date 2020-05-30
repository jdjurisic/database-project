package gui;

import javax.swing.*;
import java.awt.*;

public class NorthToolBar extends JToolBar {


    public NorthToolBar() {

        super(BorderLayout.NORTH);
        setFloatable(false);
        addSeparator();
        this.add(Box.createHorizontalStrut(5));
        add(MainFrame.getInstance().getActionManager().getAddAction());

        addSeparator();
        this.add(Box.createHorizontalStrut(5));
        add(MainFrame.getInstance().getActionManager().getUpdateAction());
        addSeparator();
        this.add(Box.createHorizontalStrut(5));
        add(MainFrame.getInstance().getActionManager().getDeleteAction());
        addSeparator();
    }
}
