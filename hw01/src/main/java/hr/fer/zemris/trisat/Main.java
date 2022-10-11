package hr.fer.zemris.trisat;

import hr.fer.zemris.trisat.algorithms.IOptAlgorithm;
import hr.fer.zemris.trisat.algorithms.GreedyHillClimbingSearch;
import hr.fer.zemris.trisat.algorithms.ModifiedHillClimbingSearch;

import java.io.IOException;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws IOException {

        //System.out.println("Hello world!");
        String path_uf20_01 = "hw01/src/main/resources/01-3sat/uf20-01.cnf";
        String path_uf20_010 = "hw01/src/main/resources/01-3sat/uf20-010.cnf";
        String path_uf20_0100 = "hw01/src/main/resources/01-3sat/uf20-0100.cnf";
        String path_uf20_01000 = "hw01/src/main/resources/01-3sat/uf20-01000.cnf";
        String path_uf50_01 = "hw01/src/main/resources/01-3sat/uf50-01.cnf";
        String path_uf50_010 = "hw01/src/main/resources/01-3sat/uf50-010.cnf";
        String path_uf50_0100 = "hw01/src/main/resources/01-3sat/uf50-0100.cnf";
        String path_uf50_01000 = "hw01/src/main/resources/01-3sat/uf50-01000.cnf";

        SATFormula formula = SATFormula.parse(path_uf20_01);


        // time algorithm execution
        long startTime = System.currentTimeMillis();

        IOptAlgorithm algorithm = new ModifiedHillClimbingSearch(formula);
        Optional<BitVector> solution = algorithm.solve(Optional.empty());

        if (solution.isPresent()) {
            System.out.println("Solution found: " + solution.get());
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) / 1000 + "s");
    }
}
