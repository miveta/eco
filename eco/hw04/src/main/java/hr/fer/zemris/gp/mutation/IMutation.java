package hr.fer.zemris.gp.mutation;

import hr.fer.zemris.gp.population.Instance;

public interface IMutation {
    Instance mutate(Instance instance);
}
