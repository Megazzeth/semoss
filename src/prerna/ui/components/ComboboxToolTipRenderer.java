package prerna.ui.components;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComponent;
import javax.swing.JList;
public class ComboboxToolTipRenderer extends DefaultListCellRenderer {
    ArrayList tooltips;

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
                        int index, boolean isSelected, boolean cellHasFocus) {

        JComponent comp = (JComponent) super.getListCellRendererComponent(list,
                value, index, isSelected, cellHasFocus);

        if (-1 < index && null != value && null != tooltips) {
        			String ttString = "<html><body style=\"border:0px solid white; box-shadow:1px 1px 1px #000; padding:2px; background-color:white;\">" +
        					"<font size=\"3\" color=\"black\"><i>"+ tooltips.get(index)+ "</i></font></body></html>";
                    list.setToolTipText(ttString);
                }
        return comp;
    }

    public void setTooltips(ArrayList tooltips) {
        this.tooltips = tooltips;
    }
}