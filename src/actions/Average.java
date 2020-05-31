package actions;

import java.awt.event.ActionEvent;

public class Average extends MyAbstractAction{
    public Average() {
        putValue(NAME, "Average");
        putValue(SHORT_DESCRIPTION, "Average table ");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Average");
    }
}
