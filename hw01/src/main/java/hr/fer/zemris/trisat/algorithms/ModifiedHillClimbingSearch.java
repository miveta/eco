package hr.fer.zemris.trisat.algorithms;

import hr.fer.zemris.trisat.*;

import java.util.*;

public class ModifiedHillClimbingSearch implements IOptAlgorithm {
    private SATFormulaStats stats;
    private final SATFormula formula;
    private int maxIterations = 100000;
    public static final int numberOfBest = 2;

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

            stats.setAssignment(assignment, true); // update stats

            List<BitVector> betterNeighbours = getMaxGoodnessNeighbours(assignment); // get best neighbours

            assignment = betterNeighbours.get(rand.nextInt(betterNeighbours.size())).copy(); // get random neighbour
            currentGoodness = goodness(assignment); // asses goodness of new assignment

            iteration++;
        }

        System.out.println("No solution found, best goodness = " + currentGoodness);
        return Optional.empty(); // no solution found
    }

    private double goodness(BitVector assignment) {
        stats.setAssignment(assignment, false);
        return stats.getNumberOfSatisfied() + stats.getPercentageBonus();
    }

    private List<BitVector> getMaxGoodnessNeighbours(BitVector assignment) {
        Map<Double, BitVector> neighboursGoodnessMap = new TreeMap<>(Collections.reverseOrder());

        for (BitVector neighbour : new BitVectorNGenerator(assignment)) {
            double neighbourGoodness = goodness(neighbour);

            neighboursGoodnessMap.put(neighbourGoodness, neighbour);
        }

        List<BitVector> bestNeighbours = new ArrayList<>();
        int i = 0;

        for (Map.Entry<Double, BitVector> entry : neighboursGoodnessMap.entrySet()) {
            if (i >= numberOfBest) {
                break;
            }

            bestNeighbours.add(entry.getValue());
            i++;

        }

        return bestNeighbours;
    }
}
