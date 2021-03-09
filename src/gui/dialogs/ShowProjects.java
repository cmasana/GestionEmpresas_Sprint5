package gui.dialogs;

import custom_ui.colors.ColorsPalette;
import custom_ui.tables.CustomTableConfig;
import custom_ui.tables.CustomTableModel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Muestra los Proyectos en una tabla
 */
public class ShowProjects extends JPanel {
    private final ColorsPalette DYE = new ColorsPalette();
    private JTable projectTable;

    public ShowProjects() throws IOException {
        this.basicConfig();
        this.putProjectTable();
    }

    private void basicConfig() {
        this.setLayout(new GridLayout());
        this.setPreferredSize(new Dimension(1000, 500));
    }

    /**
     * Añade una tabla y su configuración al panel actual
     */
    private void putProjectTable() throws IOException {
        String[] titulos = {"Título", "Descripción", "Manager"};

        JPanel projectPanelTable = new JPanel(new BorderLayout());
        projectPanelTable.setBackground(DYE.getSECONDARY());

        // Tabla
        JScrollPane scrollPane = new JScrollPane();
        projectPanelTable.add(scrollPane);

        projectTable = new JTable();

        // Modelo por defecto de la tabla
        projectTable.setModel(new CustomTableModel(
                new Object [][] {

                },
                titulos
        ));

        // Diseño básico de la tabla
        CustomTableConfig.initConfig(projectTable);

        scrollPane.setViewportView(projectTable);

        this.add(projectPanelTable);
    }

    // Getter
    public JTable getProjectTable() {
        return projectTable;
    }
}
