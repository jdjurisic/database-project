package gui;

import javax.swing.*;
import java.awt.*;

public class SouthTablePanel extends JPanel {

    private SouthTab southTab;

    public SouthTablePanel() {
        southTab = new SouthTab();

        southTab.setVisible(true);
        add(southTab, BorderLayout.NORTH);
    }
}
