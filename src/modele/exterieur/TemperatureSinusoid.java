package modele.exterieur;

/**
 *
 * @author Maxime BLAISE
 */
public class TemperatureSinusoid extends Exterieur {
    
    /**
     * Température moyenne autour de laquelle va osciller la courbe de température.
     */
    private double temperatureMoyenne;
    
    /**
     * Amplitude, qui correspond à la différence entre les extrémités et la moyenne.
     */
    private double amplitude;
    
    /**
     * Période.
     */
    private double periode;

    public TemperatureSinusoid(double temperatureMoyenne, double amplitude, double periode) {
        this.temperatureMoyenne = temperatureMoyenne;
        this.amplitude = amplitude;
        this.periode = periode;
    }
    
    @Override
    public double getTemperatureExterieure(double temps) {
        return Math.sin((temps * Math.PI) / this.periode) * this.amplitude + this.temperatureMoyenne;
    }

    public double getTemperatureMoyenne() {
        return temperatureMoyenne;
    }

    public void setTemperatureMoyenne(double temperatureMoyenne) {
        this.temperatureMoyenne = temperatureMoyenne;
    }

    public double getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(double amplitude) {
        this.amplitude = amplitude;
    }

    public double getPeriode() {
        return periode;
    }

    public void setPeriode(double periode) {
        this.periode = periode;
    }
    
    
}
