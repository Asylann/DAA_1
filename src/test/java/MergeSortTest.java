import com.example.CsvWriter;
import com.example.algorithm.MergeSort;
import com.example.util.Metrics;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MergeSortTest {
    @Test
    void MergeTest() throws IOException {
        int n = 100; // any number of length of array
        int[] arr = new int[n];
        Random rnd = new Random();

        for (int i= 0;i<n;i++) {
            arr[i] = 1+rnd.nextInt(100); // from 1 to 100
        }

        int[] copy = Arrays.copyOfRange(arr,0,arr.length);

        CsvWriter csvWriter = new CsvWriter("result.csv");
        Metrics m = new Metrics();

        MergeSort mergeSort = new MergeSort();
        m.startTime();
        mergeSort.sort(arr,m);
        m.stopTime();

        Arrays.sort(copy); // coping for checking

        boolean isCorrect = true;
        for (int i= 0;i<n;i++) {
            if (copy[i]!=arr[i]) {
                isCorrect = false;
            }
            System.out.printf("Mine:%d, Arrays.sort:%d\n",arr[i],copy[i]);
        }

        assertTrue(isCorrect);
        csvWriter.writeRow("MergeSort",m);
        csvWriter.close();
    }
}
