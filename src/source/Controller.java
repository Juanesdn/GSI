package source;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    void mostrarCamposNumericos() {
        /**
         * Obtiene los campos numericos del archivo.
         */
        System.out.println("La posición de los campos numericos disponibles son:");
        for (int i = 0; i < getNumberOfFields(); i++) {
            if (i % 2 == 0) {
                System.out.print(i + 1 + ", ");
            }
        }
        System.out.println("");
    }

    int[] getNumericalField(int fieldPos) {
        int[] numericalFields = new int[archivo.getLines()];
        String[] registros = archivo.readFile();

        for (int i = 0; i < registros.length; i++) {
            String currentLine = registros[i];
            String[] dividedLine = currentLine.split(";");

            numericalFields[i] = Integer.valueOf(dividedLine[fieldPos]);
        }

        return numericalFields;
    }

    void calcularValorMaximo(int fieldPos) {
        Long timeSpent = System.nanoTime();

        int[] numericalFields = getNumericalField(fieldPos);
        int maxValue = numericalFields[0];

        for (int i = 1; i < numericalFields.length; i++) {
            if (numericalFields[i] > maxValue) {
                maxValue = numericalFields[i];
            }
        }

        System.out.println("El valor maximo es " + maxValue);

        System.out.println("Tiempo transcurrido para generar el valor maximo es : " + (System.nanoTime() - timeSpent));

    }

    void calcularValorMinimo(int fieldPos) {
        Long timeSpent = System.nanoTime();

        int[] numericalFields = getNumericalField(fieldPos);
        int minValue = numericalFields[0];

        for (int i = 1; i < numericalFields.length; i++) {
            if (numericalFields[i] < minValue) {
                minValue = numericalFields[i];
            }
        }

        System.out.println("El valor minimo es " + minValue);

        System.out.println("Tiempo transcurrido para generar el valor minimo es : " + (System.nanoTime() - timeSpent));
    }

    void calcularModa(int fieldPos) {
        Long timeSpent = System.nanoTime();

        int[] numericalFields = getNumericalField(fieldPos);
        int moda = 0;
        int maxNumRepeticiones = 0;

        for (int i = 0; i < numericalFields.length; i++) {
            int vecesQueSeRepite = 0;
            for (int j = 0; j < numericalFields.length; j++) {
                if (numericalFields[i] == numericalFields[j]) {
                    vecesQueSeRepite++;
                }
            }
            if (vecesQueSeRepite > maxNumRepeticiones) {
                moda = numericalFields[i];
                maxNumRepeticiones = vecesQueSeRepite;
            }
        }

        System.out.println("La moda es " + moda + " se repitio " + maxNumRepeticiones + " veces");

        System.out.println("Tiempo transcurrido para calcular la moda : " + (System.nanoTime() - timeSpent));
    }

    void calcularPromedio(int fieldPos) {
        Long timeSpent = System.nanoTime();
        
        int[] numericalFields = getNumericalField(fieldPos);
        int promedio;
        int sumaTotal = numericalFields[0];

        for (int i = 1; i < numericalFields.length; i++) {
            sumaTotal += numericalFields[i];
        }

        promedio = sumaTotal / archivo.getLines();

        System.out.println("El promedio total del campo es " + promedio);
        System.out.println("Tiempo transcurrido para calcular el promedio : " + (System.nanoTime() - timeSpent));
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
