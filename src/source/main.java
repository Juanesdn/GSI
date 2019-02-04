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

                        System.out.println("Indique la longitud de los campos");
                        longCampos = scanner.nextInt();

                        controller.generateRecords(cantRegistros, cantCampos, longCampos);
                    } else {
                        // En caso de que exista, se toman la longitud de los
                        // campos anteriores y la cantidad de estos para generar
                        // de manera random un nuevo registro
                        System.out.println("Indique la cantidad de registros deseados");
                        cantRegistros = scanner.nextInt();
                        controller.generateRecords(cantRegistros, controller.getNumberOfFields(), controller.getFieldSize());
                    }
                    break;
                case 2:
                    if (archivo.exists()){
                        
                        boolean bigint = false, integer = false, string = false, results = false;
                        int fieldSize = controller.getFieldSize();

                        System.out.println("Digite el dato a buscar");
                        data = scanner.next();

                        // Se verifica por medio de un try si el dato puede
                        // transformarse en un bigInteger o en un int para poder
                        // diferencias las llaves de los datos
                        try {
                            Integer.valueOf(data);
                            integer = true;

                        } catch (Exception e) {

                            try {
                                BigInteger a = new BigInteger(data);
                                bigint = true;
                            } catch (Exception ex) {
                                string = true;
                            }

                        }

                        String[] lines = archivo.readFile();

                        int pos = 0;
                        Long timeSpent;
                        if (bigint) {

                            BigInteger[] keys = archivo.getKeys();
                            timeSpent = System.nanoTime();
                            
                            pos = controller.searchKey(keys, 0, keys.length - 1, new BigInteger(data));
                            
                            System.out.println("Tiempo transcurrido para encontrar la llave unica: " + (System.nanoTime() - timeSpent));
                            
                            if (pos != -1) 
                                controller.showResults(pos, lines);
                            else if (!results && pos == -1) 
                                System.out.println("El dato no fue encontrado");
                            

                        } else if (string || integer) {
                            // En caso de que el dato no sea de la longitud de 
                            // los campos, se retorna que el valor no fue encontrado
                            System.out.println(data.length());
                            System.out.println(fieldSize);
                            if (data.length() == fieldSize) {
                                System.out.println("!!!!");
                                timeSpent = System.nanoTime();
                                while (pos != -1) {
                                    
                                    pos = controller.searchString(lines, data);
                                    
                                    if (pos != -1) {
                                        controller.showResults(pos, lines);
                                        lines[pos] = "";
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

                                    System.out.println("Indique la cantidad de campos deseados");
                                    cantCampos = scanner.nextInt();

                                    System.out.println("Indique la longitud de los campos");
                                    longCampos = scanner.nextInt();
                                    controller.generateRecords(cantRegistros, cantCampos, longCampos);

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
                    controller.mostrarCamposNumericos();
                    int campoElegido = scanner.nextInt();
                    
                    controller.calcularValorMinimo(campoElegido);
                    controller.calcularValorMaximo(campoElegido);                 
                    break;
                case 4:
                    System.out.println("Digite el numero de la posición del campo a seleccionar");
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
