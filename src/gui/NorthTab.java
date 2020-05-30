package gui;

import actions.ChangeNortTab;
import gui.table.TableModel;
import resource.implementation.Entity;

import javax.swing.*;

public class NorthTab extends JTabbedPane {



    public NorthTab() {
        //this.
        this.addChangeListener(
            new ChangeNortTab()
        );
    }

    public void addTabWithTable(Entity entity, TableModel tableModel){
        NorthTablePanel northTablePanel = new NorthTablePanel(entity, tableModel);
        this.addTab(entity.getName().toString(),northTablePanel);

    }


}
