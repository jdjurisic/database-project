package gui;

import gui.table.TableModel;

import javax.swing.*;
import java.awt.*;

public class NorthTablePanel extends JPanel {

    private String name;
    private TableModel tableModel;
    private JTable jTable;

    public NorthTablePanel(String name, TableModel tableModel) {
        super();
        setLayout(new BorderLayout());
        this.name =name;
        this.tableModel = tableModel;
        setName(this.name);

        jTable = new JTable();
        jTable.setPreferredScrollableViewportSize(new Dimension(500, 200));
        jTable.setFillsViewportHeight(true);
        this.add(new JScrollPane(jTable), BorderLayout.NORTH);
        jTable.setModel(this.tableModel);

        NorthToolBar northToolBar = new NorthToolBar();
        this.add(northToolBar, BorderLayout.SOUTH);






    }
}
