package source;

import java.util.Scanner;

/**
 *
 * @author Juanes
 */
public class main {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        int cantRegistros, cantCampos, longCampos;

        Scanner scanner = new Scanner(System.in);

        Archivo archivo = new Archivo();

        Controller controller = new Controller(archivo);

        if (archivo.createFile()) {
            System.out.println("Indique la cantidad de registros deseados");
            cantRegistros = scanner.nextInt();

            System.out.println("Indique la cantidad de campos deseados");
            cantCampos = scanner.nextInt();

            System.out.println("Indique la longitud de los campos");
            longCampos = scanner.nextInt();

            controller.generateRecords(cantRegistros, cantCampos, longCampos);
        }else {
            System.out.println("Indique la cantidad de registros deseados");
            cantRegistros = scanner.nextInt();
            controller.generateRecords(cantRegistros, controller.getNumberOfFields(), controller.getFieldSize());
        }

    }

}
