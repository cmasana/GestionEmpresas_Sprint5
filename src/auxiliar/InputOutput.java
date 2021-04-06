package auxiliar;

import custom_ui.components.forms.RowForm;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Grup Individual Sprint 3 2020 - Carlos Masana -
 *
 * Biblioteca de Entradas / Salidas de la aplicación
 */
public class InputOutput {
    // Fechas
    private final static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Muestra por pantalla un cuadro de alerta con el mensaje indicado
     * @param message mensaje que se muestra por pantalla
     */
    public static void printAlert(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", 0);
    }

    /**
     * Vacía los textfields indicados
     * @param inputText numero variable de argumentos de tipo RowForm
     */
    public static void cleanInputs(RowForm... inputText) {
        for (int i = 0; i < inputText.length; i++) {
            inputText[i].setTxtInput("");
        }
    }

    /**
     * Transforma un objeto Date a String
     * @param fecha Objeto de tipo Date
     * @return devuelve un String con la fecha (dd/MM/yyyy)
     */
    public static String dateToString(Date fecha) {
        return SDF.format(fecha);
    }

    /**
     * Transforma un String a Date
     * @param fecha fecha en formato String
     * @return devuelve un Objeto de la clase Date
     * @throws ParseException excepción que arroja si existen errores
     */
    public static Date stringToDate(String fecha) throws ParseException {
        return SDF.parse(fecha);
    }

    /**
     * Permite averiguar si la fecha introducida es correcta o no
     * @param fecha Date que queremos comparar
     * @return devuelve TRUE si la fecha es anterior o FALSE si es posterior o igual
     */
    public static boolean wrongDate(String fecha) throws ParseException {
        Date dFecha;
        String sToday; // Fecha de hoy en string

        Date today = new Date();
        sToday = dateToString(today);

        today = stringToDate(sToday);
        dFecha = stringToDate(fecha);

        // Si es menor que 0 se trata de una fecha anterior a hoy
        if (dFecha.compareTo(today) >= 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Permite averiguar si la fecha introducida es correcta o no
     * @param modificada Date que queremos comprobar con la fecha modificada
     * @param original Date fecha original
     * @return devuelve TRUE si la fecha es anterior o FALSE si es posterior o igual
     */
    public static boolean wrongEditedDate(String modificada, Date original) {
        boolean after = true;
        try {
            Date dModificada = stringToDate(modificada);

            // Si es menor que 0 se trata de una fecha anterior
            if (dModificada.compareTo(original) >= 0) {
                after = false;
            }

        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        return after;
    }

    /**
     * Muestra la fecha actual en formato String
     * @return devuelve un String con la fecha actual
     */
    public static String todayDate() {
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sdf.format(today);
    }

    /**
     * Muestra un mensaje de confirmación cuando queremos eliminar un elemento
     * @return devuelve un entero, OK = 0
     */
    public static int deleteConfirmation() {
       return JOptionPane.showConfirmDialog(null, "Estás seguro?", "El elemento va a ser eliminado", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Muestra un mensaje de confirmación cuando queremos modificar un elemento
     * @return devuelve un entero, OK = 0
     */
    public static int editConfirmation() {
        return JOptionPane.showConfirmDialog(null, "Estás seguro?", "El elemento va a ser modificado", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Comprueba la opción seleccionada en los mensajes de confirmación
     * @param resultado entero con el valor de la opción seleccionada (OK = 0)
     * @return devuelve TRUE si el valor es 0
     */
    public static boolean ifOk(int resultado) {
        return resultado == 0;
    }

    /**
     * Muestra un mensaje de confirmación cuando queremos eliminar todos los elementos
     * @return devuelve un entero, OK = 0
     */
    public static int emptyConfirmation() {
        return JOptionPane.showConfirmDialog(null, "Estás seguro?", "Se eliminarán todos los elementos", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Transforma un String a entero
     * @param elemento String que queremos cambiar
     * @return devuelve un entero
     */
    public static int stringToInt(String elemento) {
        return Integer.parseInt(elemento);
    }

    // Comprobaciones JTables

    /**
     * Permite averiguar si hay alguna fila seleccionada en una JTable
     * @param table JTable que almacena registros de la bbdd
     * @return devuelve TRUE si hay una fila seleccionada o FALSE si no hay
     */
    public static boolean rowIsSelected(JTable table) {
        boolean isSelected = true;
        int selectedRow = table.getSelectedRow();

        if (selectedRow < 0) {
            isSelected = false;
        }
        return isSelected;
    }


    /**
     * Permite averiguar si existe algún registro en una JTable
     * @param table JTable que almacena registros de la bbdd
     * @return devuelve TRUE si existe al menos una fila o FALSE si no existe ninguna
     */
    public static boolean someRows(JTable table) {
        boolean exists = true;
        int totalRows = table.getRowCount();

        if (totalRows == 0) {
            exists = false;
        }
        return exists;
    }


}
