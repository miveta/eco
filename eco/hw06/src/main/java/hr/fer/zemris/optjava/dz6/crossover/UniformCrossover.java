package hr.fer.zemris.optjava.dz6.crossover;

import hr.fer.zemris.optjava.dz6.population.Instance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UniformCrossover implements ICrossover{
    private double crossoverProbability = 0.3;
    private Random rand = new Random();

    @Override
    public List<Instance> crossover(Instance parent1, Instance parent2) {
        int numSolutionValues = parent1.getSolutionValues().length;
        int numObjectives = parent1.getObjectiveCount();

        double[] child1 = new double[numSolutionValues];
        double[] child2 = new double[numSolutionValues];

        for(int i = 0; i < numSolutionValues; i++){
            if(rand.nextDouble() < crossoverProbability){
                child1[i] = parent1.getSolutionValues()[i];
                child2[i] = parent2.getSolutionValues()[i];
            } else {
                child1[i] = parent2.getSolutionValues()[i];
                child2[i] = parent1.getSolutionValues()[i];
            }
        }

        List<Instance> children = new ArrayList<>();
        children.add(new Instance(child1, numObjectives));
        children.add(new Instance(child2, numObjectives));

        return children;
    }
}
