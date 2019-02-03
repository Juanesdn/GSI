package source;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Archivo {

    private String name;
    private File file;

    public Archivo() {
        this.name = "Registros.txt";
        this.file = new File(this.name);
    }

    void createFile() {
        /**
         * Crea el archivo.
         */
        try {
            if (file.createNewFile()) {
                System.out.println("Archivo creado");
            } else {
                System.out.println("El archivo ya existe (la cantidad de campos y su longitud serán iguales a los anteriores registros)");
            }
        } catch (IOException ex) {
            System.out.println("El archivo no pudo ser creado");
        }
    }

    void writeToFile(String[] lines) {
        /**
         * Escribe en el archivo.
         */
        Long timeSpent = System.nanoTime();

        try{            
        FileOutputStream fos = new FileOutputStream(new File("Registros.txt"));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        
            try {
                for (String line : lines) {
                    bw.write(line);
                    bw.newLine();
                }
                bw.close();
            } catch (Exception e) {
                System.out.println("Hubo un error escribiendo en el archivo");
            }
        } catch (IOException ex){
            System.out.println("Hubo un error encontrando el archivo");
        }
        
        System.out.println("Tiempo transcurrido para escribir registros en el archivo: " + (System.nanoTime() - timeSpent));
    }

    String[] readFile() {
        /**
         * Lee el archivo y retorna un vector con las lineas de este.
         */
        int index = 0;
        String[] lines = new String[getLines()];
        try {
            Scanner scanner = new Scanner(file);
            
            while (scanner.hasNextLine()) {
                lines[index] = scanner.nextLine();
                index++;
            }
            
            scanner.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lines;
    }
    
    int getLines() {
        /**
         * Retorna la cantidad de lineas del archivo.
         */
        int lineCounter = 0;
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                lineCounter++;
                scanner.nextLine();
            }
            scanner.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lineCounter;
    }
    
    BigInteger[] getKeys() {
        /**
         * Retorna un arreglo con las llaves unicas.
         */
        int n = getLines();
        
        BigInteger[] keys = new BigInteger[n];
        
        String[] lines = readFile();
        
        for (int i = 0; i < n; i++) {
            keys[i] = new BigInteger(lines[i].split(";")[0]);
        }
        
        return keys;
    }
    
    boolean exists(){
        return file.exists();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
