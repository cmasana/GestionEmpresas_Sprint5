package custom_ui.colors;

import auxiliar.ReadConfig;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Permite obtener los colores de nuestra aplicación
 * @author cmasana
 */
public class ColorsPalette {

    // Colores predeterminados
    private final Color MAIN = new Color(240,165,0);
    private final Color SECONDARY = new Color(244,244,244);
    private final Color HOVER = new Color(207,117,0);
    private final Color TXTMAIN = new Color(26,28,32);
    private final Color TXTHOVER = new Color(244,244,244);
    private final Color TXTPRESSED = new Color(207, 117,0);
    private final Color TXTRELEASED = new Color(244,244,244);


    // Getters
    public Color getMAIN() throws IOException {
        return getRgbColor(MAIN, "color_main");
    }

    public Color getSECONDARY() throws IOException {
        return getRgbColor(SECONDARY, "color_secondary");
    }

    public Color getHOVER() throws IOException {
        return getRgbColor(HOVER, "color_hover");
    }

    public Color getTXTMAIN() throws IOException {
        return getRgbColor(TXTMAIN, "color_txt_main");
    }

    public Color getTXTHOVER() throws IOException {
        return getRgbColor(TXTHOVER, "color_txt_hover");
    }

    public Color getTXTPRESSED() throws IOException {
        return getRgbColor(TXTPRESSED, "color_txt_pressed");
    }

    public Color getTXTRELEASED() throws IOException {
        return getRgbColor(TXTRELEASED, "color_txt_released");
    }

    /**
     * Devuelve un color
     * @param predeterminado color predeterminado por si no existiera archivo de configuración
     * @param optionLine línea de la que queremos extraer el valor de color
     * @return devuelve un color, ya sea el predeterminado o el personalizado
     * @throws IOException excepción de entrada/salida
     */
    private Color getRgbColor(Color predeterminado, String optionLine) throws IOException {
        // Archivo de configuración
        File myCfg = new File("./cfg.properties");

        // Si existe archivo de configuración cargamos el color indicado
        if (myCfg.exists()) {
            String option = ReadConfig.loadCfg(myCfg.getPath(), optionLine);
            return this.hexToColor(option);
        } else {
            // Si no, cargamos el color predeterminado
            return predeterminado;
        }
    }

    /**
     * Permite obtener un color desde un String con formato hexadecimal
     * @param color string con el valor en hexadecimal de un determinado color
     * @return devuelve un Color con formato RGB
     */
    private Color hexToColor(String color) {
        return Color.decode(color);
    }

}
