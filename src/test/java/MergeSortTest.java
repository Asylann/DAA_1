import com.example.algorithm.MergeSort;
import com.example.util.CsvWriter;
import com.example.util.Metrics;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MergeSortTest {
    CsvWriter csvWriter = new CsvWriter("result.csv");
    Random random = new Random();

    public MergeSortTest() throws IOException {
    }

    @Test
    void mergeSortCorrectness() {
        int[] arr = random.ints(2000, -5000, 5000).toArray();
        int[] copy = arr.clone();
        Arrays.sort(copy);

        Metrics metrics = new Metrics();
        MergeSort mergeSort = new  MergeSort();

        metrics.startTime();
        mergeSort.sort(arr, metrics);
        metrics.stopTime();
        csvWriter.writeRow("MergeSort",metrics);

        assertArrayEquals(copy, arr);
    }

    @Test
    void mergeSortSmallCutoff() {
        int[] arr = {5, 3, 1, 4, 2};
        int[] copy = arr.clone();
        Arrays.sort(copy);

        Metrics metrics = new Metrics();
        MergeSort mergeSort = new  MergeSort();

        metrics.startTime();
        mergeSort.sort(arr, metrics);
        metrics.stopTime();

        csvWriter.writeRow("MergeSort",metrics);
        csvWriter.close();

        assertArrayEquals(copy, arr);
    }
}
