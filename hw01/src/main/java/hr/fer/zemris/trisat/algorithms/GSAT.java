package hr.fer.zemris.trisat.algorithms;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.SATFormula;

import java.util.Optional;

public class GSAT implements IOptAlgorithm{
    private int maxIterations = 100000;
    private int maxFlips = 1000;
    private SATFormula formula;

    public GSAT(SATFormula formula) {
        this.formula = formula;
    }

    @Override
    public Optional<BitVector> solve(Optional<BitVector> initial) {
        int iteration = 0;

        IOptAlgorithm algorithm = new GreedyHillClimbingSearch(this.formula, this.maxFlips);

        while (iteration < maxIterations) {
            Optional<BitVector> solution = algorithm.solve(Optional.empty());

            if(solution.isPresent()) {
                System.out.println("Found a solution after " + iteration + " iterations");
                return solution;
            }

            iteration++;
        }

        return Optional.empty();
    }
}
