package hr.fer.zemris.gp.crossover;

import hr.fer.zemris.gp.population.Instance;

import java.util.List;

public interface ICrossover {
    public List<Instance> crossover(Instance parent1, Instance parent2);
}

