package hr.fer.zemris.gp.population.node;

public abstract class NodeOperator extends NodeValue {
    @Override
    public boolean isTerminal() {
        return false;
    }

    @Override
    public double getTerminalValue() {
        return 0;
    }

    @Override
    public void setTerminalValue(double value) {
        return;
    }

    public abstract boolean isUnary();
}
