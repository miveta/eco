package hr.fer.zemris.trisat.algorithms;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.BitVectorNGenerator;
import hr.fer.zemris.trisat.MutableBitVector;
import hr.fer.zemris.trisat.SATFormula;

import java.util.*;

public class GreedyHillClimbingSearch implements IOptAlgorithm {
    private SATFormula formula;
    private int maxIterations = 100000;

    private boolean verbose = true;

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public GreedyHillClimbingSearch(SATFormula formula) {
        this.formula = formula;
    }

    public GreedyHillClimbingSearch(SATFormula formula, int maxIterations, boolean verbose) {
        this.formula = formula;
        this.maxIterations = maxIterations;
        this.verbose = verbose;
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

        int numberOfClauses = formula.getNumberOfClauses();
        int currentGoodness = goodness(assignment); // asses goodness of initial assignment
        int iteration = 0;


        while (iteration < maxIterations) {
            if (currentGoodness == numberOfClauses) { // if we have found a satisfiable solution, return it
                return Optional.of(assignment);
            }

            List<BitVector> betterNeighbours = getMaxGoodnessNeighbours(assignment); // get best neighbours

            if (betterNeighbours.isEmpty()) { // no better neighbours
                if(verbose) System.out.println("We've reached a local optimum with goodness = " + currentGoodness); // reached local optimum, fail
                return Optional.empty();
            }

            assignment = betterNeighbours.get(rand.nextInt(betterNeighbours.size())).copy(); // get random neighbour
            currentGoodness = goodness(assignment);
            iteration++;
        }

        if (verbose) System.out.println("No solution found, best goodness = " + currentGoodness);
        return Optional.empty(); // no solution found
    }

    private int goodness(BitVector assignment) {
        return formula.getNumberOfSatisfiedClauses(assignment);
    }

    private List<BitVector> getMaxGoodnessNeighbours(BitVector assignment) {
        int initialGoodness = goodness(assignment);
        int bestGoodness = initialGoodness;

        List<BitVector> bestNeighbours = new ArrayList<>();

        Iterator<MutableBitVector> neighbours = new BitVectorNGenerator(assignment).iterator();

        while (neighbours.hasNext()) {
            BitVector neighbour = neighbours.next();
            int neighbourGoodness = goodness(neighbour);

            if (neighbourGoodness > bestGoodness) {
                bestGoodness = neighbourGoodness;
                bestNeighbours.clear();
                bestNeighbours.add(neighbour);
            } else if (neighbourGoodness == bestGoodness && neighbourGoodness != initialGoodness) {
                bestNeighbours.add(neighbour);
            }
        }

        return bestNeighbours;
    }
}
