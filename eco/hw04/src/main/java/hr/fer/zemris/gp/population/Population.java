package hr.fer.zemris.gp.population;

import hr.fer.zemris.gp.population.node.Node;
import hr.fer.zemris.gp.population.node.NodeOperator;
import hr.fer.zemris.gp.population.node.NodeTerminal;
import hr.fer.zemris.gp.population.node.NodeValue;

import java.util.ArrayList;
import java.util.List;

public class Population {
    final static int MAX_DEPTH = 7;
    final static int MAX_NODES = 30;
    final static int POPULATION_SIZE = 100;
    final static int MAX_GENERATIONS = 1000;
    final static int TOURNAMENT_SIZE = 5;
    final static double MUTATION_PROBABILITY = 0.1;
    final static double CROSSOVER_PROBABILITY = 0.9;
    final static double ELITISM_PROBABILITY = 0.1;

    private List<NodeOperator> operators;
    private List<NodeTerminal> terminals;

    private List<Instance> instances;

    public Population(List<Instance> instances) {
        this.instances = instances;
    }

    public List<Instance> getInstances() {
        return instances;
    }

    public List<Instance> getRandomInstances(int count) {
        List<Instance> randomInstances = new ArrayList<>();

        for(int i = 0; i < count; i++) {
            randomInstances.add(instances.get((int) (Math.random() * instances.size())));
        }

        return randomInstances;
    }


    public void addInstance(Instance instance) {
        instances.add(instance);
    }

    public void removeInstance(Instance instance) {
        instances.remove(instance);
    }

    public void removeInstance(int index) {
        instances.remove(index);
    }

    public int getPopulationSize() {
        return instances.size();
    }
}
