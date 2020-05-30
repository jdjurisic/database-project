package actions;

import java.awt.event.ActionEvent;

public class AddAction extends MyAbstractAction {

    public AddAction(){
        putValue(NAME, "Add row");
        putValue(SHORT_DESCRIPTION, "Add new row to the selected table ");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Add action");
    }
}
