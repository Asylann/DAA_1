import com.example.algorithm.QuickSort;
import com.example.util.CsvWriter;
import com.example.util.Metrics;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuickSortTest {
    CsvWriter csvWriter = new CsvWriter("result.csv");
    Random random = new Random();

    public QuickSortTest() throws IOException {
    }

    @Test
    void quickSortCorrectness() {
        int[] arr = random.ints(1000, -1000, 1000).toArray();
        int[] copy = arr.clone();
        Arrays.sort(copy);

        Metrics metrics = new Metrics();
        QuickSort quickSort = new  QuickSort();

        metrics.startTime();
        quickSort.sort(arr, metrics);
        metrics.stopTime();

        csvWriter.writeRow("QuickSort",metrics);

        assertArrayEquals(copy, arr);
    }

    @Test
    void quickSortRecursionDepth() {
        int n = 10000;
        int[] arr = random.ints(n, -1_000_000, 1_000_000).toArray();
        Metrics metrics = new Metrics();
        QuickSort quickSort = new QuickSort();

        metrics.startTime();
        quickSort.sort(arr, metrics);
        metrics.stopTime();
        // depth should be O(log n), random pivot avoids n worst-case
        int expected = 2 * (int) (Math.log(n) / Math.log(2)) + 50;
        assertTrue(metrics.getMaxDepth() <= expected,
                "QuickSort recursion depth too high: " + metrics.getMaxDepth());

        csvWriter.writeRow("QuickSort",metrics);
        csvWriter.close();
    }
}
