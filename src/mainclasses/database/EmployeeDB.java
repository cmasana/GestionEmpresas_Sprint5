package mainclasses.database;

import auxiliar.DatabaseConnection;
import mainclasses.user.Employee;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Grupo Individual Sprint 3 2021 - Carlos Masana
 * Clase EmployeeDB: Simula una base de datos con usuarios de tipo Empleado
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
     * @param emp Objeto de la clase empleado
     */
    public void addEmployee(Employee emp) {
        LISTA_EMPLEADOS.add(emp);
    }

    /**
     * Elimina un empleado del ArrayList
     * @param posicion posición del empleado dentro del ArrayList
     */
    public void removeEmployee(int posicion) {
        LISTA_EMPLEADOS.remove(posicion);
    }

    /**
     * Obtiene un empleado desde el ArrayList
     * @param posicion posición del empleado dentro del ArrayList
     * @return devuelve un objeto de la clase Empleado
     */
    public Employee getEmployeeFromDB(int posicion) {
        return LISTA_EMPLEADOS.get(posicion);
    }

    /**
     * Obtiene el tamaño del ArrayList de Empleados
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
     * @return devuelve un array de Strings
     */
    public String[][] listEmployeesObject() {
        String[][] array = new String[sizeEmployeeDB()][5];

        for (int i = 0; i < sizeEmployeeDB(); i ++) {
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
        String sql = "SELECT iduser, username, dni, nss, employeeid FROM USERS WHERE status = 'active'";

        // Try-with-resources Statement: Se realiza el close() automaticamente
        try(Statement stmt = DatabaseConnection.getConnection().createStatement()) {
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
}


