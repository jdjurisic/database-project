package actions;


import java.awt.event.ActionEvent;

public class Count extends MyAbstractAction {

    public Count() {
        putValue(NAME, "Count");
        putValue(SHORT_DESCRIPTION, "Count table ");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Count");
    }
}