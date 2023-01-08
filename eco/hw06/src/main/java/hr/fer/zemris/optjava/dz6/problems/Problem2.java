package hr.fer.zemris.optjava.dz6.problems;

import java.util.ArrayList;
import java.util.List;

public class Problem2 implements MOOPProblem {
    /*
     * minimize f1 = x_1
     * minimize f2 = (1 + x_2) / x_1
     *
     * 0.1 <= x_1 <= 1
     * 0 <= x_2 <= 5
     * */
    @Override
    public int getNumberOfObjectives() {
        return 2;
    }

    @Override
    public int getSolutionSize() {
        return 2;
    }

    @Override
    public void evaluateSolution(double[] solution, double[] objectives) {
        objectives[0] = solution[0];
        objectives[1] = (1 + solution[1]) / solution[0];
    }

    @Override
    public double[] evaluateSolution(double[] solution) {
        double[] objectives = new double[getNumberOfObjectives()];
        evaluateSolution(solution, objectives);
        return objectives;
    }

    @Override
    public List<double[]> getBounds() {
        List<double[]> bounds = new ArrayList<>();

        bounds.add(new double[]{0.1, 1});
        bounds.add(new double[]{0, 5});

        return bounds;
    }
}
