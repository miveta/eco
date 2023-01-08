package hr.fer.zemris.optjava.dz6.mutation;

import hr.fer.zemris.optjava.dz6.population.Instance;

public interface IMutation {
    Instance mutate(Instance instance);
}
