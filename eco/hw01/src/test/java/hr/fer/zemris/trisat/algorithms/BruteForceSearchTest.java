package hr.fer.zemris.trisat.algorithms;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.SATFormula;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BruteForceSearchTest {
    /*
String path_uf20_01 = "hw01/src/main/resources/01-3sat/uf20-01.cnf";
String path_uf20_010 = "hw01/src/main/resources/01-3sat/uf20-010.cnf";
String path_uf20_0100 = "hw01/src/main/resources/01-3sat/uf20-0100.cnf";
String path_uf20_01000 = "hw01/src/main/resources/01-3sat/uf20-01000.cnf";


SATFormula formula = SATFormula.parse(path_uf20_01000);

//System.out.println(formula);

//BitVector assignment = new BitVector(new boolean[] {true, false, true, false, true, false, true, false, true, false, true, false, true, false, true, false, true, false, true, false});
//System.out.println(formula.isSatisfied(assignment));

IOptAlgorithm algorithm = new BruteForceSearch(formula);
Optional<BitVector> solution = algorithm.solve(Optional.empty());

    */

    @Test
    void testUf20_01() throws IOException {
        String path = "hw01/src/main/resources/01-3sat/uf20-01.cnf";
        SATFormula formula = SATFormula.parse(path);
        IOptAlgorithm algorithm = new BruteForceSearch(formula);


        PrintStream out = System.out;
        ByteArrayOutputStream solutionStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(solutionStream));
        Optional<BitVector> solution = algorithm.solve(Optional.empty());
        System.setOut(out);

        String solutionString = solutionStream.toString();
        String expectedSolution = "10000100100001101001\n" +
                "10000100000011101001\n" +
                "10010100000011101001\n" +
                "10000100100011101001\n" +
                "10010000010011101001\n" +
                "10010100010011101001\n" +
                "10010001010011101001\n" +
                "01110001111001101111\n";

        assertEquals(expectedSolution, solutionString);

        assertTrue(solution.isPresent());
    }

    @Test
    void testUf20_010() throws IOException {
        String path = "hw01/src/main/resources/01-3sat/uf20-010.cnf";
        SATFormula formula = SATFormula.parse(path);
        IOptAlgorithm algorithm = new BruteForceSearch(formula);


        PrintStream out = System.out;
        ByteArrayOutputStream solutionStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(solutionStream));
        Optional<BitVector> solution = algorithm.solve(Optional.empty());
        System.setOut(out);

        String solutionString = solutionStream.toString();
        String expectedSolution = "00111101100001100010\n" +
                "00111101100101100010\n" +
                "00111101100101110010\n" +
                "10111100100111100011\n" +
                "00111101100111100011\n" +
                "10111101100111100011\n" +
                "10111100110111100011\n" +
                "01001111000111110011\n" +
                "00111101100111110011\n";

        assertEquals(expectedSolution, solutionString);

        assertTrue(solution.isPresent());
    }

    @Test
    void testUf20_0100() throws IOException {
        String path = "hw01/src/main/resources/01-3sat/uf20-0100.cnf";
        SATFormula formula = SATFormula.parse(path);
        IOptAlgorithm algorithm = new BruteForceSearch(formula);


        PrintStream out = System.out;
        ByteArrayOutputStream solutionStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(solutionStream));
        Optional<BitVector> solution = algorithm.solve(Optional.empty());
        System.setOut(out);

        String solutionString = solutionStream.toString();
        String expectedSolution = "01110011100111110010\n" +
                "01110011100111110011\n" +
                "01111011100111110011\n" +
                "01101101110011101011\n";

        assertEquals(expectedSolution, solutionString);

        assertTrue(solution.isPresent());
    }

    @Test
    void testUf20_01000() throws IOException {
        String path = "hw01/src/main/resources/01-3sat/uf20-01000.cnf";
        SATFormula formula = SATFormula.parse(path);
        IOptAlgorithm algorithm = new BruteForceSearch(formula);


        PrintStream out = System.out;
        ByteArrayOutputStream solutionStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(solutionStream));
        Optional<BitVector> solution = algorithm.solve(Optional.empty());
        System.setOut(out);

        String solutionString = solutionStream.toString();
        String expectedSolution = "01011010100000011010\n";

        assertEquals(expectedSolution, solutionString);

        assertTrue(solution.isPresent());
    }
}