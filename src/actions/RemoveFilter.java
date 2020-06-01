package actions;

import gui.MainFrame;
import gui.NorthTablePanel;
import resource.implementation.Entity;

import java.awt.event.ActionEvent;

public class RemoveFilter extends MyAbstractAction {
    public RemoveFilter() {
        putValue(NAME, "Remove Filter&Sort");
        putValue(SHORT_DESCRIPTION, "remove filter and sort table ");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Entity entity = ((NorthTablePanel) MainFrame.getInstance().getNorthTab().getSelectedComponent()).getEntity();


        MainFrame.getInstance().getAppCore().removeFilter(entity.getName(),
                ((NorthTablePanel) MainFrame.getInstance().getNorthTab().getSelectedComponent()).getTableModel());
    }
}
