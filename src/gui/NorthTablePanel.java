package gui;

import gui.table.TableModel;
import resource.implementation.Entity;

import javax.swing.*;
import java.awt.*;

public class NorthTablePanel extends JPanel {

    private Entity entity;
    private TableModel tableModel;
    private JTable jTable;

    public NorthTablePanel(Entity entity, TableModel tableModel) {
        super();
        setLayout(new BorderLayout());
        this.entity = entity;

        this.tableModel = tableModel;
        setName(entity.getName().toString());

        jTable = new JTable();
        //float yDimenzija = this.getAlignmentY()/3;
        Dimension dimension=MainFrame.getInstance().getSize();
        int height = (int) ((int) dimension.height/3.5);
        jTable.setPreferredScrollableViewportSize(new Dimension(500, height));
        jTable.setFillsViewportHeight(true);
        this.add(new JScrollPane(jTable), BorderLayout.NORTH);
        jTable.setModel(this.tableModel);

        NorthToolBar northToolBar = new NorthToolBar();
        this.add(northToolBar, BorderLayout.SOUTH);


    }

    public Entity getEntity() {
        return entity;
    }
}
