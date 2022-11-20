package hr.fer.zemris.gp.fitness;

import hr.fer.zemris.gp.fitness.IFitness;
import hr.fer.zemris.gp.population.Instance;

public class MSEFitness implements IFitness {
    private double[][] x;
    private double[] y;

    public MSEFitness(double[][] x, double[] y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double calculateFitness(Instance instance) {
        double sum = 0.0;
        for (int i = 0; i < x.length; i++) {
            for(int j = 0; j < x[i].length; j++) {
                instance.setVariable("x" + j, x[i][j]);
            }

            double yTrue = y[i];
            double eval = instance.evaluate();
            sum += Math.pow(yTrue - eval, 2);
        }


        double fitness = sum / x.length;
        instance.setFitness(fitness);

        return fitness;
    }
}

