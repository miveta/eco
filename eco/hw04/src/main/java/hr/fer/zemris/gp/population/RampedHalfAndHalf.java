package hr.fer.zemris.gp.population;

import hr.fer.zemris.gp.RandomGeneration;
import hr.fer.zemris.gp.population.node.Node;
import hr.fer.zemris.gp.population.node.NodeOperator;
import hr.fer.zemris.gp.population.node.NodeTerminal;
import hr.fer.zemris.gp.population.node.NodeValue;

import java.util.ArrayList;
import java.util.List;

public class RampedHalfAndHalf {
    private List<NodeOperator> operators;
    private List<NodeTerminal> terminals;

    private int MAX_DEPTH = 6;


    private RandomGeneration randomGeneration;

    public RampedHalfAndHalf(List<NodeOperator> operators, List<NodeTerminal> terminals, Double min, Double max, Double constProbabilty) {
        this.operators = operators;
        this.terminals = terminals;
        this.randomGeneration = new RandomGeneration(operators, terminals, min, max, constProbabilty);
    }

    public RampedHalfAndHalf(List<NodeOperator> operators, List<NodeTerminal> terminals) {
        this.operators = operators;
        this.terminals = terminals;
        this.randomGeneration = new RandomGeneration(operators, terminals);
    }

    public List<Instance> generatePopulation(int populationSize) {
        List<Instance> population = new ArrayList<>();

        int differentDepths = MAX_DEPTH - 1;

        for(int depth = 2; depth <= MAX_DEPTH; depth++) {
            int populationDepthSize = populationSize / differentDepths;

            for(int i = 0; i < populationDepthSize / 2; i++) {
                Instance full = generateRandomInstance(true, depth);
                population.add(full);
                Instance grow = generateRandomInstance(false, depth);
                population.add(grow);
            }
        }


        return population;
    }

    public Instance generateRandomInstance(boolean full, int depth) {
        // Both methods full and grow require an operator at the root of the tree.
        NodeOperator rootOperator = operators.get((int) (Math.random() * operators.size()));
        Node root = new Node(rootOperator);

        int depthLeft = depth - 1;

        if(rootOperator.isUnary()) {
            root.setLeft(randomGeneration.generateRandomNode(full, depthLeft));
        } else {
            root.setLeft(randomGeneration.generateRandomNode(full, depthLeft));
            root.setRight(randomGeneration.generateRandomNode(full, depthLeft));
        }

        return new Instance(root);
    }


}
