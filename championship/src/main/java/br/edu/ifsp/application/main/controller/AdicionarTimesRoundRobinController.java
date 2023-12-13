package br.edu.ifsp.application.main.controller;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.championship.Round;
import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.entities.dbsupport.TeamKnockout;
import br.edu.ifsp.domain.entities.dbsupport.TeamRoundRobin;
import br.edu.ifsp.domain.entities.team.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.edu.ifsp.application.main.Main.*;

public class AdicionarTimesRoundRobinController {
    @FXML
    private TableView<Team> tabelaTimesAdicionados;

    @FXML
    private TableColumn<Team, Integer> cIdTeam;

    @FXML
    private TableColumn<Team, String> cNameTeam;

    @FXML
    private TableColumn<Team, String> cStatus;

    @FXML
    private Button btnAdicionarTime;

    @FXML
    private Button btnCancelar;

    private ObservableList<Team> tableData;

    private RoundRobin selectedRoundRobin;

    private List<Team> teamsForRoundRobin = new ArrayList<>();

    private List<Team> teams = new ArrayList<>();

    @FXML
    public void initialize(RoundRobin selectedRoundRobin) {
        this.selectedRoundRobin = selectedRoundRobin;
        bindTableViewToItemList();
        bindColumnsToValueSources();
        loadDataAndShow();
        tabelaTimesAdicionados.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Team selectedTeam = tabelaTimesAdicionados.getSelectionModel().getSelectedItem();
                showConfirmationDialog(selectedTeam);
            }
        });
    }

    private void bindTableViewToItemList() {
        tableData = FXCollections.observableArrayList();
        tabelaTimesAdicionados.setItems(tableData);
    }

    private void bindColumnsToValueSources() {
        cIdTeam.setCellValueFactory(new PropertyValueFactory<>("idTeam"));
        cNameTeam.setCellValueFactory(new PropertyValueFactory<>("name"));
        cStatus.setCellValueFactory(new PropertyValueFactory<>("isActive"));

    }

    private void loadDataAndShow() {
        List<Team> teams = findTeamUseCase.findAll();
        List<Team> activeTeams = new ArrayList<>();

        for (Team team: teams
        ) {
            if (team.getIsActive())
                activeTeams.add(team);
        }

        tableData.clear();
        tableData.addAll(activeTeams);

    }

    public void adicionarTime(ActionEvent actionEvent) {
    }

    private void showConfirmationDialog(Team team) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Adicionar Time");
        alert.setHeaderText("Deseja adicionar o time ao campeonato?");
        alert.setContentText("Time: " + team.getName());

        ButtonType buttonTypeYes = new ButtonType("Sim");
        ButtonType buttonTypeNo = new ButtonType("Não", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();

        List<TeamRoundRobin> foundCombinations =
                findTeamRoundRobinUseCase.findAllByRoundRobin(selectedRoundRobin.getIdChampionship());

        System.out.println("SIZE: " + foundCombinations.size());

        List<Team> foundTeams = new ArrayList<>();

        for (TeamRoundRobin combination: foundCombinations
        ) {
            foundTeams.add(combination.getTeam());
            System.out.println(combination.getTeam().getName());
        }
        teamsForRoundRobin.addAll(foundTeams);

        List<Integer> ids = new ArrayList<>();

        for (Team foundTeam: foundTeams
        ) {
            ids.add(team.getIdTeam());
        }

        if (result.isPresent() && result.get() == buttonTypeYes) {
            if (!teams.contains(team) && team.getIsActive() && !ids.contains(team.getIdTeam())) {
                teamsForRoundRobin.add(team);
                TeamRoundRobin teamRoundRobin = new TeamRoundRobin(team, selectedRoundRobin);

                createTeamRoundRobinUseCase.insert(teamRoundRobin);

                System.out.println("Time adicionado ao campeonato: " + team.getName());

                for (Team time: teams
                ) {
                    System.out.println(time.getName());
                }
            } else {
                System.out.println("Não foi possível adicionar o time ao campeonato.");
            }
        }
    }

    @FXML
    private void cancelar(ActionEvent actionEvent) {
        backToPreviousScene(actionEvent);
    }

    @FXML
    private void backToPreviousScene(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifsp/ManageChampionship.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) btnCancelar.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
