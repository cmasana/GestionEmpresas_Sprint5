package modules;

import auxiliar.CustomException;
import auxiliar.InputOutput;
import auxiliar.Log;
import custom_ui.tables.*;
import mainclasses.database.ProposalDB;
import mainclasses.entity.Entity;
import auxiliar.Error;
import mainclasses.proposal.Proposal;

import javax.swing.*;
import java.text.ParseException;
import java.util.Date;


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
     * @param name          nombre de la propuesta
     * @param description   descripción de la propuesta
     * @param startDate     fecha de la propuesta
     * @param entity        entidad de la propuesta
     */
    public void createProposal(JTable proposalTable, String name, String description, String startDate, Entity entity) {

        try {
            // Si hay algún campo vacío
            if (name.isEmpty() || description.isEmpty() || startDate.isEmpty() || entity == null) {
                throw new CustomException(1111);

            } else {
                // Si la fecha introducida es anterior a la actual
                if (InputOutput.wrongDate(startDate)) {
                    throw new CustomException(1117);

                } else {
                    // Creamos objeto de la clase Proposal
                    Proposal prop = new Proposal(name, description, InputOutput.stringToDate(startDate), entity);

                    // Lo añadimos al arraylist de tipo Proposal
                    proposalList.addProposal(prop);

                    // Añadimos la entrada al log
                    Log.capturarRegistro("PROPOSAL CREATE " + prop.toString());

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
        }
    }


    /**
     * Permite eliminar propuestas
     *
     * @param proposalTable tabla donde se visualizan las propuestas
     */
    public void deleteProposal(JTable proposalTable) {
        // Almacena el resultado de un cuadro de alerta si es 0 se elimina el elemento
        int ok;

        // Guardamos el número de fila (coincide con la posición en el ArrayList)
        int row = proposalTable.getSelectedRow();

        try {
            // Si hay una fila seleccionada, mostramos mensaje de confirmación
            if (row >= 0) {
                // Mensaje de confirmación para eliminar
                ok = InputOutput.deleteConfirmation();

                // Si el resultado es igual a 0, eliminamos la propuesta
                if (ok == 0) {
                    // Añadimos la entrada al log
                    Log.capturarRegistro("PROPOSAL DELETE " + proposalList.getProposalFromDB(row));

                    // Eliminamos propuesta
                    proposalList.removeProposal(row);

                    // Actualizamos datos de la tabla
                    showData(proposalTable);
                }
            }
            // En caso contrario, mostramos un error por pantalla
            else {
                throw new CustomException(1114);
            }
        } catch (CustomException ce) {
            InputOutput.printAlert(ce.getMessage());

            // Capturamos error para el registro
            Error.capturarError("PROPOSAL " + ce.getMessage());

        }
    }

    /**
     * Permite vaciar toda la lista de propuestas
     *
     * @param proposalTable tabla donde se visualizan las propuestas creadas
     * @deprecated
     */
    public void emptyAll(JTable proposalTable) {
        // Almacena un entero, si es 0 se eliminan todos los elementos
        int ok;

        // Almacenamos el nº total de filas que hay en la tabla
        int totalrows = proposalTable.getRowCount();

        try {
            if (totalrows != 0) {
                // Mensaje de confirmación para eliminar
                ok = InputOutput.emptyConfirmation();

                if (ok == 0) {
                    // Recorremos el arrayList y eliminamos 1 a 1 los registros
                    for (int i = (totalrows - 1); i >= 0; i--) {
                        proposalList.removeProposal(i);
                    }

                    // Actualizamos los datos de la tabla
                    showData(proposalTable);
                }
            } else {
                throw new CustomException(1116);
            }
        } catch (CustomException ce) {
            InputOutput.printAlert(ce.getMessage());

            // Capturamos error para el registro
            Error.capturarError("PROPOSAL " + ce.getMessage());
        }
    }

    /**
     * Permite modificar propuestas
     *
     * @param proposalTable tabla donde se visualizan las propuestas
     * @param name          nombre de la propuesta
     * @param description   descripción de la propuesta
     * @param startDate     fecha de la propuesta
     * @param cbEntity      comboBox con la lista de entidades
     */
    public void editProposal(JTable proposalTable, String name, String description, String startDate, JComboBox<Entity> cbEntity) {

        // Almacenamos el nº total de filas que hay en la tabla
        int totalRows = proposalTable.getRowCount();

        // Almacena el nº de fila seleccionado (coincide con el índice en el ArrayList)
        int selectedRow = proposalTable.getSelectedRow();

        // Almacena el item seleccionado del comboBox
        Entity itemSelected = (Entity) cbEntity.getSelectedItem();

        try {
            // Si no hay ninguna fila creada
            if (totalRows == 0) {
                throw new CustomException(1116);
            } else {
                // Si no hay ninguna fila seleccionada
                if (selectedRow < 0) {
                    throw new CustomException(1114);
                } else {
                    // Si los campos están vacíos
                    if (name.isEmpty() || description.isEmpty() || startDate.isEmpty() || itemSelected == null) {
                        throw new CustomException(1111);
                    } else {
                        // Si la fecha introducida es anterior a HOY
                        if (InputOutput.wrongDate(startDate)) {
                            throw new CustomException(1117);
                        } else {
                            proposalList.getProposalFromDB(selectedRow).setName(name);
                            proposalList.getProposalFromDB(selectedRow).setDescription(description);
                            proposalList.getProposalFromDB(selectedRow).setStartDate(InputOutput.stringToDate(startDate));
                            proposalList.getProposalFromDB(selectedRow).setEntity(itemSelected);

                            // Añadimos la entrada al log
                            Log.capturarRegistro("PROPOSAL EDIT " + name + " "
                                    + description + " "
                                    + InputOutput.stringToDate(startDate) + "  "
                                    + itemSelected.toString());

                            // Actualizamos datos de la tabla
                            showData(proposalTable);
                        }
                    }
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

        // Creamos array de tipo string e inicializamos con el tamaño del ArrayList
        String[][] tabla = new String[proposalList.sizeProposalDB()][5];

        // Recorre la lista de Propuestas
        for (int i = 0; i < proposalList.sizeProposalDB(); i++) {

            // Datos de cada Empleado
            tabla[i][0] = proposalList.getProposalFromDB(i).getName();
            tabla[i][1] = proposalList.getProposalFromDB(i).getDescription();
            tabla[i][2] = InputOutput.dateToString(proposalList.getProposalFromDB(i).getStartDate());
            tabla[i][3] = proposalList.getProposalFromDB(i).getEntity().toString();
        }

        // Añade los datos al modelo
        proposalTable.setModel(new CustomTableModel(
                tabla,
                new String[]{
                        "Título", "Descripción", "Fecha Inicio", "Entidad"
                }
        ));

        // Diseño de la tabla
        CustomTableConfig.initConfig(proposalTable);
    }
}
