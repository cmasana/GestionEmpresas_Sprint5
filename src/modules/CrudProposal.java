package modules;

import auxiliar.*;
import auxiliar.Error;
import custom_ui.tables.*;

import mainclasses.database.ProposalDB;
import mainclasses.entity.Entity;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;


/**
 * Clase CrudProposal: Implementa todos los métodos necesarios para la gestión de propuestas
 *
 * @author cmasana
 */
public class CrudProposal {

    // Array de tipo ProposalDB
    private final ProposalDB proposalList = new ProposalDB();

    /**
     * Permite crear propuestas
     *
     * @param proposalTable tabla donde se visualizan las propuestas
     * @param title         nombre de la propuesta
     * @param description   descripción de la propuesta
     * @param startDate     fecha de la propuesta
     * @param entity        entidad de la propuesta
     */
    public void createProposal(JTable proposalTable, String title, String description, String startDate, int entity) {
        String sql = "INSERT INTO PROPOSALS (title, description, startdate, creationdate, status, identity) VALUES (?,?,?,?,?,?)";

        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            // Si hay algún campo vacío
            if (title.isEmpty() || description.isEmpty() || startDate.isEmpty() || entity == 0) {
                throw new CustomException(1111);

            } else {
                // Si la fecha introducida es anterior a la actual
                if (InputOutput.wrongDate(startDate)) {
                    throw new CustomException(1117);

                } else {
                    stmt.setString(1, title);
                    stmt.setString(2, description);
                    stmt.setString(3, startDate);

                    stmt.setString(4, InputOutput.todayDate());
                    stmt.setString(5, "active");
                    stmt.setObject(6, entity);

                    stmt.executeUpdate();

                    // Añadimos la entrada al log
                    Log.capturarRegistro("PROPOSAL CREATE " + title + " " + description + " " + startDate);

                    // Actualizamos los datos en la tabla
                    showData(proposalTable);
                }
            }
        } catch (CustomException ce) {
            InputOutput.printAlert(ce.getMessage());

            // Capturamos error para el registro
            Error.capturarError("PROPOSAL " + ce.getMessage());

        } catch (ParseException pe) {
            String alerta = "Error: Fecha con formato desconocido";
            InputOutput.printAlert(alerta);

            // Capturamos error para el registro
            Error.capturarError("PROPOSAL " + alerta);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    /**
     * Permite modificar propuestas
     * @param proposalTable tabla donde se visualizan las propuestas
     * @param title         nombre de la propuesta
     * @param description   descripción de la propuesta
     * @param startDate     fecha de la propuesta
     * @param indexEntity   indice del objeto del JCombobox
     */
    public void editProposal(JTable proposalTable, String title, String description, String startDate, int indexEntity, int idProposal) {
        String sql = "UPDATE PROPOSALS SET title = ?, description = ?, startDate = ?, creationdate = ?, identity = ? WHERE idproposal = ?";

        try {
            // Almacenamos el nº total de filas que hay en la tabla
            int totalRows = proposalTable.getRowCount();

            // Almacena el nº de fila seleccionado
            int selectedRow = proposalTable.getSelectedRow();

            // Si no hay ninguna fila creada
            if (totalRows == 0) {
                throw new CustomException(1116);

                // Si no hay ninguna fila seleccionada
            } else if (selectedRow < 0) {
                throw new CustomException(1114);

                // Si los campos están vacíos
            } else if (title.isEmpty() || description.isEmpty() || startDate.isEmpty() || indexEntity == -1) {
                throw new CustomException(1111);

                // Si la fecha introducida es anterior a HOY
            } else if (InputOutput.wrongDate(startDate)) {
                throw new CustomException(1117);

            } else {
                try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

                    stmt.setString(1, title);
                    stmt.setString(2, description);
                    stmt.setString(3, startDate);
                    stmt.setString(4, InputOutput.todayDate());
                    stmt.setInt(5, indexEntity);

                    stmt.setInt(6, idProposal);

                    stmt.executeUpdate();
                    //stmt.close(); // No hace falta con el try-with-resources

                    // Añadimos la entrada al log
                    Log.capturarRegistro("PROPOSAL EDIT " + title + " "
                            + description + " "
                            + InputOutput.stringToDate(startDate) + "  "
                            + "ENTITY ID " + indexEntity);

                    // Actualizamos datos de la tabla
                    showData(proposalTable);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


        } catch (CustomException ce) {
            InputOutput.printAlert(ce.getMessage());

            // Capturamos error para el registro
            Error.capturarError("PROPOSAL " + ce.getMessage());
        } catch (ParseException e) {
            InputOutput.printAlert("Error: La fecha introducida no es válida");
        }

    }

    /**
     * Permite modificar propuestas
     * @param proposalTable tabla donde se visualizan las propuestas
     * @param title         nombre de la propuesta
     * @param description   descripción de la propuesta
     * @param startDate     fecha de la propuesta
     * @param indexEntity   indice del objeto del JCombobox
     */
    public void softDeleteProposal(JTable proposalTable, String title, String description, String startDate, int indexEntity, int idProposal) {
        String sql = "UPDATE PROPOSALS SET status = ? WHERE idproposal = ?";

        try {
            // Almacenamos el nº total de filas que hay en la tabla
            int totalRows = proposalTable.getRowCount();

            // Almacena el nº de fila seleccionado
            int selectedRow = proposalTable.getSelectedRow();

            // Si no hay ninguna fila creada
            if (totalRows == 0) {
                throw new CustomException(1116);

                // Si no hay ninguna fila seleccionada
            } else if (selectedRow < 0) {
                throw new CustomException(1114);

                // Si los campos están vacíos
            } else if (title.isEmpty() || description.isEmpty() || startDate.isEmpty() || indexEntity == -1) {
                throw new CustomException(1111);

            } else {
                try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

                    stmt.setString(1, "inactive");

                    stmt.setInt(2, idProposal);

                    stmt.executeUpdate();
                    //stmt.close(); // No hace falta con el try-with-resources

                    // Añadimos la entrada al log
                    Log.capturarRegistro("PROPOSAL DELETE " + title + " "
                            + description + " "
                            + InputOutput.stringToDate(startDate) + "  "
                            + "ENTITY ID " + indexEntity);

                    // Actualizamos datos de la tabla
                    showData(proposalTable);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


        } catch (CustomException ce) {
            InputOutput.printAlert(ce.getMessage());

            // Capturamos error para el registro
            Error.capturarError("PROPOSAL " + ce.getMessage());
        } catch (ParseException e) {
            InputOutput.printAlert("Error: La fecha introducida no es válida");
        }

    }

    /**
     * Muestra los datos actualizados en la tabla de propuestas
     *
     * @param proposalTable tabla dónde se visualizan las propuestas creadas
     */
    public void showData(JTable proposalTable) {

        // Instanceamos el objeto para cargar los datos de la bbdd
        ProposalDB propdb = new ProposalDB();

        String[] colIdentifiers = {"ID", "Título", "Descripción", "Fecha de inicio", "Entidad"};

        // Añade los datos al modelo
        proposalTable.setModel(new CustomTableModel(
                propdb.listProposalsObject(),
                colIdentifiers
        ));

        // Diseño básico de la tabla
        CustomTableConfig.initConfig(proposalTable);
    }
}
