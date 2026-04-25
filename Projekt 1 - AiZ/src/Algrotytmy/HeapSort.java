package Algrotytmy;

import Metryki.Metryki;

public class HeapSort implements AlgrorytmSortowania {
    public String getName() { return "HeapSort"; }

    public void sort(int[] arr, Metryki metryki) {
        metryki.pamiec += arr.length;
        int n = arr.length;

        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i, metryki);

        for (int i = n - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            metryki.zamiany++;

            heapify(arr, i, 0, metryki);
        }
    }

    private void heapify(int[] arr, int n, int i, Metryki metryki) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < n) {
            metryki.porownania++;
            if (arr[l] > arr[largest]) largest = l;
        }

        if (r < n) {
            metryki.porownania++;
            if (arr[r] > arr[largest]) largest = r;
        }

        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            metryki.zamiany++;

            heapify(arr, n, largest, metryki);
        }
    }
}
