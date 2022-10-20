package hr.fer.zemris.optjava.dz2.functions;

import Jama.Matrix;
import hr.fer.zemris.optjava.dz2.IFunction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class LinearEquationSystem implements IFunction {
    Matrix coefficients;
    Matrix constants;

    public LinearEquationSystem(Matrix coefficients, Matrix constants) {
        this.coefficients = coefficients;
        this.constants = constants;
    }

    public static LinearEquationSystem parseFromFile(String path) throws IOException {
        List<String> rows = Files.readAllLines(Paths.get(path));

        while(rows.get(0).startsWith("#")) {
            rows.remove(0);
        }

        int n = rows.size();
        double[] constants = new double[n];
        double[][] coefficients = new double[n][n];

        for(int i = 0; i < rows.size(); i++){
            // remove [] from the ends of a row
            String line = rows.get(i).trim().substring(1, rows.get(i).length() - 1);

            String[] split = line.split(", ");
            constants[i] = Double.parseDouble(split[split.length - 1]);
            for(int j = 0; j < split.length - 1; j++){
                coefficients[i][j] = Double.parseDouble(split[j]);
            }
        }

        Matrix constantsMatrix = new Matrix(constants, 1);
        Matrix coefficientsMatrix = new Matrix(coefficients);

        return new LinearEquationSystem(coefficientsMatrix, constantsMatrix);

    }

    @Override
    public int getNumberOfVariables() {
        return coefficients.getColumnDimension();
    }

    @Override
    public double getValue(Matrix vector) {
        Matrix diff = coefficients.times(vector).minus(constants.transpose()); // y_hat - y_true - column vector
        Matrix diffSquared = diff.transpose().times(diff); // diff^T * diff - to get the diff squared

        // the matrix is just one element
        double squaredError = diffSquared.get(0, 0);

        // dividing squared error by 2 for easier gradient calc
        return squaredError / 2;
    }

    @Override
    public Matrix getGradient(Matrix vector) {
        // y_hat - y_true
        Matrix diff = coefficients.times(vector).minus(constants.transpose());

        // grad MSE = X^T * (y_hat - y_true)

        double numberOfSamples = coefficients.getRowDimension();
        return coefficients.transpose().times(diff).times(1/numberOfSamples);
    }
}
