package source;

import java.math.BigInteger;
import java.util.Random;

public class Controller {

    private Archivo archivo;

    public Controller(Archivo archivo) {
        this.archivo = archivo;
    }

    void generateRecords(int cantRegistros, int cantCampos, int longCampos) {
        /**
         * Genera n cantidad de registros al azar, con k cantidad de campos y
         * cada campo con longitud L.
         */
        String line;

        String[] records = new String[cantRegistros];
        String[] pastRecords = archivo.readFile();

        BigInteger[] keys = new BigInteger[cantRegistros];

        QuickSort sorter = new QuickSort(); // Objeto para ordenar con quicksort

        // Adjunta los datos anteriores del archivo al nuevo arreglo.
        for (int i = 0; i < pastRecords.length; i++) {
            String[] temp = pastRecords[i].split(";");
            keys[i] = new BigInteger(temp[0]);
            records[i] = pastRecords[i];
        }

        for (int i = 0; i < cantRegistros; i++) {
            line = generateKey().toString();
            keys[i] = new BigInteger(line);

            for (int j = 0; j < cantCampos; j++) {
                if (j % 2 == 0) {
                    line = line + ";" + generateRandomInteger(longCampos);
                } else {
                    line = line + ";" + generateRandomCharacters(longCampos);
                }
            }
            records[i] = line;
        }

        sorter.sort(keys, records);
        archivo.writeToFile(records);
    }

    int generateRandomInteger(int longCampo) {
        /**
         * Genera una combinación de numeros random dependiendo de la longitud
         * del campo.
         */
        Random rnd = new Random();

        int longitud = (int) Math.pow(10, longCampo - 1);
        int integer = rnd.nextInt(longitud * 9) + longitud;

        return integer;
    }

    String generateRandomCharacters(int longCampo) {
        /**
         * Genera un string random dependiendo de la longitud del campo.
         */
        String characters = "";
        int rnd;
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int cont = 0;

        while (longCampo > cont) {
            rnd = (int) (Math.random() * 25);
            characters = characters + alphabet.charAt(rnd);
            cont++;
        }

        return characters;
    }

    BigInteger generateKey() {
        /**
         * Genera una llave (BigInteger) random para el archivo.
         */
        boolean Unique = false;

        Random rnd = new Random();

        BigInteger key;
        BigInteger lowerLimit = new BigInteger("99999999999999999999999"); // limite inferior
        BigInteger upperLimit = new BigInteger("999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999"); // limite superior
        BigInteger tempBigInt = upperLimit.subtract(lowerLimit);

        int maxNumBitLength = upperLimit.bitLength();

        while (Unique == false) {
            key = new BigInteger(maxNumBitLength, rnd);

            if (key.compareTo(lowerLimit) < 0) {
                key = key.add(lowerLimit);
            }
            if (key.compareTo(upperLimit) >= 0) {
                key = key.mod(tempBigInt).add(lowerLimit);
            }

            if (isUnique(key, archivo.readFile())) {
                Unique = true;
                return key;
            }
        }

        return BigInteger.ONE;
    }

    boolean isUnique(BigInteger key, String[] lines) {
        /**
         * Retorna si una llave es unica.
         */
        for (String line : lines) {
            String[] temp = line.split(";");
            if (key.compareTo(new BigInteger(temp[0])) == 0) {
                return false;
            }
        }
        return true;
    }

    int searchString(String arr[], String value) {
        /**
         * Busca el dato dado por medio de una busqueda linear
         */
        int n = arr.length;

        for (int i = 0; i < n; i++) {
            if (arr[i].contains(value)) {
                return i;
            }

        }
        return -1;
    }

    int searchKey(BigInteger arr[], int l, int r, BigInteger x) {
        /**
         * Busca la llave dada por medio de busqueda binaria
         */
        if (r >= l) {
            int mid = l + (r - l) / 2;

            // Se verifica que el elemento este en la mitad
            // del arreglo
            if (arr[mid].compareTo(x) == 0) {
                return mid;
            }

            // si el elemento es menor que el indice, el elemnto
            // solo puede estar a la izquierda del arreglo
            if (arr[mid].compareTo(x) > 0) {
                return searchKey(arr, l, mid - 1, x);
            }

            // el elemento solamente puede estar presente a la izquierda
            return searchKey(arr, mid + 1, r, x);
        }

        // Se llega aca solamente cuando el valor no esta presente
        return -1;
    }

    void showResults(int pos, String[] lines) {
        /**
         * Muestra los resultados en la posición dada.
         */
        System.out.println("El dato fue encontrado en la posición " + pos);
        System.out.println("|||||||||||||||||||||Datos contenidos|||||||||||||||||||||");
        System.out.println(lines[pos]);
        System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
    }

    int getNumberOfFields() {
        /**
         * Retorna la cantidad de campos en el archivo
         */
        String records = archivo.readFile()[0];
        String[] splitRecords = records.split(";");
        return splitRecords.length - 1;
    }

    int getFieldSize() {
        /**
         * Retorna el tamaño de un campo
         */
        String records = archivo.readFile()[0];
        String[] splitRecords = records.split(";");
        return splitRecords[1].length();
    }

}
