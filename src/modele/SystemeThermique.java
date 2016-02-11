package modele;

import modele.radiateur.Radiateur;
import modele.exterieur.Exterieur;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ListPropertyBase;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;

/**
 *
 * @author Maxime BLAISE
 * @author Antoine NOSAL
 */
public class SystemeThermique {

    /**
     * Temps courant (incrémentateur).
     */
    private int tempsCourant;

    /**
     * Piece du système.
     */
    private Piece piece;

    /**
     * Environnement extérieur.
     */
    private Exterieur environnement;

    /**
     * Radiateur de la pièce, pour la chauffer.
     */
    private Radiateur radiateur;

    public ListProperty<Data<Double, Double>> listeDesTemperatures;

    /**
     * Crée un système thermique avec une pièce, un environnement et un
     * radiateurs.
     *
     * @param piece .
     * @param environnement .
     * @param radiateur .
     */
    public SystemeThermique(Piece piece, Exterieur environnement, Radiateur radiateur) {
        this.listeDesTemperatures = new SimpleListProperty<>(FXCollections.observableArrayList());
        this.tempsCourant = 0;
        this.piece = piece;
        this.environnement = environnement;
        this.radiateur = radiateur;
    }
    
    public void demarrerSimulation() {
        this.tempsCourant = 0;
        for(int i=0;i<100;i++) {
            evolutionTemperature();
        }
    }
    
    public void arreterSimulation() {
        this.listeDesTemperatures.removeAll(this.listeDesTemperatures);
    }

    /**
     * Evolution de la température.
     */
    public void evolutionTemperature() {
        // Mise à jour de notre liste de températures
        //System.out.println("Ajout de " + (double)this.tempsCourant + " - " + this.piece.getTemperatureAmbiante());
        this.listeDesTemperatures.add(new XYChart.Data<>((double)this.tempsCourant, this.piece.getTemperatureAmbiante()));
        // Calcul du thermostat
        double thermostat = this.radiateur.getThermostat(piece, environnement.getTemperatureExterieure(tempsCourant));
        // Calcul de la nouvelle température
        double nouvelleTemperature = this.calculerProchaineTemperature(thermostat);
        // Modification de l'état du radiateur
        this.radiateur.setEtatRadiateur(nouvelleTemperature);
        // Modification des données
        piece.setTemperatureAmbiante(nouvelleTemperature);
        this.tempsCourant++;
        //afficherEtatSysteme(thermostat);
    }

    /**
     * Calcule la température au prochain pas de simulation.
     *
     * @param thermostat Thermostat actuel du radiateur
     * @return La température
     */
    private double calculerProchaineTemperature(double thermostat) {
        return piece.getTemperatureAmbiante() + thermostat + piece.getIsolation() * (environnement.getTemperatureExterieure(this.tempsCourant) - piece.getTemperatureAmbiante());

    }

    public void afficherEtatSysteme(double dT) {
        NumberFormat nf = new DecimalFormat("#.##");
        String s = nf.format(piece.getTemperatureAmbiante());
        System.out.println(tempsCourant + " | T.int:" + s + " | Obj.:" + radiateur.getConsigne() + " | dT:" + dT + " | T.ext:" + environnement.getTemperatureExterieure(this.tempsCourant));
    }

    public int getTempsCourant() {
        return tempsCourant;
    }

    public void setTempsCourant(int tempsCourant) {
        this.tempsCourant = tempsCourant;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Exterieur getEnvironnement() {
        return environnement;
    }

    public void setEnvironnement(Exterieur environnement) {
        this.environnement = environnement;
    }

    public Radiateur getRadiateur() {
        return radiateur;
    }

    public void setRadiateur(Radiateur radiateur) {
        this.radiateur = radiateur;
    }


}
