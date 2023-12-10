package br.edu.ifsp.application.main.controller.team;

import br.edu.ifsp.application.main.controller.team.EditTeamController;
import br.edu.ifsp.domain.entities.team.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static br.edu.ifsp.application.main.Main.findTeamUseCase;
import static br.edu.ifsp.application.main.Main.removeTeamUseCase;

public class ManageTeamUIController {

    @FXML
    private TableView<Team> tableView;

    @FXML
    private TableColumn<Team, Integer> cId;

    @FXML
    private TableColumn<Team, String> cName;

    @FXML
    private TableColumn<Team, String> cCrest;

    @FXML
    private TableColumn<Team, Boolean> cIsActive;

    private ObservableList<Team> tableData;

    @FXML
    public void initialize() {
        blindTableViewToItemList();
        blindColumnsToValueSources();
        loadDataAndShow();
    }

    private void blindTableViewToItemList(){
        tableData = FXCollections.observableArrayList();
        tableView.setItems(tableData);
    }

    private void blindColumnsToValueSources(){
        cId.setCellValueFactory(new PropertyValueFactory<>("idTeam"));
        cName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cCrest.setCellValueFactory(new PropertyValueFactory<>("crest"));
        cIsActive.setCellValueFactory(new PropertyValueFactory<>("isActive"));
    }

    private void loadDataAndShow(){
        List<Team> teams = findTeamUseCase.findAll();
        tableData.clear();
        tableData.addAll(teams);
    }

    public void deleteTeam(ActionEvent actionEvent) {
        Team selectedTeam = tableView.getSelectionModel().getSelectedItem();
        if (selectedTeam != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Are you sure you want to delete this team?");
            alert.setContentText("Team: " + selectedTeam.getName());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                boolean success = removeTeamUseCase.remove(selectedTeam);
                if (success) {
                    loadDataAndShow();
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText("Could not delete team");
                    errorAlert.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Team Selected");
            alert.setContentText("Please select a team to delete.");
            alert.showAndWait();
        }
    }

    public void createTeam(ActionEvent actionEvent) {
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

    @FXML
    public void editTeam(ActionEvent actionEvent) {
        Team selectedTeam = tableView.getSelectionModel().getSelectedItem();
        if (selectedTeam != null) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifsp/EditTeam.fxml"));
            Parent root;
            try {
                root = loader.load();

                EditTeamController editTeamController = loader.getController();
                editTeamController.initializeData(selectedTeam);

                Stage editStage = new Stage();
                Scene editScene = new Scene(root);
                editStage.setScene(editScene);

                editStage.showAndWait();

                loadDataAndShow();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Team Selected");
            alert.setContentText("Please select a team to edit.");
            alert.showAndWait();
        }
    }

    @FXML
    public void backToPreviousScene(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifsp/FirstScene.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) tableView.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
