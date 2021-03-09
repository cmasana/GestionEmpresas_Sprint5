package custom_ui.tables;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;

/**
 * Tables basic configuration
 * @author  cmasana
 */
public class CustomTableConfig {
    public CustomTableConfig(JTable table) {
        initConfig(table);
    }

    public static void initConfig(JTable table) {
        // Row Height
        table.setRowHeight(30);

        // Columns Width
        table.getColumnModel().getColumn(0).setMaxWidth(200);
        table.getColumnModel().getColumn(0).setPreferredWidth(250);
        table.getColumnModel().getColumn(2).setMaxWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);

        table.setIntercellSpacing(new Dimension(1,1));

        // Customize headers from table
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setDefaultRenderer(new custom_ui.tables.CustomTableHeaderRender());
        table.setTableHeader(tableHeader);
        table.getTableHeader().setReorderingAllowed(false);

        // Customize cells from table
        table.setDefaultRenderer(Object.class, new custom_ui.tables.CustomTableCellRender());
    }
}
