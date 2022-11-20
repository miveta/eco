package hr.fer.zemris.gp.tasks;

import hr.fer.zemris.gp.population.Instance;
import hr.fer.zemris.gp.population.Operators;
import hr.fer.zemris.gp.population.node.Node;
import hr.fer.zemris.gp.population.node.NodeVariable;

public abstract class AbstractFunction {
    public double[][] x;
    public double[] y;


    public abstract Instance correct();

    public abstract int numberOfVariables();
}
