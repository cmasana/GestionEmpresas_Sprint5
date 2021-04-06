package mainclasses.database;

import auxiliar.CustomException;
import auxiliar.DatabaseConnection;
import auxiliar.Error;
import auxiliar.InputOutput;
import auxiliar.Log;
import custom_ui.tables.CustomTableConfig;
import custom_ui.tables.CustomTableModel;
import mainclasses.user.Employee;
import validations.ValidadorDNI;
import validations.ValidadorNSS;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Grupo Individual Sprint 3 2021 - Carlos Masana
 * Clase EmployeeDB: Contiene todos los métodos que permiten realizar operaciones en la bbdd y que hacen referencia
 * a la gestión de empleados
 */
public class EmployeeDB {
    // ArrayList que simula la base de datos de Empleados
    private final ArrayList<Employee> LISTA_EMPLEADOS = new ArrayList<Employee>();

    // Constructor vacío
    public EmployeeDB() {
        this.getUsersTable();
    }

    // Métodos para realizar operaciones básicas en nuestro ArrayList

    /**
     * Añadir empleado al array list
     *
     * @param emp Objeto de la clase empleado
     */
    public void addEmployee(Employee emp) {
        LISTA_EMPLEADOS.add(emp);
    }

    /**
     * Elimina un empleado del ArrayList
     *
     * @param posicion posición del empleado dentro del ArrayList
     */
    public void removeEmployee(int posicion) {
        LISTA_EMPLEADOS.remove(posicion);
    }

    /**
     * Obtiene un empleado desde el ArrayList
     *
     * @param posicion posición del empleado dentro del ArrayList
     * @return devuelve un objeto de la clase Empleado
     */
    public Employee getEmployeeFromDB(int posicion) {
        return LISTA_EMPLEADOS.get(posicion);
    }

    /**
     * Obtiene el tamaño del ArrayList de Empleados
     *
     * @return devuelve un entero con el tamaño del ArrayList de tipo Empleado
     */
    public int sizeEmployeeDB() {
        return LISTA_EMPLEADOS.size();
    }


    /**
     * Transforma ArrayList a array de Empleados
     */
    public Employee[] listEmployees() {
        return LISTA_EMPLEADOS.toArray(new Employee[sizeEmployeeDB()]);
    }


    /**
     * Permite transformar un arraylist en un array 2d de Strings
     * (es necesario para cargar los datos del arraylist en el JTable)
     *
     * @return devuelve un array de Strings
     */
    public String[][] listEmployeesObject() {
        String[][] array = new String[sizeEmployeeDB()][5];

        for (int i = 0; i < sizeEmployeeDB(); i++) {
            array[i][0] = String.valueOf(getEmployeeFromDB(i).getIdUser());
            array[i][1] = getEmployeeFromDB(i).getName();
            array[i][2] = getEmployeeFromDB(i).getDni();
            array[i][3] = getEmployeeFromDB(i).getNss();
            array[i][4] = getEmployeeFromDB(i).getEmployeeId();
        }

        return array;
    }

