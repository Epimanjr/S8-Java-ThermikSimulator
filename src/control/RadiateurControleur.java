/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import exception.ValeurIncorrecteException;
import graphique.MainFrame;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import modele.ValeurParDefaut;
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
    private ThreadSimulation threadSimulation;
    private Thread thread;
    
    @FXML private TextField tfPas;
    
    // Boutons
    @FXML private Button btDemarrer;
    @FXML private Button btTemperatureSinus;
    
    // Saisies (pièce)
    @FXML private TextField tfPieceIsolation;
    
    // Saisies (radiateur)
    @FXML private RadioButton rbAvecTempsRepos;
    @FXML private TextField tfRadPuissanceMax;
    @FXML private TextField tfRadAllumer;
    @FXML private TextField tfRadEteindre;
    @FXML private TextField tfRadConsigne;
    
    // Saisies (environnement)
    @FXML private RadioButton rbEnvTemperatureFixe;
    @FXML private RadioButton rbEnvTemperatureSinus;
    @FXML private TextField tfEnvTemperatureFixe;
    @FXML private TextField tfEnvTemperatureMoyenne;
    @FXML private TextField tfEnvAmplitude;
    @FXML private TextField tfEnvPeriode;
            
    // Slider(s)
    @FXML private Slider sliderPieceIsolation;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Nommage des séries
        serieInterieure.setName("Température intérieure");
        serieExterieure.setName("Température extérieure");
        // Ajout des séries au graphe
        graph.getData().add(serieInterieure);
        graph.getData().add(serieExterieure);
        // Instanciation du thread de simulation
        threadSimulation = new ThreadSimulation(serieInterieure, serieExterieure);
        thread = new Thread(threadSimulation);
        thread.setDaemon(true);
        // Groupe de RadioButton
        final ToggleGroup tg = new ToggleGroup();
        rbEnvTemperatureFixe.setToggleGroup(tg);
        rbEnvTemperatureSinus.setToggleGroup(tg);
        // Initialisation des sliders
    }
    
    private void initialiserSliders() {
        // Slider pièce isolation
        sliderPieceIsolation.setMin(ParametresControl.minIsolation);
        sliderPieceIsolation.setMax(ParametresControl.maxIsolation);
        sliderPieceIsolation.setBlockIncrement((ParametresControl.maxIsolation - ParametresControl.minIsolation)/10);
    }
    
    @FXML
    public void changerPas(ActionEvent event) {
        try {
            Integer pas = Integer.parseInt(tfPas.getText());
            threadSimulation.setPas(pas);
        } catch (Exception e) {
            tfPas.setText(threadSimulation.getPas()+"");
        }
       
    }
    
    @FXML
    private void pieceIsolation(ActionEvent event) {
        // Récupération de la valeur pour vérification
        double isolation = sliderPieceIsolation.getValue();
        if(isolation < ParametresControl.minIsolation || isolation > ParametresControl.maxIsolation) {
            System.err.println("Erreur: mauvaise valeur pour l'isolation de la pièce.");
        } else {
            MainFrame.systeme.getPiece().setIsolation(isolation);
        }
    }
    
    @FXML
    private void radPuissanceMax(ActionEvent event) {
        Double old = MainFrame.systeme.getRadiateur().getPuissanceMax();
        try {
            Double d = Double.parseDouble(tfRadPuissanceMax.getText());
            MainFrame.systeme.getRadiateur().setPuissanceMax(d);
        } catch(Exception e) {
            tfRadPuissanceMax.setText(old.toString());
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
        Double old = MainFrame.systeme.getRadiateur().getConsigne();
        try {
            Double d = Double.parseDouble(tfRadConsigne.getText());
            MainFrame.systeme.getRadiateur().setConsigne(d);
        } catch(Exception e) {
            tfRadConsigne.setText(old.toString());
        }
    }
    
    @FXML
    private void radAvecTempsRepos(ActionEvent event) {
        Double oldRadPuissanceMax = MainFrame.systeme.getRadiateur().getPuissanceMax();
        Double oldRadConsigne = MainFrame.systeme.getRadiateur().getConsigne();
        try {
            Double d1 = Double.parseDouble(tfRadPuissanceMax.getText());
            Double d2 = Double.parseDouble(tfRadConsigne.getText());
            MainFrame.systeme.setRadiateur(new AvecTempsRepos(d1, d2));
        } catch(Exception e) {
            tfRadPuissanceMax.setText(oldRadPuissanceMax.toString());
            tfRadConsigne.setText(oldRadConsigne.toString());
        }
    }
    
    @FXML
    private void envTemperatureFixe(ActionEvent event) {
        Double old = MainFrame.systeme.getEnvironnement().getTemperatureExterieure(threadSimulation.getTemps());
        try {
            Double d = Double.parseDouble(tfEnvTemperatureFixe.getText());
            if(MainFrame.systeme.getEnvironnement() instanceof TemperatureConstante) {
                TemperatureConstante tc = (TemperatureConstante)MainFrame.systeme.getEnvironnement();
                tc.setTemperatureExterieure(d);
            }
        } catch(Exception e) {
            tfEnvTemperatureFixe.setText(old.toString());
        }
    }
    
    @FXML
    private void envTemperatureSinus(ActionEvent event) {
        try {
            double tempMoyenne = getEnvTemperatureMoyenne(tfEnvTemperatureMoyenne.getText());
            double amplitude = getEnvAmplitude(tfEnvAmplitude.getText());
            double periode = getEnvPeriode(tfEnvPeriode.getText());
            MainFrame.systeme.setEnvironnement(new TemperatureSinusoid(tempMoyenne, amplitude, periode));
        } catch (ValeurIncorrecteException ex) {
        }
    }
    
    @FXML
    private double getEnvTemperatureMoyenne(String envTemperatureMoyenne) throws ValeurIncorrecteException {
        try {
            Double d = Double.parseDouble(envTemperatureMoyenne);
            // Ici, test min/max au besoin
            return d;
        } catch (Exception e) {
        }
        // Si on arrive ici, c'est qu'on a rien retourné donc soit c'est pas un double soit pas entre min&max
        // Si températureSinus, on remet la valeur d'avant, et on lève l'exception
        if(MainFrame.systeme.getEnvironnement() instanceof TemperatureSinusoid) {
            TemperatureSinusoid ts = (TemperatureSinusoid)MainFrame.systeme.getEnvironnement();
            tfEnvTemperatureMoyenne.setText(ts.getTemperatureMoyenne()+"");
        }
        throw new ValeurIncorrecteException();
    }
    
    @FXML
    private double getEnvAmplitude(String envAmplitude) throws ValeurIncorrecteException {
        try {
            Double d = Double.parseDouble(envAmplitude);
            return d;
        } catch (Exception e) {
        }
        if(MainFrame.systeme.getEnvironnement() instanceof TemperatureSinusoid) {
            TemperatureSinusoid ts = (TemperatureSinusoid)MainFrame.systeme.getEnvironnement();
            tfEnvAmplitude.setText(ts.getAmplitude()+"");
        }
        throw new ValeurIncorrecteException();
    }
    
    @FXML
    private double getEnvPeriode(String envPeriode) throws ValeurIncorrecteException {
        try {
            Double d = Double.parseDouble(envPeriode);
            return d;
        } catch (Exception e) {   
        }
        if(MainFrame.systeme.getEnvironnement() instanceof TemperatureSinusoid) {
            TemperatureSinusoid ts = (TemperatureSinusoid)MainFrame.systeme.getEnvironnement();
            tfEnvPeriode.setText(ts.getPeriode()+"");
        }
        throw new ValeurIncorrecteException();
    }
    
    @FXML
    private void envTemperatureMoyenne(ActionEvent event) {
        try {
            double tempMoyenne = getEnvTemperatureMoyenne(tfEnvTemperatureMoyenne.getText());
            if(MainFrame.systeme.getEnvironnement() instanceof TemperatureSinusoid) {
                TemperatureSinusoid ts = (TemperatureSinusoid)MainFrame.systeme.getEnvironnement();
                ts.setTemperatureMoyenne(tempMoyenne);
            }
        } catch (ValeurIncorrecteException ex) {
        }
    }
    
    @FXML
    private void envAmplitude(ActionEvent event) {
        try {
            double amplitude = getEnvTemperatureMoyenne(tfEnvAmplitude.getText());
            if(MainFrame.systeme.getEnvironnement() instanceof TemperatureSinusoid) {
                TemperatureSinusoid ts = (TemperatureSinusoid)MainFrame.systeme.getEnvironnement();
                ts.setAmplitude(amplitude);
            }
        } catch (ValeurIncorrecteException ex) {
        }
    }
    
    @FXML
    private void envPeriode(ActionEvent event) {
        try {
            double periode = getEnvTemperatureMoyenne(tfEnvPeriode.getText());
            if(MainFrame.systeme.getEnvironnement() instanceof TemperatureSinusoid) {
                TemperatureSinusoid ts = (TemperatureSinusoid)MainFrame.systeme.getEnvironnement();
                ts.setPeriode(periode);
            }
        } catch (ValeurIncorrecteException ex) {
        }
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
            thread.start();
        } catch(Exception e) {
            System.err.println("Erreur de thread");
        }
    }
    
}
