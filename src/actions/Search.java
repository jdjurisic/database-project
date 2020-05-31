package actions;

import java.awt.event.ActionEvent;

public class Search extends MyAbstractAction {
    public Search() {

        putValue(NAME, "Search");
        putValue(SHORT_DESCRIPTION, "Search table ");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Search");
    }
}
