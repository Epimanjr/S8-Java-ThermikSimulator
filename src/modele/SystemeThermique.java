package modele;

import modele.radiateur.Radiateur;
import modele.exterieur.Exterieur;
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
    private Exterieur environnement;

    /**
     * Radiateur de la pièce, pour la chauffer.
     */
    private Radiateur radiateur;

    private boolean repos = false;
    private int reposDepuis = 0;

    private double puissanceRadiateurActuelle = 0;

    /**
     * Temps de repos en minutes du radiateur
     */
    public static int tempsRepos = 5;

    /**
     * Repos forcé, par l'interface graphique.
     */
    public boolean reposForce = false;

    /**
     * Automatisation de la température auto.
     */
    public boolean tempExtAuto = false;

    /**
     * Crée un système thermique avec une pièce, un environnement et un
     * radiateurs.
     *
     * @param piece .
     * @param environnement .
     * @param radiateur .
     */
    public SystemeThermique(Piece piece, Exterieur environnement, Radiateur radiateur) {
        this.tempsCourant = 0;
        this.piece = piece;
        this.environnement = environnement;
        this.radiateur = radiateur;
    }

    /**
     * Evolution de la température.
     */
    public void evolutionTemperature() {
        if (this.repos) {
            if (this.reposDepuis >= SystemeThermique.tempsRepos) {
                this.reposDepuis = 0;
                this.repos = false;
            }
        }

        calculTemperatureExterieure(this.tempsCourant);

        // Calcul thermostat
        double dT = calculdT();

        // Calcul de la température au temps t+1
        double t = piece.getTemperatureAmbiante() + dT + piece.getIsolation() * (environnement.getTemperatureFixe() - piece.getTemperatureAmbiante());
        if (t > (radiateur.getConsigne() - 0.1)) {
            this.repos = true;
        }

        piece.setTemperatureAmbiante(t);
        this.puissanceRadiateurActuelle = dT;

        this.tempsCourant++;
        afficherEtatSysteme(dT);
    }

    public void calculTemperatureExterieure(double t) {
        if (this.tempExtAuto) {
            this.environnement.setTemperatureFixe(Math.sin((t * Math.PI) / 10) * 10 + 14);
        }
    }

    public double calculdT() {
        double dT = 0;
        if (this.repos || this.reposForce) {
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
        return dT;
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

    public double getPuissanceRadiateurActuelle() {
        return puissanceRadiateurActuelle;
    }

    public void setPuissanceRadiateurActuelle(double puissanceRadiateurActuelle) {
        this.puissanceRadiateurActuelle = puissanceRadiateurActuelle;
    }

}
