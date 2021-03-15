package custom_ui.tables;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * Permite transformar un ResultSet a un CustomTableModel
 */
public class ResultSetToCustomTableModel {

    /**
     * Rellena el CustomTableModel con los datos del ResultSet
     * (Primero, vacía todos los datos e introduce los datos)
     * @param rs resultado de la consulta a la bbdd
     * @param model modelo de la tabla
     */
    public static void fillModel(ResultSet rs, DefaultTableModel model) {
        configColumns(rs, model);
        cleanRows(model);
        addDataRows(rs, model);
    }

    /**
     * Permite añadir al modelo las filas correspondientes del ResultSet
     * @param rs Resultado de la consulta de la bbdd
     * @param model modelo de la tabla que queremos rellenar
     */
    private static void addDataRows(ResultSet rs, DefaultTableModel model) {
        int numeroFila = 0;

        try {
            while(rs.next()) {
                Object[] datosFila = new Object[model.getColumnCount()];

                for (int i = 0; i < model.getColumnCount(); i++) {
                    datosFila[i] = rs.getObject(i + 1);
                }
                model.addRow(datosFila);
                numeroFila++;
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Limpia todas las filas del modelo
     * @param model modelo de la tabla
     */
    private static void cleanRows(final DefaultTableModel model) {
        try {
            /*
            La llamada se hace in un invokeAndWait para que se ejecute en el hilo de refresco de ventanas y evitar
            que salten excepciones durante dicho refresco.
             */
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    while (model.getRowCount() > 0) {
                        model.removeRow(0);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Introduce en el modelo tantas columnas como tiene el resultado de la consulta de la bbdd
     * @param rs Resultado de la consulta a bbdd
     * @param model modelo de la tabla
     */
    public static void configColumns(final ResultSet rs, final DefaultTableModel model) {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    try {
                        /*
                        Se obtienen los metadatos de la consulta. Con ellos podemos obtener el número de columnas y su
                        nombre
                         */
                        ResultSetMetaData metaDatos = rs.getMetaData();

                        /*
                        Se obtiene el número de columnas
                         */
                        int numeroColumnas = metaDatos.getColumnCount();

                        /*
                        Se obtienen los encabezados para cada columna
                         */
                        Object[] encabezados = new Object[numeroColumnas];

                        for (int i = 0; i < numeroColumnas; i++) {
                            encabezados[i] = metaDatos.getColumnLabel(i + 1);
                        }

                        /*
                        Introducimos encabezados al modelo
                         */
                        model.setColumnIdentifiers(encabezados);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
