package br.edu.ifsp.application.main.controller;

import br.edu.ifsp.application.main.export.PDFExporterClassificacao;
import br.edu.ifsp.domain.entities.championship.KnockoutMatch;
import br.edu.ifsp.domain.entities.championship.Phase;
import br.edu.ifsp.domain.entities.championship.Round;
import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.entities.team.TeamStats;
import br.edu.ifsp.domain.services.RoundRobinServices;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import br.edu.ifsp.domain.usecases.roundrobin.administration.FinishRoundRobin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PontosCorridosManageController {
    @FXML
    private Button btnVoltar;

    @FXML
    private Button btnExportClassificacao;
    @FXML
    private Button btnAcompanharRodada;
    private RoundRobin selectedRoundRobin;
    private List<Round> selectedRoundList;
    @FXML
    private TableView<TeamStats> tableView;
    @FXML
    private TableColumn<TeamStats, String> nomeTime;
    @FXML
    private TableColumn<TeamStats, String> vitoriasTime;
    @FXML
    private TableColumn<TeamStats, String> derrotasTime;
    @FXML
    private TableColumn<TeamStats, String> empatesTime;
    @FXML
    private TableColumn<TeamStats, String> pontosTime;
    @FXML
    private TableColumn<TeamStats, String> saldoDeGolsTime;
    private ObservableList<TeamStats> tableData;

    @FXML
    public void initialize(RoundRobin roundRobin) {
        selectedRoundRobin = roundRobin;
        blindTableViewToItemList();
        blindColumnsToValueSources();
        loadDataAndShow(selectedRoundRobin.getTeamStats());
    }

    private void blindTableViewToItemList(){
        tableData = FXCollections.observableArrayList();
        tableView.setItems(tableData);
    }

    private void blindColumnsToValueSources() {
        nomeTime.setCellValueFactory(new PropertyValueFactory<>("teamName"));
        vitoriasTime.setCellValueFactory(new PropertyValueFactory<>("wins"));
        derrotasTime.setCellValueFactory(new PropertyValueFactory<>("losses"));
        empatesTime.setCellValueFactory(new PropertyValueFactory<>("draws"));
        pontosTime.setCellValueFactory(new PropertyValueFactory<>("points"));
        saldoDeGolsTime.setCellValueFactory(new PropertyValueFactory<>("pointsStandings"));
    }

    private void loadDataAndShow(List<TeamStats> teamStats) {
        tableData.clear();
        tableData.addAll(teamStats);
    }

    public void nextScene(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifsp/manageRoundPontosCorridos.fxml"));
            Parent root = loader.load();

            ManageRoundPontosCorridosController manageRoundPontosCorridosController = loader.getController();
            manageRoundPontosCorridosController.initialize(selectedRoundRobin,selectedRoundRobin.getTable());

            Scene scene = new Scene(root);

            Stage stage = (Stage) btnAcompanharRodada.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ExportClassificacao(ActionEvent actionEvent) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar Classificação");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos PDF", "*.pdf"));

        Stage stage = (Stage) btnExportClassificacao.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {

            PDFExporterClassificacao.exportTableToPDF(tableData, file.getAbsolutePath());
        }
    }



    public void previousScene(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifsp/manageChampionship.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) btnVoltar.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void finishiChampionship(ActionEvent actionEvent) {
        selectedRoundList = selectedRoundRobin.getTable();
        if(todasRodadasConcluidas()){
            RoundRobinServices roundRobinServices = new RoundRobinServices();
            FinishRoundRobin finishRoundRobin = new FinishRoundRobin();
            Team champion = roundRobinServices.findChampion(selectedRoundRobin.getTeamStats());
            finishRoundRobin.finishChampionship(selectedRoundRobin.getIdChampionship());
            showAlert("                             Parabéns ao campeão \n" +
                    "                                          " + champion.getName());
        }else{
            showAlert("O campeonato ainda não acabou!! \n Alguma rodada ainda não foi concluida");
        }
    }

    private boolean todasRodadasConcluidas() {
        for (Round round : selectedRoundList) {
            if (!round.getFinished()) {
                return false;
            }
        }
        return true;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}
