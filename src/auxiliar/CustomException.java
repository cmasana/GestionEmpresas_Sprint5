package auxiliar;

import java.util.HashMap;

/**
 * Permite crear Excepciones personalizadas y enviar su correspondiente mensaje de alerta
 */
public class CustomException extends Exception {
    // Código de error
    private final int errorId;

    // HashMap que almacena parejas clave/valor con el errorId y su correspondiente mensaje de alerta
    private final HashMap<Integer, String> ERRORLIST = new HashMap<Integer, String>() {{
        put(1111, "Error: Por favor, rellene todos los campos");
        put(1112, "Error: El DNI que ha introducido es incorrecto");
        put(1113, "Error: El NSS que ha introducido es incorrecto");
        put(1114, "Error: No hay ninguna fila seleccionada");
        put(1115, "Error: No hay ningún empleado creado");
        put(1116, "Error: No hay ninguna propuesta creada");
        put(1117, "Error: La fecha introducida es anterior a la actual");
    }};


    // Constructor sobrecargado
    public CustomException(int errorId) {
        super();
        this.errorId = errorId;
    }

    /**
     * Muestra un mensaje de alerta dependiendo del código de error
     * @return devuelve un String con el mensaje de error
     */
    @Override
    public String getMessage() {
        return ERRORLIST.get(errorId);
    }
}
