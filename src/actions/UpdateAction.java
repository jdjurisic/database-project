package actions;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class UpdateAction extends MyAbstractAction {

    public UpdateAction(){
        //putValue(SMALL_ICON, UIManager.getIcon("FileChooser.newFolderIcon"));
        putValue(NAME, "Edit row");
        putValue(SHORT_DESCRIPTION, " Edit currently selected row ");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(" edit ");
    }
}
