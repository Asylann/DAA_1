package com.example.algorithm;

import com.example.util.Metrics;

import java.util.Random;

public class QuickSort {
    private static final Random random = new Random();

    public void sort(int[] arr, Metrics metrics) {
        if (arr==null || arr.length<=1) {
            return;
        }
        metrics.enterRecursion();
        quickSort(arr,0,arr.length-1,metrics);
        metrics.exitRecursion();
    }
    private void quickSort(int[] arr, int start, int end, Metrics metrics) {
        while (start < end) {
            // random pivot selecting
            int pivotIndex = start + random.nextInt(end - start + 1);
            swap(arr, pivotIndex, end);

            // partition around random pivot
            int p = partition(arr, start, end);
            if (p - start < end - p) {
                metrics.enterRecursion();
                quickSort(arr, start, p - 1, metrics);
                metrics.exitRecursion();
                start = p + 1;
            } else {
                metrics.enterRecursion();
                quickSort(arr, p + 1, end, metrics);
                metrics.exitRecursion();
                end = p - 1;
            }
        }
    }

    private int partition(int[] arr, int start, int end) {
        int pivot = arr[end];
        int i = start - 1;

        for (int j = start; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, end);
        return i + 1;
    }

    private void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}
