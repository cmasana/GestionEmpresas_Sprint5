package auxiliar;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Clase que permite leer el archivo de configuración
 */
public class ReadConfig {

    /**
     * Permite cargar una propiedad del archivo de configuración
     * @param path ruta del archivo
     * @param option opción del archivo de configuración que queremos cargar
     * @return devuelve un string con la combinación de colores RGB
     * @throws IOException excepción de entrada y salida
     */
    public static String loadCfg(String path, String option) throws IOException {
        Properties prop = new Properties();
        String line;

        prop.load(new FileReader(path));

        line = prop.getProperty(option);

        return line;
    }
}
