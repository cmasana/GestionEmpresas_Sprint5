package validations;

/**
 * Clase que permite validar que un NSS introducido sea correcto
 *
 * @author cmasana
 */
public class ValidadorNSS {

    public static boolean validar(String nss) {

        // Excluimos cadenas distintas a 12 caracteres
        if(nss.length() != 12) {
            return false;
        }
        else {
            // Validamos que sólo tengo 12 dígitos y que la ID de provincia y el DC son correctos
            if (soloNumeros(nss) && digitosProvincia(nss) && digitosControl(nss).equals(nss.substring(10))) {
                return true;
            } else {
                return false;
            }
        }
    }

    private static boolean soloNumeros(String nss) {
        String numero = ""; // Es el número que se comprueba uno a uno por si hay alguna letra
        String miNss = "";
        String[] unoNueve = {"0","1","2","3","4","5","6","7","8","9"};

        for(int i = 0; i < nss.length(); i++) {
            numero = nss.substring(i, i+1);

            for(int j = 0; j < unoNueve.length; j++) {
                if(numero.equals(unoNueve[j])) {
                    miNss += unoNueve[j];
                }
            }
        }

        if(miNss.length() != 12) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Evalua si los 2 primeros dígitos se corresponden con alguno de los ID de provincia permitidos
     * @param nss número de la seguridad social a evaluar
     * @return devuelve TRUE si es un código permitido o FALSE en caso contrario
     */
    private static boolean digitosProvincia(String nss) {
        String miNss = "";
        String provincia = nss.substring(0,2);

        String[] codProvincia = {
                "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17",
                "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34",
                "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "53",
                "66"
        };

        for (int i = 0; i < codProvincia.length; i++) {
            if (provincia.equals(codProvincia[i])) {
                miNss += codProvincia[i];
            }
        }

        if(miNss.length() != 2) {
            return false;
        }
        else {
            return true;
        }

    }

    private static String digitosControl(String nss) {

        // pasamos los diez primeros dígitos del nss a entero
        int miNss = Integer.parseInt(nss.substring(0,10));

        // Almacenamos el resto de la ecuación
        int resto = miNss % 97;

        // Devolvemos el resto transformado en String
        return String.valueOf(resto);
    }
}
