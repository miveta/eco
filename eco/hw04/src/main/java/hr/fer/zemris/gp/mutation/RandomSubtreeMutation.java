package hr.fer.zemris.gp.mutation;

import hr.fer.zemris.gp.RandomGeneration;
import hr.fer.zemris.gp.population.Instance;
import hr.fer.zemris.gp.population.RampedHalfAndHalf;
import hr.fer.zemris.gp.population.node.Node;
import hr.fer.zemris.gp.population.node.NodeOperator;
import hr.fer.zemris.gp.population.node.NodeTerminal;

import java.util.List;

public class RandomSubtreeMutation implements IMutation {

    RandomGeneration randomGeneration;

    public RandomSubtreeMutation(List<NodeOperator> operators, List<NodeTerminal> terminals, Double min, Double max, Double constProbabilty) {
        this.randomGeneration = new RandomGeneration(operators, terminals, min, max, constProbabilty);
    }

    public RandomSubtreeMutation(List<NodeOperator> operators, List<NodeTerminal> terminals) {
        this.randomGeneration = new RandomGeneration(operators, terminals);
    }

    @Override
    public Instance mutate(Instance instance) {
        Instance mutatedInstance = instance.copy();
        Node node = mutatedInstance.getRandomNode();

        int maxDepth = node.getDepth();
        int newDepth = (int) (Math.random() * maxDepth);

        Node newNode = randomGeneration.generateRandomNode(true, newDepth);

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
}
