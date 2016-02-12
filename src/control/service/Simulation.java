package control.service;

import graphique.MainFrame;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

/**
 *
 * @author Maxime
 */
public abstract class Simulation {

    /**
     * Série qui représente les températures à l'intérieur de la pièce.
     */
    private final XYChart.Series<Double, Double> serieInterieure;

    /**
     * Série qui représente les températures de l'environnement extérieur.
     */
    private final XYChart.Series<Double, Double> serieExterieure;

    /**
     * Série qui représente l'objectif à atteindre
     */
    private final XYChart.Series<Double, Double> serieObjectif;

    /**
     * Objet qui représente notre graphique (en ligne).
     */
    private final LineChart<Double, Double> graph;

    /**
     * Constructeur qui initialise les séries.
     *
     * @param graph Objet qui représente notre graphique (en ligne).
     */
    public Simulation(LineChart<Double, Double> graph) {
        this.graph = graph;
        this.serieInterieure = new XYChart.Series<>();
        this.serieInterieure.setName("Température intérieure");
        this.serieExterieure = new XYChart.Series<>();
        this.serieExterieure.setName("Température extérieure");
        this.serieObjectif = new XYChart.Series<>();
        this.serieObjectif.setName("Objectif");
        // Initialisation du système
        MainFrame.initialiserSysteme();
    }

    /**
     * Lancement de la simulation.
     */
    public abstract void run();

    public XYChart.Series<Double, Double> getSerieInterieure() {
        return serieInterieure;
    }

    public XYChart.Series<Double, Double> getSerieExterieure() {
        return serieExterieure;
    }

    public XYChart.Series<Double, Double> getSerieObjectif() {
        return serieObjectif;
    }
    
    

    public LineChart<Double, Double> getGraph() {
        return graph;
    }

}
