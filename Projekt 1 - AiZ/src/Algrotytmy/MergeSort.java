package Algrotytmy;

import Metryki.Metryki;

import java.util.Arrays;

public class MergeSort implements AlgrorytmSortowania {
    public String getName() { return "MergeSort"; }

    public void sort(int[] arr, Metryki metryki) {
        mergeSort(arr, metryki);
    }

    private void mergeSort(int[] arr, Metryki metryki) {
        if (arr.length < 2) return;

        int mid = arr.length / 2;
        int[] left = Arrays.copyOfRange(arr, 0, mid);
        int[] right = Arrays.copyOfRange(arr, mid, arr.length);

        metryki.pamiec += left.length + right.length;

        mergeSort(left, metryki);
        mergeSort(right, metryki);

        merge(arr, left, right, metryki);
    }

    private void merge(int[] arr, int[] left, int[] right, Metryki metryki) {
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            metryki.porownania++;
            if (left[i] <= right[j]) {
                arr[k++] = left[i++];
                metryki.zamiany++;
            } else {
                arr[k++] = right[j++];
                metryki.zamiany++;
            }
        }

        while (i < left.length) {
            arr[k++] = left[i++];
            metryki.zamiany++;
        }

        while (j < right.length) {
            arr[k++] = right[j++];
            metryki.zamiany++;
        }
    }
}
