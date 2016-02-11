/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.service;

import graphique.MainFrame;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

/**
 *
 * @author Maxime
 */
public class ServiceStatique extends Service<Void> {

    private final double nombreTemps;

    private final XYChart.Series<Double, Double> serieInterieure = new XYChart.Series<>();
    private final XYChart.Series<Double, Double> serieExterieure = new XYChart.Series<>();

    public ServiceStatique(int nombreTemps) {
        this.nombreTemps = (double) nombreTemps;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                for (int i = 0; i < nombreTemps + 1; i++) {
                    serieInterieure.getData().add(new XYChart.Data<>((double) i, MainFrame.systeme.getPiece().getTemperatureAmbiante()));
                    serieExterieure.getData().add(new XYChart.Data<>((double) i, MainFrame.systeme.getEnvironnement().getTemperatureExterieure(i)));
                    
                    MainFrame.systeme.evolutionTemperature();
                }

                return null;
            }
        };
    }

    public XYChart.Series<Double, Double> getSerieInterieure() {
        return serieInterieure;
    }

    public XYChart.Series<Double, Double> getSerieExterieure() {
        return serieExterieure;
    }

    
}
