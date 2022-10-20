package hr.fer.zemris.optjava.dz2;

import Jama.Matrix;
import hr.fer.zemris.optjava.dz2.functions.LinearEquationSystem;
import hr.fer.zemris.optjava.dz2.functions.TransferFunction;

import java.io.IOException;
import java.util.Map;

public class Prijenosna {
    public static void main(String[] args) throws IOException {
        TransferFunction system = TransferFunction.parseFromFile("src/main/resources/prijenosna.txt");

        Matrix point = NumOptAlgorithms.gradientDescent(system, null, 200000);

        System.out.println("Minimum found at:");
        printMatrix(point);
        System.out.println("Value: " + system.getValue(point));
    }

    private static void printMatrix(Matrix matrix){
        for(int i = 0; i < matrix.getRowDimension(); i++){
            for(int j = 0; j < matrix.getColumnDimension(); j++){
                System.out.print(matrix.get(i, j) + " ");
            }
            System.out.println();
        }
    }
}
