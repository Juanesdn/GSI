package source;

import java.math.BigInteger;
import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        int cantRegistros, cantCampos, longCampos, option, op;
        boolean isRunning = true, chooseOp = true;;

        Scanner scanner = new Scanner(System.in);

        String data;

        Archivo archivo = new Archivo();

        Controller controller = new Controller(archivo);

        while (isRunning) {

            System.out.println("Digite el numero de la opcion deseada");
            System.out.println("1) Generar registros");
            System.out.println("2) Buscar un registro");
            System.out.println("3) Calcular maximo y minimo de un campo ");
            System.out.println("4) Calcular moda y promedio de un campo");
            System.out.println("5) Terminar programa");
            option = scanner.nextInt();

            if (option > 5) {
                System.out.println("Opción incorrecta, digite el número de la opción a elegir");
            }
            switch (option) {
                case 1:
                    if (!archivo.exists()) {
                        // Si el archivo no existe se crea
                        archivo.createFile();
                        System.out.println("Indique la cantidad de registros deseados");
                        cantRegistros = scanner.nextInt();

                        System.out.println("Indique la cantidad de campos deseados");
                        cantCampos = scanner.nextInt();

                        controller.generateRecords(cantRegistros, cantCampos);
                    } else {
                        // En caso de que exista, se toman la longitud de los
                        // campos anteriores y la cantidad de estos para generar
                        // de manera random un nuevo registro
                        System.out.println("Indique la cantidad de registros deseados");
                        cantRegistros = scanner.nextInt();
                        controller.generateRecords(cantRegistros, controller.getNumberOfFields());
                    }
                    break;
                case 2:
                    if (archivo.exists()) {
                        int pos = 0;

                        boolean results = false;

                        Long timeSpent;

                        String[] lines = archivo.readFile();

                        BigInteger bigData;

                        System.out.println("Digite la opcion por la cual quiere buscar: ");
                        System.out.println("1) Llave única");
                        System.out.println("2) Dato");
                        op = scanner.nextInt();

                        switch (op) {
                            case 1:
                                System.out.println("Digite la llave a buscar");
                                bigData = scanner.nextBigInteger();

                                BigInteger[] keys = archivo.getKeys();
                                timeSpent = System.nanoTime();

                                pos = controller.searchKey(keys, 0, keys.length - 1, bigData);

                                System.out.println("Tiempo transcurrido para encontrar la llave unica: " + (System.nanoTime() - timeSpent));

                                if (pos != -1) {
                                    controller.showResults(pos, lines);
                                } else if (!results && pos == -1) {
                                    System.out.println("El dato no fue encontrado");
                                }
                                break;
                            case 2:
                                System.out.println("Digite la posición del campo en el que se encuentra el dato (del 1 al " + (lines[0].split(";").length - 1) + ")");
                                int dataPos = scanner.nextInt();
                                String[] tempLines = lines[0].split(";");
                                if (dataPos > tempLines.length || dataPos < 0) {
                                    System.out.println("El dato debe pertenecer a un campo");
                                } else {
                                    int fieldSize = controller.getFieldSize(dataPos);
                                    System.out.println("Digite el dato a buscar");
                                    data = scanner.next();
                                    
                                    if (data.length() == fieldSize) {
                                        timeSpent = System.nanoTime();
                                        while (pos != -1) {
                                            pos = controller.searchString(lines, data, dataPos);
                                            if (pos != -1) {
                                                controller.showResults(pos, lines);
                                                lines[pos] = "!;!;!";
                                                results = true;
                                            } else if (!results && pos == -1) {
                                                System.out.println("El dato no fue encontrado");
                                            }
                                        }
                                        System.out.println("Tiempo transcurrido para encontras los datos: " + (System.nanoTime() - timeSpent));
                                    } else {
                                        System.out.println("El dato no fue encontrado");
                                    }
                                }
                                break;
                        }
                    } else {
                        while (chooseOp) {
                            // Si el archivo no existe se da la opcion de crearlo
                            System.out.println("El archivo no existe, desea crearlo? (1.Si / 2.No)");
                            op = scanner.nextInt();

                            switch (op) {
                                case 1:
                                    archivo.createFile();

                                    System.out.println("Indique la cantidad de registros deseados");
                                    cantRegistros = scanner.nextInt();

                                    controller.generateRecords(cantRegistros, controller.getNumberOfFields());

                                    chooseOp = false;
                                    break;
                                case 2:
                                    System.out.println("Retornando al menu");
                                    chooseOp = false;
                                    break;
                                default:
                                    System.out.println("Valor incorrecto");
                                    break;
                            }
                        }

                    }
                    break;
                case 3:
                    System.out.println("Digite el numero de la posición del campo a seleccionar");
                    System.out.println("Campos numericos:");
                    controller.mostrarCamposNumericos();
                    int campoElegido = scanner.nextInt();

                    controller.calcularValorMinimo(campoElegido);
                    controller.calcularValorMaximo(campoElegido);
                    break;
                case 4:
                    System.out.println("Digite el numero de la posición del campo a seleccionar");
                    System.out.println("Campos numericos:");
                    controller.mostrarCamposNumericos();
                    campoElegido = scanner.nextInt();

                    controller.calcularModa(campoElegido);
                    controller.calcularPromedio(campoElegido);
                    break;
                case 5:
                    System.out.println("Saliendo del sistema...");
                    isRunning = false;
                    break;
            }

        }

    }

}
