package source;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Archivo {

    private String name;
    private File file;

    public Archivo() {
        this.name = "Registros.txt";
    }

    boolean createFile() {
        File file = new File(this.name);

        try {
            if (file.createNewFile()) {
                System.out.println("Archivo creado");
                return true;
            } else {
                System.out.println("El archivo ya existe (la cantidad de campos y su longitud serán iguales a los anteriores registros)");
                return false;
            }
        } catch (IOException ex) {
            System.out.println("El archivo no pudo ser creado");
        }
        return false;
    }

    void writeToFile(String[] lines) {
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

    String[] readFile(File file) {
        int lineCounter = 0, index = 0;
        String[] lines = new String[100];
        try {
            Scanner scanner = new Scanner(new File("Registros.txt"));
            while (scanner.hasNextLine()) {
                lineCounter++;
                scanner.nextLine();
            }
            scanner.close();
            lines = new String[lineCounter];
            
            scanner = new Scanner(new File("Registros.txt"));
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
