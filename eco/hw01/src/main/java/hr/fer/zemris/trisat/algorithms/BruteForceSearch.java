package hr.fer.zemris.trisat.algorithms;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.SATFormula;

import java.util.Optional;

public class BruteForceSearch implements IOptAlgorithm {
    SATFormula formula;

    public BruteForceSearch(SATFormula formula) {
        this.formula = formula;
    }

    @Override
    public Optional<BitVector> solve(Optional<BitVector> initial) {
        // Check all possible variable assignments and return the last one that satisfies the formula
        int numberOfCombinations = (int) Math.pow(2, formula.getNumberOfVariables());

        Optional<BitVector> solution = Optional.empty();

        for (int i = 0; i < numberOfCombinations; i++) {
            BitVector assignment = new BitVector(i, formula.getNumberOfVariables());

            if (formula.isSatisfied(assignment)) {
                solution = Optional.of(assignment);

                System.out.println(assignment);
            }
        }
        return solution;
    }

}
