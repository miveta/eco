package hr.fer.zemris.optjava.dz6.selection;

import hr.fer.zemris.optjava.dz6.population.Instance;

import java.util.List;

public interface ISelection {
    Instance select(List<Instance> population);

    List<Instance> select(List<Instance> population, int n);
}
