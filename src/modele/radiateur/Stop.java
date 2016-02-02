package modele.radiateur;

import modele.Piece;

/**
 * Ce mode de fonctionnement éteint totalement le radiateur une fois 
 * le seuil de température atteint.
 * 
 * @author Maxime BLAISE
 * @author Antoine NOSAL
 */
public class Stop extends Radiateur {

    public Stop(double puissanceMax, double consigne) {
        super(puissanceMax, consigne);
    }

    @Override
    public double getThermostat(Piece piece, int temperatureExterieure) {
        return (this.isEteint()) ? 0 : calculerThermostat(piece, temperatureExterieure);
    }

    @Override
    public void setEtatRadiateur(int temperatureAmbiante) {
        if(temperatureAmbiante >= this.getConsigne()) {
            this.eteindreLeRadiateur();
        }
    }


    
}
