package hr.fer.zemris.gp.tasks;

import hr.fer.zemris.gp.population.Instance;
import hr.fer.zemris.gp.population.Operators;
import hr.fer.zemris.gp.population.node.Node;
import hr.fer.zemris.gp.population.node.NodeVariable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Function1 extends AbstractFunction{
    public Instance correct(){
        // x + x**2 sin(x)

        Node root = new Node(Operators.ADD);
        Node left = new Node(new NodeVariable("x0"));
        Node right = new Node(Operators.MUL);
        Node rightLeft = new Node(Operators.MUL);
        Node rightLeftLeft = new Node(new NodeVariable("x0"));
        Node rightLeftRight = new Node(new NodeVariable("x0"));

        Node rightRight = new Node(Operators.SIN);
        Node rightRightLeft = new Node(new NodeVariable("x0"));

        rightLeft.setLeft(rightLeftLeft);
        rightLeft.setRight(rightLeftRight);

        rightRight.setLeft(rightRightLeft);

        right.setLeft(rightLeft);
        right.setRight(rightRight);

        root.setLeft(left);
        root.setRight(right);

        return new Instance(root);
    }

    public int numberOfVariables(){
        return 1;
    }
    public Function1() throws IOException {
        String path = "src/main/resources/f1.txt";

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
