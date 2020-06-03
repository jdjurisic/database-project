package actions;

import gui.MainFrame;
import gui.NorthTablePanel;
import resource.data.Row;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UpdateAction extends MyAbstractAction {

    public UpdateAction(){
        //putValue(SMALL_ICON, UIManager.getIcon("FileChooser.newFolderIcon"));
        putValue(NAME, "Edit row");
        putValue(SHORT_DESCRIPTION, " Edit currently selected row ");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //System.out.println(" edit ");
        NorthTablePanel ntp = (NorthTablePanel) MainFrame.getInstance().getNorthTab().getSelectedComponent();
        if(ntp.getjTable().getSelectedRow() == -1){
            JOptionPane.showMessageDialog(null,
                    "Select column!",
                    "Input error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        Row currentRow = ntp.getTableModel().getRows().get(ntp.getjTable().getSelectedRow());

        ArrayList<JTextField> fields = new ArrayList<>();
        ArrayList<JLabel> labels = new ArrayList<>();

        JPanel myPanel = new JPanel();
        GridLayout gr = new GridLayout(currentRow.getFields().size(),2,2,2);
        myPanel.setLayout(gr);

        for(Map.Entry<String,Object> entry : currentRow.getFields().entrySet()){
            JTextField jtf = new JTextField(5);
            if(entry.getValue() != null){
                jtf.setText(entry.getValue().toString());
            }
            fields.add(jtf);
            labels.add(new JLabel(entry.getKey().toString()));
        }

        for(int i=0;i<labels.size();i++){
            myPanel.add(labels.get(i));
            myPanel.add(fields.get(i));
        }

        HashMap<String,Object> newValues = new HashMap<>();
        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please Enter New Values", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            for (int i = 0; i < labels.size(); i++) {
                //System.out.println(labels.get(i).getText() + " " + fields.get(i).getText());
                if (fields.get(i).getText() != null) {
                    newValues.put(labels.get(i).getText(), fields.get(i).getText());
                }
            }

            MainFrame.getInstance().getAppCore().updateToTable((HashMap<String, Object>)currentRow.getFields(),newValues,
                    ((NorthTablePanel) MainFrame.getInstance().getNorthTab().getSelectedComponent()).getTableModel(),ntp.getEntity().getName());

        }

    }
}
