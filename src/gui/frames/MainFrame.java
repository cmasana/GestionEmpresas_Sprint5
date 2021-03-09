package gui.frames;

import custom_ui.components.buttons.SidebarButton;
import custom_ui.colors.ColorsPalette;
import gui.panels.EmployeesContent;
import gui.panels.ProposalContent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

/**
 * Permite crear la ventana principal de nuestra aplicación con todas sus configuraciones
 * @author cmasana
 */
public class MainFrame extends JFrame {
    // Paleta de colores
    private final ColorsPalette DYE = new ColorsPalette();

    // Paneles UI
    private final JPanel sideBar = new JPanel();
    private final JPanel content = new JPanel();
    private final JPanel emptySpace = new JPanel();
    private final EmployeesContent eContent = new EmployeesContent();
    private final ProposalContent pContent = new ProposalContent();

    /**
     * Crea un objeto de la clase MainWindow
     */
    public MainFrame() throws IOException {
        this.initComponents();
    }

    /**
     * Permite inicializar todos los componentes de nuestra ventana
     */
    private void initComponents() throws IOException {
        // Cargamos configuración de la ventana
        this.windowSettings();

        // Añadimos barra lateral
        this.putSidebar();

        // Añadimos panel de contenido
        this.putContent();

        // Añadimos un espacio vacío (SOLO DISEÑO)
        this.putEmptySpace();
    }

    /**
     * Añade la configuración de la ventana
     */
    private void windowSettings() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Cerrar aplicación al hacer clic en X
        this.setTitle("Gestión de Empresas"); // Añadir título a ventana
        this.setSize(1600,900); // Establecer un tamaño por defecto
        this.setResizable(true); // No se puede redimensionar, también quita botón maximizar
        this.setLayout(new BorderLayout()); // Establecemos layout
    }

    /**
     * Añade la barra lateral, su configuración y sus respectivos componentes
     */
    private void putSidebar() throws IOException {
        // Configuración barra lateral
        sideBar.setPreferredSize(new Dimension(200,900)); // Establecer un tamaño por defecto
        sideBar.setBackground(DYE.getMAIN());

        // Cargamos componentes de la barra lateral
        this.sbLogo();
        this.sbBrand();
        this.sbButtons();

        // Añadimos barra lateral a ventana actual
        this.add(sideBar, BorderLayout.WEST);
    }

    /**
     * Permite añadir el logo de la barra lateral
     */
    private void sbLogo() {
        JLabel imageLogo;
        imageLogo = new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("img/logo.png"))));
        imageLogo.setBorder(new EmptyBorder(50,0,20,0)); // Top, left, bottom, right
        sideBar.add(imageLogo);
    }

    /**
     * Permite añadir un título a la barra lateral
     */
    private void sbBrand() throws IOException {
        JLabel appTitle = new JLabel("PROIECTUS");
        appTitle.setBorder(new EmptyBorder(0,0,150,0)); // Top, left, bottom, right
        appTitle.setFont(new Font("Open Sans", Font.BOLD, 26)); // Fuente del texto
        appTitle.setForeground(DYE.getTXTMAIN()); // Color de texto
        sideBar.add(appTitle);
    }

    /**
     * Permite añadir el bloque de contenido a la ventana principal
     */
    private void putContent() throws IOException {
        content.setPreferredSize(new Dimension(1400,900));
        content.setBackground(DYE.getSECONDARY());

        // Añadimos paneles que mostrarán el contenido, dependiendo del botón al que hagamos clic
        content.add(eContent);
        content.add(pContent);

        // Añadimos visibilidad por defecto a los paneles (hijo)
        eContent.setVisible(true);
        pContent.setVisible(false);

        // Añadimos panel (padre)
        this.add(content, BorderLayout.CENTER);
    }

    /**
     * Permite añadir una serie de botones a la barra lateral con sus respectivas acciones al hacer clic
     */
    private void sbButtons() throws IOException {
        SidebarButton employeeBtn = new SidebarButton("Empleados");
        employeeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                eContent.setVisible(true);
                pContent.setVisible(false);
            }
        });
        sideBar.add(employeeBtn);

        SidebarButton proposalBtn = new SidebarButton("Propuestas");
        proposalBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                eContent.setVisible(false);
                pContent.setVisible(true);
            }
        });
        sideBar.add(proposalBtn);
    }

    /**
     * Permite añadir un espacio vacío a la ventana (con fines meramente estéticos)
     */
    private void putEmptySpace() throws IOException {
        emptySpace.setPreferredSize(new Dimension(1600, 1));
        emptySpace.setBackground(DYE.getTXTMAIN());
        this.add(emptySpace, BorderLayout.SOUTH);
    }
}
