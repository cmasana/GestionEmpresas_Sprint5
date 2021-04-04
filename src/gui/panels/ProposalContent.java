package gui.panels;


import auxiliar.CustomException;
import auxiliar.InputOutput;
import custom_ui.components.buttons.ImageButton;
import custom_ui.components.forms.*;
import custom_ui.tables.*;
import io.loli.datepicker.DatePicker;
import mainclasses.database.EntityDB;
import mainclasses.database.ProjectDB;
import mainclasses.database.ProposalDB;
import mainclasses.entity.Entity;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ProposalContent extends ContentWindow {
    // Simula las bbdd
    private final ProposalDB PROPOSALDB = new ProposalDB();
    private final ProjectDB PROJECTDB = new ProjectDB();
    // Paneles
    private JPanel module;
    private JPanel management;
    private JPanel tablePanel;
    // Formulario
    private RowForm rowId;
    private RowForm rowTitle;
    private RowForm rowDescription;
    private RowForm rowStartDate;
    // ComboBox
    private JComboBox<Entity> cbEntity;
    // Tabla
    private JTable proposalTable;

    // Constructor
    public ProposalContent() throws IOException {
        super("GESTIÓN DE PROPUESTAS");
    }

    /**
     * Método que permite personalizar el contenido del apartado de empleados
     */
    @Override
    protected void putContentModule() throws IOException {
        module = new JPanel();
        module.setLayout(new GridLayout(2, 1));
        module.setBackground(DYE.getSECONDARY());

        this.putManagementPanel();
        this.putTablesPanel();

        this.add(module, BorderLayout.CENTER);
    }

    /**
     * Añade un panel con una serie de botones y un formulario
     */
    private void putManagementPanel() throws IOException {
        management = new JPanel();
        management.setLayout(new BorderLayout());
        management.setBorder(new EmptyBorder(0, 50, 0, 0));
        management.setBackground(DYE.getSECONDARY());

        this.putManagementButtons();
        this.putForm();

        module.add(management);
    }

    /**
     * Añade un formulario con labels e inputs
     */
    private void putForm() throws IOException {
        JPanel form = new JPanel();
        form.setBackground(DYE.getSECONDARY());
        form.setLayout(new GridLayout(4, 1));
        form.setBorder(new EmptyBorder(20, 50, 20, 100)); // Top, left, bottom, right

        rowId = new RowForm("ID", false);

        rowTitle = new RowForm("Título", true);
        form.add(rowTitle);

        rowDescription = new RowForm("Descripción", true);
        form.add(rowDescription);

        rowStartDate = new RowForm("Fecha de inicio", true);

        // Datepicker: https://github.com/chocotan/datepicker4j
        DatePicker.datePicker(rowStartDate.getTxtInput(), "yyyy-MM-dd");
        form.add(rowStartDate);

        form.add(putCombobox());

        management.add(form, BorderLayout.CENTER);
    }

    /**
     * Permite mostrar un panel con un Label y un Combobox de Entidades
     *
     * @return devuelve un JPanel con los componentes asignados
     */
    private JPanel putCombobox() throws IOException {
        JPanel comboPanel = new JPanel();
        comboPanel.setBorder(new EmptyBorder(20, 75, 20, 297)); // Top, left, bottom, right
        comboPanel.setLayout(new BoxLayout(comboPanel, BoxLayout.Y_AXIS));
        comboPanel.setBackground(DYE.getSECONDARY());

        // Combobox
        JLabel lbEntity = new JLabel("Entidad");
        lbEntity.setFont(new Font("Open Sans", Font.BOLD, 14));
        lbEntity.setAlignmentX(Component.LEFT_ALIGNMENT);
        comboPanel.add(lbEntity);

        EntityDB lista = new EntityDB();

        cbEntity = new JComboBox<Entity>(lista.listEntities());
        cbEntity.setAlignmentX(Component.LEFT_ALIGNMENT);
        comboPanel.add(cbEntity);

        return comboPanel;
    }


    /**
     * Añade los botones de la parte izquierda que permiten realizar toda la gestión de propuestas
     */
    private void putManagementButtons() throws IOException {
        JPanel mButtonsProposal = new JPanel();
        mButtonsProposal.setLayout(new BoxLayout(mButtonsProposal, BoxLayout.Y_AXIS));
        mButtonsProposal.setBorder(new EmptyBorder(110, 50, 0, 0)); // Top, left, bottom, right
        mButtonsProposal.setBackground(DYE.getSECONDARY());

        // Botón Crear
        ImageButton btnCreate = new ImageButton("img/create.png", "CREAR");
        btnCreate.setPreferredSize(new Dimension(150, 40));
        btnCreate.setMaximumSize(new Dimension(150, 40));
        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // No he conseguido utilizar getSelectedItem en el Combobox, pero funciona igualmente, quizás si pudiera eliminar las entidades no funcionaría correctamente
                PROPOSALDB.createProposal(proposalTable,
                        rowTitle.getTxtInput().getText(),
                        rowDescription.getTxtInput().getText(),
                        rowStartDate.getTxtInput().getText(),
                        (cbEntity.getSelectedIndex() + 1)
                );

                InputOutput.cleanInputs(rowTitle, rowDescription, rowStartDate);
            }
        });
        mButtonsProposal.add(btnCreate);

        // Crea un espacio en blanco de separación
        mButtonsProposal.add(Box.createRigidArea(new Dimension(0, 5)));

        // Botón editar
        ImageButton btnEdit = new ImageButton("img/edit.png", "MODIFICAR");
        btnEdit.setPreferredSize(new Dimension(150, 40));
        btnEdit.setMaximumSize(new Dimension(150, 40));

        btnEdit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                PROPOSALDB.editProposal(proposalTable,
                        rowTitle.getTxtInput().getText(),
                        rowDescription.getTxtInput().getText(),
                        rowStartDate.getTxtInput().getText(),
                        (cbEntity.getSelectedIndex() + 1),
                        rowId.getTxtInput().getText()
                );

                InputOutput.cleanInputs(rowId, rowTitle, rowDescription, rowStartDate);
            }
        });

        mButtonsProposal.add(btnEdit);

        // Crea un espacio en blanco de separación
        mButtonsProposal.add(Box.createRigidArea(new Dimension(0, 5)));

        // Botón eliminar
        ImageButton btnDelete = new ImageButton("img/delete.png", "ELIMINAR");
        btnDelete.setPreferredSize(new Dimension(150, 40));
        btnDelete.setMaximumSize(new Dimension(150, 40));
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                PROPOSALDB.softDeleteProposal(proposalTable,
                        rowTitle.getTxtInput().getText(),
                        rowDescription.getTxtInput().getText(),
                        rowStartDate.getTxtInput().getText(),
                        (cbEntity.getSelectedIndex() + 1),
                        rowId.getTxtInput().getText()
                );

                InputOutput.cleanInputs(rowId, rowTitle, rowDescription, rowStartDate);
            }
        });
        mButtonsProposal.add(btnDelete);

        // Crea un espacio en blanco de separación
        mButtonsProposal.add(Box.createRigidArea(new Dimension(0, 40)));

        // Botón crear proyecto
        ImageButton btnCreateProject = new ImageButton("img/empty.png", "CREAR PROYECTO");
        btnCreateProject.setPreferredSize(new Dimension(150, 40));
        btnCreateProject.setMaximumSize(new Dimension(150, 40));
        btnCreateProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    PROJECTDB.createProject(
                            proposalTable,
                            rowTitle.getTxtInput().getText(),
                            rowDescription.getTxtInput().getText(),
                            rowId.getTxtInput().getText()
                    );

                    PROPOSALDB.toProject(
                            proposalTable,
                            rowId.getTxtInput().getText()
                    );


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (CustomException ce) {
                    InputOutput.printAlert(ce.getMessage());
                }
            }
        });
        mButtonsProposal.add(btnCreateProject);

        // Crea un espacio en blanco de separación
        mButtonsProposal.add(Box.createRigidArea(new Dimension(0, 5)));

        // Botón Ver proyectos
        ImageButton btnShow = new ImageButton("img/info.png", "VER PROYECTOS");
        btnShow.setPreferredSize(new Dimension(150, 40));
        btnShow.setMaximumSize(new Dimension(150, 40));
        btnShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    PROJECTDB.showData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        mButtonsProposal.add(btnShow);

        management.add(mButtonsProposal, BorderLayout.WEST);
    }

    private void putTablesPanel() throws IOException {
        tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());

        this.putProposalTable();

        module.add(tablePanel);
    }

    /**
     * Añade una tabla y su configuración al panel actual
     */
    private void putProposalTable() throws IOException {
        String[] titulos = {"ID", "Título", "Descripción", "Fecha Inicio", "Entidad"};

        JPanel proposalPanelTable = new JPanel(new BorderLayout());
        proposalPanelTable.setBackground(DYE.getSECONDARY());

        // Tabla
        JScrollPane scrollPane = new JScrollPane();
        proposalPanelTable.add(scrollPane, BorderLayout.CENTER);

        proposalTable = new JTable();

        ProposalDB propdb = new ProposalDB();

        // Modelo por defecto de la tabla
        proposalTable.setModel(new CustomTableModel(
                propdb.listProposalsObject(),
                titulos
        ));

        // Diseño básico de la tabla
        CustomTableConfig.initConfig(proposalTable);

        scrollPane.setViewportView(proposalTable);


        // Carga los datos de una fila cuando hacemos clic en ella
        proposalTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = proposalTable.getSelectedRow();

                // Condición que limpia datos cada vez que seleccionamos una fila (sino peta)
                if (selectedRow == -1) {
                    InputOutput.cleanInputs(rowId, rowTitle, rowDescription, rowStartDate);
                } else {
                    rowId.setTxtInput(proposalTable.getValueAt(proposalTable.getSelectedRow(), 0).toString());
                    rowTitle.setTxtInput(proposalTable.getValueAt(proposalTable.getSelectedRow(), 1).toString());
                    rowDescription.setTxtInput(proposalTable.getValueAt(proposalTable.getSelectedRow(), 2).toString());
                    rowStartDate.setTxtInput(proposalTable.getValueAt(proposalTable.getSelectedRow(), 3).toString());
                    cbEntity.setSelectedItem(proposalTable.getValueAt(proposalTable.getSelectedRow(), 4).toString());
                }
            }
        });

        tablePanel.add(proposalPanelTable, BorderLayout.CENTER);
    }
}
