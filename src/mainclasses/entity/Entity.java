package mainclasses.entity;

/**
 * Grupo Individual Sprint 3 2020-2021
 * @author Carlos Masana
 * Clase entidad: Implementa atributos y métodos comunes en clases Company y School
 */

 public abstract class Entity {
     protected int idEntity;
     protected String nombre;
     protected String poblacion;
     protected int telefono;

    /**
     * Constructor vacío de la clase Entity
     */
    public Entity() {

    }

    /**
     * Constructor sobrecargado de la clase Entity
     * @param nombre nombre de la entidad
     * @param poblacion lugar dónde se encuentra ubicada la entidad
     * @param telefono número de teléfono de la entidad
     */
    public Entity(int idEntity, String nombre, String poblacion, int telefono) {
        this.idEntity = idEntity;
        this.nombre = nombre;
        this.poblacion = poblacion;
        this.telefono = telefono;
    }

    /**
     * Getters and Setters
     */

    public int getIdEntity() {
        return idEntity;
    }

    public void setIdEntity(int idEntity) {
        this.idEntity = idEntity;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }



    /**
     * Permite mostrar la información completa de un objeto de la clase entidad
     * @return mensaje con la información del objeto
     */
    @Override
    public String toString() {
        return  "ID: " + this.idEntity + " | " +
                "Nombre: " + this.nombre + " | " +
                "Población: " + this.poblacion + " | ";
    }
}