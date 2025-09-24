import com.example.CsvWriter;
import com.example.algorithm.DeterministicSelect;
import com.example.util.Metrics;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SelectTest {
    @Test
    void DeterministicTest() throws IOException {
        int n = 100;
        int[] arr = new int[n];
        Random rnd = new Random();

        for (int i= 0;i<n;i++) {
            arr[i] = rnd.nextInt(100)+1;
        }


        CsvWriter csvWriter = new CsvWriter("result.csv");
        Metrics m = new Metrics();

        int k = 7;
        DeterministicSelect deterministicSelect = new DeterministicSelect();
        m.startTime();
        int result = deterministicSelect.select(arr,k,m);
        m.stopTime();
        Arrays.sort(arr);
        int expect = arr[k];

        assertEquals(expect,result);
        System.out.println(result);
        csvWriter.writeRow("DeterministicTest",m);
        csvWriter.close();
    }
}
