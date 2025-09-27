package com.example.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CsvWriter {
    private final PrintWriter printWriter;

    public CsvWriter(String fileName) throws IOException {
        this.printWriter = new PrintWriter(new FileWriter(fileName,true));
    }

    public void writeRow(String algorithm, Metrics metrics) {
        printWriter.printf("%s;%d;%.6f;%d;%d%n",
                algorithm,
                metrics.getInputSize(),
                metrics.getDuration() / 1_000_000.0, // ms
                metrics.getAllocations(),
                metrics.getMaxDepth());
        printWriter.flush();
    }

    public void close() {
        printWriter.close();
    }
}
