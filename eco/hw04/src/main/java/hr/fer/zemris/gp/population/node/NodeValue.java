package hr.fer.zemris.gp.population.node;

public abstract class NodeValue {
    public abstract boolean isTerminal();

    public boolean isVariable(){
        return false;
    }

    public abstract double getTerminalValue();

    public abstract void setTerminalValue(double value);

    public abstract double calculate(double left, double right);

    public abstract String toString();
}
