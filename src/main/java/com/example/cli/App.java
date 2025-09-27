package com.example.cli;

import com.example.algorithm.ClosestPair;
import com.example.algorithm.DeterministicSelect;
import com.example.algorithm.MergeSort;
import com.example.algorithm.QuickSort;
import com.example.util.Metrics;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class App {
    private static final Random random = new Random();
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("You need to give at least 2 arguments");
            return;
        }
        String algoName = args[0].toLowerCase();
        int n = Integer.parseInt(args[1]);

        switch (algoName) {
            case "mergesort":
                runMergeSort(n);
                break;
            case "quicksort":
                runQuickSort(n);
                break;
            case "select":
                int k;
                if (args.length >= 3) {
                    k = Integer.parseInt(args[2]);
                }else {
                    System.out.println("Provide a third argument for that algo");
                    return;
                }
                runDeterministicSelect(n, k);
                break;
            case "closest":
                runClosestPair(n);
                break;
            default:
                System.err.println("Unknown command: " + algoName);
        }
    }

    private static void runMergeSort(int n) {
        int[] arr = random.ints(n, -1_000_000, 1_000_000).toArray();
        int[] copy = arr.clone();
        Arrays.sort(copy);

        Metrics metrics = new Metrics();
        MergeSort ms = new MergeSort();

        metrics.startTime();
        ms.sort(arr, metrics);
        metrics.stopTime();

        if (!IsSorted(arr)){
            System.out.println("Sorry this algo doesnt work");
        }

        System.out.println("Merge run (n=" + n + ")");
        System.out.printf("Time: %.3f ms\n",  metrics.getDuration()/ 1000000.0);
        System.out.println("Max recursion depth (metrics): " + metrics.getMaxDepth());
        System.out.println("Allocation: "+metrics.getAllocations());
    }

    private static void runQuickSort(int n) {
        int[] arr = random.ints(n, -1_000_000, 1_000_000).toArray();
        int[] copy = arr.clone();
        Arrays.sort(copy);

        Metrics metrics = new Metrics();
        QuickSort qs = new QuickSort();

        metrics.startTime();
        qs.sort(arr, metrics);
        metrics.stopTime();

        if (!IsSorted(arr)){
            System.out.println("Sorry this algo doesnt work");
        }

        System.out.println("Quick run (n=" + n + ")");
        System.out.printf("Time: %.3f ms\n", metrics.getDuration()/ 10000000.0);
        System.out.println("Max recursion depth (metrics): " + metrics.getMaxDepth());
        System.out.println("Allocation: "+metrics.getAllocations());
    }

    private static void runDeterministicSelect(int n, int kArg) {
        if (n <= 0) {
            System.err.println("n must be positive");
            return;
        }
        int[] arr = random.ints(n, -10_000, 10_000).toArray();
        int k = (kArg != 0) ? kArg : random.nextInt(n);
        if (k < 0 || k >= n) {
            System.err.println("k must be in range [0, n-1]");
            return;
        }

        int[] sorted = arr.clone();
        Arrays.sort(sorted);

        Metrics metrics = new Metrics();
        DeterministicSelect sel = new DeterministicSelect();

        long t0 = System.nanoTime();
        int result = sel.select(arr.clone(), k, metrics);
        long t1 = System.nanoTime();

        System.out.println("DeterministicSelect run (n=" + n + ", k=" + k + ")");
        System.out.println("Result: " + result + ", Expected: " + sorted[k]);
        System.out.println("Match: " + (result == sorted[k]));
        System.out.printf("Time: %.3f ms\n", (t1 - t0) / 1000000.0);
        System.out.println("Max recursion depth (metrics): " + metrics.getMaxDepth());
    }

    private static void runClosestPair(int n) {
        com.example.algorithm.Point[] pts = new com.example.algorithm.Point[n];
        for (int i = 0; i < n; i++) {
            pts[i] = new com.example.algorithm.Point(random.nextDouble() * 1e6, random.nextDouble() * 1e6);
        }

        Metrics metrics = new Metrics();
        long t0 = System.nanoTime();
        double fast = ClosestPair.find(pts, metrics);
        long t1 = System.nanoTime();

        System.out.println("ClosestPair run (n=" + n + ")");
        System.out.printf("Fast algorithm result: %.9f\n", fast);
        System.out.printf("Time (fast): %.3f ms\n", (t1 - t0) / 1000000.0);
        System.out.println("Max recursion depth (metrics): " + metrics.getMaxDepth());

        if (n <= 2000) {
            long b0 = System.nanoTime();
            double brute = bruteForce(pts);
            long b1 = System.nanoTime();
            System.out.printf("Brute-force result: %.9f\n", brute);
            System.out.printf("Time (brute): %.3f ms\n", (b1 - b0) / 1000000.0);
            System.out.println("Matches: " + (Math.abs(brute - fast) < 1e-9));
        } else {
            System.out.println("n > 2000; skipping brute-force validation (too slow)");
        }
    }

    private static double bruteForce(com.example.algorithm.Point[] pts) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < pts.length; i++) {
            for (int j = i + 1; j < pts.length; j++) {
                double dx = pts[i].x - pts[j].x;
                double dy = pts[i].y - pts[j].y;
                double d = Math.sqrt(dx * dx + dy * dy);
                if (d < min) min = d;
            }
        }
        return min;
    }

    private static boolean IsSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] > arr[i]) {
                return false;
            }
        }
        return true;
    }
}

