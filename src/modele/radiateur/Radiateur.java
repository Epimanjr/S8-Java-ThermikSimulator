package modele.radiateur;

import modele.Piece;

/**
 * @author Maxime BLAISE
 * @author Antoine NOSAL
 */
public abstract class Radiateur {

    /**
     * Puissance maximale en C°/min.
     */
    private double puissanceMax;

    /**
     * Températeur de consigne en C°.
     */
    private double consigne;

    /**
     * Crée un radiateur à l'aide de plusieurs paramètres.
     *
     * @param puissanceMax
     * @param consigne
     */
    public Radiateur(double puissanceMax, double consigne) {
        this.puissanceMax = puissanceMax;
        this.consigne = consigne;
    }

    /**
     * Méthode abstraite qui permet de récupérer le thermostat.
     *
     * @param piece Pièce dans laquelle se trouve le radiateur.
     * @param temperatureExterieure Température extérieure actuelle.
     * @return
     */
    public abstract double getThermostat(Piece piece, int temperatureExterieure);

    /**
     * Retourne la puissance maximale (C°/min)
     *
     * @return double
     */
    public double getPuissanceMax() {
        return puissanceMax;
    }

    /**
     * Définit la puissance maximale (C°/min)
     *
     * @param puissanceMax
     */
    public void setPuissanceMax(double puissanceMax) {
        this.puissanceMax = puissanceMax;
    }

    /**
     * Retourne la température de consigne (C°)
     *
     * @return
     */
    public double getConsigne() {
        return consigne;
    }

    /**
     * Définit la températeur de consigne (C°)
     *
     * @param consigne
     */
    public void setConsigne(double consigne) {
        this.consigne = consigne;
    }

}
