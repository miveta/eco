package hr.fer.zemris.gp.population;

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

    public RampedHalfAndHalf(List<NodeOperator> operators, List<NodeTerminal> terminals) {
        this.operators = operators;
        this.terminals = terminals;
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
            root.setLeft(generateRandomNode(full, depthLeft));
        } else {
            root.setLeft(generateRandomNode(full, depthLeft));
            root.setRight(generateRandomNode(full, depthLeft));
        }

        return new Instance(root);
    }

    public static Node generateRandomNode(boolean full, int depth, List<NodeOperator> operators, List<NodeTerminal> terminals){
        RampedHalfAndHalf rampedHalfAndHalf = new RampedHalfAndHalf(operators, terminals);
        return rampedHalfAndHalf.generateRandomNode(full, depth);
    }

    public Node generateRandomNode(boolean full, int depth){
        /*
         * If the length of the path from the root (depth) has reached max - pick a terminal
         * */
        if(depth == 0){
            NodeTerminal terminal = getRandomTerminal();
            return new Node(terminal);
        }

        NodeValue value;
        /*
         * If the method is not full (it's grow) - pick a node value from the union of operators and terminals
         * */
        if(!full){
            value = getRandomNodeValue(); // method == grow, pick a node value from the union of operators and terminals
        } else {
            value = getRandomOperator(); // method == full, pick an operator
        }

        Node node = new Node(value);
        if (value.isTerminal()) {
            return node;
        }

        NodeOperator operator = (NodeOperator) value;
        if (operator.isUnary()) {
            node.setLeft(generateRandomNode(full, depth - 1));
        } else {
            node.setLeft(generateRandomNode(full, depth - 1));
            node.setRight(generateRandomNode(full, depth - 1));
        }

        return node;
    }

    public NodeOperator getRandomOperator() {
        return operators.get((int) (Math.random() * operators.size()));
    }

    public NodeTerminal getRandomTerminal() {
        return terminals.get((int) (Math.random() * terminals.size()));
    }

    public NodeValue getRandomNodeValue() {
        return Math.random() < 0.5 ? getRandomOperator() : getRandomTerminal();
    }
}
