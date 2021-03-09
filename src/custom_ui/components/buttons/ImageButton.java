package custom_ui.components.buttons;

import custom_ui.colors.ColorsPalette;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Objects;

public class ImageButton extends JButton implements MouseListener {
    // Paleta de colores
    private final ColorsPalette DYE = new ColorsPalette();

    // Panel botones
    JPanel panelBG;

    public ImageButton() throws IOException {
        this.initComponent();
        this.putImage("", "");
    }

    public ImageButton(String pathImage, String btnLabel) throws IOException {
        this.initComponent();
        this.putImage(pathImage, btnLabel);
    }

    private void initComponent() throws IOException {
        this.setBorder(new MatteBorder(1,1,1,1, DYE.getTXTMAIN()));
        this.setOpaque(true); // Opacidad
        this.setBackground(DYE.getMAIN()); // Color de fondo
        this.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambiar a cursor de mano
        this.setFocusable(false); // Quitar bordes alrededor de texto
        addMouseListener(this); // Añadir los eventos de ratón
    }

    private void putImage(String pathImage, String btnLabel) throws IOException {
        panelBG = new JPanel();
        panelBG.setLayout(new FlowLayout());
        panelBG.setBackground(DYE.getMAIN());

        JLabel image;
        image = new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(pathImage))));
        panelBG.add(image);

        JLabel title;
        title = new JLabel(btnLabel);
        panelBG.add(title);

        this.add(panelBG);
    }



    @Override
    public void mouseClicked(MouseEvent e) {
        // No lo uso
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // No lo uso
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // No lo uso
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        try {
            panelBG.setBackground(DYE.getHOVER());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        try {
            panelBG.setBackground(DYE.getMAIN());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
