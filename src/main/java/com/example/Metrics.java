package com.example;

public class Metrics {
    private long startTime;
    private long duration;
    private long allocations;


    public void startTime() {
        startTime = System.nanoTime();
    }

    public void stopTime() {
        duration = (System.nanoTime() - startTime)/1000000;
    }

    public long getDuration() {
        return duration;
    }

    public void addAllocation(long elements) {
        allocations += elements;
    }

    public long getAllocations() {
        return allocations;
    }

    public void reset() {
        startTime = 0;
        duration = 0;
        allocations = 0;
    }

    @Override
    public String toString() {
        return String.format("time_d=%d allocationElements =%d",duration,allocations);
    }
}
