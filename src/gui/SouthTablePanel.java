package gui;

import gui.table.TableModel;
import resource.implementation.Entity;

import javax.swing.*;
import java.awt.*;

public class SouthTablePanel extends JPanel {

    private Entity entity;
    private TableModel tableModel;
    private JTable jTable;

    public SouthTablePanel(Entity entity, TableModel tableModel) {
        setLayout(new BorderLayout());
        this.entity = entity;

        this.tableModel = tableModel;
        setName(entity.getName().toString());

        jTable = new JTable();
        Dimension dimension=MainFrame.getInstance().getSize();
        int height = (int) ((int) dimension.height/3.5);
        jTable.setPreferredScrollableViewportSize(new Dimension(500, height));
        jTable.setFillsViewportHeight(true);
        this.add(new JScrollPane(jTable), BorderLayout.NORTH);
        jTable.setModel(this.tableModel);
        jTable.setDefaultEditor(Object.class, null);
        jTable.setFocusable(false);
        jTable.setRowSelectionAllowed(false);
    }
}
