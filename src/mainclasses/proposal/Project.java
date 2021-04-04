package mainclasses.proposal;

import mainclasses.user.Employee;

/**
 * Grup Individual Sprint 3 2020 - Carlos Masana -
 *
 * Clase Project: Define atributos y métodos de la clase
 */
public class Project {

    private final int idProject;
    private String title;
    private Employee manager;
    private String description;

    public Project(int idProject, String title, String description, Employee manager) {
        this.idProject = idProject;
        this.title = title;
        this.description = description;
        this.manager = manager;
    }

    public int getIdProject() {
        return idProject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        return  "Título: " + title + " | " +
                "Descripción: " + description + " | " +
                "Jefe de proyecto: " + manager.getName();
    }
}

        
