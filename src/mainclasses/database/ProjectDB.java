package mainclasses.database;

import auxiliar.CustomException;
import auxiliar.DatabaseConnection;
import auxiliar.Error;
import auxiliar.InputOutput;
import auxiliar.Log;
import custom_ui.tables.CustomTableConfig;
import custom_ui.tables.CustomTableModel;
import gui.dialogs.ProjectDialog;
import gui.dialogs.ShowProjects;
import mainclasses.proposal.Project;
import mainclasses.user.Employee;

import javax.swing.*;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Grupo Individual Sprint 3 2021 - Carlos Masana
 * Clase ProjectDB: Contiene todos los métodos que permiten realizar operaciones en la bbdd y que hacen referencia
 *  * a la gestión de proyectos
 */
public class ProjectDB {
    // ArrayList que simula la base de datos
    private final ArrayList<Project> listaProyectos = new ArrayList<Project>();;

    // Constructor vacío
    public ProjectDB() {
        this.getProjectsTable();
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

    /**
     * Permite transformar un arraylist en un array 2d de Strings
     * (es necesario para cargar los datos del arraylist en el JTable)
     * @return devuelve un array de Strings
     */
    public String[][] listProjectsObject() {
        String[][] array = new String[sizeProjectDB()][4];

        for (int i = 0; i < sizeProjectDB(); i ++) {
            array[i][0] = String.valueOf(getProjectFromDB(i).getIdProject());
            array[i][1] = getProjectFromDB(i).getTitle();
            array[i][2] = getProjectFromDB(i).getDescription();
            array[i][3] = getProjectFromDB(i).getManager().getName();
        }

        return array;
    }

    /**
     * Permite cargar un ResultSet con los datos de la bbdd en el arraylist de esta clase
     */
    private void getProjectsTable() {
        String sql = "SELECT p.idproject, p.title, p.description, u.iduser, u.username, u.dni, u.nss, u.employeeid " +
                     "FROM PROJECTS AS p " +
                     "INNER JOIN USER_PROJECTS AS up " +
                        "ON p.idproject = up.idproject " +
                     "INNER JOIN USERS AS u " +
                        "ON up.iduser = u.iduser " +
                     "WHERE p.status = 'active' " +
                     "ORDER BY p.idproject ASC";

        String title, description, userName, dni, nss, employeeId;
        int idProject, idUser;

        // Try-with-resources Statement: Se realiza el close() automaticamente
        try(Statement stmt = DatabaseConnection.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            Employee employee;
            Project project;

            while (rs.next()) {
                    idProject = rs.getInt("idproject");
                    title = rs.getString("title");
                    description = rs.getString("description");

                    idUser = rs.getInt("iduser");
                    userName = rs.getString("username");
                    dni = rs.getString("dni");
                    nss = rs.getString("nss");
                    employeeId = rs.getString("employeeid");

                    employee = new Employee(idUser, userName, dni, nss, employeeId);
                    project = new Project(idProject, title, description, employee);

                    this.addProject(project);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Permite crear Proyectos
     * @param proposalTable JTable con propuestas
     * @param title String con título de la propuesta
     * @param description String con la descripción de la propuesta
     * @throws IOException Excepción de entrada/salida
     */
    public void createProject(JTable proposalTable, String title, String description, String idProposal) throws CustomException, IOException {
        // Panel con formulario y comboBox
        ProjectDialog projectDialog = new ProjectDialog(title, description, idProposal);

        String sqlFirst = "INSERT INTO PROJECTS (idproject, title, description, creationdate, status) VALUES (?,?,?,?,?)";

        String sqlSecond = "INSERT INTO USER_PROJECTS (iduser, idproject) VALUES (?,?)";

        // Almacena un entero, necesario para Diálogo de confirmación
        int resultado;

        // Fila seleccionada
        int selectedRow = proposalTable.getSelectedRow();

        try {
            // Si no hay empleados creados
            if (projectDialog.getCbEmployee() == null) {
                throw new CustomException(1115);

                // Si no hay filas seleccionadas en la tabla de propuestas
            } else if (selectedRow < 0) {
                throw new CustomException(1114);
            } else {
                // Mostramos diálogo de confirmación
                resultado = JOptionPane.showConfirmDialog(null, projectDialog, "CREAR PROYECTO", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                // No se puede utilizar un listener si utilizamos showxxxxDialog
                if (resultado == 0) {
                    // Primera consulta inserta en tabla Proyectos
                    try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sqlFirst)) {
                        stmt.setInt(1, InputOutput.stringToInt(idProposal));
                        stmt.setString(2, title);
                        stmt.setString(3, description);
                        stmt.setString(4, InputOutput.todayDate());
                        stmt.setString(5, "active");

                        stmt.executeUpdate();
                    }

                    // Segunda consulta inserta en tabla intermedia
                    try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sqlSecond)) {

                        stmt.setInt(1, projectDialog.getCbEmployee().getIdUser());
                        stmt.setInt(2, InputOutput.stringToInt(projectDialog.getTxtIdProposal()));

                        stmt.executeUpdate();
                    }

                    // Añadimos la entrada al log
                    Log.capturarRegistro("PROJECT CREATE " + title + " | " + projectDialog.getCbEmployee().getName());
                }
            }
        } catch (SQLException | CustomException ce) {
            InputOutput.printAlert(ce.getMessage());

            // Capturamos error para el registro
            Error.capturarError("PROJECT " + ce.getMessage());
        }
    }


    /**
     * Muestra los datos actualizados en la tabla de proyectos
     */
    public void showData() throws IOException {
        // Implementa panel para visualizar proyectos
        ShowProjects showProjects = new ShowProjects();

        ProjectDB projects = new ProjectDB();

        // Añade los datos al modelo
        showProjects.getProjectTable().setModel(new CustomTableModel(
                projects.listProjectsObject(),
                new String [] {
                        "ID", "Título", "Descripción", "Manager"
                }
        ));

        // Diseño de la tabla
        CustomTableConfig.initConfig(showProjects.getProjectTable());

        // Mostramos diálogo de confirmación
        JOptionPane.showConfirmDialog(null, showProjects, "VER PROYECTOS", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    }
}
