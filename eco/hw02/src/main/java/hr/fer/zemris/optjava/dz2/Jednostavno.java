package hr.fer.zemris.optjava.dz2;

import Jama.Matrix;

import java.util.Map;

public class Jednostavno {
    /*
     * f(x) = x_1 ** 2 + (x_2 - 1) ** 2
     * */
    static IFunction function1 = new IFunction() {
        @Override
        public int getNumberOfVariables() {
            return 2;
        }

        @Override
        public double getValue(Matrix vector) {
            double x1 = vector.get(0, 0);
            double x2 = vector.get(1, 0);
            return x1 * x1 + (x2 - 1) * (x2 - 1);
        }

        @Override
        public Matrix getGradient(Matrix vector) {
            double x1 = vector.get(0, 0);
            double x2 = vector.get(1, 0);
            Matrix gradient = new Matrix(2, 1);
            gradient.set(0, 0, 2 * x1);
            gradient.set(1, 0, 2 * (x2 - 1));
            return gradient;
        }
    };

    /*
     * f(x) = (x_1 - 1) ** 2 + 10 * (x_2 - 2) ** 2
     * */
    static IFunction function2 = new IFunction() {
        @Override
        public int getNumberOfVariables() {
            return 2;
        }

        @Override
        public double getValue(Matrix vector) {
            double x1 = vector.get(0, 0);
            double x2 = vector.get(1, 0);
            return (x1 - 1) * (x1 - 1) + 10 * (x2 - 2) * (x2 - 2);
        }

        @Override
        public Matrix getGradient(Matrix vector) {
            double x1 = vector.get(0, 0);
            double x2 = vector.get(1, 0);
            Matrix gradient = new Matrix(2, 1);
            gradient.set(0, 0, 2 * (x1 - 1));
            gradient.set(1, 0, 20 * (x2 - 2));
            return gradient;
        }
    };


    public static void main(String[] args) {
        IFunction function = function1;

        Matrix startingPoint = new Matrix(new double[][]{{-5}, {-5}});

        Matrix point = NumOptAlgorithms.gradientDescent(function, startingPoint, 200000);

        System.out.println("Minimum found at:");
        printMatrix(point);
        System.out.println("Value: " + function.getValue(point));
    }

    private static void printMatrix(Matrix matrix) {
        for (int i = 0; i < matrix.getRowDimension(); i++) {
            for (int j = 0; j < matrix.getColumnDimension(); j++) {
                System.out.print(matrix.get(i, j) + " ");
            }
            System.out.println();
        }
    }
}
