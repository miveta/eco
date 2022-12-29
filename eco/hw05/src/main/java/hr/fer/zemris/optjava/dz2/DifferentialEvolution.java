package hr.fer.zemris.optjava.dz2;

import Jama.Matrix;
import hr.fer.zemris.optjava.dz2.functions.TransferFunction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/*
 * JMBAG = 0036413744, second to last digit = 4 % 3 = 1 + 1 = 2 -> solving 2nd task
 * */
public class DifferentialEvolution {
    public int populationSize = 100;

    // scale factor, differential weight
    public double F = 0.5;

    public double crossoverProbability = 0.4;
    public int maxIterations = 10000;

    public LinkedList<double[]> bounds;

    public Random rand = new Random();

    public IFunction function;


    public DifferentialEvolution(IFunction function, LinkedList<double[]> bounds) {
        this.function = function;
        this.bounds = bounds;
    }


    public static void main(String[] args) throws IOException {
        LinkedList<double[]> bounds = new LinkedList<>();
        bounds.add(new double[]{-10, 10});
        bounds.add(new double[]{-10, 10});
        bounds.add(new double[]{-10, 10});
        bounds.add(new double[]{-10, 10});
        bounds.add(new double[]{-10, 10});
        bounds.add(new double[]{-10, 10});

        IFunction function = TransferFunction.parseFromFile("src/main/resources/prijenosna.txt");

        DifferentialEvolution de = new DifferentialEvolution(function, bounds);

        String strategyRand = "DE/rand/1";
        String strategyBest = "DE/best/1";

        String strategy = strategyBest;

        if (strategy.equals(strategyRand)) {
            de.crossoverProbability = 0.8;
        } else if (strategy.equals(strategyBest)) {
            de.crossoverProbability = 0.4;
        } else {
            System.out.println("Invalid strategy");
        }

        Instance best = de.differentialEvolution(strategy);

        System.out.println("Strategy: " + strategy + " Minimum found at:" + best);
    }


    /*
     * function - fitness aka cost function is in getValue - calculating MSE
     * */
    public Instance differentialEvolution(String strategy) {
        // https://www.frontiersin.org/articles/10.3389/fbuil.2020.00102/full - strategies description
        if (!strategy.equals("DE/rand/1") && !strategy.equals("DE/best/1")) {
            throw new IllegalArgumentException("Strategy not supported");
        }
        // generate population
        Population population = generateRandomPopulation();
        Instance best = population.getBest();

        int iteration = 0;
        while (iteration < maxIterations) {
            int i = 0;

            // mutate and crossover each instance in the population
            while (i < populationSize) {
                Instance point, nextPoint;
                boolean boundsBroken;

                do {
                    boundsBroken = false;

                    // get three random instances from the population - that are not the original instance
                    List<Instance> individuals = population.getRandomInstances(3, i);

                    Instance instance1 = individuals.get(0);
                    Instance instance2 = individuals.get(1);
                    Instance instance3 = individuals.get(2);

                    point = population.getInstance(i);
                    Matrix candidateVector = point.vector.copy();
                    // not calculating fitness yet because:
                    // 1st we might add the noise
                    // 2nd we might not use the nextPoint at all if the bounds end up being broken
                    nextPoint = new Instance(candidateVector, Double.MAX_VALUE);


                    // MUTATION
                    Matrix mutation = instance1.vector.plus(instance2.vector.minus(instance3.vector).times(F));
                    if (strategy.equals("DE/rand/1")) {
                        // between the three instances (i1 + DW * (i2 - i3))
                        mutation = instance1.vector.plus(instance2.vector.minus(instance3.vector).times(F));
                    } else if (strategy.equals("DE/best/1")) {
                        // between the best instance and the other two (best + DW * (i1 - i2))
                        mutation = best.vector.plus(instance1.vector.minus(instance2.vector).times(F));
                    }

                    // CROSSOVER
                    // one variable gets the noise from mutation, others get noise if rand < crossoverProbability
                    int R = rand.nextInt(function.getNumberOfVariables());

                    for (int v = 0; v < function.getNumberOfVariables(); v++) {
                        double crossoverProbability = rand.nextDouble();

                        if (crossoverProbability < this.crossoverProbability || v == R) {
                            nextPoint.vector.set(v, 0, mutation.get(v, 0));
                        }
                    }

                    // bounds satisfied?
                    for (int v = 0; v < function.getNumberOfVariables(); v++) {
                        double[] bound = bounds.get(v);
                        double value = nextPoint.vector.get(v, 0);

                        if (value < bound[0] || value > bound[1]) {
                            boundsBroken = true;
                            break;
                        }
                    }

                } while (boundsBroken);

                //check if better than the original value
                double pointMSE = point.fitness;
                double nextMSE = nextPoint.evaluateFitness(function);

                if (pointMSE > nextMSE) {
                    population.removeInstance(point);
                    population.addInstance(nextPoint);

                    if (nextMSE < best.fitness) {
                        best = nextPoint;
                    }
                }


                i++;
            }


            if (best.fitness < 10e-6) {
                System.out.println("Algorithm stopped after " + iteration + " iterations");
                return best;
            }

            if (iteration % 100 == 0) {
                System.out.println("Iteration: " + iteration + " best MSE: " + best.fitness);
            }

            iteration++;
        }


        return best;
    }

