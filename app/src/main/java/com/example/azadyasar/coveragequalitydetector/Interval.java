package com.example.azadyasar.coveragequalitydetector;

/**
 * Created by azadyasar on 29/05/2016.
 */
public class Interval {

    private double latBeginning, latEnd;
    private double longBeginning, longEnd;

    public Interval(double latBeginning, double latEnd, double longBeginning, double longEnd) {
        this.latBeginning = latBeginning;
        this.latEnd = latEnd;
        this.longBeginning = longBeginning;
        this.longEnd = longEnd;
    }

    public double getLatBeginning() {
        return latBeginning;
    }

    public void setLatBeginning(double latBeginning) {
        this.latBeginning = latBeginning;
    }

    public double getLatEnd() {
        return latEnd;
    }

    public void setLatEnd(double latEnd) {
        this.latEnd = latEnd;
    }

    public double getLongBeginning() {
        return longBeginning;
    }

    public void setLongBeginning(double longBeginning) {
        this.longBeginning = longBeginning;
    }

    public double getLongEnd() {
        return longEnd;
    }

    public void setLongEnd(double longEnd) {
        this.longEnd = longEnd;
    }
}
