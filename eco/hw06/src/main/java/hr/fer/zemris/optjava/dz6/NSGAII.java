package hr.fer.zemris.optjava.dz6;

import hr.fer.zemris.optjava.dz6.crossover.ICrossover;
import hr.fer.zemris.optjava.dz6.mutation.IMutation;
import hr.fer.zemris.optjava.dz6.population.Instance;
import hr.fer.zemris.optjava.dz6.problems.MOOPProblem;
import hr.fer.zemris.optjava.dz6.selection.ISelection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NSGAII {
    int maxIterations;
    int populationSize;
    MOOPProblem problem;
    ICrossover crossover;
    IMutation mutation;
    ISelection selection;

    public NSGAII(int maxIterations, int populationSize, MOOPProblem problem, ICrossover crossover, IMutation mutation, ISelection selection) {
        this.maxIterations = maxIterations;
        this.populationSize = populationSize;
        this.problem = problem;
        this.crossover = crossover;
        this.mutation = mutation;
        this.selection = selection;
    }

    /*
    * Initialize Population
    Generate N random solutions and insert into Population
    for (i = 1 to MaxGenerations) do
        Generate ChildPopulation of size N
        Select Parents from Population
        Create Children from Parents
        Mutate Children
        Combine Population and ChildPopulations into CurrentPopulation with
        size
         2N
        for each individual in CurrentPopulation do
            Assign rank based on Pareto – Fast non-dominated sort
        end for
        Generate sets of non-dominated vectors along PFknown
        Loop (inside) by adding solutions to next generation of Population
        starting
         from the best front
        until N solutions found and determine crowding distance between
         points on each front
    end for
    Present results
    * */
    public List<List<Instance>> optimise() {
        // initialize population
        // generate N random solutions and insert into population
        List<Instance> parents = generatePopulation();
        List<Instance> kids;
        List<List<Instance>> fronts;

        // for i = 1 to max generations
        for (int i = 0; i < maxIterations; i++) {
            // generate child population of size N
            kids = generateKids(parents);

            // combine population and child population into current population with size 2N
            List<Instance> currentPopulation = combineParentsAndKids(parents, kids);


            // for each individual in current population
            // assign rank based on pareto - fast non-dominated sort
            fronts = nonDominatedSort(currentPopulation);

            calculateCrowdingDistances(fronts);

            // combine fronts into the new population based on crowding distance
            parents = combinePopulations(fronts);
        }

        fronts = nonDominatedSort(parents);
        calculateCrowdingDistances(fronts);


        return fronts;
    }

    /*
     * Calculating crowding distances
     * */
    private void calculateCrowdingDistances(List<List<Instance>> fronts) {
        // Book 15.2 - step 1
        for (List<Instance> front : fronts) {
            for (Instance instance : front) {
                instance.setCrowdingDistance(0.0); // set crowding distance to 0
            }

            for (int i = 0; i < problem.getNumberOfObjectives(); i++) {
                // Book 15.2 - step 2
                // sort front by objective i
                int finalI = i;
                front.sort(Comparator.comparingDouble(o -> o.getObjectiveValue(finalI)));

                if (front.size() <= 3) continue;

                // calculate crowding distance
                double fMin = front.get(0).getObjectiveValue(i);
                double fMax = front.get(front.size() - 1).getObjectiveValue(i);
                double fDiff = fMax - fMin;



                for (int j = 1; j < front.size() - 1; j++) {
                    double crowdingDistance = front.get(j).getCrowdingDistance();
                    crowdingDistance += (front.get(j + 1).getObjectiveValue(i) - front.get(j - 1).getObjectiveValue(i)) / fDiff;
                    front.get(j).setCrowdingDistance(crowdingDistance);
                }

                // set crowding distance to infinity for the first and last
                front.get(0).setCrowdingDistance(Double.POSITIVE_INFINITY);
                front.get(front.size() - 1).setCrowdingDistance(Double.POSITIVE_INFINITY);
            }
        }
    }

    /*
     * Book - step 1, part 2
     * Nondominated sorting of the population, splitting the population into fronts
     * First front is all nondominated examples, then the second front is all nondominated examples
     * from the subset population - all examples that went to the first front
     * */
    public List<List<Instance>> nonDominatedSort(List<Instance> population) {
        List<List<Instance>> fronts = new ArrayList<>();

        int frontNumber = 0;
        while (population.size() > 0) {
            List<Instance> front = new ArrayList<>();

            // population with all the dominated examples
            List<Instance> nextPopulation = new ArrayList<>();

            // repeat for every X from P
            for (Instance instance : population) {
                // dominated = no
                boolean dominated = false;

                // repeat for every Y from P
                for (Instance other : population) {
                    // if X == Y continue
                    if (instance.equals(other)) continue;

                    // if Y dominates X
                    if (other.dominates(instance)) {
                        dominated = true; // dominated = true
                        break;
                    }
                }

                if (dominated) {
                    /// add the dominated examples to the next population
                    nextPopulation.add(instance);
                    continue;
                }

                // if not dominated then add to this front
                front.add(instance);
                instance.setFrontNumber(frontNumber);
            }

            fronts.add(front);

            population = nextPopulation;
            frontNumber++;
        }

        return fronts;
    }

    /*
     * Book - step 4
     * Create a new child population by using a grouping tournament selection, crossover, and mutation
     * */
    public List<Instance> generateKids(List<Instance> parents) {
        List<Instance> kids = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            // The logic behind grouping tournament selection is in the implementation
            List<Instance> selectedParents = selection.select(parents, 2);

            Instance parent1 = selectedParents.get(0);
            Instance parent2 = selectedParents.get(1);

            List<Instance> children = crossover.crossover(parent1, parent2);

            if (kids.size() < populationSize) {
                kids.add(children.get(0));
            } else break;

            if (kids.size() < populationSize) {
                kids.add(children.get(1));
            } else break;
        }

        for (Instance kid : kids) {
            mutation.mutate(kid);
        }

        // evaluate kids (kind of?)
        for (Instance kid : kids) {
            double[] objectiveValues = problem.evaluateSolution(kid.getSolutionValues());
            kid.setObjectiveValues(objectiveValues);
        }

        return kids;
    }

    /*
     * Book - step 1, part 1
     * Make a union of parents and kids.
     * */
    public List<Instance> combineParentsAndKids(List<Instance> parents, List<Instance> kids) {
        List<Instance> population = new ArrayList<>();
        population.addAll(parents);
        population.addAll(kids);

        return population;
    }

    /*
     * Book - steps 2 & 3
     * Add solutions to the new population starting from the best front
     * Add all fronts that fully fit into the new population
     * The last front that fits partially, add the examples with the highest crowding distance
     * */
    public List<Instance> combinePopulations(List<List<Instance>> fronts) {
        List<Instance> population = new ArrayList<>();

        for (List<Instance> front : fronts) {
            // add all fronts that fully fit into population
            if (population.size() + front.size() < populationSize) {
                population.addAll(front);
            } else {
                // add the examples from the front that have the largest crowding distance
                front.sort((o1, o2) -> Double.compare(o2.getCrowdingDistance(), o1.getCrowdingDistance()));
                population.addAll(front.subList(0, populationSize - population.size()));
                break;
            }
        }

        return population;
    }

    /*
     * Generate N random instances
     * */
    public List<Instance> generatePopulation() {
        List<Instance> instances = new ArrayList<>();

        List<double[]> bounds = problem.getBounds();
        for (int i = 0; i < populationSize; i++) {
            double[] solution = new double[problem.getSolutionSize()];

            for (int j = 0; j < solution.length; j++) {
                double[] bound = bounds.get(j);
                solution[j] = bound[0] + Math.random() * (bound[1] - bound[0]);
            }

            Instance instance = new Instance(solution, problem.getNumberOfObjectives());
            double[] objectiveValues = problem.evaluateSolution(instance.getSolutionValues());
            instance.setObjectiveValues(objectiveValues);
            instances.add(instance);
        }

        return instances;
    }
}
