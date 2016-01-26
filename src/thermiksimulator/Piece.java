package thermiksimulator;

/**
 *
 * @author Maxime BLAISE
 * @author Antoine NOSAL
 */
public class Piece {

    /**
     * Température ambiance.
     */
    private double temperatureAmbiante;

    /**
     * Isolation.
     */
    private double isolation;

    /**
     * Crée une nouvelle pièce avec une certaine température et isolation.
     *
     * @param temperatureAmbiante .
     * @param isolation .
     */
    public Piece(double temperatureAmbiante, double isolation) {
        this.temperatureAmbiante = temperatureAmbiante;
        this.isolation = isolation;
    }

    public double getTemperatureAmbiante() {
        return temperatureAmbiante;
    }

    public void setTemperatureAmbiante(double temperatureAmbiante) {
        this.temperatureAmbiante = temperatureAmbiante;
    }

    public double getIsolation() {
        return isolation;
    }

    public void setIsolation(double isolation) {
        this.isolation = isolation;
    }

}