    /**
     * Permite cargar un ResultSet con los datos de la bbdd en el arraylist de esta clase
     */
    private void getUsersTable() {
        String sql = "SELECT iduser, username, dni, nss, employeeid " +
                     "FROM users WHERE status = 'active' " +
                     "ORDER BY username ASC";

        // Try-with-resources Statement: Se realiza el close() automaticamente
        try (Statement stmt = DatabaseConnection.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Employee employee = new Employee();
                employee.setIdUser(rs.getInt("iduser"));
                employee.setName(rs.getString("username"));
                employee.setDni(rs.getString("dni"));
                employee.setNss(rs.getString("nss"));
                employee.setEmployeeId(rs.getString("employeeid"));

                this.addEmployee(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
        String sql = "INSERT INTO users (username, dni, nss, employeeid, creationdate, updated_at, status) " +
                     "VALUES (?,?,?,?,?,?,?)";

        // Try-with-resources: No hace falta hacer close() del statement
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            // Si hay algún campo vacío
            if (name.isEmpty() || dni.isEmpty() || nss.isEmpty() || employeeId.isEmpty()) {
                throw new CustomException(1111);

            } else {
                if (!ValidadorDNI.validar(dni)) {
                    throw new CustomException(1112);

                } else if (!ValidadorNSS.validar(nss)) {
                    throw new CustomException(1113);

                } else {
                    stmt.setString(1, name);
                    stmt.setString(2, dni);
                    stmt.setString(3, nss);
                    stmt.setString(4, employeeId);
                    stmt.setString(5, InputOutput.todayDate()); // Creation Date
                    stmt.setString(6, InputOutput.todayDate()); // Updated Date
                    stmt.setString(7, "active");

                    stmt.executeUpdate();

                    // Añadimos la entrada al log
                    Log.capturarRegistro("EMPLOYEE CREATE " + name + " " + dni + " " + nss + " " + employeeId);

                    // Actualizamos los datos en la tabla
                    this.showData(userTable);
                }
            }
        } catch (CustomException | SQLException ce) {
            InputOutput.printAlert(ce.getMessage());

            // Capturamos error para el registro
            auxiliar.Error.capturarError("EMPLOYEE " + ce.getMessage());
        }
    }

    /**
     * Permite importar una lista de usuarios en formato CSV
     *
     * @param userTable tabla dónde visualizamos los empleados creados
     * @param file      archivo csv que contiene los datos de usuarios de tipo empleado
     */
    public void importUsers(JTable userTable, File file) {
        String sql = "INSERT INTO users (username, dni, nss, employeeid, creationdate, updated_at, status) " +
                     "VALUES (?,?,?,?,?,?,?)";

        int batchSize = 20; // Paquete de filas que se importarán a la vez (mejor rendimiento)

        // Try-with-resources: No hace falta hacer close() del statement
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            try(BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line = null;
                String name, dni, nss, employeeId;

                int count = 0;

                br.readLine(); // Saltar encabezados

                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    name = data[0];
                    dni = data[1];
                    nss = data[2];
                    employeeId = data[3];

                    if (name.isEmpty() || dni.isEmpty() || nss.isEmpty() || employeeId.isEmpty()) {
                        throw new CustomException(2111);

                    } else if (!ValidadorDNI.validar(dni)) {
                        throw new CustomException(2112);

                    } else if (!ValidadorNSS.validar(nss)) {
                        throw new CustomException(2113);

                    } else {
                        stmt.setString(1, name);
                        stmt.setString(2, dni);
                        stmt.setString(3, nss);
                        stmt.setString(4, employeeId);
                        stmt.setString(5, InputOutput.todayDate()); // Fecha de hoy con formato sql
                        stmt.setString(6, InputOutput.todayDate());
                        stmt.setString(7, "active");

                        stmt.addBatch();

                        // Añadimos la entrada al log
                        Log.capturarRegistro("EMPLOYEE CREATE " + name + " " + dni + " " + nss + " " + employeeId);

                        if (count % batchSize == 0) {
                            stmt.executeBatch();
                        }
                    }

                    stmt.executeBatch();

                    // Actualizamos los datos en la tabla
                    this.showData(userTable);
                }
            }
        } catch (CustomException | SQLException | IOException ce) {
            InputOutput.printAlert(ce.getMessage());

            // Capturamos error para el registro
            auxiliar.Error.capturarError("EMPLOYEE IMPORT " + ce.getMessage());
        }
    }


    /**
     * Cambia a estado inactivo a un usuario (softDelete)
     *
     * @param userTable  tabla dónde se visualizan los empleados
     * @param idUser     id del usuario en la bbdd
     * @param name       nombre del empleado
     * @param dni        dni del empleado
     * @param nss        nss del empleado
     * @param employeeId codigo del empleado
     */
    public void softDeleteUser(JTable userTable, String idUser, String name, String dni, String nss, String employeeId) {
        String sql = "UPDATE users SET status = ?, updated_at = ? WHERE iduser = ?";
        int resultado;

        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

            // Almacenamos el nº total de filas que hay en la tabla
            int totalRows = userTable.getRowCount();

            // Almacena el nº de fila seleccionado
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
                            resultado = InputOutput.deleteConfirmation();

                            // Si el resultado es igual a 0, eliminamos el empleado
                            if (resultado == 0) {
                                stmt.setString(1, "inactive");
                                stmt.setString(2, InputOutput.todayDate());

                                stmt.setInt(3, InputOutput.stringToInt(idUser));

                                stmt.executeUpdate();

                                // Añadimos la entrada al log
                                Log.capturarRegistro("EMPLOYEE DELETE " + name + " " + dni + " "
                                        + nss + "  " + employeeId);

                                // Actualizamos datos de la tabla
                                this.showData(userTable);
                            }
                        }
                    }
                }
            } catch (CustomException ce) {
                InputOutput.printAlert(ce.getMessage());

                // Capturamos error para el registro
                auxiliar.Error.capturarError("EMPLOYEE " + ce.getMessage());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Permite modificar un empleado
     *
     * @param userTable  tabla dónde se visualizan los empleados
     * @param idUser     id del usuario en la bbdd
     * @param name       nombre del empleado
     * @param dni        dni del empleado
     * @param nss        nss del empleado
     * @param employeeId codigo del empleado
     */
    public void editUser(JTable userTable, String idUser, String name, String dni, String nss, String employeeId) {
        String sql = "UPDATE users SET username = ?, dni = ?, nss = ?, employeeid = ?, updated_at = ? WHERE iduser = ?";
        int resultado;

        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

            // Almacenamos el nº total de filas que hay en la tabla
            int totalRows = userTable.getRowCount();

            // Almacena el nº de fila seleccionado
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
                                // Mensaje de confirmación al modificar
                                resultado = InputOutput.editConfirmation();

                                if (InputOutput.ifOk(resultado)) {
                                    stmt.setString(1, name);
                                    stmt.setString(2, dni);
                                    stmt.setString(3, nss);
                                    stmt.setString(4, employeeId);
                                    stmt.setString(5, InputOutput.todayDate());

                                    stmt.setInt(6, InputOutput.stringToInt(idUser));

                                    stmt.executeUpdate();
                                    stmt.close();

                                    // Añadimos la entrada al log
                                    Log.capturarRegistro("EMPLOYEE EDIT " + name + " " + dni + " "
                                            + nss + "  " + employeeId);

                                    // Actualizamos datos de la tabla
                                    showData(userTable);
                                }
                            }
                        }
                    }
                }
            } catch (CustomException ce) {
                InputOutput.printAlert(ce.getMessage());

                // Capturamos error para el registro
                auxiliar.Error.capturarError("EMPLOYEE " + ce.getMessage());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Muestra los datos actualizados en la tabla de Usuarios
     *
     * @param userTable tabla dónde se visualizan los empleados creados
     */
    public void showData(JTable userTable) {
        // Encabezados de la tabla
        String[] colIdentifiers = {"ID", "Nombre", "DNI", "NSS", "Cod. Empleado"};

        EmployeeDB employees = new EmployeeDB();

        // Añade los datos al modelo
        userTable.setModel(new CustomTableModel(
                employees.listEmployeesObject(),
                colIdentifiers
        ));

        // Diseño básico de la tabla
        CustomTableConfig.initConfig(userTable);
    }

    /**
     * Permite eliminar un usuario DEPRECATED
     *
     * @param userTable tabla dónde se visualizan los empleados
     * @deprecated
     */
    public void deleteUser(JTable userTable) {
        EmployeeDB employeeList = new EmployeeDB();

        // Almacena el resultado de un cuadro de alerta si es 0 se elimina el elemento
        int resultado;

        // Permite conocer si hay una fila seleccionada o no
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
}


