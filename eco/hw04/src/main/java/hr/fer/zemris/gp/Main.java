package hr.fer.zemris.gp;

import hr.fer.zemris.gp.crossover.ICrossover;
import hr.fer.zemris.gp.crossover.SubtreeSwitchCrossover;
import hr.fer.zemris.gp.mutation.IMutation;
import hr.fer.zemris.gp.mutation.RandomSubtreeMutation;
import hr.fer.zemris.gp.population.Instance;
import hr.fer.zemris.gp.population.Operators;
import hr.fer.zemris.gp.population.Population;
import hr.fer.zemris.gp.population.RampedHalfAndHalf;
import hr.fer.zemris.gp.population.node.NodeOperator;
import hr.fer.zemris.gp.population.node.NodeTerminal;

import java.util.ArrayList;
import java.util.List;

public class Main {

        public static void main(String[] args) {
            // TODO Auto-generated method stub
            List<NodeOperator> operators = new ArrayList<>();
            operators.add(Operators.ADD);
            operators.add(Operators.SUB);
            operators.add(Operators.MUL);
            operators.add(Operators.DIV);
            operators.add(Operators.SIN);
            operators.add(Operators.COS);
            operators.add(Operators.LOG);
            operators.add(Operators.EXP);

            List<NodeTerminal> terminals = new ArrayList<>();
            terminals.add(new NodeTerminal(2));
            terminals.add(new NodeTerminal(3));

            RampedHalfAndHalf ramped = new RampedHalfAndHalf(operators, terminals);

            List<Instance> instances = ramped.generatePopulation(10);
            Population population = new Population(instances);

            ICrossover subtreeSwitchCrossover = new SubtreeSwitchCrossover();
            IMutation randomSubtreeMutation = new RandomSubtreeMutation(terminals, operators);


        }

}
