package br.edu.ifsp.application.main.controller;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.dbsupport.TeamKnockout;
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

public class AdicionarTimesMataMataController {
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

    private Knockout selectedKnockout;

    private List<Team> teamsForKnockout = new ArrayList<>();

    private List<Team> teams = new ArrayList<>();

    @FXML
    public void initialize(Knockout selectedKnockout) {
        this.selectedKnockout = selectedKnockout;
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

        List<TeamKnockout> foundCombinations =
                findTeamKnockoutUseCase.findAllByKnockout(selectedKnockout.getIdChampionship());

        System.out.println("SIZE: " + foundCombinations.size());

        List<Team> foundTeams = new ArrayList<>();

        for (TeamKnockout combination: foundCombinations
        ) {
            foundTeams.add(combination.getTeam());
            System.out.println(combination.getTeam().getName());
        }
        teamsForKnockout.addAll(foundTeams);

        List<Integer> ids = new ArrayList<>();

        for (Team foundTeam: foundTeams
             ) {
            ids.add(foundTeam.getIdTeam());
        }

        if (result.isPresent() && result.get() == buttonTypeYes) {
            if (!teams.contains(team) && team.getIsActive() && !ids.contains(team.getIdTeam())) {
                teamsForKnockout.add(team);
                TeamKnockout teamKnockout = new TeamKnockout(team, selectedKnockout);

                createTeamKnockoutUseCase.insert(teamKnockout);

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
