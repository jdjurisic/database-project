package actions;

import java.awt.event.ActionEvent;

public class FilterAndSort extends MyAbstractAction{

    public FilterAndSort() {
        putValue(NAME, "Filter&Sort");
        putValue(SHORT_DESCRIPTION, "filter and sort table ");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("filter");
    }
}
