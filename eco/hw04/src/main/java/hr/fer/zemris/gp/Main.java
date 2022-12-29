package hr.fer.zemris.gp;

import hr.fer.zemris.gp.crossover.ICrossover;
import hr.fer.zemris.gp.crossover.SubtreeSwitchCrossover;
import hr.fer.zemris.gp.fitness.IFitness;
import hr.fer.zemris.gp.fitness.MSEFitness;
import hr.fer.zemris.gp.mutation.IMutation;
import hr.fer.zemris.gp.mutation.RandomSubtreeMutation;
import hr.fer.zemris.gp.population.Instance;
import hr.fer.zemris.gp.population.Operators;
import hr.fer.zemris.gp.population.Population;
import hr.fer.zemris.gp.population.RampedHalfAndHalf;
import hr.fer.zemris.gp.population.node.NodeOperator;
import hr.fer.zemris.gp.population.node.NodeTerminal;
import hr.fer.zemris.gp.population.node.NodeVariable;
import hr.fer.zemris.gp.selection.ISelection;
import hr.fer.zemris.gp.selection.TournamentSelection;
import hr.fer.zemris.gp.tasks.AbstractFunction;
import hr.fer.zemris.gp.tasks.Function1;
import hr.fer.zemris.gp.tasks.Function2;
import hr.fer.zemris.gp.tasks.Function3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static List<NodeOperator> operators = List.of(new NodeOperator[]{Operators.ADD
            , Operators.SUB
            , Operators.MUL
            , Operators.DIV
            , Operators.SIN, Operators.COS
            , Operators.EXP, Operators.LOG
            });

    static ICrossover crossover = new SubtreeSwitchCrossover();
    static ISelection selection = new TournamentSelection();

    static int populationSize = 100;
    static int maxGenerations = 1000;
    static double crossoverProbability = 0.85;
    static double mutationProbability = 0.14;
    static double reproductionProbability = 0.01;

    static double constProbability = 0.1;
    static double min = -1.0;
    static double max = 1.0;

    public static void main(String[] args) throws IOException {
        Function1 function1 = new Function1();
        Function2 function2 = new Function2();
        Function3 function3 = new Function3();

        AbstractFunction function = function3;

        task(function, true);
    }


    public static void task(AbstractFunction function, boolean useConstants){
        IFitness mse = new MSEFitness(function.x, function.y);

        List<NodeTerminal> terminals = new ArrayList<>();
        for(int i = 0; i < function.numberOfVariables(); i++){
            terminals.add(new NodeVariable("x" + i));
        }


        RampedHalfAndHalf ramped = useConstants ? new RampedHalfAndHalf(operators, terminals, min, max, constProbability) : new RampedHalfAndHalf(operators, terminals);
        List<Instance> instances = ramped.generatePopulation(populationSize);
        for (Instance instance : instances) {
            mse.calculateFitness(instance);
        }

        Population population = new Population(instances);

        Instance correct = function.correct();
        System.out.println("Correct: " + correct);
        System.out.println("Fitness: " + mse.calculateFitness(correct));

        IMutation mutation = useConstants ? new RandomSubtreeMutation(operators, terminals, min, max, constProbability) : new RandomSubtreeMutation(operators, terminals);
        GeneticProgramming gp = new GeneticProgramming(population, mse, crossover, mutation, selection);
        gp.evolve(true, maxGenerations, mutationProbability, crossoverProbability, reproductionProbability);
    }


}
