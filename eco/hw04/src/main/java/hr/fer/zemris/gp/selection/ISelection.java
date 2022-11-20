package hr.fer.zemris.gp.selection;

import hr.fer.zemris.gp.population.Instance;
import hr.fer.zemris.gp.population.Population;

public interface ISelection {
    Instance select(Population population);
}
