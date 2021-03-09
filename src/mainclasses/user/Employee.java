package mainclasses.user;

/**
 * Grup 3 Sprint 2 2020-2021
 * @author Carlos Masana
 * Clase Employee: Hereda de la clase User
 */
public class Employee extends User {
    private String employeeId;

    /**
     * Constructor vacío
     */
    public Employee() {
        super();
        this.employeeId = "";
    }

    /**
     * Constructor sobrecargado de la clase Employee
     *
     * @param name        nombre del empleado
     * @param dni         documento nacional de identidad del empleado
     * @param nss         número de la seguridad social del empleado
     * @param employeeId  número de identificación de cada empleado
     */
    public Employee(String name, String dni, String nss, String employeeId) {
        super(name, dni, nss);
        this.employeeId = employeeId;
    }

    // Getters & Setters
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }


    // Método toString

    /**
     * Método que permite transformar un objeto en un String
     *
     * @return String con los datos de un empleado
     */
    public String toString() {
        return  super.toString() +
                " ID: " + this.employeeId;
    }
}
