package com.example;

public class Metrics {
    private long startTime;
    private long duration;
    private long allocations;
    private int inputSize;

    private int currentDepth;
    private int maxDepth;



    public void startTime() {
        startTime = System.nanoTime();
    }

    public void stopTime() {
        duration = System.nanoTime() - startTime;
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

    public void enterRecursion() {
        this.currentDepth++;
        if (this.currentDepth>this.maxDepth) {
            maxDepth = currentDepth;
        }
    }

    public int getMaxDepth() {
        return this.maxDepth;
    }

    public void exitRecursion() {
        this.currentDepth--;
    }

    public void setInputSize(int size) {
        this.inputSize = size;
    }

    public int getInputSize() {
        return this.inputSize;
    }

    public void reset() {
        startTime = 0;
        duration = 0;
        allocations = 0;
        currentDepth = 0;
        maxDepth = 0;
        inputSize = 0;
    }

    @Override
    public String toString() {
        return String.format("time_d=%d allocationElements =%d inputSize=%d maxDepth=%d",duration,allocations,inputSize,maxDepth);
    }
}
