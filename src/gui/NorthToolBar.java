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
        this.add(Box.createHorizontalStrut(5));
        addSeparator();
        this.add(Box.createHorizontalStrut(5));
        add(MainFrame.getInstance().getActionManager().getUpdateAction());
        this.add(Box.createHorizontalStrut(5));
        addSeparator();
        this.add(Box.createHorizontalStrut(5));
        add(MainFrame.getInstance().getActionManager().getDeleteAction());
        this.add(Box.createHorizontalStrut(5));
        addSeparator();

        this.add(Box.createHorizontalStrut(5));
        add(MainFrame.getInstance().getActionManager().getFilterAndSort());
        this.add(Box.createHorizontalStrut(5));
        addSeparator();

        this.add(Box.createHorizontalStrut(5));
        add(MainFrame.getInstance().getActionManager().getRemoveFilter());
        this.add(Box.createHorizontalStrut(5));
        addSeparator();

        this.add(Box.createHorizontalStrut(5));
        add(MainFrame.getInstance().getActionManager().getSearch());
        this.add(Box.createHorizontalStrut(5));
        addSeparator();

        this.add(Box.createHorizontalStrut(5));
        add(MainFrame.getInstance().getActionManager().getAverage());
        this.add(Box.createHorizontalStrut(5));
        addSeparator();

        this.add(Box.createHorizontalStrut(5));
        add(MainFrame.getInstance().getActionManager().getCount());
        this.add(Box.createHorizontalStrut(5));
        addSeparator();


    }
}
