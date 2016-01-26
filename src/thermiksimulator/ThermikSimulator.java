
package thermiksimulator;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Maxime BLAISE
 */
public class ThermikSimulator extends Application {

	// Séries de points
    private ScatterChart.Series<Number, Number> series;
    private ScatterChart.Series<Number, Number> seriesRadiateur;
    
    // Deuxième fenêtre
    private Stage stage2 = new Stage();

    private double nextX = 0;
    private SequentialTransition animation;
    private SystemeThermique systeme;

    @Override
    public void start(Stage primaryStage) {
    	// Initialise notre simulation
        Piece piece = new Piece(15, 0.1);
        Environnement environnement = new Environnement(2);
        Radiateur radiateur = new Radiateur(piece, 2, 20);
        systeme = new SystemeThermique(piece, environnement, radiateur);

        // Initialise l'affichage graphique
        init(primaryStage);
        primaryStage.show();
        stage2.show();
        
        // Lance la simulation
        play();
    }

    private void init(Stage primaryStage) {
    	// First scene with first chart
        Group root = new Group();
        primaryStage.setScene(new Scene(root));
        primaryStage.setX(250);
        primaryStage.setY(50);
        root.getChildren().add(createChart());

        // Second scene with second chart
        Group group1 = new Group();
        stage2.setScene(new Scene(group1));
        stage2.setX(765);
        stage2.setY(50); 
        group1.getChildren().add(createChartRadiateur());

        // create animation
        Timeline timeline1 = new Timeline();
        timeline1.getKeyFrames().add(
                new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        series.getData().add(new XYChart.Data<>(
                                nextX,
                                systeme.getPiece().getTemperatureAmbiante()
                        ));
                        seriesRadiateur.getData().add(new XYChart.Data<>(
                                nextX,
                               systeme.getPuissanceRadiateurActuelle()
                        ));
                        nextX++;
                        systeme.evolutionTemperature();
                    }
                })
        );
        timeline1.setCycleCount(Animation.INDEFINITE);

        animation = new SequentialTransition();
        animation.getChildren().addAll(timeline1);
    }

    protected ScatterChart<Number, Number> createChartRadiateur() {
        final NumberAxis xAxis = new NumberAxis();
        xAxis.setForceZeroInRange(true);
        final NumberAxis yAxis = new NumberAxis(-1, 3, 0.5);
        final ScatterChart<Number, Number> sc = new ScatterChart<Number, Number>(xAxis, yAxis);
        // setup chart
        sc.setId("thermik");
        sc.setTitle("Simulation thermique");
        xAxis.setLabel("Temps");
        xAxis.setAnimated(false);
        yAxis.setLabel("Température");
        yAxis.setAutoRanging(false);
        // add starting data
        seriesRadiateur = new ScatterChart.Series<Number, Number>();
        seriesRadiateur.setName("Variation du radiateur");
        sc.getData().add(seriesRadiateur);
        return sc;
    }

    protected ScatterChart<Number, Number> createChart() {
        final NumberAxis xAxis = new NumberAxis();
        xAxis.setForceZeroInRange(true);
        final NumberAxis yAxis = new NumberAxis(-25, 25, 1);
        final ScatterChart<Number, Number> sc = new ScatterChart<Number, Number>(xAxis, yAxis);
        // setup chart
        sc.setId("thermik");
        sc.setTitle("Simulation thermique");
        xAxis.setLabel("Temps");
        xAxis.setAnimated(false);
        yAxis.setLabel("Température");
        yAxis.setAutoRanging(false);
        // add starting data
        series = new ScatterChart.Series<Number, Number>();
        series.setName("Thermik");
        sc.getData().add(series);
        return sc;
    }

    public void play() {
        animation.play();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
