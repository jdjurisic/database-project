package gui;

import gui.table.TableModel;
import resource.implementation.Entity;

import javax.swing.*;

public class SouthTab extends JTabbedPane {
    public SouthTab() {
        super();
    }


    public void addTabWithTable(Entity entity, TableModel tableModel){
        SouthTablePanel northTablePanel = new SouthTablePanel(entity, tableModel);
        this.addTab(entity.getName().toString(),northTablePanel);

    }
}
