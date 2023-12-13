package br.edu.ifsp.application.main.controller;

import br.edu.ifsp.domain.entities.championship.*;
import br.edu.ifsp.domain.entities.team.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

import static br.edu.ifsp.application.main.Main.findRoundRobinMatchUseCase;
import static br.edu.ifsp.application.main.Main.findRoundUseCase;

public class ViewPartidaPontosCorridosController {
    @FXML
    private Button btnVoltar;
    @FXML
    private Button btnGerenciarPartida;
    private List<Round> selectedRoundList;
    private List<RoundRobinMatch> roundRobinMatches;
    @FXML
    private TableColumn<Team, String> time1;
    @FXML
    private TableColumn<Match, String> placarTime1;
    @FXML
    private TableColumn<Match, String> placarTime2;
    @FXML
    private TableColumn<Team, String> time2;
    @FXML
    private TableColumn<Match, String> statusPartida;
    @FXML
    private ObservableList<RoundRobinMatch> tableData;
    @FXML
    private TableView<RoundRobinMatch> tableView;
    @FXML
    public void initialize(List<Round> roundList, Round round) {
        selectedRoundList = roundList;
        roundRobinMatches = round.getMatches();
        blindTableViewToItemList();
        blindColumnsToValueSources();
        loadDataAndShow(roundRobinMatches);
    }

    private void blindTableViewToItemList(){
        tableData = FXCollections.observableArrayList();
        tableView.setItems(tableData);
    }

    private void blindColumnsToValueSources() {
        time1.setCellValueFactory(new PropertyValueFactory<>("team1Name"));
        placarTime1.setCellValueFactory(new PropertyValueFactory<>("scoreboard1"));
        placarTime2.setCellValueFactory(new PropertyValueFactory<>("scoreboard2"));
        time2.setCellValueFactory(new PropertyValueFactory<>("team2Name"));
        statusPartida.setCellValueFactory(new PropertyValueFactory<>("concluded"));
    }

    private void loadDataAndShow(List<RoundRobinMatch> roundRobinMatches) {
        //System.out.println(roundRobinMatches);
        tableData.clear();
        tableData.addAll(roundRobinMatches);
    }
    @FXML
    public void nextScene(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifsp/managePartidaPontosCorridos.fxml"));
            Parent root = loader.load();

            ManagePartidaPontosCorridosController partidaPontosCorridos = loader.getController();
            RoundRobinMatch roundRobinMatch = tableView.getSelectionModel().getSelectedItem();

            if (roundRobinMatch != null) {
                partidaPontosCorridos.initialize(roundRobinMatch);
                Scene scene = new Scene(root);

                Stage stage = (Stage) btnGerenciarPartida.getScene().getWindow();
                stage.setScene(scene);
            } else {
                showAlert("Nenhuma partida foi selecionada!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public void previousScene(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifsp/manageRoundPontosCorridos.fxml"));
            Parent root = loader.load();

            ManageRoundPontosCorridosController manageRoundPontosCorridosController = loader.getController();
            manageRoundPontosCorridosController.initialize(selectedRoundList);

            Scene scene = new Scene(root);

            Stage stage = (Stage) btnVoltar.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void concluirRodada(ActionEvent actionEvent) {
    }

    public void updateData(List<Round> roundList, Round round) {
        selectedRoundList = roundList;
        roundRobinMatches = round.getMatches();
        loadDataAndShow(roundRobinMatches);
    }

}
