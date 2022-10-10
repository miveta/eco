package hr.fer.zemris.trisat;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("Hello world!");
        String path_uf20_01 = "hw01/src/main/resources/01-3sat/uf20-01.cnf";

        SATFormula formula = SATFormula.parse(path_uf20_01);

        System.out.println(formula);

        BitVector assignment = new BitVector(new boolean[] {true, false, true, false, true, false, true, false, true, false, true, false, true, false, true, false, true, false, true, false});
        System.out.println(formula.isSatisfied(assignment));

    }
}
