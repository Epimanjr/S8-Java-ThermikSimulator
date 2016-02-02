package modele.radiateur;

/**
 * @author Maxime BLAISE
 * @author Antoine NOSAL
 */
public class AvecTempsRepos extends Radiateur {
    
    private boolean repos;
    private int reposDepuis = 0;

    public AvecTempsRepos(boolean repos, double puissanceMax, double consigne) {
        super(puissanceMax, consigne);
        this.repos = repos;
    }

    @Override
    public double getThermostat(int pieceTemp, int t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

    
}
