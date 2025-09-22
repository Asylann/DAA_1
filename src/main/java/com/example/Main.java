package com.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        CsvWriter csvWriter = new CsvWriter("result.csv");
        Metrics m = new Metrics();
        m.setInputSize(5);
        m.enterRecursion();
        m.exitRecursion();
        m.addAllocation(4);
        m.startTime();
        m.stopTime();
        csvWriter.writeRow("QuickSort",m);
        csvWriter.close();
    }
}
