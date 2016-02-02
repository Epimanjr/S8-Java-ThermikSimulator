package graphique;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import modele.exterieur.Exterieur;
import modele.Piece;
import modele.Radiateur;
import modele.SystemeThermique;

/**
 *
 * @author Maxime BLAISE
 */
public class ThermikSimulator extends Application {

    private Stage stageTempExt = new Stage();

    // Séries de points
    private LineChart.Series<Number, Number> series;
    private LineChart.Series<Number, Number> seriesTempExt;

    private double nextX = 0;
    private SequentialTransition animation;
    private SystemeThermique systeme;

    // Components
    final Button powerOn = new Button("Allumer le radiateur");
    final Button powerOff = new Button("Eteindre le radiateur");
    final Button butValiderTempExt = new Button("OK");
    final Button butValiderIso = new Button("OK");
    final Button butTmpExtAuto = new Button("Auto");
    final Button butValiderPuissanceRadiateur = new Button("OK");

    final Label labelIsolation = new Label("Isolation");
    final Label labelTemperatureExt = new Label("Temp. Ext.");
    final Label labelPuissanceRadiateur = new Label("Puissance radiateur");

    final TextField saisieIsolation = new TextField("0.1");
    final TextField saisieTemperatureExt = new TextField("2");
    final TextField saisiePuissanceRadiateur = new TextField("2");

    @Override
    public void start(Stage primaryStage) {
        // Initialise notre simulation
        Piece piece = new Piece(15, 0.1);
        Exterieur environnement = new Exterieur(2);
        Radiateur radiateur = new Radiateur(piece, 2, 20);
        systeme = new SystemeThermique(piece, environnement, radiateur);

        // Initialise l'affichage graphique
        init(primaryStage);
        initTempExt();
        stageTempExt.show();
        primaryStage.show();

        // Lance la simulation
        play();
    }

    private void initTempExt() {
        Group root = new Group();
        root.getChildren().add(createChartTempExt());
        stageTempExt.setScene(new Scene(root));
        stageTempExt.setX(810);
        stageTempExt.setY(150);
    }

    private void init(Stage primaryStage) {
        // First scene with first chart
        Group root = new Group();
        primaryStage.setScene(new Scene(root));
        primaryStage.setX(130);
        primaryStage.setY(150);

        final VBox vboxGauche = new VBox();
        vboxGauche.getChildren().addAll(createChart());

        final VBox vboxDroit = new VBox();
        vboxDroit.setPadding(new Insets(20, 10, 100, 20));
        vboxDroit.setSpacing(20);
        final VBox vboxTempExt = new VBox();
        final VBox vboxIso = new VBox();
        final VBox vboxPuissance = new VBox();
        HBox hboxTempExt = new HBox();
        saisieTemperatureExt.setMaxWidth(50);
        hboxTempExt.getChildren().addAll(saisieTemperatureExt, butValiderTempExt, butTmpExtAuto);
        vboxTempExt.getChildren().addAll(labelTemperatureExt, hboxTempExt);
        HBox hboxIso = new HBox();
        saisieIsolation.setMaxWidth(50);
        hboxIso.getChildren().addAll(saisieIsolation, butValiderIso);
        vboxIso.getChildren().addAll(labelIsolation, hboxIso);
        HBox hboxPuissance = new HBox();
        saisiePuissanceRadiateur.setMaxWidth(50);
        hboxPuissance.getChildren().addAll(saisiePuissanceRadiateur, butValiderPuissanceRadiateur);
        vboxPuissance.getChildren().addAll(labelPuissanceRadiateur, hboxPuissance);

        vboxDroit.getChildren().addAll(vboxTempExt, vboxIso, vboxPuissance, powerOn, powerOff);

        final HBox hboxCentral = new HBox();
        hboxCentral.getChildren().addAll(vboxGauche, vboxDroit);

        root.getChildren().add(hboxCentral);

        setActionOnButton();
        // create animation
        Timeline timeline1 = new Timeline();

        timeline1.getKeyFrames().add(
                new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent
                    ) {
                        series.getData().add(new XYChart.Data<>(
                                nextX,
                                systeme.getPiece().getTemperatureAmbiante()
                        ));
                        seriesTempExt.getData().add(new XYChart.Data<>(
                                nextX,
                                systeme.getEnvironnement().getTemperatureFixe()
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

    private void setActionOnButton() {
        powerOn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                systeme.reposForce = false;
            }
        });
        powerOff.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                systeme.reposForce = true;
            }
        });
        butValiderTempExt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                systeme.tempExtAuto = false;
                systeme.getEnvironnement().setTemperatureFixe(new Double(saisieTemperatureExt.getText()));
            }
        });
        saisieTemperatureExt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                systeme.tempExtAuto = false;
                systeme.getEnvironnement().setTemperatureFixe(new Double(saisieTemperatureExt.getText()));
            }
        });
        butTmpExtAuto.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                systeme.tempExtAuto = true;
            }
        });
        butValiderIso.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                systeme.getPiece().setIsolation(new Double(saisieIsolation.getText()));
            }
        });
        saisieIsolation.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                systeme.getPiece().setIsolation(new Double(saisieIsolation.getText()));
            }
        });
        saisiePuissanceRadiateur.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                systeme.getRadiateur().setPuissanceMax(new Double(saisiePuissanceRadiateur.getText()));
            }
        });
        butValiderPuissanceRadiateur.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                systeme.getRadiateur().setPuissanceMax(new Double(saisiePuissanceRadiateur.getText()));
            }
        });
    }

    protected LineChart<Number, Number> createChartTempExt() {
        final NumberAxis xAxis = new NumberAxis();
        //xAxis.setForceZeroInRange(true);
        final NumberAxis yAxis = new NumberAxis();
        final LineChart<Number, Number> sc = new LineChart<>(xAxis, yAxis);
        // setup chart
        sc.setId("thermik");
        sc.setTitle("Simulation thermique");
        xAxis.setLabel("Temps");
        xAxis.setAnimated(false);
        //xAxis.setAutoRanging(false);
        yAxis.setLabel("Température extérieure");
        yAxis.setAutoRanging(true);
        // add starting data
        seriesTempExt = new LineChart.Series<>();
        seriesTempExt.setName("Variation de la température extérieure");
        sc.getData().add(seriesTempExt);
        return sc;
    }

    protected LineChart<Number, Number> createChart() {
        final NumberAxis xAxis = new NumberAxis();
        //xAxis.setForceZeroInRange(true);
        final NumberAxis yAxis = new NumberAxis();
        final LineChart<Number, Number> sc = new LineChart<>(xAxis, yAxis);
        // setup chart
        sc.setId("thermik");
        sc.setTitle("Simulation thermique");
        xAxis.setLabel("Temps");
        xAxis.setAnimated(false);
        //xAxis.setAutoRanging(false);
        yAxis.setLabel("Température");
        yAxis.setAutoRanging(true);
        // add starting data
        series = new LineChart.Series<Number, Number>();
        series.setName("Variation de la température du radiateur");
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
