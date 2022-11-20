package hr.fer.zemris.gp;

import hr.fer.zemris.gp.crossover.ICrossover;
import hr.fer.zemris.gp.fitness.IFitness;
import hr.fer.zemris.gp.mutation.IMutation;
import hr.fer.zemris.gp.population.Instance;
import hr.fer.zemris.gp.population.Population;
import hr.fer.zemris.gp.selection.ISelection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GeneticProgramming {
    Population population;
    int maxGenerations = 100;

    IFitness fitness;
    ICrossover crossover;
    IMutation mutation;
    ISelection selection;

    boolean elitism = true;

    public GeneticProgramming(Population population, IFitness fitness, ICrossover crossover, IMutation mutation, ISelection selection) {
        this.population = population;
        this.fitness = fitness;
        this.crossover = crossover;
        this.mutation = mutation;
        this.selection = selection;
    }


    public void evolve(boolean elitism, int maxGenerations, double mutationProbability, double crossoverProbability, double reproductionProbability) {
        double bestFitness = Double.MAX_VALUE;
        int lastFitnessChange = 0;

        for (int i = 0; i < maxGenerations; i++) {
            //System.out.println("Generation: " + i);

            List<Instance> newInstances = new ArrayList<>();

            if (elitism) {
                List<Instance> bestInstances = population.getBestInstances(1);
                for(Instance instance : bestInstances){
                    newInstances.add(instance.copy());
                }
            }

            while (newInstances.size() < population.getPopulationSize()) {
                double random = Math.random();
                if (random < mutationProbability) {
                    Instance instance = selection.select(population);
                    Instance mutated = mutation.mutate(instance);
                    newInstances.add(mutated);
                } else if (random < mutationProbability + crossoverProbability) {
                    Instance instance1 = selection.select(population);
                    Instance instance2 = selection.select(population);
                    List<Instance> children = crossover.crossover(instance1, instance2);
                    newInstances.addAll(children);
                } else {
                    Instance instance = selection.select(population);
                    newInstances.add(instance);
                }
            }

            for(Instance instance : newInstances) {
                fitness.calculateFitness(instance);
            }

            population = new Population(newInstances);

            Instance bestInstance = population.getBestInstances(1).get(0);
            if(bestInstance.getFitness() < bestFitness){
                bestFitness = bestInstance.getFitness();
                lastFitnessChange = i;

                System.out.println("Best instance: " + bestInstance);
                System.out.println("Fitness: " + bestFitness);
            }

            if(lastFitnessChange + 100 < i){
                System.out.println("Generation: " + i);
                System.out.println("Fitness hasn't changed for 100 generations, stopping evolution.");
                break;
            }
        }

    }

}
