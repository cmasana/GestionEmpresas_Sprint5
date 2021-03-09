package mainclasses.user;

/**
 * Grup Individual Sprint 3 2020
 * @author Carlos Masana
 * Clase User
 */
public abstract class User {
    protected String name;
    protected String dni;
    protected String nss;

    /**
     * Constructor vacío de la clase User
     */
    public User() {
        this.name = "";
        this.dni = "";
        this.nss = "";
    }

    /**
     * Constructor sobrecargado de la clase User
     *
     * @param name nombre del Usuario
     * @param dni documento nacional de identificación del Usuario
     * @param nss número de la seguridad social del Usuario
     */
    public User(String name, String dni, String nss) {
        this.name = name;
        this.dni = dni;
        this.nss = nss;
    }

    /**
     * Getters & Setters
     */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    @Override
    public String toString() {
        return "Usuario: " + name + " ";
    }
}