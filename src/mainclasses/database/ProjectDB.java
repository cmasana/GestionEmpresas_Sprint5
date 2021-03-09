package mainclasses.database;

import mainclasses.proposal.Project;
import java.util.ArrayList;

/**
 * Grupo Individual Sprint 3 2021 - Carlos Masana
 * Clase ProjectDB: Simula una base de datos con los proyectos
 */
public class ProjectDB {
    // ArrayList que simula la base de datos
    private static ArrayList<Project> listaProyectos;

    // Constructor vacío
    public ProjectDB() {
        listaProyectos = new ArrayList<Project>();
    }


    // Métodos para realizar operaciones básicas en nuestro ArrayList

    /**
     * Añadir proyectos al array list
     * @param project Objeto de la clase proyecto
     */
    public void addProject(Project project) {
        listaProyectos.add(project);
    }

    /**
     * Elimina un proyecto del ArrayList
     * @param posicion posición del proyecto dentro del ArrayList
     */
    public void removeProject(int posicion) {
        listaProyectos.remove(posicion);
    }

    /**
     * Obtiene un proyecto desde el ArrayList
     * @param posicion posición del proyecto dentro del ArrayList
     * @return devuelve un objeto de la clase Project
     */
    public Project getProjectFromDB(int posicion) {
        return listaProyectos.get(posicion);
    }

    /**
     * Obtiene el tamaño del ArrayList de Proyectos
     * @return devuelve un entero con el tamaño del ArrayList de tipo Project
     */
    public int sizeProjectDB() {
        return listaProyectos.size();
    }
}
