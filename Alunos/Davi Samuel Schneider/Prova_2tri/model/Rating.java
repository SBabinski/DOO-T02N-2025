package model;

public class Rating {
    private double average;

    public double getAverage() {
        return average;
    }

    @Override
    public String toString() {
        return "" + average;
    }
}
