package modules;

import auxiliar.CustomException;
import auxiliar.Error;
import auxiliar.InputOutput;
import auxiliar.Log;
import custom_ui.tables.CustomTableConfig;
import custom_ui.tables.CustomTableModel;
import gui.dialogs.ProjectDialog;
import gui.dialogs.ShowProjects;
import mainclasses.database.ProjectDB;
import mainclasses.database.ProposalDB;
import mainclasses.proposal.Project;

import javax.swing.*;
import java.io.IOException;

/**
 * Clase CrudProject: Implementa los métodos necesarios para realizar la gestión de proyectos
 */
public class CrudProject {

    // Simula bbdd
    private final ProjectDB projectList = new ProjectDB();

    // Crud de propuestas
    private final CrudProposal crudProposal = new CrudProposal();


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
                    Project project = new Project(projectDialog.getTxtTitle(), projectDialog.getTxtDescription(), projectDialog.getCbEmployee());

                    // Añadimos proyecto
                    projectList.addProject(project);

                    // Añadimos la entrada al log
                    Log.capturarRegistro("PROJECT CREATE " + project.toString());

                    // Añadimos la entrada, antes de eliminar la propuesta para obtener el valor de la fila seleccionada
                    Log.capturarRegistro("PROPOSAL DELETE " + proposalList.getProposalFromDB(selectedRow));

                    // Al crear proyecto, eliminamos la propuesta
                    proposalList.removeProposal(selectedRow);

                    // Refrescamos la tabla para eliminar la propuesta de ahí también
                    crudProposal.showData(proposalTable);
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
    public void showData() throws IOException {
        int resultado;

        // Implementa panel para visualizar proyectos
        ShowProjects showProjects = new ShowProjects();

        // Creamos array de tipo string e inicializamos con el tamaño del ArrayList
        String[][] tabla = new String[projectList.sizeProjectDB()][5];

        // Recorre la lista de Proyectos
        for(int i = 0; i < projectList.sizeProjectDB(); i++) {
            // Datos de cada Proyecto
            tabla[i][0] = projectList.getProjectFromDB(i).getName();
            tabla[i][1] = projectList.getProjectFromDB(i).getDescription();
            tabla[i][2] = projectList.getProjectFromDB(i).getManager().getName();
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
