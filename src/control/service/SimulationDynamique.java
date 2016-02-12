package control.service;

import graphique.MainFrame;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.util.Duration;

/**
 *
 * @author Maxime
 */
public class SimulationDynamique extends Simulation {

    private int iteration = 0;
    private SequentialTransition animation;

    public SimulationDynamique(LineChart<Double, Double> graph) {
        super(graph);
    }

    @Override
    public void run() {
        // Init series
        getGraph().getData().addAll(getSerieInterieure(), getSerieExterieure(), getSerieObjectif());
        Data<Double, Double> horizontalMarker = new Data<>(0.0, 25.0);
        // create animation
        Timeline timeline1 = new Timeline();
        timeline1.getKeyFrames().add(new KeyFrame(Duration.millis(500), (ActionEvent actionEvent) -> {
            getSerieInterieure().getData().add(new XYChart.Data<>((double) iteration, MainFrame.systeme.getPiece().getTemperatureAmbiante()));
            getSerieExterieure().getData().add(new XYChart.Data<>((double) iteration, MainFrame.systeme.getEnvironnement().getTemperatureExterieure(iteration)));
            getSerieObjectif().getData().add(new XYChart.Data<>((double) iteration, MainFrame.systeme.getRadiateur().getConsigne()));

            MainFrame.systeme.evolutionTemperature();
            iteration++;
        }));
        timeline1.setCycleCount(Animation.INDEFINITE);

        animation = new SequentialTransition();
        animation.getChildren().addAll(timeline1);
        animation.play();
    }

    public void arreter() {
        animation.stop();
    }
    
    public void continuer() {
        animation.play();
    }
}
