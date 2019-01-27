package source;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.Random;

public class Controller {

    private Archivo archivo;

    public Controller(Archivo archivo) {
        this.archivo = archivo;
    }
    
    void generateRecords(int cantRegistros, int cantCampos, int longCampos){
        
        LinkedList<String> records = new LinkedList();
        LinkedList<String> pastRecords = archivo.readFile(archivo.getFile()); // Registros anteriores del archivo
        String tempString;
        
        // Añade los registros anteriores al nuevo archivo
        for (String line : pastRecords) {
            records.add(line);
        }
        
        for (int i = 0; i < cantRegistros; i++) {
            tempString = generateKey().toString(); // Genera una llave unica (BigInteger)
            
            for (int j = 0; j < cantCampos; j++) {
                // A los numeros pares les asigna un campo numerico y alos impares uno alfabético
                if (j % 2 == 0){
                    tempString = tempString + ";" + generateRandomInteger(longCampos);
                }else{
                    tempString = tempString + ";" + generateRandomCharacters(longCampos);
                }
            }
            
            records.add(tempString);
        }
        archivo.writeToFile(records); // Escribe los registros en el archivo
    }
    
    int generateRandomInteger(int longCampo){

        Random rnd = new Random();
        
        int longitud = (int) Math.pow(10, longCampo-1);
        int integer = rnd.nextInt(longitud*9) + longitud;
        
        return integer;
    }
    
    String generateRandomCharacters(int longCampo){
        String characters = "";
        int rnd;
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int cont = 0;
        
        while(longCampo > cont){
            
            rnd = (int) (Math.random()*25);
            
            characters = characters + alphabet.charAt(rnd);
            cont++;
        }

        return characters;
    }

    BigInteger generateKey() {
        boolean Unique = false;

        BigInteger key;
        Random rnd = new Random();
        BigInteger upperLimit = new BigInteger("999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999");

        while (Unique == false) {
            key = new BigInteger(upperLimit.bitLength(), rnd);

            if (isUnique(key,archivo.readFile(archivo.getFile()))) {
                Unique = true;
                return key;
            }
        }

        return BigInteger.ONE;
    }

    boolean isUnique(BigInteger key, LinkedList<String> lines) {
        for (String line : lines) {
            String temp = key.toString();
            if(line.contains(temp)){
                return false;
            }
        }
        return true;
    }
    
    int getNumberOfFields(){
        String records = archivo.readFile(archivo.getFile()).getFirst();
        String[] splitRecords = records.split(";");
        return splitRecords.length - 1;
    }
    
    int getFieldSize(){
        String records = archivo.readFile(archivo.getFile()).getFirst();
        String[] splitRecords = records.split(";");
        return splitRecords[1].length();
    }

}
