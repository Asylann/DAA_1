import com.example.CsvWriter;
import com.example.algorithm.ClosestPair;
import com.example.algorithm.Point;
import com.example.util.Metrics;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClosestPairTest {
    @Test
    void ClosestTest() throws IOException {
        int n = 100;
        Point[] pts = new Point[n];
        Random rnd = new Random();

        for (int i = 0; i < n; i++) {
            double x = rnd.nextDouble() * 100.0;
            double y = rnd.nextDouble() * 100.0;
            pts[i] = new Point(x, y);
        }

        CsvWriter csvWriter = new CsvWriter("result.csv");
        Metrics m = new Metrics();

        m.startTime();
        double found = ClosestPair.find(pts, m);
        m.stopTime();

        double expect = bruteForce(pts);

        double eps = 1e-6;
        System.out.printf("ClosestPair: %.8f, BruteForce: %.8f\n", found, expect);
        assertEquals(expect, found, eps, "ClosestPair result must equal brute-force result within epsilon");

        csvWriter.writeRow("ClosestPair", m);
        csvWriter.close();
    }

    // for testing with brute force finding
    private double bruteForce(Point[] pts) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < pts.length; i++) {
            for (int j = i + 1; j < pts.length; j++) {
                double dx = pts[i].x - pts[j].x;
                double dy = pts[i].y - pts[j].y;
                double dist = Math.hypot(dx, dy);
                if (dist < min) min = dist;
            }
        }
        return min;
    }
}
