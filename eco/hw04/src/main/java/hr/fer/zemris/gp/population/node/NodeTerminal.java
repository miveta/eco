package hr.fer.zemris.gp.population.node;

public class NodeTerminal extends NodeValue{
    double value;

    public NodeTerminal(double value) {
        this.value = value;
    }

    public NodeTerminal(double min, double max) {
        double val = Math.random() * (max - min) + min;
        double scale = Math.pow(10, 2);
        this.value = Math.round(val * scale) / scale;
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
