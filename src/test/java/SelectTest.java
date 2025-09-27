import com.example.algorithm.DeterministicSelect;
import com.example.util.CsvWriter;
import com.example.util.Metrics;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SelectTest {
    CsvWriter csvWriter = new CsvWriter("result.csv");
    Random random = new Random();

    public SelectTest() throws IOException {
    }

    @Test
    void deterministicSelectMatchesSorted() {
        int[] arr = random.ints(200, -1000, 1000).toArray();
        int[] sorted = arr.clone();
        Arrays.sort(sorted);

        int k = random.nextInt(arr.length);
        Metrics metrics = new Metrics();
        metrics.startTime();
        int result = new DeterministicSelect().select(arr.clone(), k, metrics);
        metrics.stopTime();
        assertEquals(sorted[k], result);

        csvWriter.writeRow("DeterministicSelect",metrics);
    }

    @Test
    void deterministicSelectOnSortedArray() {
        int[] arr = new int[50];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i; // sorted [0, 1, 2, ..., 49]
        }

        for (int k = 0; k < arr.length; k += 10) { // test every 10th element
            Metrics metrics = new Metrics();
            int result = new DeterministicSelect().select(arr.clone(), k, metrics);
            assertEquals(k, result, "Expected element " + k + " at index " + k);
        }
    }
}
