package br.edu.ifsp.application.main.controller;

import br.edu.ifsp.application.main.export.PDFExporterClassificacao;
import br.edu.ifsp.domain.entities.championship.*;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.entities.team.TeamStats;
import br.edu.ifsp.domain.usecases.teamstats.FindTeamStatsUseCase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static br.edu.ifsp.application.main.Main.*;

public class PontosCorridosManageController {
    @FXML
    private Button btnVoltar;

    @FXML
    private Button btnExportClassificacao;
    @FXML
    private Button btnAcompanharRodada;
    private RoundRobin selectedRoundRobin;
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
        this.selectedRoundRobin = roundRobin;
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

        List<TeamStats> foundTeamStats = findTeamStatsUseCase.findAll();

        for (TeamStats teamStatsTest: foundTeamStats
             ) {
            if(teamStatsTest.getRoundRobin().getIdChampionship() == selectedRoundRobin.getIdChampionship()){
                teamStats.add(teamStatsTest);
            }
        }
        tableData.clear();
        tableData.addAll(teamStats);
    }

    public void nextScene(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifsp/manageRoundPontosCorridos.fxml"));
            Parent root = loader.load();

            List<Round> foundRounds = findRoundUseCase.findAll();
            List<Round> table = new ArrayList<>();
            for (Round round: foundRounds
                 ) {
                if(round.getRoundRobin().getIdChampionship() == selectedRoundRobin.getIdChampionship()){
                    table.add(round);
                }
            }
            Collections.sort(table, Comparator.comparingInt(round -> round.getNumber()));

            List<RoundRobinMatch> foundRoundRobinMatchs = findRoundRobinMatchUseCase.findAll();

            for (Round round: table
            ) {
                for (RoundRobinMatch roundRobinMatch: foundRoundRobinMatchs
                ) {
                    if (roundRobinMatch.getRound().getIdRound() == round.getIdRound()){
                        round.addMatch(roundRobinMatch);
                    }
                }
            }

            ManageRoundPontosCorridosController manageRoundPontosCorridosController = loader.getController();
            manageRoundPontosCorridosController.initialize(selectedRoundRobin);

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
    }
}
