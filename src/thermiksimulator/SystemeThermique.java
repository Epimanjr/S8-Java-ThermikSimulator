package thermiksimulator;

import java.text.DecimalFormat;
import java.text.NumberFormat;

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
    private Environnement environnement;

    /**
     * Radiateur de la pièce, pour la chauffer.
     */
    private Radiateur radiateur;

    private boolean repos = false;
    private int reposDepuis = 0;
    
    private double puissanceRadiateurActuelle= 0;

    /**
     * Temps de repos en minutes du radiateur
     */
    public static int tempsRepos = 10;

    /**
     * Crée un système thermique avec une pièce, un environnement et un
     * radiateurs.
     *
     * @param piece .
     * @param environnement .
     * @param radiateur .
     */
    public SystemeThermique(Piece piece, Environnement environnement, Radiateur radiateur) {
        this.tempsCourant = 0;
        this.piece = piece;
        this.environnement = environnement;
        this.radiateur = radiateur;
    }

    /**
     * Evolution de la température.
     */
    public void evolutionTemperature() {
        if (this.reposDepuis == SystemeThermique.tempsRepos) {
            this.reposDepuis = 0;
            this.repos = false;
        }
        // Calcul de la température au temps t+1
        double dT = 0;
        if (this.repos) {
            this.reposDepuis++;
        } else {
            dT = radiateur.getConsigne() - piece.getTemperatureAmbiante() - piece.getIsolation() * (environnement.getTemperatureFixe() - piece.getTemperatureAmbiante());
            if (dT > radiateur.getPuissanceMax()) {
                dT = radiateur.getPuissanceMax();
            }
            if (dT < 0) {
                dT = 0;

            }
        }

        double t = piece.getTemperatureAmbiante() + dT + piece.getIsolation() * (environnement.getTemperatureFixe() - piece.getTemperatureAmbiante());
        if(t > (radiateur.getConsigne() - 1)) {
            this.repos = true;
        }
        piece.setTemperatureAmbiante(t);
        this.puissanceRadiateurActuelle = dT;
        this.tempsCourant++;
        afficherEtatSysteme(dT);
    }

    public void afficherEtatSysteme(double dT) {
        NumberFormat nf = new DecimalFormat("#.##");
        String s = nf.format(piece.getTemperatureAmbiante());
        System.out.println(tempsCourant + " | T.int:" + s + " | Obj.:" + radiateur.getConsigne() + " | dT:" + dT + " | T.ext:" + environnement.getTemperatureFixe());
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

    public Environnement getEnvironnement() {
        return environnement;
    }

    public void setEnvironnement(Environnement environnement) {
        this.environnement = environnement;
    }

    public Radiateur getRadiateur() {
        return radiateur;
    }

    public void setRadiateur(Radiateur radiateur) {
        this.radiateur = radiateur;
    }

    public double getPuissanceRadiateurActuelle() {
        return puissanceRadiateurActuelle;
    }

    public void setPuissanceRadiateurActuelle(double puissanceRadiateurActuelle) {
        this.puissanceRadiateurActuelle = puissanceRadiateurActuelle;
    }

    
}
