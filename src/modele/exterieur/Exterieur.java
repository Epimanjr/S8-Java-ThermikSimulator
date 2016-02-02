package modele.exterieur;

/**
 *
 * @author Maxime BLAISE
 * @author Antoine NOSAL
 */
public abstract class Exterieur {

    /**
     * Méthode qui permet de récupérer la température extérieure.
     *
     * @param temps Temps t de la simulation
     * @return Double
     */
    public abstract double getTemperatureExterieure(double temps);

}
