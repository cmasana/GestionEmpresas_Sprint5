package mainclasses.database;

import auxiliar.DatabaseConnection;
import mainclasses.user.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Grupo Individual Sprint 3 2021 - Carlos Masana
 * Clase EmployeeDB: Simula una base de datos con usuarios de tipo Empleado
 */
public class EmployeeDB {
    // ArrayList que simula la base de datos de Empleados
    private static final ArrayList<Employee> LISTA_EMPLEADOS = new ArrayList<Employee>();

    // Constructor vacío
    public EmployeeDB() {

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


/*    *//**
     * Obtiene los registros introducidos en la tabla USERS de la bbdd
     * @return devuelve un ArrayList con los registros de la tabla de USERS
     *//*
    public ResultSet getAllEmployees() {
        String sql = "SELECT iduser, username, dni, nss, employeeid FROM USERS WHERE status = 'active'";
        ResultSet rs = null;

        try (Statement stmt = DatabaseConnection.getConnection().createStatement()) {
            rs = stmt.executeQuery(sql);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return rs;
    }*/
}


