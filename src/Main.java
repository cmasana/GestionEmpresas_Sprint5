import gui.frames.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Permite iniciar la aplicación, cargando una serie de configuraciones por defecto
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    JFrame frame = new MainFrame();

                    // Icono aplicación
                    Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/icon.png"));
                    frame.setIconImage(icon);

                    // Configuración básica
                    frame.setSize(1600,950); // Tamaño por defecto
                    frame.setMinimumSize(new Dimension(600, 600)); // Tamaño mínimo
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
