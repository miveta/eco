package hr.fer.zemris.gp.tasks;

import hr.fer.zemris.gp.population.Instance;
import hr.fer.zemris.gp.population.Operators;
import hr.fer.zemris.gp.population.node.Node;
import hr.fer.zemris.gp.population.node.NodeTerminal;
import hr.fer.zemris.gp.population.node.NodeVariable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Function3 extends AbstractFunction{
    public int numberOfVariables() {
        return 1;
    }
    public Instance correct(){
        // 0.5 * x * x + 1.3

        Node root = new Node(Operators.ADD);
        Node left = new Node(Operators.MUL);

        Node leftLeft = new Node(Operators.MUL);
        Node leftLeftLeft = new Node(new NodeTerminal(0.5));
        Node leftLeftRight = new Node(new NodeVariable("x0"));

        Node leftRight = new Node(new NodeVariable("x0"));
        Node right = new Node(new NodeTerminal(1.3));


        leftLeft.setLeft(leftLeftLeft);
        leftLeft.setRight(leftLeftRight);

        left.setLeft(leftLeft);
        left.setRight(leftRight);

        root.setLeft(left);
        root.setRight(right);


        return new Instance(root);
    }
    public Function3() throws IOException {
        String path = "src/main/resources/f3.txt";

        List<String> lines = Files.readAllLines(Paths.get(path));

        x = new double[lines.size()][];
        y = new double[lines.size()];

        for (int i = 0; i < lines.size(); i++) {
            String[] line = lines.get(i).split("\t");
            x[i] = new double[line.length - 1];
            for (int j = 0; j < line.length - 1; j++) {
                x[i][j] = Double.parseDouble(line[j]);
            }
            y[i] = Double.parseDouble(line[line.length - 1]);
        }
    }
}
