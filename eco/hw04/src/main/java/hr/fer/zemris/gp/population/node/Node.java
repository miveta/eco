package hr.fer.zemris.gp.population.node;

/*
* Tree node class that can contain an operator or a terminal.
*/
public class Node {
    private Node left;
    private Node right;
    private Node parent;

    private NodeValue value;

    public Node(NodeValue value) {
        this.value = value;
    }


    public boolean isTerminal() {
        return value.isTerminal();
    }

    public boolean isVariable(){
        return value.isVariable();
    }

    public void setTerminalValue(double value){
        this.value.setTerminalValue(value);
    }

    public double evaluate() {
        if (value.isTerminal()) {
            double val = value.getTerminalValue();
            return val;
        }

        NodeOperator operator = (NodeOperator) value;

        if (operator.isUnary()) {
            double l = left.evaluate();
            double val = operator.calculate(l, 0);
            return val;
        } else {

            double l = left.evaluate();
            double r = right.evaluate();
            double val = operator.calculate(l, r);
            return val;
        }
    }

    public String toString() {
        if (value.isTerminal()) {
            return value.toString();
        }

        NodeOperator operator = (NodeOperator) value;

        if (operator.isUnary()) {
            return operator.toString() + left.toString();
        } else {
            return "(" + left + operator + right + ")";
        }
    }

    public NodeValue getValue() {
        return value;
    }
    public int getDepth() {
        if(left == null && right == null) {
            return 1;
        }

        if(left == null) {
            return right.getDepth() + 1;
        }

        if(right == null) {
            return left.getDepth() + 1;
        }

        return Math.max(left.getDepth(), right.getDepth()) + 1;
    }

    public int getNumberOfNodes() {
        if(left == null && right == null) {
            return 1;
        }

        if(left == null) {
            return right.getNumberOfNodes() + 1;
        }

        if(right == null) {
            return left.getNumberOfNodes() + 1;
        }

        return left.getNumberOfNodes() + right.getNumberOfNodes() + 1;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public void setLeft(Node left) {
        this.left = left;
        if(left != null) left.parent = this;
    }

    public void setRight(Node right) {
        this.right = right;
        if(right != null) right.parent = this;
    }

    public Node getParent() {
        return parent;
    }

    public Node copy(){
        Node clone = new Node(value);

        if(left != null) {
            clone.left = left.copy();
            clone.left.parent = clone;
        }

        if(right != null) {
            clone.right = right.copy();
            clone.right.parent = clone;
        }

        return clone;
    }

}
