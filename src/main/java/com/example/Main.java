package com.example;

import com.example.algorithm.QuickSort;
import com.example.util.Metrics;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws IOException {
        int n = 100;
        int[] arr = new int[n];
        Random rnd = new Random();

        for (int i= 0;i<n;i++) {
            arr[i] = rnd.nextInt(100)+1;
        }
        CsvWriter csvWriter = new CsvWriter("result.csv");
        Metrics m = new Metrics();
        m.setInputSize(n);
        QuickSort quickSort= new QuickSort();
        m.startTime();
        quickSort.sort(arr,m);
        m.stopTime();
        Arrays.sort(arr);
        for (int i= 0;i<n;i++) {
            System.out.println(arr[i]);
        }
        csvWriter.writeRow("QuickSort",m);
        csvWriter.close();
    }
}
