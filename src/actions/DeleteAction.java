package actions;

import java.awt.event.ActionEvent;

public class DeleteAction extends MyAbstractAction {

    public DeleteAction(){
        putValue(NAME, "Delete row");
        putValue(SHORT_DESCRIPTION, " Delete currently selected row ");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(" del ");

    }
}
