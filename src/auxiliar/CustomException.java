package auxiliar;

/**
 * Permite crear Excepciones personalizadas y enviar su correspondiente mensaje de alerta
 */
public class CustomException extends Exception {
    // Código de error
    private final int errorId;

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
        String alertMessage = "";

        switch (errorId) {
            case 1111:
                alertMessage = "Error: Por favor, rellene todos los campos";
                break;
            case 1112:
                alertMessage = "Error: El DNI que ha introducido es incorrecto";
                break;
            case 1113:
                alertMessage = "Error: El NSS que ha introducido es incorrecto";
                break;
            case 1114:
                alertMessage = "Error: No hay ninguna fila seleccionada";
                break;
            case 1115:
                alertMessage = "Error: No hay ningún empleado creado";
                break;
            case 1116:
                alertMessage = "Error: No hay ninguna propuesta creada";
                break;
            case 1117:
                alertMessage = "Error: La fecha introducida es anterior a la actual";
                break;
        }
        return alertMessage;
    }
}
