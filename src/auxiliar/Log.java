package auxiliar;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * Permite crear un archivo Log con todos los registros de creación, modificación y eliminación de empleados
 * y propuestas
 */
public class Log {

    private static BufferedWriter buffered;
    private static final String PATH = "./log.txt"; // Ruta del archivo

    // Abrimos buffer
    private static void open() throws IOException {
        // Con TRUE no se sobreescribe el archivo, con FALSE si
        buffered = new BufferedWriter(new FileWriter(PATH, true));
    }

    // Añadimos línea al buffer
    public static void addLine(String line) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String formatoFecha = sdf.format(new Date());

        open();
        buffered.write("["+ formatoFecha +"] " + line + "\n");

        close();
    }

    // Obtenemos líneas del log
    public static void getLines() throws IOException {
        ArrayList<String> linesFile = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(PATH));

        String line;

        while ((line = br.readLine()) != null) {
            linesFile.add(line);
        }

        br.close();

        Iterator<String> it = linesFile.iterator();

        while(it.hasNext()) {
            System.out.println(it.next());
        }
    }

    // Cerramos buffer
    private static void close() throws IOException {
        buffered.close();
    }

    // Captura un registro y lo escribe dentro del archivo correspondiente
    public static void capturarRegistro(String registro) {
        try {
            addLine(registro);

        } catch (IOException e) {
            InputOutput.printAlert("Error: Problema en la operación de escritura del archivo");
        }
    }

}
