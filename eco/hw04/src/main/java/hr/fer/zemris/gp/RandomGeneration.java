package hr.fer.zemris.gp;

import hr.fer.zemris.gp.population.RampedHalfAndHalf;
import hr.fer.zemris.gp.population.node.Node;
import hr.fer.zemris.gp.population.node.NodeOperator;
import hr.fer.zemris.gp.population.node.NodeTerminal;
import hr.fer.zemris.gp.population.node.NodeValue;

import java.util.List;

public class RandomGeneration {
    List<NodeOperator> operators;
    List<NodeTerminal> terminals;

    Double min = null;
    Double max = null;

    Double constProbabilty = null;

    public RandomGeneration(List<NodeOperator> operators, List<NodeTerminal> terminals) {
        this.operators = operators;
        this.terminals = terminals;
    }

    public RandomGeneration(List<NodeOperator> operators, List<NodeTerminal> terminals, Double min, Double max, Double constProbabilty) {
        this.operators = operators;
        this.terminals = terminals;
        this.min = min;
        this.max = max;
        this.constProbabilty = constProbabilty;
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
        double random = Math.random();
        if (constProbabilty != null && min != null && max != null && random < constProbabilty) {
            return new NodeTerminal(min, max);
        } else {
            return terminals.get((int) (Math.random() * terminals.size()));
        }
    }

    public NodeValue getRandomNodeValue() {
        return Math.random() < 0.5 ? getRandomOperator() : getRandomTerminal();
    }

}
