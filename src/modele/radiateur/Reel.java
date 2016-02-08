package modele.radiateur;

import modele.Piece;

/**
 *
 * @author Maxime BLAISE
 * @author Antoine NOSAL
 */
public class Reel extends Radiateur {

    public Reel(double puissanceMax, double consigne) {
        super(puissanceMax, consigne);
    }


    @Override
    public double getThermostat(Piece piece, double temperatureExterieure) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setEtatRadiateur(double temperatureAmbiante) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
