package mainclasses.proposal;

import mainclasses.user.Employee;

/**
 * Grup Individual Sprint 3 2020 - Carlos Masana -
 *
 * Clase Project: Define atributos y métodos de la clase
 */
public class Project {

    private int idProject;
    private String name;
    private Employee manager;
    private String description;

    public Project(int idProject, String name, String description, Employee manager) {
        this.idProject = idProject;
        this.name = name;
        this.description = description;
        this.manager = manager;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String toString() {
        return  "Título: " + name + " | " +
                "Descripción: " + description + " | " +
                "Jefe de proyecto: " + manager.getName();
    }
}

        
