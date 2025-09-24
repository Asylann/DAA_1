package com.example.algorithm;

import com.example.util.Metrics;
import com.example.util.AlgoUtils;

public class MergeSort {
    private static final int CUTOFF = 12;
    public void sort(int[] array, Metrics metrics) {
        // A new aux array for merging
        int[] aux = new int[array.length];
        metrics.addAllocation(array.length);

        metrics.setInputSize(array.length);
        // start the main algorithm
        mergeSort(array, aux, 0, array.length - 1, metrics);
    }

    private void mergeSort(int[] arr, int[] aux, int left, int right, Metrics metrics) {
        metrics.enterRecursion();

        if (left >= right) {
            metrics.exitRecursion();
            return;
        }

        // cutoff usage
        if (right - left + 1 <= CUTOFF) {
            AlgoUtils.insertionSort(arr, left, right);
            metrics.exitRecursion();
            return;
        }

        int mid = (left + right) / 2;
        mergeSort(arr, aux, left, mid, metrics);
        mergeSort(arr, aux, mid + 1, right, metrics);


        merge(arr, aux, left, mid, right, metrics);

        metrics.exitRecursion();
    }

    private void merge(int[] arr, int[] aux, int left, int mid, int right, Metrics metrics) {
        // copying
        for (int i = left; i <= right; i++) {
            aux[i] = arr[i];
        }


        int i = left, j = mid + 1, k = left; // Index of left sub array and right subarray and for merged array
        while (i <= mid && j <= right) {
            if (aux[i] <= aux[j]) arr[k++] = aux[i++];
            else arr[k++] = aux[j++];
        }
        while (i <= mid) {
            arr[k++] = aux[i++];
        }
        while (j <= right) {
            arr[k++] = aux[j++];
        }
    }
}
