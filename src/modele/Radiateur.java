package modele;

/**
 * @author Maxime BLAISE
 * @author Antoine NOSAL
 */
public class Radiateur {

    /**
     * Pièce dans laquelle se trouve le radiateur.
     */
    private Piece piece;
    
    /**
     * Puissance maximale en C°/min.
     */
    private double puissanceMax;
    
    /**
     * Températeur de consigne en C°.
     */
    private double consigne;

    /**
     * Crée un radiateur à l'aide de plusieurs paramètres
     * @param piece
     * @param puissanceMax
     * @param consigne 
     */
    public Radiateur(Piece piece, double puissanceMax, double consigne) {
        this.piece = piece;
        this.puissanceMax = puissanceMax;
        this.consigne = consigne;
    }

    /**
     * Retourne la pièce dans laquelle le radiateur se trouve
     * @return Piece
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Définit la pièce dans laquelle le radiateur se trouve
     * @param piece 
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * Retourne la puissance maximale (C°/min)
     * @return double
     */
    public double getPuissanceMax() {
        return puissanceMax;
    }

    /**
     * Définit la puissance maximale (C°/min)
     * @param puissanceMax 
     */
    public void setPuissanceMax(double puissanceMax) {
        this.puissanceMax = puissanceMax;
    }

    /**
     * Retourne la température de consigne (C°)
     * @return 
     */
    public double getConsigne() {
        return consigne;
    }

    /**
     * Définit la températeur de consigne (C°)
     * @param consigne 
     */
    public void setConsigne(double consigne) {
        this.consigne = consigne;
    }
    
}
