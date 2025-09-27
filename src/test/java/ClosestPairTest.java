import com.example.algorithm.ClosestPair;
import com.example.algorithm.Point;
import com.example.util.CsvWriter;
import com.example.util.Metrics;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClosestPairTest {
    CsvWriter csvWriter = new CsvWriter("result.csv");
    Random random = new Random();

    public ClosestPairTest() throws IOException {}

    @Test
    void closestPairMatchesBruteForce() {
        Point[] points = new Point[100];
        for (int i = 0; i < 100; i++) {
            points[i] = new Point(random.nextDouble() * 100, random.nextDouble() * 100);
        }

        double brute = bruteForce(points);
        Metrics metrics = new Metrics();
        metrics.startTime();
        double fast = ClosestPair.find(points, metrics);
        metrics.stopTime();

        csvWriter.writeRow("ClosestPair",metrics);
        csvWriter.close();
        assertEquals(brute, fast, 1e-9, "Mismatch for n=" + 100);
    }

    @Test
    void closestPairLargeRunsFast() {
        int n = 5000;
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(random.nextDouble() * 1e6, random.nextDouble() * 1e6);
        }

        // Just ensure it runs and returns finite result
        Metrics metrics = new Metrics();
        metrics.startTime();
        double fast = ClosestPair.find(points, metrics);
        metrics.stopTime();

        csvWriter.writeRow("ClosestPair",metrics);

        csvWriter.close();
        assertTrue(fast >= 0 && Double.isFinite(fast));
    }

    // helper for brute force check
    private double bruteForce(Point[] pts) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < pts.length; i++) {
            for (int j = i + 1; j < pts.length; j++) {
                double dx = pts[i].x - pts[j].x;
                double dy = pts[i].y - pts[j].y;
                min = Math.min(min, Math.sqrt(dx * dx + dy * dy));
            }
        }
        return min;
    }
}
