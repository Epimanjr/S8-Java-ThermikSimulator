/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import graphique.MainFrame;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import modele.exterieur.TemperatureSinusoid;

/**
 *
 * @author Maxime BLAISE
 */
public class RadiateurControleur implements Initializable {

    
    @FXML private LineChart<Double, Double> graph;
    @FXML private Button btDemarrer;
    @FXML private Button btTemperatureSinus;

    private final LineChart.Series<Double, Double> serieInterieure = new LineChart.Series<>();
    private final LineChart.Series<Double, Double> serieExterieure = new LineChart.Series<>();

    private Thread threadSimulation;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Nommage des séries
        serieInterieure.setName("Température intérieure");
        serieExterieure.setName("Température extérieure");
        // Ajout des séries au graphe
        graph.getData().add(serieInterieure);
        graph.getData().add(serieExterieure);
        // Instanciation du thread de simulation
        threadSimulation = new Thread(new ThreadSimulation(serieInterieure, serieExterieure));
        threadSimulation.setDaemon(true);
    }
    
    @FXML
    private void temperatureSinus(ActionEvent event) {
        MainFrame.systeme.setEnvironnement(new TemperatureSinusoid(14, 10, 10));
    }

    @FXML
    private void handleButton(ActionEvent event) {
        serieInterieure.getData().add(new XYChart.Data<>(
                10.0, 10.0
        ));
    }
    
    @FXML
    private void demarrerSimulation(ActionEvent event) {
        try {
            threadSimulation.start();
        } catch(Exception e) {
            System.err.println("Erreur de thread");
        }
    }
    
}
