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
     * <-> Repos forcé, savoir si le radiateur est allumé ou pas.
     */
    private boolean eteint;

    /**
     * Crée un radiateur à l'aide de plusieurs paramètres.
     *
     * @param puissanceMax
     * @param consigne
     */
    public Radiateur(double puissanceMax, double consigne) {
        this.puissanceMax = puissanceMax;
        this.consigne = consigne;
        this.eteint = false;
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
    
    /**
     * Méthode qui allume le radiateur.
     */
    public void allumerLeRadiateur() {
        this.setEteint(false);
    }
    
    /**
     * Méthode qui éteint le radiateur.
     */
    public void eteindreLeRadiateur() {
        this.setEteint(true);
    }

    /**
     * Pour savoir si le radiateur est allumé ou éteint.
     *
     * @return Vrai si allumé / Faux si éteint
     */
    public boolean isEteint() {
        return eteint;
    }

    /**
     * Permet d'allumer ou d'éteindre le radiateur.
     *
     * @param eteint Vrai pour l'allumer / Faux pour l'éteindre
     */
    public void setEteint(boolean eteint) {
        this.eteint = eteint;
    }

}
