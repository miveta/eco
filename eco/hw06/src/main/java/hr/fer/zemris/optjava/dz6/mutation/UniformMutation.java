package hr.fer.zemris.optjava.dz6.mutation;

import hr.fer.zemris.optjava.dz6.population.Instance;

import java.util.List;

public class UniformMutation implements IMutation {
    private double mutationProbability = 0.15;

    private List<double[]> bounds;

    public UniformMutation(List<double[]> bounds) {
        this.bounds = bounds;
    }

    @Override
    public Instance mutate(Instance instance) {
        int numSolutionValues = instance.getSolutionValues().length;

        for (int i = 0; i < numSolutionValues; i++) {
            if (Math.random() < mutationProbability) {
                double value = bounds.get(i)[0] + Math.random() * (bounds.get(i)[1] - bounds.get(i)[0]);
                instance.setSolutionValue(i, value);
            }
        }

        return instance;
    }
}
