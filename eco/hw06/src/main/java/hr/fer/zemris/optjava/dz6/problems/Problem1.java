package hr.fer.zemris.optjava.dz6.problems;

import java.util.ArrayList;
import java.util.List;

public class Problem1 implements MOOPProblem {
    /*
    * minimize f1 = x_1 ** 2
    * minimize f2 = x_2 ** 2
    * minimize f3 = x_3 ** 2
    * minimize f4 = x_4 ** 2
    *
    * -5 <= x_i <= 5
    *
    * */
    @Override
    public int getNumberOfObjectives() {
        return 4;
    }

    @Override
    public int getSolutionSize() {
        return 4;
    }

    @Override
    public void evaluateSolution(double[] solution, double[] objectives) {
        objectives[0] = solution[0] * solution[0];
        objectives[1] = solution[1] * solution[1];
        objectives[2] = solution[2] * solution[2];
        objectives[3] = solution[3] * solution[3];
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

        for(int i = 0; i < getSolutionSize(); i++) {
            bounds.add(new double[]{-5, 5});
        }

        return bounds;
    }
}
