package mainclasses.entity;

/**
 * Grupo Individual Sprint 3 2020-2021
 * @author Carlos Masana
 * Clase Company: Permite realizar operaciones de gestión de empresas
 */

public class Company extends Entity {
    private String cif;

    /**
     * Constructor vacío de la clase Company
     */
    public Company() {
        super(); // Llama al constructor de la clase padre
        this.cif = "";
    }

    /**
     * Constructor sobrecargado de la clase Company
     * @param nombre nombre de la empresa
     * @param poblacion lugar dónde se encuentra ubicada la empresa
     * @param telefono número de teléfono de la empresa
     * @param cif código de identificación fiscal de la empresa
     */
    public Company(String nombre, String poblacion, int telefono, String cif) {
        super(nombre, poblacion, telefono);
        this.cif = cif;
    }

    /**
     * Getters & Setters
     */

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    /**
     * Permite mostrar la información completa de un objeto de la clase Company
     * @return mensaje con la información del objeto
     */
    @Override
    public String toString() {
        return  super.toString() +
                "CIF: " + this.cif;
    }
}