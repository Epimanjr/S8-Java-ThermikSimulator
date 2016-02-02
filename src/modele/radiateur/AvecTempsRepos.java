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
    public double getThermostat(Piece piece, int temperatureExterieure) {
        if (this.repos) {
            if (this.reposDepuis >= this.tempsRepos) {
                this.reposDepuis = 0;
                this.repos = false;
            }
        }

        // Calcul thermostat
        double dT = 0;
        if (this.repos) {
            this.reposDepuis++;
        } else {
            dT = this.calculerThermostat(piece,temperatureExterieure);
        }

        return dT;
    }

    @Override
    public void setEtatRadiateur(double temperatureAmbiante) {
        if (temperatureAmbiante > (this.getConsigne())) {
            this.repos = true;
        }
    }
    
}
