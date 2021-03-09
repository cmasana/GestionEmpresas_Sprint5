package custom_ui.tables;

import custom_ui.colors.ColorsPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.IOException;

/**
 * Allow customize the cells of a table
 * @author cmasana
 */
public class CustomTableCellRender extends DefaultTableCellRenderer {
    // Custom colors
    private final ColorsPalette DYE = new ColorsPalette();

    // Renderize the component with their configurations
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        try {
            // Cells configuration (UI)
            this.setOpaque(true);
            this.setHorizontalAlignment(JLabel.CENTER);
            this.setBorder(new EmptyBorder(0, 0, 0, 0));
            this.setFont(new Font("Open Sans", Font.PLAIN, 12));

            // Hover effect when a row is selected
            if (isSelected) {
                this.setBackground(DYE.getHOVER());
                this.setForeground(DYE.getTXTHOVER());
            } else {
                this.setBackground(DYE.getSECONDARY());
                this.setForeground(DYE.getTXTMAIN());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this;
    }
}
