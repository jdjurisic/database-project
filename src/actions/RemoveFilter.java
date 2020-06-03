package actions;

import gui.MainFrame;
import gui.NorthTablePanel;
import resource.implementation.Entity;

import java.awt.event.ActionEvent;

public class RemoveFilter extends MyAbstractAction {
    public RemoveFilter() {
        putValue(NAME, "Reload table");
        putValue(SHORT_DESCRIPTION, "reload table from date base");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Entity entity = ((NorthTablePanel) MainFrame.getInstance().getNorthTab().getSelectedComponent()).getEntity();


        MainFrame.getInstance().getAppCore().removeFilter(entity.getName(),
                ((NorthTablePanel) MainFrame.getInstance().getNorthTab().getSelectedComponent()).getTableModel());
        ChangeSouthPanel.change();
    }
}
