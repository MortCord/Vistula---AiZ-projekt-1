package Algrotytmy;

import Metryki.Metryki;

public class BubbleSort implements AlgrorytmSortowania {

    public String getName() {
        return "BubbleSort";
    }

    public void sort(int[] arr, Metryki metryki) {

        for (int i = 0; i < arr.length - 1; i++) {

            boolean swapped = false;

            for (int j = 0; j < arr.length - i - 1; j++) {

                metryki.porownania++;

                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;

                    metryki.zamiany++;
                    swapped = true;
                }

                metryki.pamiec++;
            }

            if (!swapped) {
                break;
            }
        }
    }
}