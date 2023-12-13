package br.edu.ifsp.application.main.controller;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.dbsupport.TeamKnockout;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.services.KnockoutServices;
import br.edu.ifsp.domain.usecases.knockout.administration.StartKnockoutUseCase;
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

public class KnockoutUIController {

    KnockoutServices knockoutServices = new KnockoutServices();

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
    private TableColumn<Team, Integer> cIdTeam;

    @FXML
    private TableColumn<Team, String> cNameTeam;

    @FXML
    private TableColumn<Team, Boolean> cStatus;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnCriarCampeonato;

    private ObservableList<Team> tableData;

    private List<Team> teams = new ArrayList<>();


    @FXML
    public void initialize() {
        loadDataAndShow();
    }


    private void loadDataAndShow(){
    }

    @FXML
    private void createKnockout(ActionEvent actionEvent) {
        String nomeCampeonato = txtNomeCampeonato.getText();
        String modalidade = txtModalidade.getText();
        String patrocinadores = txtPatrocinadores.getText();
        String premiacao = txtPremiacao.getText();
        LocalDate dataInicial = initialDate.getValue();
        LocalDate dataFinal = finalDate.getValue();

        Knockout knockout = new Knockout(nomeCampeonato, dataInicial, dataFinal, modalidade, patrocinadores, premiacao);

        Integer idChampionship = createKnockoutUseCase.insert(knockout);

        System.out.println("Campeonato criado com sucesso! ID: " + idChampionship);
        backToPreviousScene(actionEvent);
    }

    @FXML
    private void backToPreviousScene(ActionEvent actionEvent) {
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
