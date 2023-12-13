package br.edu.ifsp.application.main.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import static br.edu.ifsp.application.main.Main.findKnockoutUseCase;
import static br.edu.ifsp.application.main.Main.findTeamUseCase;

public class FirstSceneController {
    public void sceneTeamUI(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifsp/TeamUI.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();

            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void sceneManagementTeamUI(ActionEvent actionEvent) {

        if (findTeamUseCase == null) {
            showAlert("Error", "Nenhum Time foi cadastrado!");
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifsp/ManagementTeamUI.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);

                Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();

                stage.setScene(scene);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    public void sceneChampionshipUI(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifsp/ChoseChampionship.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();

            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sceneAcompanhar(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifsp/manageChampionship.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();

            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void closeSoft(ActionEvent actionEvent) {
        Platform.exit();
    }
}
