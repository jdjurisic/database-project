package gui;

import javax.swing.*;
import java.awt.*;

public class SouthPanel extends JPanel {

    private SouthTab southTab;

    public SouthPanel() {
        southTab = new SouthTab();

        southTab.setVisible(true);
        add(southTab, BorderLayout.NORTH);
    }
}
