package modele;

import modele.radiateur.Radiateur;
import modele.exterieur.Exterieur;
import java.util.logging.Level;
import java.util.logging.Logger;
import modele.exterieur.TemperatureConstante;
import modele.radiateur.AvecTempsRepos;

/**
 *
 * @author Maxime BLAISE
 * @author Antoine NOSAL
 */
public class Main {
    
    /**
     * Durée d'un pas de simulation.
     */
    public static int msEntreChaquePasDeSimulation = 1000;
    
    /**
     * Méthode principale
     * @param args arguments
     */
    public static void main(String[] args) {
        // Création d'un système thermique
        Piece piece = new Piece(15, 0.1);
        Exterieur environnement = new TemperatureConstante(5);
        Radiateur radiateur = new AvecTempsRepos(2, 22);
        SystemeThermique systeme = new SystemeThermique(piece, environnement, radiateur);
        // Loop
        while(true) {
            systeme.evolutionTemperature();
            try {
                Thread.sleep(Main.msEntreChaquePasDeSimulation);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
