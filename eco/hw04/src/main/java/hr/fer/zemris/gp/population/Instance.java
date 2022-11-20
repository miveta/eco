package hr.fer.zemris.gp.population;

import hr.fer.zemris.gp.population.node.Node;

import java.util.ArrayList;
import java.util.List;

public class Instance {
    Node root;
    double fitness;

    List<Node> inOrder = new ArrayList<>();

    public Instance(Node root) {
        this.root = root;
        this.fitness = 0.0;
    }

    public Instance(Node root, double fitness) {
        this.root = root;
        this.fitness = fitness;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public double getFitness() {
        return fitness;
    }

    public double calculateFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public void inOrder() {
        this.inOrder.clear();
        inOrder(root);
    }

    void inOrder(Node node) {
        if (node == null) {
            return;
        }

        inOrder(node.getLeft());
        this.inOrder.add(node);
        inOrder(node.getRight());
    }

    public void setVariable(String variableName, double value) {
        if(inOrder.isEmpty()){
            inOrder();
        }

        for (Node node : inOrder) {
            if (node.getValue().isVariable() && node.toString().equals(variableName)) {
                node.setTerminalValue(value);
            }
        }
    }

    public double evaluate() {
        return root.evaluate();
    }

    public String toString() {
        return root.toString();
    }

    public int getDepth() {
        return root.getDepth();
    }

    public int getNumberOfNodes() {
        return root.getNumberOfNodes();
    }

    public Node getRandomNode() {
        this.inOrder();

        int nodeIndex = (int) (Math.random() * getNumberOfNodes());

        return inOrder.get(nodeIndex);
    }

    public Instance copy() {
        return new Instance(root.copy(), fitness);
    }
}
