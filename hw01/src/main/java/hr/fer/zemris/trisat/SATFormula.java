package hr.fer.zemris.trisat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class SATFormula {
    int numberOfVariables;
    Clause[] clauses;

    public SATFormula(int numberOfVariables, Clause[] clauses) {
        this.numberOfVariables = numberOfVariables;
        this.clauses = clauses;
    }

    public int getNumberOfVariables() {
        return numberOfVariables;
    }

    public int getNumberOfClauses() {
        return clauses.length;
    }

    public Clause getClause(int index) {
        if (index < 0 || index >= clauses.length) {
            throw new IndexOutOfBoundsException();
        }
        return clauses[index];
    }

    public int getNumberOfSatisfiedClauses(BitVector assignment) {
        int numberOfSatisfiedClauses = 0;

        for (Clause clause : clauses) {
            if (clause.isSatisfied(assignment)) {
                numberOfSatisfiedClauses++;
            }
        }

        return numberOfSatisfiedClauses;
    }

    public boolean isSatisfied(BitVector assignment) {
        for (Clause clause : clauses) {
            if (!clause.isSatisfied(assignment)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Clause clause : clauses) {
            sb.append(clause).append(" ");
        }

        return sb.toString();
    }

    public static SATFormula parse(String path) throws IOException {
        // parse formula from file
        List<String> rows = Files.readAllLines(Path.of(path));

        int index = 0;

        while (rows.get(index).startsWith("c")) {
            index++;
        }

        int numberOfVariables = Integer.parseInt(rows.get(index).split(" ")[2]);
        int numberOfClauses = Integer.parseInt(rows.get(index).split(" ")[4]);

        index++;

        Clause[] clauses = new Clause[numberOfClauses];
        for (int i = 0; i < numberOfClauses; i++) {
            String[] clause = rows.get(index + i).trim().split(" ");
            int[] indexes = new int[clause.length - 1];
            for (int j = 0; j < clause.length - 1; j++) {
                indexes[j] = Integer.parseInt(clause[j]);
            }
            clauses[i] = new Clause(indexes);
        }

        return new SATFormula(numberOfVariables, clauses);
    }
}
