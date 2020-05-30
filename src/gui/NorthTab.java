package gui;

import gui.table.TableModel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class NorthTab extends JTabbedPane {



    public NorthTab() {
        this.addChangeListener( new ChangeListener(){

            @Override
            public void stateChanged(ChangeEvent e) {
                //System.out.println("aa1313");
                NorthTablePanel northTablePanel= (NorthTablePanel) MainFrame.getInstance().getNorthTab().getSelectedComponent();
                System.out.println(northTablePanel.getName());
            }
        });
    }

    public void addTabWithTable(String name, TableModel tableModel){
        NorthTablePanel northTablePanel = new NorthTablePanel(name, tableModel);
        this.addTab(name,northTablePanel);

    }


}
