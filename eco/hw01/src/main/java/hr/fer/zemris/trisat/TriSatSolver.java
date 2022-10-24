package hr.fer.zemris.trisat;

import hr.fer.zemris.trisat.algorithms.*;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class TriSatSolver {
    public static void main(String[] args) {
        if (args.length == 0) {
            testAllCombinations();
            return;
        }
        if (args.length != 2) {
            System.out.println("Invalid number of arguments");
            return;
        }

        String algorithm = args[0];
        String formulaPath = args[1];

        SATFormula formula;
        int algorithmType;

        try {
            formula = SATFormula.parse(formulaPath);
        } catch (IOException ex) {
            System.out.println("Unable to parse SAT formula with exception: ");
            System.out.println(ex.getMessage());
            return;
        }

        try {
            algorithmType = Integer.parseInt(algorithm);
        } catch (NumberFormatException ex) {
            System.out.println("Unable to parse algorithm type with exception: ");
            System.out.println(ex.getMessage());
            return;
        }

        IOptAlgorithm optAlgorithm;
        switch (algorithmType) {
            case 1 -> optAlgorithm = new BruteForceSearch(formula);
            case 2 -> optAlgorithm = new GreedyHillClimbingSearch(formula);
            case 3 -> optAlgorithm = new ModifiedHillClimbingSearch(formula);
            case 4 -> optAlgorithm = new GSAT(formula);
            case 5 -> optAlgorithm = new RandomWalkSAT(formula);
            case 6 -> optAlgorithm = new IteratedLocalSearch(formula);
            default -> {
                System.out.println("Invalid algorithm type");
                return;
            }
        }

        long startTime = System.currentTimeMillis();

        Optional<BitVector> solution = optAlgorithm.solve(Optional.empty());
        if (solution.isPresent()) {
            System.out.println("Found a solution: " + solution.get());
        } else {
            System.out.println("No solution found");
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + "ms");
    }

    public static void testAllCombinations() {
        String path_uf20_01 = "hw01/src/main/resources/01-3sat/uf20-01.cnf";
        String path_uf20_010 = "hw01/src/main/resources/01-3sat/uf20-010.cnf";
        String path_uf20_0100 = "hw01/src/main/resources/01-3sat/uf20-0100.cnf";
        String path_uf20_01000 = "hw01/src/main/resources/01-3sat/uf20-01000.cnf";
        String path_uf50_01 = "hw01/src/main/resources/01-3sat/uf50-01.cnf";
        String path_uf50_010 = "hw01/src/main/resources/01-3sat/uf50-010.cnf";
        String path_uf50_0100 = "hw01/src/main/resources/01-3sat/uf50-0100.cnf";
        String path_uf50_01000 = "hw01/src/main/resources/01-3sat/uf50-01000.cnf";

        String[] paths = {path_uf20_01, path_uf20_010, path_uf20_0100, path_uf20_01000, path_uf50_01, path_uf50_010, path_uf50_0100, path_uf50_01000};
        Map<String, String> algs = new LinkedHashMap<>();


        algs.put("6", "IteratedLocalSearch");

        for (Map.Entry<String, String> alg : algs.entrySet()) {
            for (int path_idx = 0; path_idx < paths.length; path_idx++) {
                if (path_idx > 3 && alg.getKey().equals("1")) continue;

                String path = paths[path_idx];

                System.out.println("Testing " + alg.getValue() + " on " + path);
                String[] args = {alg.getKey(), path};

                main(args);
                System.out.println();
                System.out.println();
            }
        }
    }
}
