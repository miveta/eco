package hr.fer.zemris.gp.population.node;

public class NodeVariable extends NodeTerminal{
    String variableName;

    public NodeVariable(String variableName) {
        super(0);
        this.variableName = variableName;
    }

    @Override
    public String toString() {
        return variableName;
    }

    @Override
    public double getTerminalValue() {
        return this.value;
    }

    public void setTerminalValue(double value) {
        this.value = value;
    }

    @Override
    public boolean isVariable(){
        return true;
    }
}
