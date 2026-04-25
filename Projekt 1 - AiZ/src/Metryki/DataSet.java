package Metryki;

import java.util.Random;

public class DataSet {
    public static int[] random(int n) {
        Random r = new Random();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = r.nextInt(100000);
        return arr;
    }

    public static int[] ascending(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = i;
        return arr;
    }

    public static int[] descending(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = n - i;
        return arr;
    }

    public static int[] partiallySorted(int n) {
        int[] arr = ascending(n);
        Random r = new Random();
        for (int i = 0; i < n / 10; i++) {
            int a = r.nextInt(n);
            int b = r.nextInt(n);
            int tmp = arr[a];
            arr[a] = arr[b];
            arr[b] = tmp;
        }
        return arr;
    }
}
