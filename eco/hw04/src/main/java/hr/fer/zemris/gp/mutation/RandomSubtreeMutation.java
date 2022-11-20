package hr.fer.zemris.gp.mutation;

import hr.fer.zemris.gp.population.Instance;
import hr.fer.zemris.gp.population.RampedHalfAndHalf;
import hr.fer.zemris.gp.population.node.Node;
import hr.fer.zemris.gp.population.node.NodeOperator;
import hr.fer.zemris.gp.population.node.NodeTerminal;

import java.util.List;

public class RandomSubtreeMutation implements IMutation {
    List<NodeTerminal> terminals;
    List<NodeOperator> operators;

    public RandomSubtreeMutation(List<NodeTerminal> terminals, List<NodeOperator> operators) {
        this.terminals = terminals;
        this.operators = operators;
    }

    @Override
    public Instance mutate(Instance instance) {
        Instance mutatedInstance = instance.copy();
        Node node = mutatedInstance.getRandomNode();

        int maxDepth = node.getDepth();
        int newDepth = (int) (Math.random() * maxDepth);

        Node newNode = generateRandomNode(newDepth);

        if (node.getParent() == null) {
            mutatedInstance.setRoot(newNode);
        } else {
            if (node.getParent().getLeft() == node) {
                node.getParent().setLeft(newNode);
            } else {
                node.getParent().setRight(newNode);
            }
        }

        return mutatedInstance;
    }

    private Node generateRandomNode(int depth) {
        return RampedHalfAndHalf.generateRandomNode(true, depth, operators, terminals);
    }
}
