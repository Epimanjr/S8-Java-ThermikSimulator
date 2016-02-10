package graphique;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import java.io.File;
import modele.Piece;
import modele.SystemeThermique;
import modele.ValeurParDefaut;
import modele.exterieur.Exterieur;
import modele.exterieur.TemperatureConstante;
import modele.radiateur.AvecTempsRepos;
import modele.radiateur.Radiateur;

public class MainFrame extends Application {

    public static SystemeThermique systeme;

    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("fxml_example.fxml"));
        Parent root = FXMLLoader.load(new File("fxml/mainframe.fxml").toURI().toURL());
        Scene scene = new Scene(root);

        stage.setTitle("Simulation thermique - Maxime BLAISE - Antoine NOSAL - ©2016");
        stage.setScene(scene);

        

        stage.show();
    }

    /**
     * Méthode qui permet d'instancier un système thermique.
     */
    public static void initialiserSysteme() {
        // Création d'un système thermique
        Piece piece = new Piece(ValeurParDefaut.pieceTemperatureInitiale,ValeurParDefaut.pieceIsolation);
        Exterieur environnement = new TemperatureConstante(ValeurParDefaut.envTemperatureFixe);
        Radiateur radiateur = new AvecTempsRepos(ValeurParDefaut.radPuissanceMax,ValeurParDefaut.radConsigne);
        systeme = new SystemeThermique(piece, environnement, radiateur);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
