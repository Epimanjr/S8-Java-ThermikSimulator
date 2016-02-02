package modele;

import modele.exterieur.Exterieur;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maxime BLAISE
 * @author Antoine NOSAL
 */
public class Main {
    
    /**
     * Méthode principale
     * @param args arguments
     */
    public static void main(String[] args) {
        // Création d'un système thermique
        Piece piece = new Piece(15, 0.1);
        Exterieur environnement = new Exterieur(2);
        Radiateur radiateur = new Radiateur(piece, 2, 20);
        SystemeThermique systeme = new SystemeThermique(piece, environnement, radiateur);
        // Loop
        while(true) {
            systeme.evolutionTemperature();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
