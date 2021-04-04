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

        // Column Header Width
        /*
        Es necesario poner la primera columna con un Min/Max a 0 para ocultar el ID
        En las celdas, hacemos lo mismo
         */
        table.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        table.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);

        // Columns Width
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(1).setMaxWidth(200);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setMaxWidth(400);
        table.getColumnModel().getColumn(2).setPreferredWidth(400);
        //table.getColumnModel().getColumn(3).setMaxWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);

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
