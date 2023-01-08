package hr.fer.zemris.optjava.dz6.crossover;

import hr.fer.zemris.optjava.dz6.population.Instance;

import java.util.List;

public interface ICrossover {
    public List<Instance> crossover(Instance parent1, Instance parent2);
}
