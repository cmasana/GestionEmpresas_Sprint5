package modules;

import auxiliar.CustomException;
import auxiliar.InputOutput;
import auxiliar.Log;
import custom_ui.tables.*;
import mainclasses.database.EmployeeDB;
import auxiliar.Error;
import mainclasses.user.Employee;
import validations.*;

import javax.swing.*;

/**
 * Clase CrudUser: Implementa todos los métodos para la gestión de empleados
 */
public class CrudUser {
    // Simula bbdd
    private final EmployeeDB employeeList = new EmployeeDB();

    /**
     * Permite crear un usuario y visualizarlo en tiempo real en su correspondiente tabla
     *
     * @param userTable  tabla dónde visualizamos los empleados creados
     * @param name       nombre del empleado
     * @param dni        dni del empleado
     * @param nss        nss del empleado
     * @param employeeId codigo del empleado
     */
    public void createUser(JTable userTable, String name, String dni, String nss, String employeeId) {

        try {
            // Si hay algún campo vacío
            if (name.isEmpty() || dni.isEmpty() || nss.isEmpty() || employeeId.isEmpty()) {
                throw new CustomException(1111);

            } else {
                if (!ValidadorDNI.validar(dni)) {
                    throw new CustomException(1112);

                } else if (!ValidadorNSS.validar(nss)) {
                    throw new CustomException(1113);

                } else {
                    // Creamos objeto de la clase empleado
                    Employee emp = new Employee(name, dni, nss, employeeId);

                    // Lo añadimos al arraylist de tipo Empleado
                    employeeList.addEmployee(emp);

                    // Añadimos la entrada al log
                    Log.capturarRegistro("EMPLOYEE CREATE " + emp.toString());

                    // Actualizamos los datos en la tabla
                    showData(userTable);
                }
            }
        } catch (CustomException ce) {
            InputOutput.printAlert(ce.getMessage());

            // Capturamos error para el registro
            Error.capturarError("EMPLOYEE " + ce.getMessage());
        }
    }

    /**
     * Permite eliminar un usuario
     *
     * @param userTable tabla dónde se visualizan los empleados
     */
    public void deleteUser(JTable userTable) {
        // Almacena el resultado de un cuadro de alerta si es 0 se elimina el elemento
        int resultado;

        // Guardamos el número de fila (coincide con la posición en el ArrayList)
        int row = userTable.getSelectedRow();

        try {
            // Si hay una fila seleccionada, mostramos mensaje de confirmación
            if (row >= 0) {
                resultado = InputOutput.deleteConfirmation();

                // Si el resultado es igual a 0, eliminamos el empleado
                if (resultado == 0) {

                    // Añadimos la entrada al log
                    Log.capturarRegistro("EMPLOYEE DELETE " + employeeList.getEmployeeFromDB(row));

                    // Eliminamos empleado
                    employeeList.removeEmployee(row);

                    // Actualizamos datos de la tabla
                    showData(userTable);
                }
            }
            // En caso contrario, mostramos un error por pantalla
            else {
                throw new CustomException(1114);
            }
        } catch (CustomException ce) {
            InputOutput.printAlert(ce.getMessage());

            // Capturamos error para el registro
            Error.capturarError("EMPLOYEE " + ce.getMessage());
        }
    }

    /**
     * Permite eliminar todos los empleados
     *
     * @param userTable tabla dónde se visualizan todos los empleados
     */
    public void emptyAll(JTable userTable) {
        // Almacena un entero, si es 0 se eliminan todos los elementos
        int ok;

        // Almacenamos el nº total de filas que hay en la tabla
        int totalrows = userTable.getRowCount();

        try {
            if (totalrows != 0) {
                // Mensaje de confirmación para eliminar
                ok = InputOutput.emptyConfirmation();

                if (ok == 0) {
                    // Recorremos el arrayList y eliminamos 1 a 1 los registros
                    for (int i = (totalrows - 1); i >= 0; i--) {
                        employeeList.removeEmployee(i);
                    }

                    // Actualizamos los datos de la tabla
                    showData(userTable);
                }
            } else {
                throw new CustomException(1115);
            }
        } catch (CustomException ce) {
            InputOutput.printAlert(ce.getMessage());

            // Capturamos error para el registro
            Error.capturarError("EMPLOYEE " + ce.getMessage());
        }
    }

    /**
     * Permite modificar un empleado
     *
     * @param userTable  tabla dónde se visualizan los empleados
     * @param name       nombre del empleado
     * @param dni        dni del empleado
     * @param nss        nss del empleado
     * @param employeeId codigo del empleado
     */
    public void editUser(JTable userTable, String name, String dni, String nss, String employeeId) {

        // Almacenamos el nº total de filas que hay en la tabla
        int totalRows = userTable.getRowCount();

        // Almacena el nº de fila seleccionado (coincide con el índice en el ArrayList)
        int selectedRow = userTable.getSelectedRow();

        try {
            // Si no hay ninguna fila creada
            if (totalRows == 0) {
                throw new CustomException(1115);
            } else {
                // Si no hay ninguna fila seleccionada
                if (selectedRow < 0) {
                    throw new CustomException(1114);
                } else {
                    // Si los campos están vacíos
                    if (name.isEmpty() || dni.isEmpty() || nss.isEmpty() || employeeId.isEmpty()) {
                        throw new CustomException(1111);
                    } else {
                        if (!ValidadorDNI.validar(dni)) {
                            throw new CustomException(1112);

                        } else if (!ValidadorNSS.validar(nss)) {
                            throw new CustomException(1113);

                        } else {
                            employeeList.getEmployeeFromDB(selectedRow).setName(name);
                            employeeList.getEmployeeFromDB(selectedRow).setDni(dni);
                            employeeList.getEmployeeFromDB(selectedRow).setNss(nss);
                            employeeList.getEmployeeFromDB(selectedRow).setEmployeeId(employeeId);

                            // Añadimos la entrada al log
                            Log.capturarRegistro("EMPLOYEE EDIT " + name + " " + dni + " "
                                                                + nss + "  " + employeeId);

                            // Actualizamos datos de la tabla
                            showData(userTable);
                        }
                    }
                }
            }
        } catch (CustomException ce) {
            InputOutput.printAlert(ce.getMessage());

            // Capturamos error para el registro
            Error.capturarError("EMPLOYEE " + ce.getMessage());
        }
    }

    /**
     * Muestra los datos actualizados en la tabla de Usuarios
     *
     * @param userTable tabla dónde se visualizan los empleados creados
     */
    public void showData(JTable userTable) {

        // Creamos array de tipo string e inicializamos con el tamaño del ArrayList
        String[][] tablaUsuarios = new String[employeeList.sizeEmployeeDB()][4];

        // Recorre la lista de Empleados
        for (int i = 0; i < employeeList.sizeEmployeeDB(); i++) {

            // Datos de cada Empleado
            tablaUsuarios[i][0] = employeeList.getEmployeeFromDB(i).getName();
            tablaUsuarios[i][1] = employeeList.getEmployeeFromDB(i).getDni();
            tablaUsuarios[i][2] = employeeList.getEmployeeFromDB(i).getNss();
            tablaUsuarios[i][3] = employeeList.getEmployeeFromDB(i).getEmployeeId();
        }

        // Añade los datos al modelo
        userTable.setModel(new CustomTableModel(
                tablaUsuarios,
                new String[]{
                        "Nombre", "DNI", "NSS", "Cod. Empleado"
                }
        ));

        // Diseño básico de la tabla
        CustomTableConfig.initConfig(userTable);
    }
}
