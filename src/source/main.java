package source;

import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        int cantRegistros, cantCampos, longCampos, option = 0;
        boolean isRunning = true;

        Scanner scanner = new Scanner(System.in);

        Archivo archivo = new Archivo();

        Controller controller = new Controller(archivo);

        while (isRunning) {
            System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||");
            System.out.println("Bienvenido al Generador de sistemas de información, Qué desea hacer? ");
            System.out.println("(Digite el numero de la opcion deseada)");
            System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||");
            System.out.println("1) Generar registros");
            System.out.println("2) Buscar un registro");
            System.out.println("3) Calcular maximo y minimo ");
            System.out.println("4) Calcular moda y promedio");
            System.out.println("0) Terminar programa");

            if (option > 4) {

            }
            switch (option) {
                case 1:
                    if (archivo.createFile()) {
                        System.out.println("Indique la cantidad de registros deseados");
                        cantRegistros = scanner.nextInt();

                        System.out.println("Indique la cantidad de campos deseados");
                        cantCampos = scanner.nextInt();

                        System.out.println("Indique la longitud de los campos");
                        longCampos = scanner.nextInt();

                        controller.generateRecords(cantRegistros, cantCampos, longCampos);
                    } else {
                        System.out.println("Indique la cantidad de registros deseados");
                        cantRegistros = scanner.nextInt();
                        controller.generateRecords(cantRegistros, controller.getNumberOfFields(), controller.getFieldSize());
                    }
                    break;
                case 2:
                    System.out.println("Digite la opcion de busqueda");
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    isRunning = false;
                    break;
            }

        }

    }

}
