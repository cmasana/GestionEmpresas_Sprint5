package custom_ui.tables;

import custom_ui.colors.ColorsPalette;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.IOException;

/**
 * Allow customize the headers of a table
 * @author cmasana
 */
public class CustomTableHeaderRender implements TableCellRenderer {
    private final ColorsPalette DYE = new ColorsPalette();


    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        JLabel jcomponent = null;

        if (value instanceof String) {
            jcomponent = new JLabel((String) value);
            jcomponent.setHorizontalAlignment(SwingConstants.CENTER);
            jcomponent.setSize(30, jcomponent.getWidth());
            jcomponent.setPreferredSize(new Dimension(6, jcomponent.getWidth()));
        }

        assert jcomponent != null;
        try {
            jcomponent.setBorder(new MatteBorder(0, 0, 1, 1, DYE.getMAIN()));
            jcomponent.setOpaque(true);
            jcomponent.setBackground(DYE.getMAIN());
            jcomponent.setForeground(DYE.getTXTMAIN());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return jcomponent;
    }
}
