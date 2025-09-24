package com.example.algorithm;

import com.example.util.Metrics;
import com.example.util.AlgoUtils;

import java.util.Arrays;

public class DeterministicSelect {

    public int select(int[] arr, int k, Metrics metrics) {
        if (arr == null || arr.length==0) {
            throw new IllegalArgumentException("Empty array not kept");
        }
        if (k < 0 || k >= arr.length) {
            throw new IllegalArgumentException("k is not in range of array");
        }

        metrics.setInputSize(arr.length);
        return deterministicSelect(arr, 0, arr.length - 1, k,metrics);
    }

    private static int deterministicSelect(int[] arr, int start, int end, int k,Metrics metrics) {
        metrics.enterRecursion();
        if (start == end) {
            metrics.exitRecursion();
            return arr[start];
        }

        int pivot = medianOfMedians(arr, start, end,metrics);

        // 3 way partition around pivot
        int lt = start;
        int i = start;
        int gt = end;

        while (i <= gt) {
            if (arr[i] < pivot) {
                AlgoUtils.swap(arr, lt++, i++);
            } else if (arr[i] > pivot) {
                AlgoUtils.swap(arr, i, gt--);
            } else {
                i++;
            }
        }

        int startBound = lt; // first index
        int endBound = gt; // last index

        // compare against bounds
        if (k < startBound) {
            int result = deterministicSelect(arr, start, startBound - 1, k, metrics);
            metrics.exitRecursion();
            return result;
        } else if (k <= endBound) {
            metrics.exitRecursion();
            return arr[k];
        } else {
            int result = deterministicSelect(arr, endBound + 1, end, k, metrics);
            metrics.exitRecursion();
            return result;
        }
    }

    private static int medianOfMedians(int[] arr, int start, int end,Metrics metrics) {
        metrics.enterRecursion();
        int n = end - start + 1;

        // sort that block and return its median
        if (n <= 5) {
            Arrays.sort(arr, start, end + 1);
            metrics.exitRecursion();
            return arr[start + n / 2];
        }

        // sort the group and move its median to front
        int medCount = 0;
        for (int i = start; i <= end; i += 5) {
            int subEnd = Math.min(i + 4, end);
            Arrays.sort(arr, i, subEnd + 1);
            int medianIdx = i + (subEnd - i) / 2;
            AlgoUtils.swap(arr, start + medCount, medianIdx);
            medCount++;
        }

        metrics.addAllocation(medCount);

        // find median of the medians stored recursively
        int result = medianOfMedians(arr, start, start + medCount - 1,metrics);
        metrics.exitRecursion();
        return result;
    }
}
