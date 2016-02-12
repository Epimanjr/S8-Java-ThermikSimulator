/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.service;

import graphique.MainFrame;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ProgressIndicator;

/**
 *
 * @author Maxime
 */
public class SimulationStatique extends Simulation {

    private final int nombreTemps;

    /**
     * Spinner pour faire patienter l'utilisateur.
     */
    private final ProgressIndicator progress;

    public SimulationStatique(LineChart<Double, Double> graph, ProgressIndicator progress, int nombreTemps) {
        super(graph);
        this.progress = progress;
        this.nombreTemps = nombreTemps;
    }

    @Override
    public void run() {
        // Création du service
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        for (int i = 0; i < nombreTemps + 1; i++) {
                            getSerieInterieure().getData().add(new XYChart.Data<>((double) i, MainFrame.systeme.getPiece().getTemperatureAmbiante()));
                            getSerieExterieure().getData().add(new XYChart.Data<>((double) i, MainFrame.systeme.getEnvironnement().getTemperatureExterieure(i)));
                            getSerieObjectif().getData().add(new XYChart.Data<>((double) i, MainFrame.systeme.getRadiateur().getConsigne()));

                            MainFrame.systeme.evolutionTemperature();
                        }

                        return null;
                    }
                };
            }
        };
        // BIND
        progress.visibleProperty().bind(service.runningProperty());
        service.start();
        service.setOnSucceeded((WorkerStateEvent event1) -> {
            getGraph().getData().addAll(getSerieInterieure(), getSerieExterieure(), getSerieObjectif());
        });
    }

}
