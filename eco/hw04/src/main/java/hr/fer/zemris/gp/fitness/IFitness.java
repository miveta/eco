package hr.fer.zemris.gp.fitness;

import hr.fer.zemris.gp.population.Instance;

public interface IFitness {
    public double calculateFitness(Instance instance);
}
