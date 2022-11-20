package hr.fer.zemris.gp.population.node;

public class NodeTerminal extends NodeValue{
    double value;

    public NodeTerminal(double value) {
        this.value = value;
    }

    @Override
    public boolean isTerminal() {
        return true;
    }

    @Override
    public double getTerminalValue() {
        return value;
    }

    @Override
    public void setTerminalValue(double value) {
        this.value = value;
    }

    @Override
    public double calculate(double left, double right) {
        return 0;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
