package actions;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClickOnRow extends MouseAdapter {

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        System.out.println("aa");
    }
}
