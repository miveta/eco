package hr.fer.zemris.optjava.dz6.population;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Instance {
    private double[] solutionValues;
    private double[] objectiveValues;
    private double crowdingDistance;

    private int frontNumber;

    private int objectiveCount;


    public Instance(int solutionSize, int objectiveCount) {
        this.solutionValues = new double[solutionSize];
        this.objectiveValues = new double[objectiveCount];

        this.objectiveCount = objectiveCount;
    }

    public Instance(double[] solutionValues, int objectiveCount) {
        this.solutionValues = solutionValues;
        this.objectiveValues = new double[objectiveCount];
        this.objectiveCount = objectiveCount;
    }

    public boolean dominates(Instance other) {
        boolean oneBetter = false;

        for (int i = 0; i < objectiveCount; i++) {
            if (objectiveValues[i] < other.objectiveValues[i]) {
                oneBetter = true;
            } else if (objectiveValues[i] > other.objectiveValues[i]) {
                return false;
            }
        }
        return oneBetter;
    }


    public void setFrontNumber(int frontIndex) {
        this.frontNumber = frontIndex;
    }

    public double getCrowdingDistance() {
        return crowdingDistance;
    }

    public void setObjectiveValues(double[] objectiveValues) {
        this.objectiveValues = Arrays.copyOf(objectiveValues, objectiveValues.length);
    }

    public void setSolutionValues(double[] solutionValues) {
        this.solutionValues = Arrays.copyOf(solutionValues, solutionValues.length);
    }

    public void setCrowdingDistance(double crowdingDistance) {
        this.crowdingDistance = crowdingDistance;
    }

    public double[] getSolutionValues() {
        return solutionValues;
    }

    public double[] getObjectiveValues() {
        return objectiveValues;
    }

    public int getFrontNumber() {
        return frontNumber;
    }

    public int getObjectiveCount() {
        return objectiveCount;
    }

    public double getObjectiveValue(int index) {
        return objectiveValues[index];
    }

    public double getSolutionValue(int index) {
        return solutionValues[index];
    }

    public void setSolutionValue(int index, double value) {
        solutionValues[index] = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Solution: ");
        for (double solutionValue : solutionValues) {
            sb.append(solutionValue).append(" ");
        }
        sb.append("Objective: ");
        for (double objectiveValue : objectiveValues) {
            sb.append(objectiveValue).append(" ");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Instance)) return false;

        Instance other = (Instance) obj;

        if (other.solutionValues.length != solutionValues.length) return false;
        if (other.objectiveValues.length != objectiveValues.length) return false;

        for (int i = 0; i < solutionValues.length; i++) {
            if (solutionValues[i] != other.solutionValues[i]) return false;
        }

        return true;
    }
}
