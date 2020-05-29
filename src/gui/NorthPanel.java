package gui;

import javax.swing.*;
import java.awt.*;

public class NorthPanel extends JPanel {

    private NorthTab northTab;

    public NorthPanel() {
        setLayout(new BorderLayout());
        northTab = new NorthTab();
        northTab.setVisible(true);
        add(northTab, BorderLayout.NORTH);
        NorthToolBar northToolBar = new NorthToolBar();
        add(northToolBar, BorderLayout.SOUTH);

    }
}
