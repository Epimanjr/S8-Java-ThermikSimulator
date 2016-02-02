package modele.exterieur;

/**
 *
 * @author Maxime BLAISE
 */
public class TemperatureConstante extends Exterieur {

    /**
     * Température extérieure actuelle.
     */
    private double temperatureExterieure;

    /**
     * Constructeur qui permet d'initiliser une température extérieure.
     *
     * @param temperatureExterieure Température extérieure actuelle.
     */
    public TemperatureConstante(double temperatureExterieure) {
        this.temperatureExterieure = temperatureExterieure;
    }

    /**
     * Permet de modifier manuellement la température extérieure.
     *
     * @param temperatureExterieure Température extérieure actuelle.
     */
    public void setTemperatureExterieure(double temperatureExterieure) {
        this.temperatureExterieure = temperatureExterieure;
    }

    @Override
    public double getTemperatureExterieure(double temps) {
        return this.temperatureExterieure;
    }

}