    public Matrix generateVector() {
        Matrix vector = new Matrix(function.getNumberOfVariables(), 1);
        for (int i = 0; i < function.getNumberOfVariables(); i++) {
            double[] bound = bounds.get(i);
            double value = rand.nextDouble() * (bound[1] - bound[0]) + bound[0];
            vector.set(i, 0, value);
        }
        return vector;
    }

    public Instance generateInstance() {
        Matrix vector = generateVector();
        double fitness = function.getValue(vector);

        return new Instance(vector, fitness);
    }

    public Population generateRandomPopulation() {
        List<Instance> instances = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            Instance instance = generateInstance();
            instances.add(instance);
        }

        return new Population(instances);
    }


    public static class Instance {
        public double fitness;

        public Matrix vector;

        public Instance(Matrix vector, double fitness) {
            this.vector = vector;
            this.fitness = fitness;
        }

        public double evaluateFitness(IFunction function) {
            fitness = function.getValue(vector);
            return fitness;
        }

        @Override
        public String toString() {
            return "fitness=" + fitness +
                    ", vector=[" + vectorToString() +
                    ']';
        }

        private String vectorToString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < vector.getRowDimension(); i++) {
                sb.append(vector.get(i, 0)).append(" ");
            }
            return sb.toString();
        }
    }

    public static class Population {
        public List<Instance> instances;

        public Population(List<Instance> instances) {
            this.instances = instances;
        }

        public Instance getBest() {
            Instance best = instances.get(0);
            for (Instance instance : instances) {
                if (instance.fitness < best.fitness) {
                    best = instance;
                }
            }
            return best;
        }

        public List<Instance> getRandomInstances(int count) {
            List<Instance> randomInstances = new ArrayList<>();

            for (int i = 0; i < count; i++) {
                randomInstances.add(instances.get((int) (Math.random() * instances.size())));
            }

            return randomInstances;
        }

        public List<Instance> getRandomInstances(int count, int notThis) {
            List<Instance> randomInstances = new ArrayList<>();

            for (int i = 0; i < count; i++) {
                int index;
                do {
                    index = (int) (Math.random() * instances.size());
                } while (index == notThis);

                randomInstances.add(instances.get(index));
            }

            return randomInstances;
        }

        public void addInstance(Instance instance) {
            instances.add(instance);
        }

        public void removeInstance(Instance instance) {
            instances.remove(instance);
        }

        public void removeInstance(int index) {
            instances.remove(index);
        }

        public Instance getInstance(int index) {
            return instances.get(index);
        }
    }

}


