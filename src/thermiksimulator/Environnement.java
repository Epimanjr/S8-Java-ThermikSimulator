package thermiksimulator;

/**
 *
 * @author Maxime BLAISE
 * @author Antoine NOSAL
 */
public class Environnement {

    /**
     * Température fixe de l'environnement.
     */
    private double temperatureFixe;

    /**
     * Crée l'environnement avec une certaine température.
     *
     * @param temperatureFixe .
     */
    public Environnement(double temperatureFixe) {
        this.temperatureFixe = temperatureFixe;
    }

    public double getTemperatureFixe() {
        return temperatureFixe;
    }

    public void setTemperatureFixe(double temperatureFixe) {
        this.temperatureFixe = temperatureFixe;
    }

}
