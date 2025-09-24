package com.example.algorithm;

import com.example.util.Metrics;
import com.example.util.AlgoUtils;

import java.util.Arrays;
import java.util.Comparator;

public class ClosestPair {

    public static double find(Point[] points, Metrics metrics) {
        if (points == null || points.length < 2) {
            throw new IllegalArgumentException("Need at least 2 points");
        }

        metrics.setInputSize(points.length);

        // Sort by x initially
        Point[] pointsByX = points.clone();
        Arrays.sort(pointsByX, Comparator.comparingDouble(p -> p.x));

        // Sort by y initially
        Point[] pointsByY = points.clone();
        Arrays.sort(pointsByY, Comparator.comparingDouble(p -> p.y));

        metrics.addAllocation(points.length);
        metrics.addAllocation(points.length);

        return closest(pointsByX, pointsByY, 0, points.length - 1, metrics);
    }

    private static double closest(Point[] pointsByX, Point[] pointsByY, int start, int end, Metrics metrics) {
        metrics.enterRecursion();

        int n = end - start + 1;
        if (n <= 3) {
            double min = bruteForce(pointsByX, start, end);
            metrics.exitRecursion();
            return min;
        }

        int mid = (start + end) / 2;
        double midX = pointsByX[mid].x;

        // Split Y array into two halves
        Point[] startY = new Point[n];
        Point[] endY = new Point[n];

        metrics.addAllocation(n);
        metrics.addAllocation(n);

        int li = 0, ri = 0;
        for (Point p : pointsByY) {
            if (p.x <= midX) {
                startY[li++] = p;
            } else {
                endY[ri++] = p;
            }
        }

        metrics.addAllocation(li);
        metrics.addAllocation(ri);

        // Recurse on both halves
        double dStart = closest(pointsByX, Arrays.copyOfRange(startY, 0, li), start, mid, metrics);
        double dEnd = closest(pointsByX, Arrays.copyOfRange(endY, 0, ri), mid + 1, end, metrics);
        double d = Math.min(dStart, dEnd);

        Point[] strip = new Point[n];
        metrics.addAllocation(n);
        int si = 0;
        for (Point p : pointsByY) {
            if (Math.abs(p.x - midX) < d) {
                strip[si++] = p;
            }
        }

        double dStrip = stripClosest(strip, si, d);
        metrics.exitRecursion();
        return Math.min(d, dStrip);
    }

    private static double bruteForce(Point[] points, int start, int end) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = start; i <= end; i++) {
            for (int j = i + 1; j <= end; j++) {
                double dist = AlgoUtils.distance(points[i], points[j]);
                min = Math.min(min, dist);
            }
        }
        return min;
    }

    private static double stripClosest(Point[] strip, int size, double d) {
        double min = d;
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size && (strip[j].y - strip[i].y) < min; j++) {
                double dist = AlgoUtils.distance(strip[i], strip[j]);
                if (dist<min) {
                    min = dist;
                }
            }
        }
        return min;
    }
}

