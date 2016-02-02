package modele.radiateur;

import modele.Piece;

/**
 * @author Maxime BLAISE
 * @author Antoine NOSAL
 */
public class AvecTempsRepos extends Radiateur {

    public static int tempsRepos = 5;
    private boolean repos;
    private int reposDepuis = 0;

    public AvecTempsRepos(double puissanceMax, double consigne) {
        super(puissanceMax, consigne);
        this.repos = false;
    }

    @Override
    public double getThermostat(Piece piece, double temperatureExterieure) {
        return (this.isEteint() || this.repos) ? 0 : this.calculerThermostat(piece, temperatureExterieure);
    }

    @Override
    public void setEtatRadiateur(double temperatureAmbiante) {
        if (this.repos) {
            // En repos
            if (this.reposDepuis >= AvecTempsRepos.tempsRepos) {
                this.reposDepuis = 0;
                this.activerLeRadiateur();
            } else {
                this.reposDepuis++;
            }
        } else // Pas en repos
        {
            if (temperatureAmbiante > (this.getConsigne())) {
                this.desactiverLeRadiateur();
            }
        }
    }

    /**
     * Méthode qui active le radiateur.
     */
    private void activerLeRadiateur() {
        this.repos = false;
    }

    /**
     * Méthode qui desactive le radiateur.
     */
    private void desactiverLeRadiateur() {
        this.repos = true;
    }

}
