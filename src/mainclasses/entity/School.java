package mainclasses.entity;

/**
 * Grupo Individual Sprint 3 2020-2021
 * @author Carlos Masana
 * Clase School: Define los atributos y métodos de la clase School
 */
public class School extends Entity {
    private String codTerritorial;

    /**
     * Constructor vacío de la clase School
     */
    public School() {
        super(); // Llama al constructor de la clase padre
        this.codTerritorial = "";
    }

    /**
     * Constructor sobrecargado de la clase School
     *
     * @param nombre          nombre del instituto
     * @param poblacion       lugar dónde está ubicado el instituto
     * @param telefono        número de teléfono del instituto
     * @param codTerritorial  código de 6 dígitos único de cada instituto
     */
    public School(String nombre, String poblacion, int telefono, String codTerritorial) {
        super(nombre, poblacion, telefono);
        this.codTerritorial = codTerritorial;
    }

    /**
     * Getters & Setters
     */
    public String getCodTerritorial() {
        return codTerritorial;
    }

    public void setCodTerritorial(String codTerritorial) {
        this.codTerritorial = codTerritorial;
    }

    /**
     * Permite mostrar la información completa de un objeto de la clase School
     *
     * @return mensaje con la información del objeto
     */
    @Override
    public String toString() {
        return super.toString() +
                "Cod. Territorial: " + codTerritorial;
    }
}