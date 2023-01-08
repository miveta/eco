package hr.fer.zemris.optjava.dz6.selection;

import hr.fer.zemris.optjava.dz6.population.Instance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GroupingTournamentSelection implements ISelection {
    Random rand = new Random();

    @Override
    public Instance select(List<Instance> population) {
        Instance instance1 = population.get(rand.nextInt(population.size()));
        Instance instance2 = population.get(rand.nextInt(population.size()));

        // smaller front number == better
        if (instance1.getFrontNumber() < instance2.getFrontNumber()) {
            return instance1;
        } else if (instance1.getFrontNumber() > instance2.getFrontNumber()) {
            return instance2;
        } else if (instance1.getCrowdingDistance() > instance2.getCrowdingDistance()) {
            return instance1;
        } else {
            return instance2;
        }

    }

    @Override
    public List<Instance> select(List<Instance> population, int n) {
        List<Instance> selection = new ArrayList<>();

        while (selection.size() < n) {
            Instance instance = select(population);
            if (!selection.contains(instance)) {
                selection.add(instance);
            }
        }

        return selection;
    }
}
