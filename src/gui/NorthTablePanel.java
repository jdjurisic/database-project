package gui;

import gui.table.TableModel;
import resource.implementation.Entity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
        jTable.setDefaultEditor(Object.class, null);
        jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //

//        jTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//            public void valueChanged(ListSelectionEvent e) {
//
//                int sel = jTable.getSelectedRow();
//                System.out.println("IZABRAN RED "+sel);
//            }
//        });
         //mouse adapter
       /* jTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int row = jTable.rowAtPoint(evt.getPoint());
                int col = jTable.columnAtPoint(evt.getPoint());
                if (row >= 0 && col >= 0) {
                    System.out.println("22");
                }
            }
        });*/


        jTable.addMouseListener(MainFrame.getInstance().getActionManager().getClickOnRow());




        jTable.getSelectedRow();

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

    public TableModel getTableModel() {
        return tableModel;
    }

    public JTable getjTable() {
        return jTable;
    }
}
