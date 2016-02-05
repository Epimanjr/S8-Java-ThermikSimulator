package graphique;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import java.io.File;
import modele.Piece;
import modele.SystemeThermique;
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

        stage.setTitle("FXML Welcome");
        stage.setScene(scene);

        // Initialisation du système
        initialiserSysteme();

        stage.show();
    }

    /**
     * Méthode qui permet d'instancier un système thermique.
     */
    private static void initialiserSysteme() {
        // Création d'un système thermique
        Piece piece = new Piece(15, 0.1);
        Exterieur environnement = new TemperatureConstante(5);
        Radiateur radiateur = new AvecTempsRepos(2, 22);
        systeme = new SystemeThermique(piece, environnement, radiateur);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
