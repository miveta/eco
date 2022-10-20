package hr.fer.zemris.optjava.dz2.functions;

import Jama.Matrix;
import hr.fer.zemris.optjava.dz2.IFunction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TransferFunction implements IFunction {
    Matrix coefficients;
    Matrix constants;

    public TransferFunction(Matrix coefficients, Matrix constants) {
        this.coefficients = coefficients;
        this.constants = constants;
    }

    public static TransferFunction parseFromFile(String path) throws IOException {
        List<String> rows = Files.readAllLines(Paths.get(path));

        while (rows.get(0).startsWith("#")) {
            rows.remove(0);
        }

        int n = rows.size();
        double[] constants = new double[n];
        double[][] coefficients = new double[n][5];

        for (int i = 0; i < rows.size(); i++) {
            // remove [] from the ends of a row
            String line = rows.get(i).trim().substring(1, rows.get(i).length() - 1);

            String[] split = line.split(", ");
            constants[i] = Double.parseDouble(split[split.length - 1]);
            for (int j = 0; j < split.length - 1; j++) {
                coefficients[i][j] = Double.parseDouble(split[j]);
            }
        }

        Matrix constantsMatrix = new Matrix(constants, 1);
        Matrix coefficientsMatrix = new Matrix(coefficients);

        return new TransferFunction(coefficientsMatrix, constantsMatrix);
    }

    /*
     *
     * y(x_1, x_2, x_3, x_4, x_5) = a * x_1 + b * x_1 ** 3 * x_2 + c * E ** (d * x_3) * (1 + cos(e * x_4)) + f * x_4 * x_5 ** 2
     * */
    @Override
    public int getNumberOfVariables() {
        return 6;
    }

    @Override
    public double getValue(Matrix vector) {
        Matrix diff = getDiff(vector);
        Matrix diffSquared = diff.transpose().times(diff);

        return diffSquared.det() / 2;
    }

    @Override
    public Matrix getGradient(Matrix vector) {
        Matrix diff = getDiff(vector);

        Matrix gradient = new Matrix(6, 1);

        for(int i = 0; i < constants.getColumnDimension(); i++){
            double a = vector.get(0, 0);
            double b = vector.get(1, 0);
            double c = vector.get(2, 0);
            double d = vector.get(3, 0);
            double e = vector.get(4, 0);
            double f = vector.get(5, 0);

            double x1 = coefficients.get(i, 0);
            double x2 = coefficients.get(i, 1);
            double x3 = coefficients.get(i, 2);
            double x4 = coefficients.get(i, 3);
            double x5 = coefficients.get(i, 4);

            double diffValue = diff.get(i, 0);

            double gradA = diffValue * x1;
            double gradB = diffValue * x1 * x1 * x1 * x2;
            double gradC = diffValue * Math.exp(d * x3) * (1 + Math.cos(e * x4));
            double gradD = diffValue * c * x3 * Math.exp(d * x3) * (1 + Math.cos(e * x4));
            double gradE = diffValue * c * Math.exp(d * x3) * (-Math.sin(e * x4) * x4);
            double gradF = diffValue * x4 * x5 * x5;

            gradient.set(0, 0, gradient.get(0, 0) + gradA);
            gradient.set(1, 0, gradient.get(1, 0) + gradB);
            gradient.set(2, 0, gradient.get(2, 0) + gradC);
            gradient.set(3, 0, gradient.get(3, 0) + gradD);
            gradient.set(4, 0, gradient.get(4, 0) + gradE);
            gradient.set(5, 0, gradient.get(5, 0) + gradF);

        }

        double norm = gradient.normF();
        return gradient.times(1 / norm);
    }

    private Matrix getDiff(Matrix vector) {
        // get y_hat - y_true vector
        Matrix diff = new Matrix(constants.getColumnDimension(), 1);
        for (int i = 0; i < constants.getColumnDimension(); i++) {
            double a = vector.get(0, 0);
            double b = vector.get(1, 0);
            double c = vector.get(2, 0);
            double d = vector.get(3, 0);
            double e = vector.get(4, 0);
            double f = vector.get(5, 0);

            double x1 = coefficients.get(i, 0);
            double x2 = coefficients.get(i, 1);
            double x3 = coefficients.get(i, 2);
            double x4 = coefficients.get(i, 3);
            double x5 = coefficients.get(i, 4);

            double y_hat = a * x1 + b * x1 * x1 * x1 * x2 + c * Math.exp(d * x3) * (1 + Math.cos(e * x4)) + f * x4 * x5 * x5;
            double y_true = constants.get(0, i);

            diff.set(i, 0, y_hat - y_true);
        }

        return diff;
    }
}
