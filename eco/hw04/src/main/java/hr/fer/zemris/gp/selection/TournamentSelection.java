package hr.fer.zemris.gp.selection;

import hr.fer.zemris.gp.population.Instance;
import hr.fer.zemris.gp.population.Population;

import java.util.ArrayList;
import java.util.List;

public class TournamentSelection implements ISelection {
    int tournamentSize = 7;


    @Override
    public Instance select(Population population) {
        Instance best = null;
        List<Instance> tournament = population.getRandomInstances(tournamentSize);

        for (Instance instance : tournament) {
            if (best == null || instance.getFitness() < best.getFitness()) {
                best = instance;
            }
        }

        return best;
    }

}
