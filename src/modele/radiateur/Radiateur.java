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
    public abstract double getThermostat(Piece piece, double temperatureExterieure);

    /**
     * Méthode abstraite qui permet de modifier l'état du radiateur.
     * 
     * @param temperatureAmbiante Température actuelle de la pièce
     */
    public abstract void setEtatRadiateur(double temperatureAmbiante);
    
    /**
     * Méthode qui permet de calculer le thermostat du radiateur.
     * 
     * @param piece Piece dans laquelle se trouve le radiateur
     * @param temperatureExterieure Température extérieure de la pièce
     * @return 
     */
    protected double calculerThermostat(Piece piece, double temperatureExterieure) {
        double dT;
        System.err.println(this.getConsigne() + " - " + piece.getTemperatureAmbiante() + " - " + piece.getIsolation() + " ( " + temperatureExterieure + " - " + piece.getTemperatureAmbiante() + " ) ");
        dT = this.getConsigne() + 0.5 - piece.getTemperatureAmbiante() - piece.getIsolation() * (temperatureExterieure - piece.getTemperatureAmbiante());
        if (dT > this.getPuissanceMax()) {
            dT = this.getPuissanceMax();
        }
        if (dT < 0) {
            dT = 0;

        }
        return dT;
    }
    
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
