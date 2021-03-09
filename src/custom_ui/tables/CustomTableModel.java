package custom_ui.tables;

import javax.swing.table.DefaultTableModel;

/**
 * Allow customize the model of a JTable
 * @author cmasana
 */
public class CustomTableModel extends DefaultTableModel {
    String[] encabezadosTabla;
    Object[][] datosTabla;

    public CustomTableModel(Object[][] datosTabla, String[] encabezadosTabla) {
        super();
        this.datosTabla = datosTabla;
        this.encabezadosTabla = encabezadosTabla;

        setDataVector(datosTabla, encabezadosTabla);
    }

    /**
     * Determines if table cells are editable or not
     * @param row row from table
     * @param col column from table
     * @return return TRUE if cells are editable or FALSE if not
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }
}
