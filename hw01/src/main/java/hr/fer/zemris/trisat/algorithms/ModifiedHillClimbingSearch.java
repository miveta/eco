package hr.fer.zemris.trisat.algorithms;

import hr.fer.zemris.trisat.*;

import java.util.*;

public class ModifiedHillClimbingSearch implements IOptAlgorithm {
    private final SATFormula formula;
    private int maxIterations = 100000;
    private SATFormulaStats stats;

    public ModifiedHillClimbingSearch(SATFormula formula) {
        this.formula = formula;
        this.stats = new SATFormulaStats(formula);
    }

    public ModifiedHillClimbingSearch(SATFormula formula, int maxIterations) {
        this.formula = formula;
        this.maxIterations = maxIterations;
        this.stats = new SATFormulaStats(formula);
    }

    @Override
    public Optional<BitVector> solve(Optional<BitVector> initial) {
        MutableBitVector assignment;
        Random rand = new Random();
        if (initial.isPresent()) {
            assignment = initial.get().copy();
        } else {
            assignment = new MutableBitVector(rand, formula.getNumberOfVariables()); // create random initial assignment
        }

        double currentGoodness = goodness(assignment); // asses goodness of initial assignment
        int iteration = 0;


        while (iteration < maxIterations) {
            if (formula.getNumberOfSatisfiedClauses(assignment) == formula.getNumberOfClauses()) { // if we have found a satisfiable solution, return it
                return Optional.of(assignment);
            }

            List<BitVector> betterNeighbours = getMaxGoodnessNeighbours(assignment); // get best neighbours

            assignment = betterNeighbours.get(rand.nextInt(betterNeighbours.size())).copy(); // get random neighbour
            currentGoodness = goodness(assignment);
            iteration++;
        }

        System.out.println("No solution found, best goodness = " + currentGoodness);
        return Optional.empty(); // no solution found
    }

    private double goodness(BitVector assignment) {
        stats.setAssignment(assignment, false);
        return formula.getNumberOfSatisfiedClauses(assignment) + stats.getPercentageBonus();
    }

    private List<BitVector> getMaxGoodnessNeighbours(BitVector assignment) {
        double bestGoodness = goodness(assignment);

        List<BitVector> bestNeighbours = new ArrayList<>();
        Iterator<MutableBitVector> neighbours = new BitVectorNGenerator(assignment).iterator();

        while (neighbours.hasNext()) {
            BitVector neighbour = neighbours.next();
            double neighbourGoodness = goodness(neighbour);

            if (neighbourGoodness > bestGoodness) {
                bestGoodness = neighbourGoodness;
                bestNeighbours.clear();
                bestNeighbours.add(neighbour);
            } else if (Math.abs(neighbourGoodness - bestGoodness) < 1e-3) {
                bestNeighbours.add(neighbour);
            }
        }

        return bestNeighbours;
    }
}
