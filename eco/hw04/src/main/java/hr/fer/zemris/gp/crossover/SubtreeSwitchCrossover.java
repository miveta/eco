package hr.fer.zemris.gp.crossover;

import hr.fer.zemris.gp.population.Instance;
import hr.fer.zemris.gp.population.node.Node;
import hr.fer.zemris.gp.population.node.NodeValue;

import java.util.List;

public class SubtreeSwitchCrossover implements ICrossover {

    int MAX_DEPTH = 7;
    int MAX_NODES = 100;

    final static boolean RETURN_BOTH_PARENTS = true;

    final static boolean RETURN_NULL_ON_FAIL = false;



    public SubtreeSwitchCrossover(){}

    public SubtreeSwitchCrossover(int maxDepth, int maxNodes) {
        MAX_DEPTH = maxDepth;
        MAX_NODES = maxNodes;
    }

    /*
    * Uniformly choose a node from parent1 and a node from parent2.
    * Switch the subtrees of both parents and return the new instances.
    * */
    @Override
    public List<Instance> crossover(Instance parent1, Instance parent2) {
        Instance child1 = parent1.copy();
        Instance child2 = parent2.copy();

        Node node1 = child1.getRandomNode();
        Node node2 = child2.getRandomNode();
        Node tmp = node1.copy();

        Node node1Parent = node1.getParent();
        Node node2Parent = node2.getParent();

        if(node1Parent == null) {
            child1.setRoot(node2);
        } else {
            if(node1Parent.getLeft() == node1) {
                node1Parent.setLeft(node2);
            } else {
                node1Parent.setRight(node2);
            }
        }


        if(node2Parent == null) {
            child2.setRoot(tmp);
        } else {
            if(node2Parent.getLeft() == node2) {
                node2Parent.setLeft(tmp);
            } else {
                node2Parent.setRight(tmp);
            }
        }

        /*
        * Crossover can create trees with huge depths or a big number of nodes. In that case, we can
        *   - return one or both parents -> turning the crossover into reproduction
        *   - declare the crossover as unsuccessful -> whoever called the crossover needs to handle it then,
        *                               e.g., trying again with a new set of parents
        * */
        boolean fail = child1.getDepth() > MAX_DEPTH || child1.getNumberOfNodes() > MAX_NODES ||
                child2.getDepth() > MAX_DEPTH || child2.getNumberOfNodes() > MAX_NODES;

        if(fail) {
            if(RETURN_NULL_ON_FAIL) {
                return null;
            } else if(RETURN_BOTH_PARENTS) {
                return List.of(parent1, parent2);
            } else {
                return List.of(parent1);
            }
        }

        //TODO - fix fitness function calc
        //child1.setFitness(calculateFitness(child1));
        //child2.setFitness(calculateFitness(child2));

        return List.of(child1, child2);
    }

    double calculateFitness(Instance instance) {
        return 0;
    }
}
