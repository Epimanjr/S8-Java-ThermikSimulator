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
import javafx.scene.control.TextField;
import modele.exterieur.TemperatureConstante;
import modele.exterieur.TemperatureSinusoid;
import modele.radiateur.AvecTempsRepos;

/**
 *
 * @author Maxime BLAISE
 */
public class RadiateurControleur implements Initializable {
    
    // LineChart
    @FXML private LineChart<Double, Double> graph;
    private final LineChart.Series<Double, Double> serieInterieure = new LineChart.Series<>();
    private final LineChart.Series<Double, Double> serieExterieure = new LineChart.Series<>();
    
    // ThreadSimulation
    private Thread threadSimulation;
    
    // Boutons
    @FXML private Button btDemarrer;
    @FXML private Button btTemperatureSinus;
    
    // Saisies (pièce)
    @FXML private TextField tfPieceIsolation;
    
    // Saisies (radiateur)
    @FXML private TextField tfRadPuissanceMax;
    @FXML private TextField tfRadAllumer;
    @FXML private TextField tfRadEteindre;
    @FXML private TextField tfRadConsigne;
    @FXML private TextField tfRadAvecTempsRepos;
    
    // Saisies (environnement)
    @FXML private TextField tfTemperatureFixe;
    @FXML private TextField tfTemperatureSinus;
    
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
    
    private double getDouble(String t) {
        Double d;
        try {
            d = Double.parseDouble(t);
        } catch (Exception e) {
            d = null;
        }
        return d;
    }
    
    @FXML
    private void pieceIsolation(ActionEvent event) {
        Double d = getDouble(tfPieceIsolation.getText());
        if(d!=null) {
            MainFrame.systeme.getPiece().setIsolation(d);
        } else {
            tfPieceIsolation.setText("");
        }
    }
    
    @FXML
    private void radPuissanceMax(ActionEvent event) {
        Double d = getDouble(tfRadPuissanceMax.getText());
        if(d!=null) {
            MainFrame.systeme.getRadiateur().setPuissanceMax(d);
        } else {
            tfRadPuissanceMax.setText("");
        }
    }
    
    @FXML
    private void radAllumer(ActionEvent event) {
        MainFrame.systeme.getRadiateur().allumerLeRadiateur();
    }
    
    @FXML
    private void radEteindre(ActionEvent event) {
        MainFrame.systeme.getRadiateur().eteindreLeRadiateur();
    }
    
    @FXML
    private void radConsigne(ActionEvent event) {
        MainFrame.systeme.getRadiateur().setConsigne(10);
    }
    
    @FXML
    private void radAvecTempsRepos(ActionEvent event) {
        MainFrame.systeme.setRadiateur(new AvecTempsRepos(10, 10));
    }

    @FXML
    private void envTemperatureSinus(ActionEvent event) {
        MainFrame.systeme.setEnvironnement(new TemperatureSinusoid(14, 10, 10));
    }
    
    @FXML
    private void envTemperatureConstante(ActionEvent event) {
        MainFrame.systeme.setEnvironnement(new TemperatureConstante(10));
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
