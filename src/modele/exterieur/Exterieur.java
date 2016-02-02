package modele.exterieur;

/**
 *
 * @author Maxime BLAISE
 * @author Antoine NOSAL
 */
public class Exterieur {

    /**
     * Température fixe de l'environnement.
     */
    private double temperatureFixe;

    /**
     * Crée l'environnement avec une certaine température.
     *
     * @param temperatureFixe .
     */
    public Exterieur(double temperatureFixe) {
        this.temperatureFixe = temperatureFixe;
    }

    public double getTemperatureFixe() {
        return temperatureFixe;
    }

    public void setTemperatureFixe(double temperatureFixe) {
        this.temperatureFixe = temperatureFixe;
    }

}
