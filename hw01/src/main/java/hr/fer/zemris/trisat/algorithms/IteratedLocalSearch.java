package hr.fer.zemris.trisat.algorithms;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.MutableBitVector;
import hr.fer.zemris.trisat.SATFormula;

import java.util.Optional;

public class IteratedLocalSearch implements IOptAlgorithm {
    private int maxIterations = 100000;
    private int maxFlips = 1000;

    private double flipPercentage = 0.2;
    private SATFormula formula;

    public IteratedLocalSearch(SATFormula formula) {
        this.formula = formula;
    }

    @Override
    public Optional<BitVector> solve(Optional<BitVector> initial) {
        int iteration = 0;

        IOptAlgorithm algorithm = new GreedyHillClimbingSearch(this.formula, this.maxFlips, false);
        MutableBitVector assignment = new MutableBitVector(formula.getNumberOfVariables());

        while (iteration < maxIterations) {
            Optional<BitVector> solution = algorithm.solve(Optional.of(assignment));

            if (solution.isPresent()) {
                System.out.println("Found a solution after " + iteration + " iterations");
                return solution;
            }

            // randomly flip percentage of variables in assignment
            for (int i = 0; i < assignment.getSize(); i++) {
                if (Math.random() < flipPercentage) {
                    assignment.set(i, !assignment.get(i));
                }
            }

            iteration++;
        }

        return Optional.empty();
    }
}
