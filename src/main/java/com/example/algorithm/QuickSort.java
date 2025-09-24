package com.example.algorithm;

import com.example.util.Metrics;
import com.example.util.SortUtils;

import java.util.Random;

public class QuickSort {
    private static final Random random = new Random();

    public void sort(int[] arr, Metrics metrics) {
        if (arr==null || arr.length<=1) {
            return;
        }
        metrics.setInputSize(arr.length);
        metrics.enterRecursion();
        quickSort(arr,0,arr.length-1,metrics);
        metrics.exitRecursion();
    }
    private void quickSort(int[] arr, int start, int end, Metrics metrics) {
        while (start < end) {
            // random pivot selecting
            int pivotIndex = start + random.nextInt(end - start + 1);
            SortUtils.swap(arr, pivotIndex, end);

            // partition around random pivot
            int p = SortUtils.partition(arr, start, end);
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
}
