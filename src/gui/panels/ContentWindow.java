package gui.panels;

import custom_ui.colors.ColorsPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

/**
 * Clase abstracta que contiene todas las configuraciones para este tipo de paneles
 * dónde se muestra el contenido principal de la aplicación
 *
 * @author cmasana
 */
public abstract class ContentWindow extends JPanel {
    // Paleta de colores
    protected final ColorsPalette DYE = new ColorsPalette();

    protected String contentTitle;
    protected String contentButton;
    protected JPanel contentModule;

    public ContentWindow(String contentTitle) throws IOException {
        this.initComponents(contentTitle);
    }

    protected void initComponents(String contentTitle) throws IOException {
        this.panelSettings();
        this.panelContent(contentTitle);
    }

    protected void panelSettings() throws IOException {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(1400,900));
        this.setBorder(new EmptyBorder(10,20,10,10)); // Top, left, bottom, right
        this.setBackground(DYE.getSECONDARY());
        this.setVisible(true);
    }

    protected void putContentModule() throws IOException {
        JPanel module = new JPanel();
        module.setBackground(DYE.getSECONDARY());
        this.add(module, BorderLayout.CENTER);
    }

    protected void panelContent(String contentTitle) throws IOException {
        JLabel title = new JLabel(contentTitle);
        title.setFont(new Font("Open Sans", Font.BOLD, 20));
        title.setForeground(DYE.getTXTMAIN());
        title.setHorizontalAlignment(JLabel.CENTER);
        this.add(title, BorderLayout.NORTH);

        this.putContentModule();
    }
}
