package auxiliar;

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
    private final static SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Muestra por pantalla un cuadro de alerta con el mensaje indicado
     * @param message mensaje que se muestra por pantalla
     */
    public static void printAlert(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", 0);
    }

    /**
     * Vacía los textfields indicados
     * @param field1 primer textfield
     * @param field2 segundo textfield
     * @param field3 tercer textfield
     * @param field4 cuarto textfield
     */
    public static void cleanInputs(JTextField field1, JTextField field2, JTextField field3, JTextField field4) {
        field1.setText("");
        field2.setText("");
        field3.setText("");
        field4.setText("");
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
     * Muestra un mensaje de confirmación cuando queremos eliminar un elemento
     * @return devuelve un entero, OK = 0
     */
    public static int deleteConfirmation() {
       return JOptionPane.showConfirmDialog(null, "Estás seguro?", "El elemento va a ser eliminado", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Muestra un mensaje de confirmación cuando queremos eliminar todos los elementos
     * @return devuelve un entero, OK = 0
     */
    public static int emptyConfirmation() {
        return JOptionPane.showConfirmDialog(null, "Estás seguro?", "Se eliminarán todos los elementos", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    }
}
