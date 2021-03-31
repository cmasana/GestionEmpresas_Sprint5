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
import mainclasses.entity.Company;
import mainclasses.entity.School;
import mainclasses.proposal.Project;
import mainclasses.proposal.Proposal;
import mainclasses.user.Employee;

import javax.swing.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

/**
 * Grupo Individual Sprint 3 2021 - Carlos Masana
 * Clase ProjectDB: Simula una base de datos con los proyectos
 */
public class ProjectDB {
    // ArrayList que simula la base de datos
    private final ArrayList<Project> listaProyectos = new ArrayList<Project>();;

    // Constructor vacío
    public ProjectDB() {

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
            array[i][1] = getProjectFromDB(i).getName();
            array[i][2] = String.valueOf(getProjectFromDB(i).getManager());
            array[i][3] = getProjectFromDB(i).getDescription();
        }

        return array;
    }

    /**
     * Permite cargar un ResultSet con los datos de la bbdd en el arraylist de esta clase
     */
    private void getProjectsTable() {
        String sql = "SELECT p.idproposal, p.title, p.description, p.startdate, e.id, e.entityname, e.city, e.phone, e.cif, e.territorialid " +
                "FROM PROPOSALS AS p " + "INNER JOIN ENTITIES AS e " +
                "ON p.identity = e.id " +
                "WHERE status = 'active'" +
                "ORDER BY p.idproposal ASC";

        String title, description, entityName, city, territorialId, cif;
        int idProposal, phone, idSchool, idCompany;
        Date date;


        // Try-with-resources Statement: Se realiza el close() automaticamente
        try(Statement stmt = DatabaseConnection.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            Proposal proposal;
            School school;
            Company company;

            while (rs.next()) {
                // Si el cif es nulo, la entidad es una escuela
                if (rs.getString("cif") == null) {
                    /*
                    Con el inner join podemos obtener un ResultSet más completo y mostrar así el objeto de School
                    en la JTable
                     */
                    idProposal = rs.getInt("idproposal");
                    title = rs.getString("title");
                    description = rs.getString("description");
                    date = rs.getDate("startdate");

                    idSchool = rs.getInt("id");
                    entityName = rs.getString("entityname");
                    city = rs.getString("city");
                    phone = rs.getInt("phone");
                    territorialId = rs.getString("territorialid");

                    school = new School(idSchool, entityName, city, phone, territorialId);
                    proposal = new Proposal(idProposal, title, description, date, school);

                    //this.addProposal(proposal);
                } else {
                    idProposal = rs.getInt("idproposal");
                    title = rs.getString("title");
                    description = rs.getString("description");
                    date = rs.getDate("startdate");

                    idCompany = rs.getInt("id");
                    entityName = rs.getString("entityname");
                    city = rs.getString("city");
                    phone = rs.getInt("phone");
                    cif = rs.getString("cif");

                    company = new Company(idCompany, entityName, city, phone, cif);
                    proposal = new Proposal(idProposal, title, description, date, company);

                    //this.addProposal(proposal);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Permite crear Proyectos
     * @param proposalTable JTable con propuestas
     * @param proposalList Objeto de la clase ProposalDB con la lista de propuestas
     * @param title String con título de la propuesta
     * @param description String con la descripción de la propuesta
     * @throws IOException Excepción de entrada/salida
     */
    public void createProject(JTable proposalTable, ProposalDB proposalList, String title, String description) throws CustomException, IOException {
        // Panel con formulario y comboBox
        ProjectDialog projectDialog = new ProjectDialog(title, description);

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
                    // Si el resultado es 0, significa que el usuario ha hecho clic en OK.
                    Project project = null; //new Project(projectDialog.getTxtTitle(), projectDialog.getTxtDescription(), projectDialog.getCbEmployee());

                    // Añadimos proyecto
                    this.addProject(project);

                    // Añadimos la entrada al log
                    Log.capturarRegistro("PROJECT CREATE " + project.toString());

                    // Añadimos la entrada, antes de eliminar la propuesta para obtener el valor de la fila seleccionada
                    Log.capturarRegistro("PROPOSAL DELETE " + proposalList.getProposalFromDB(selectedRow));

                    // Al crear proyecto, eliminamos la propuesta
                    proposalList.removeProposal(selectedRow);

                    // Refrescamos la tabla para eliminar la propuesta de ahí también
                    this.showData(proposalTable);
                }
            }
        } catch (CustomException ce) {
            InputOutput.printAlert(ce.getMessage());

            // Capturamos error para el registro
            Error.capturarError("PROJECT " + ce.getMessage());
        }
    }


    /**
     * Muestra los datos actualizados en la tabla de proyectos
     */
    public void showData(JTable proposalTable) throws IOException {
        int resultado;

        // Implementa panel para visualizar proyectos
        ShowProjects showProjects = new ShowProjects();

        // Creamos array de tipo string e inicializamos con el tamaño del ArrayList
        String[][] tabla = new String[this.sizeProjectDB()][5];

        // Recorre la lista de Proyectos
        for(int i = 0; i < this.sizeProjectDB(); i++) {
            // Datos de cada Proyecto
            tabla[i][0] = this.getProjectFromDB(i).getName();
            tabla[i][1] = this.getProjectFromDB(i).getDescription();
            tabla[i][2] = this.getProjectFromDB(i).getManager().getName();
        }

        // Añade los datos al modelo
        showProjects.getProjectTable().setModel(new CustomTableModel(
                tabla,
                new String [] {
                        "Título", "Descripción", "Manager"
                }
        ));

        // Diseño de la tabla
        CustomTableConfig.initConfig(showProjects.getProjectTable());

        // Mostramos diálogo de confirmación
        JOptionPane.showConfirmDialog(null, showProjects, "VER PROYECTOS", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    }
}
