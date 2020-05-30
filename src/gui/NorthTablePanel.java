package gui;

import gui.table.TableModel;
import resource.implementation.Entity;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
        //

        jTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                int sel = jTable.getSelectedRow();
                System.out.println("IZABRAN RED "+sel);
            }
        });

        //float yDimenzija = this.getAlignmentY()/3;
        Dimension dimension=MainFrame.getInstance().getSize();
        int height = (int) ((int) dimension.height/3.5);
        jTable.setPreferredScrollableViewportSize(new Dimension(500, height));
        jTable.setFillsViewportHeight(true);
        this.add(new JScrollPane(jTable), BorderLayout.NORTH);
        jTable.setModel(this.tableModel);
        this.add(Box.createVerticalStrut(10));

        NorthToolBar northToolBar = new NorthToolBar();
        this.add(northToolBar, BorderLayout.SOUTH);


    }

    public Entity getEntity() {
        return entity;
    }
}
