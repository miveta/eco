package hr.fer.zemris.trisat.algorithms;

import hr.fer.zemris.trisat.*;

import java.util.*;

public class RandomWalkSAT implements IOptAlgorithm {
    private int maxIterations = 50;
    private int maxFlips = 1000;
    private double prob = 0.2;

    private SATFormula formula;

    public RandomWalkSAT(SATFormula formula) {
        this.formula = formula;
    }

    @Override
    public Optional<BitVector> solve(Optional<BitVector> initial) {
        Random rand = new Random();
        MutableBitVector assignment;

        int numberOfClauses = formula.getNumberOfClauses();
        int currentGoodness;
        int iteration = 0;

        while (iteration < maxIterations) {
            // T = random dodjela varijabli
            assignment = new MutableBitVector(rand, formula.getNumberOfVariables());
            currentGoodness = goodness(assignment);

            if (currentGoodness == numberOfClauses) {
                return Optional.of(assignment);
            }

            for (int i = 0; i < maxFlips; i++) {
                // random odaberi jednu nezadovoljenu klauzulu
                List<Clause> unsatisfiedClauses = getUnsatisfied(assignment);
                Clause unsatisfiedClause = unsatisfiedClauses.get(rand.nextInt(unsatisfiedClauses.size()));


                if (rand.nextDouble() < prob) {
                    // with prob - prob, randomly flip one variable in that clause
                    int literal = unsatisfiedClause.getLiteral(rand.nextInt(unsatisfiedClause.getSize()) + 1);
                    literal = literal > 0 ? literal : -literal;
                    assignment.set(literal - 1, !assignment.get(literal - 1));
                } else {
                    // with prob - (1 - prob), flip the variable that minimizes the number of unsatisfied clauses
                    assignment = findBestLiteral(unsatisfiedClause, assignment);
                }

                currentGoodness = goodness(assignment);

                if (currentGoodness == numberOfClauses) {
                    return Optional.of(assignment);
                }
            }
            iteration++;
        }


        return Optional.empty();
    }

    private MutableBitVector findBestLiteral(Clause unsatisfiedClause, MutableBitVector assignment) {
        int bestLiteral = 1;
        int bestGoodness = goodness(assignment);

        for (int i = 1; i < unsatisfiedClause.getSize() + 1; i++) {
            int literal = unsatisfiedClause.getLiteral(i);
            literal = literal > 0 ? literal : -literal;

            MutableBitVector newAssignment = assignment.copy();
            newAssignment.set(literal - 1, !newAssignment.get(literal - 1));

            int newGoodness = goodness(newAssignment); // goodness = opposite of number of unsatisfied clauses
            // minimzing the #unsatisfied == maximizing the #satisfied

            if (newGoodness > bestGoodness) {
                bestGoodness = newGoodness;
                bestLiteral = literal;
            }
        }

        assignment.set(bestLiteral - 1, !assignment.get(bestLiteral - 1));

        return assignment;
    }


    private List<Clause> getUnsatisfied(BitVector assignment) {
        List<Clause> unsatisfied = new ArrayList<>();

        for (int i = 0; i < formula.getNumberOfClauses(); i++) {
            Clause clause = formula.getClause(i);
            if (!clause.isSatisfied(assignment)) {
                unsatisfied.add(clause);
            }
        }

        return unsatisfied;
    }

    private int goodness(BitVector assignment) {
        return formula.getNumberOfSatisfiedClauses(assignment);
    }
}
