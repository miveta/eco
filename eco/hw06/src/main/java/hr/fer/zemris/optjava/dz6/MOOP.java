package hr.fer.zemris.optjava.dz6;

import hr.fer.zemris.optjava.dz6.crossover.ICrossover;
import hr.fer.zemris.optjava.dz6.crossover.UniformCrossover;
import hr.fer.zemris.optjava.dz6.mutation.IMutation;
import hr.fer.zemris.optjava.dz6.mutation.UniformMutation;
import hr.fer.zemris.optjava.dz6.population.Instance;
import hr.fer.zemris.optjava.dz6.problems.MOOPProblem;
import hr.fer.zemris.optjava.dz6.problems.Problem1;
import hr.fer.zemris.optjava.dz6.problems.Problem2;
import hr.fer.zemris.optjava.dz6.selection.GroupingTournamentSelection;
import hr.fer.zemris.optjava.dz6.selection.ISelection;

import javax.swing.*;
import java.io.FileWriter;
import java.util.List;

public class MOOP {
    public static void main(String[] args) {
        int fja = 2;
        boolean plot = true;
        int populationSize = 100;
        int maxIterations = 10;

        MOOPProblem problem1 = new Problem1();
        MOOPProblem problem2 = new Problem2();
        MOOPProblem problem = fja == 1 ? problem1 : problem2;

        ISelection selection = new GroupingTournamentSelection();
        IMutation mutation = new UniformMutation(problem.getBounds());
        ICrossover crossover = new UniformCrossover();

        NSGAII nsgaii = new NSGAII(maxIterations, populationSize, problem, crossover, mutation, selection);

        List<List<Instance>> fronts = nsgaii.optimise();

        for (List<Instance> front : fronts) {
            System.out.println("Front " + front.get(0).getFrontNumber() + " num solutions: " + front.size());
        }


        StringBuilder sbDec = new StringBuilder();
        StringBuilder sbObj = new StringBuilder();

        for (List<Instance> front : fronts) {
            for (Instance instance : front) {
                for (int i = 0; i < instance.getSolutionValues().length; i++) {
                    sbDec.append(instance.getSolutionValues()[i]).append(" ");
                }

                sbDec.append("\n");

                for (int i = 0; i < instance.getObjectiveValues().length; i++) {
                    sbObj.append(instance.getObjectiveValues()[i]).append(" ");
                }
                sbObj.append("\n");

            }
        }

        String pathDec = "src/main/resources/izlaz-dec" + fja + ".txt";
        String pathObj = "src/main/resources/izlaz-obj" + fja + ".txt";

        try (FileWriter fw = new FileWriter(pathDec)) {
            fw.write(sbDec.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (FileWriter fw = new FileWriter(pathObj)) {
            fw.write(sbObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (fja == 2 && plot) {
            SwingUtilities.invokeLater(() -> {
                Visualise example = new Visualise(fronts);
                example.setSize(800, 400);
                example.setLocationRelativeTo(null);
                example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                example.setVisible(true);
            });

        }
    }
}
