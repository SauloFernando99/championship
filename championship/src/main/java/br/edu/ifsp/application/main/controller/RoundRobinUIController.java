package br.edu.ifsp.application.main.controller;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.services.KnockoutServices;
import br.edu.ifsp.domain.services.RoundRobinServices;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.edu.ifsp.application.main.Main.*;

public class RoundRobinUIController {

    RoundRobinServices roundRobinServices = new RoundRobinServices();

    @FXML
    private TextField txtNomeCampeonato;

    @FXML
    private TextField txtModalidade;

    @FXML
    private DatePicker initialDate;

    @FXML
    private DatePicker finalDate;

    @FXML
    private TextField txtPatrocinadores;

    @FXML
    private TextField txtPremiacao;

    @FXML
    private TableView<Team> tabelaTime;

    @FXML
    private TableColumn<Team, Integer> idTeam;

    @FXML
    private TableColumn<Team, String> name;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnCriarCampeonato;

    private ObservableList<Team> tableData;

    private List<Team> teams = new ArrayList<>();


    @FXML
    public void initialize() {
        blindTableViewToItemList();
        blindColumnsToValueSources();
        loadDataAndShow();
        tabelaTime.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Team selectedTeam = tabelaTime.getSelectionModel().getSelectedItem();

                showConfirmationDialog(selectedTeam);
            }
        });
    }
    private void blindTableViewToItemList(){
        tableData = FXCollections.observableArrayList();
        tabelaTime.setItems(tableData);
    }

    private void blindColumnsToValueSources(){
        idTeam.setCellValueFactory(new PropertyValueFactory<>("idTeam"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    private void loadDataAndShow(){
        List<Team> teams = findTeamUseCase.findAll();
        tableData.clear();
        tableData.addAll(teams);
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

        if (result.isPresent() && result.get() == buttonTypeYes) {
            if (!teams.contains(team) && team.getIsActive()) {
                teams.add(team);
                System.out.println("Time adicionado ao campeonato: " + team.getName());
            } else {
                System.out.println("Não foi possível adicionar o time ao campeonato.");
            }
        }
    }


    public void createRoundRobin(ActionEvent actionEvent) {
        String nomeCampeonato = txtNomeCampeonato.getText();
        String modalidade = txtModalidade.getText();
        String patrocinadores = txtPatrocinadores.getText();
        String premiacao = txtPremiacao.getText();
        LocalDate dataInicial = initialDate.getValue();
        LocalDate dataFinal = finalDate.getValue();

        RoundRobin roundRobin = new RoundRobin(nomeCampeonato, dataInicial, dataFinal, modalidade, patrocinadores, premiacao, teams);



        Boolean par = false;
        if (teams.size() % 2 == 0) {
            par = true;
        }

        if (par == true && roundRobin.getTeams().size() > 1) {
            Integer idChampionship = createRoundRobinUseCase.insert(roundRobin);
            System.out.println("Campeonato criado com sucesso! ID: " + idChampionship);

            backToPreviousScene(actionEvent);
        } else {
            showAlert("Error", "Número de times insuficiente ou não é par!");
            System.out.println("Número de times insuficiente ou não é par.");
        }
    }

    public void backToPreviousScene(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifsp/FirstScene.fxml"));
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
