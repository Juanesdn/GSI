package source;

import java.math.BigInteger;

public class QuickSort {

    private BigInteger[] keys;
    private String[] lines;
    private int length;

    public void sort(BigInteger[] keys, String[] lines) {
        // si el array es nulo o esta vacio se retorna
        if (keys == null || keys.length == 0) {
            return;
        }
        this.keys = keys;
        this.lines = lines;
        length = keys.length;
        quicksort(0, length - 1);
    }

    private void quicksort(int low, int high) {
        int i = low, j = high;
        // Se obtiene el pivote de la mitad del array
        BigInteger pivot = keys[low + (high - low) / 2];

        // se dividen en dos listas
        while (i <= j) {
            // Si el valor de la izquierda es mas pequeño que el pivote
            // se obtiene el siguiente elemento de la lista izquierda
            while (keys[i].compareTo(pivot) == -1) {
                i++;
            }
            // Si el elemento a la derecha del pivote es mayor
            // se obtiene el elemento de la lista derecha
            while (keys[j].compareTo(pivot) == 1) {
                j--;
            }

            // Si se encuentra un valor en la lista izquierda mayor que el pivote
            // y si se encuentra un valor en la lista derecha menor que el pivote
            // se intercambian ambos valores
            if (i <= j) {
                exchange(i, j);
                i++;
                j--;
            }
        }
        // Se vuelve a llamar la funcion
        if (low < j) {
            quicksort(low, j);
        }
        if (i < high) {
            quicksort(i, high);
        }
    }

    private void exchange(int i, int j) {
        BigInteger temp = keys[i];
        String tempLine = lines[i];
        keys[i] = keys[j];
        keys[j] = temp;
        lines[i] = lines[j];
        lines[j] = tempLine;
    }
}
