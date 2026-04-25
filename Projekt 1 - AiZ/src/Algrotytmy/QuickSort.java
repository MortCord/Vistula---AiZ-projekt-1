package Algrotytmy;

import Metryki.Metryki;

import java.util.Random;

public class QuickSort implements AlgrorytmSortowania {
    public String getName() { return "QuickSort"; }

    public void sort(int[] arr, Metryki metryki) {
        quick(arr, 0, arr.length - 1, metryki);
    }

    private void quick(int[] arr, int low, int high, Metryki metryki) {
        if (low < high) {
            int pi = partition(arr, low, high, metryki);
            quick(arr, low, pi - 1, metryki);
            quick(arr, pi + 1, high, metryki);
            metryki.pamiec++; // symulacja stosu rekurencjyjnie
        }
    }

    private int partition(int[] arr, int low, int high, Metryki metryki) {
        Random rand = new Random();
        int pivotIndex = low + rand.nextInt(high - low + 1);

        int tempPivot = arr[pivotIndex];
        arr[pivotIndex] = arr[high];
        arr[high] = tempPivot;

        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            metryki.porownania++;
            if (arr[j] < pivot) {
                i++;
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
                metryki.zamiany++;
            }
        }

        int tmp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = tmp;
        metryki.zamiany++;

        return i + 1;
    }
}
