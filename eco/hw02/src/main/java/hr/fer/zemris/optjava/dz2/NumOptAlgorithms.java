package hr.fer.zemris.optjava.dz2;

import Jama.Matrix;

import java.util.*;

public class NumOptAlgorithms {
    /*
     * Gradient - one column vector whose value in the i-th row is the partial derivative
     * of the function with respect to the i-th variable.
     * */
    public static Matrix gradientDescent(IFunction function, Matrix startingPoint, int maxIterations) {
        Matrix point;
        if (startingPoint == null) {
            point = generatePoint(function.getNumberOfVariables());
        } else {
            point = startingPoint.copy();
        }

        Matrix gradient = function.getGradient(point);
        double value = function.getValue(point);

        int iteration = 0;
        while (iteration < maxIterations) {
            System.out.println("Iteration: " + iteration);
            System.out.println("Value: " + value);

            double lambda = getLambda(function, point);

            Matrix nextPoint = point.minus(gradient.times(lambda));
            Matrix nextGradient = function.getGradient(nextPoint);
            double nextValue = function.getValue(nextPoint);

            if(Math.abs(nextValue - value) > 1e-6 && nextValue < value) {
                /*
                * If the next point is better than the current solution (has a smaller value)
                * then continue
                * */
                point = nextPoint;
                gradient = nextGradient;
                value = nextValue;
            } else {
                /*
                * If the next point is worse than the current one, break the algorithm
                * */
                System.out.println("Algorithm stopped after " + iteration + " iterations");
                break;
            }

            iteration++;
        }

        System.out.println(iteration + " iterations");
        return point;
    }

    /*
    * The bisection method
    * */
    public static double getLambda(IFunction function, Matrix point) {
        double lower = 0;
        /*
        * Finding the upper lambda bound - the one for which the derivation starts growing again
        * */
        double upper = getUpperLambda(function, point);
        double lambda;

        Matrix x = point.copy();
        /*
        * Direction in which we're going - opposite of the gradient direction
        * */
        Matrix d = function.getGradient(point).times(-1);

        while (true) {
            // Set lambda at the middle of the interval
            lambda = (lower + upper) / 2;

            Matrix x1 = x.plus(d.times(lambda));
            Matrix g1 = function.getGradient(x1);
            double derivative = g1.transpose().times(d).det();


            // Derivative is close-ish to zero - we've found the lambda
            if(Math.abs(derivative) < 1e-3) {
                break;
            } else if (derivative > 0) {
                upper = lambda;
            } else {
                lower = lambda;
            }
        }

        return lambda;
    }


    private static double getUpperLambda(IFunction function, Matrix point){
        double lambda = 0.01;

        Matrix x = point.copy();
        // d = - grad f(x)
        Matrix d = function.getGradient(point).times(-1);

        while (true) {
            // x1 = x + lambda * d
            Matrix x1 = x.plus(d.times(lambda));
            Matrix g1 = function.getGradient(x1);
            // g1_T * d gives a dot product - couldn't find it in Jama Matrix
            double derivation = g1.transpose().times(d).det();

            if (derivation > 0 || Math.abs(derivation) < 1e-6) {
                break;
            } else {
                lambda *= 2;
            }
        }

        return lambda;
    }
    public static Matrix generatePoint(int numberOfVariables) {
        Random random = new Random();
        Matrix point = new Matrix(numberOfVariables, 1);

        for (int i = 0; i < numberOfVariables; i++) {
            point.set(i, 0, random.nextDouble() * 10 - 5);
        }

        return point;
    }

}
