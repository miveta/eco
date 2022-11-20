package hr.fer.zemris.gp.tasks;

import hr.fer.zemris.gp.population.Instance;
import hr.fer.zemris.gp.population.Operators;
import hr.fer.zemris.gp.population.node.Node;
import hr.fer.zemris.gp.population.node.NodeVariable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Function2 extends AbstractFunction{
    public Instance correct(){
        // x * y + sin (x + y) * cos(y)

        Node root = new Node(Operators.ADD);
        Node left = new Node(Operators.MUL);
        Node leftLeft = new Node(new NodeVariable("x0"));
        Node leftRight = new Node(new NodeVariable("x1"));

        Node right = new Node(Operators.MUL);
        Node rightLeft = new Node(Operators.SIN);
        Node rightLeftLeft = new Node(Operators.ADD);
        Node rightLeftLeftLeft = new Node(new NodeVariable("x0"));
        Node rightLeftLeftRight = new Node(new NodeVariable("x1"));

        Node rightRight = new Node(Operators.COS);
        Node rightRightLeft = new Node(new NodeVariable("x1"));

        rightLeftLeft.setLeft(rightLeftLeftLeft);
        rightLeftLeft.setRight(rightLeftLeftRight);

        rightLeft.setLeft(rightLeftLeft);

        rightRight.setLeft(rightRightLeft);

        right.setLeft(rightLeft);
        right.setRight(rightRight);

        left.setLeft(leftLeft);
        left.setRight(leftRight);

        root.setLeft(left);
        root.setRight(right);


        return new Instance(root);
    }
    public Function2() throws IOException {
        String path = "src/main/resources/f2.txt";

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

    public int numberOfVariables(){
        return 2;
    }
}
