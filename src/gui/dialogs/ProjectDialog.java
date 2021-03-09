package gui.dialogs;

import custom_ui.colors.ColorsPalette;
import mainclasses.database.EmployeeDB;
import mainclasses.user.Employee;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.io.IOException;

/**
 * Muestra un panel para la creación de proyectos
 */
public class ProjectDialog extends JPanel {
    private JTextField txtTitle;
    private JTextField txtDescription;
    private JComboBox<Employee> cbEmployee;

    private final EmployeeDB employeeList = new EmployeeDB();

    // Paleta de colores
    private final ColorsPalette DYE = new ColorsPalette();

    // Constructor
    public ProjectDialog(String title, String description) throws IOException {
        this.initComponents(title, description);
    }

    private void initComponents(String title, String description) throws IOException {
        this.basicConfig();
        this.putComponents(title, description);
    }

    private void basicConfig() {
        this.setLayout(new GridLayout(0,1));
        this.setBorder(new EmptyBorder(50,50,50,50));
    }

    private void putComponents(String title, String description) throws IOException {
        JLabel lbProposal = new JLabel("Título");
        txtTitle = new JTextField(50);
        txtTitle.setEditable(false);
        txtTitle.setText(title);
        txtTitle.setBorder(new MatteBorder(0, 0, 1, 0, DYE.getTXTMAIN()));

        JLabel lbDescription = new JLabel("Descripción");
        txtDescription = new JTextField(50);
        txtDescription.setEditable(false);
        txtDescription.setText(description);
        txtDescription.setBorder(new MatteBorder(0, 0, 1, 0, DYE.getTXTMAIN()));


        // ComboBox con objetos de tipo Empleado
        JLabel lbEmployee = new JLabel("Jefe Proyecto");
        cbEmployee = new JComboBox<>(employeeList.listEmployees());


        // Añadimos componentes al panel principal
        this.add(lbProposal);
        this.add(txtTitle);

        // Crea un espacio en blanco de separación
        this.add(Box.createRigidArea(new Dimension(0, 20)));

        this.add(lbDescription);
        this.add(txtDescription);

        // Crea un espacio en blanco de separación
        this.add(Box.createRigidArea(new Dimension(0, 20)));

        this.add(lbEmployee);
        this.add(cbEmployee);
    }

    // Getters
    public String getTxtTitle() {
        return txtTitle.getText();
    }

    public String getTxtDescription() {
        return txtDescription.getText();
    }

    public Employee getCbEmployee() {
        return (Employee) cbEmployee.getSelectedItem();
    }
}
