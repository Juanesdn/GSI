package source;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class Controller {

    private final Archivo archivo;

    public Controller(Archivo archivo) {
        this.archivo = archivo;
    }

    void generateRecords(int cantRegistros, int cantCampos) {
        /**
         * Genera n cantidad de registros al azar, con k cantidad de campos y
         * cada campo con longitud L.
         */
        String line;

        int[] dataType = new int[cantCampos];
        int[] dataSize = new int[cantCampos];

        Scanner scanner = new Scanner(System.in);

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

        for (int i = 0; i < cantCampos; i++) {
            System.out.println("Digite la longitud para el campo " + (i + 1));
            dataSize[i] = scanner.nextInt();
            System.out.println("Digite el numero indicado para el tipo de campo");
            System.out.println("1) numerico");
            System.out.println("2) alfabetico");
            int temp = scanner.nextInt();
            if(!(temp < 1) || !(temp > 2)){
                dataType[i] = temp;
            }
            

        }

        for (int i = 0; i < cantRegistros; i++) {
            line = generateKey().toString();
            keys[i] = new BigInteger(line);

            for (int j = 0; j < cantCampos; j++) {
                if (dataType[j] == 1) {
                    line = line + ";" + generateRandomNumber(dataSize[j]);
                } else if (dataType[j] == 2) {
                    line = line + ";" + generateRandomCharacters(dataSize[j]);
                }

            }
            records[i] = line;
        }

        sorter.sort(keys, records);
        archivo.writeToFile(records);
    }

    String generateRandomNumber(int longCampo) {
        /**
         * Genera una combinación de numeros random dependiendo de la longitud
         * del campo.
         */
        String characters = "";
        int rnd;
        String alphabet = "0123456789";
        int cont = 0;

        while (longCampo > cont) {
            rnd = (int) (Math.random() * 9);
            characters = characters + alphabet.charAt(rnd);
            cont++;
        }

        return characters;
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

    int searchString(String arr[], String value, int pos) {
        /**
         * Busca el dato dado por medio de una busqueda linear
         */
        String field;

        for (int i = 0; i < arr.length; i++) {
            field = arr[i].split(";")[pos];
            if (field.equals(value)) {
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
        String[] lines = archivo.readFile();
        String[] temp = lines[0].split(";");
        for (int j = 1; j <= temp.length; j++) {
            try {
                new BigInteger(temp[j]);
                System.out.print(j + ",");
            } catch (Exception e) {

            }

        }
        System.out.println("");
    }

    BigInteger[] getNumericalField(int fieldPos) {
        BigInteger[] numericalFields = new BigInteger[archivo.getLines()];
        String[] registros = archivo.readFile();

        for (int i = 0; i < registros.length; i++) {
            String currentLine = registros[i];
            String[] dividedLine = currentLine.split(";");

            numericalFields[i] = new BigInteger(dividedLine[fieldPos]);
        }

        return numericalFields;
    }

    void calcularValorMaximo(int fieldPos) {
        Long timeSpent = System.nanoTime();

        BigInteger[] numericalFields = getNumericalField(fieldPos);
        BigInteger maxValue = numericalFields[0];

        for (int i = 1; i < numericalFields.length; i++) {
            if (numericalFields[i].compareTo(maxValue) == 1) {
                maxValue = numericalFields[i];
            }
        }

        System.out.println("El valor maximo es " + maxValue.toString());

        System.out.println("Tiempo transcurrido para generar el valor maximo es : " + (System.nanoTime() - timeSpent));

    }

    void calcularValorMinimo(int fieldPos) {
        Long timeSpent = System.nanoTime();

        BigInteger[] numericalFields = getNumericalField(fieldPos);
        BigInteger minValue = numericalFields[0];

        for (int i = 1; i < numericalFields.length; i++) {
            if (numericalFields[i].compareTo(minValue) == -1) {
                minValue = numericalFields[i];
            }
        }

        System.out.println("El valor minimo es " + minValue.toString());

        System.out.println("Tiempo transcurrido para generar el valor minimo es : " + (System.nanoTime() - timeSpent));
    }

    void calcularModa(int fieldPos) {
        Long timeSpent = System.nanoTime();

        BigInteger[] numericalFields = getNumericalField(fieldPos);
        BigInteger moda = BigInteger.ZERO;
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

        System.out.println("La moda es " + moda.toString() + " se repitio " + maxNumRepeticiones + " veces");

        System.out.println("Tiempo transcurrido para calcular la moda : " + (System.nanoTime() - timeSpent));
    }

    void calcularPromedio(int fieldPos) {
        Long timeSpent = System.nanoTime();

        BigInteger[] numericalFields = getNumericalField(fieldPos);
        BigDecimal promedio;
        BigDecimal sumaTotal = new BigDecimal(numericalFields[0]);

        for (int i = 1; i < numericalFields.length; i++) {
            sumaTotal.add(new BigDecimal(numericalFields[i]));
        }

        promedio = sumaTotal.divide(new BigDecimal(archivo.getLines()));

        System.out.println("El promedio total del campo es " + promedio.toString());
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

    int getFieldSize(int pos) {
        String[] lines = archivo.readFile()[0].split(";");
        return lines[pos].length();
    }

}
