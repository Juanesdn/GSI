package source;

import java.math.BigInteger;
import java.util.Random;

public class Controller {

    private Archivo archivo;

    public Controller(Archivo archivo) {
        this.archivo = archivo;
    }

    void generateRecords(int cantRegistros, int cantCampos, int longCampos) {
        String line;

        String[] records = new String[cantRegistros];
        String[] pastRecords = archivo.readFile(archivo.getFile());

        BigInteger[] keys = new BigInteger[cantRegistros];

        QuickSort sorter = new QuickSort();

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

        Random rnd = new Random();

        int longitud = (int) Math.pow(10, longCampo - 1);
        int integer = rnd.nextInt(longitud * 9) + longitud;

        return integer;
    }

    String generateRandomCharacters(int longCampo) {
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
        boolean Unique = false;

        Random rnd = new Random();

        BigInteger key;
        BigInteger lowerLimit = new BigInteger("99999999999999999999999");
        BigInteger upperLimit = new BigInteger("999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999");
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

            if (isUnique(key, archivo.readFile(archivo.getFile()))) {
                Unique = true;
                return key;
            }
        }

        return BigInteger.ONE;
    }

    boolean isUnique(BigInteger key, String[] lines) {
        for (String line : lines) {
            String[] temp = line.split(";");
            if (key.compareTo(new BigInteger(temp[0])) == 0) {
                return false;
            }
        }
        return true;
    }

    int getNumberOfFields() {
        String records = archivo.readFile(archivo.getFile())[0];
        String[] splitRecords = records.split(";");
        return splitRecords.length - 1;
    }

    int getFieldSize() {
        String records = archivo.readFile(archivo.getFile())[0];
        String[] splitRecords = records.split(";");
        return splitRecords[1].length();
    }

}
