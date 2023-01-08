package hr.fer.zemris.optjava.dz6.problems;

import java.util.List;

public interface MOOPProblem {
    int getNumberOfObjectives();

    int getSolutionSize();

    void evaluateSolution(double[] solution, double[] objectives);

    double[] evaluateSolution(double[] solution);

    List<double[]> getBounds();
}
