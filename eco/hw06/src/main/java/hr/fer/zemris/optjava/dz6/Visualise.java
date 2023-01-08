package hr.fer.zemris.optjava.dz6;

import hr.fer.zemris.optjava.dz6.population.Instance;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Visualise extends JFrame {
    private List<List<Instance>> fronts;

    public Visualise(List<List<Instance>> fronts) {
        this.fronts = fronts;

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);
        setVisible(true);

        XYDataset dataset = createDataset();

        JFreeChart chart = ChartFactory.createScatterPlot("Pareto fronta", "x", "y", dataset, org.jfree.chart.plot.PlotOrientation.VERTICAL, true, true, false);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(new Color(255, 228, 196));

        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    private XYDataset createDataset() {
        XYSeriesCollection dataset = new XYSeriesCollection();

        for (List<Instance> front : fronts) {
            XYSeries series = new XYSeries("Front " + fronts.indexOf(front));
            for (Instance instance : front) {
                series.add(instance.getObjectiveValues()[0], instance.getObjectiveValues()[1]);
            }
            dataset.addSeries(series);
        }

        return dataset;
    }
}
