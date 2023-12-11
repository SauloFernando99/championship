package br.edu.ifsp.application.main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChoseChampionshipController {
    public void nextSceneMataMata(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifsp/KnockoutUI.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();

            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void nextScenePontosCorridos(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifsp/RoundRobinUI.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();

            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
