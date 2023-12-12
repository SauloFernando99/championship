package br.edu.ifsp.application.main.controller;

import br.edu.ifsp.application.main.controller.team.EditTeamController;
import br.edu.ifsp.domain.entities.championship.*;
import br.edu.ifsp.domain.entities.team.Team;
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
import javafx.stage.Stage;
import javafx.scene.control.Alert;

import java.time.LocalDate;
import java.util.List;

import static br.edu.ifsp.application.main.Main.*;


public class ManageChampionshipController {


    @FXML
    private Button btnVoltar;

    @FXML
    private Button btnAcompanharCamp;

    @FXML
    private TableView<RoundRobin> tabelaCampeonatosPontosCorridos;
    @FXML
    private TableView<Knockout> tabelaCampeonatosMataMata;

    private ObservableList<RoundRobin> tableDataRoundRobin;
    private ObservableList<Knockout> tableDataKnockout;

    @FXML
    private TableColumn<RoundRobin, String> nomeCampeonatoPontosCorridos;
    @FXML
    private TableColumn<RoundRobin, LocalDate> dataIniCampeonatoPontosCorridos;
    @FXML
    private TableColumn<RoundRobin, LocalDate> dataFimCampeonatoPontosCorridos;
    @FXML
    private TableColumn<RoundRobin, String> modalidadeCampeonatoPontosCorridos;
    @FXML
    private TableColumn<RoundRobin, Boolean> statusCampeonatoPontosCorridos;
    @FXML
    private TableColumn<Knockout, String> nomeCampeonatoMataMata;

    @FXML
    private TableColumn<Knockout, LocalDate> dataIniCampeonatoMataMata;
    @FXML
    private TableColumn<Knockout, LocalDate> dataFimCampeonatoMataMata;
    @FXML
    private TableColumn<Knockout, String> modalidadeCampeonatoMataMata;
    @FXML
    private TableColumn<Knockout, Boolean> statusCampeonatoMataMata;


    @FXML
    public void initialize() {
        if (findKnockoutUseCase == null && findRoundRobinUseCase == null) {
            showAlert("Error", "Nenhum campeonato foi cadastrado!");
        } else {
            blindTableViewToItemListPontosCorridos();
            blindColumnsToValueSourcesPontosCorridos();
            loadDataAndShowPontosCorridos();
            blindTableViewToItemListMataMata();
            blindColumnsToValueSourcesMataMata();
            loadDataAndShowMataMata();
        }
    }

    private void blindTableViewToItemListPontosCorridos(){
        tableDataRoundRobin = FXCollections.observableArrayList();
        tabelaCampeonatosPontosCorridos.setItems(tableDataRoundRobin);
    }

    private void blindColumnsToValueSourcesPontosCorridos(){
        nomeCampeonatoPontosCorridos.setCellValueFactory(new PropertyValueFactory<>("name"));
        dataIniCampeonatoPontosCorridos.setCellValueFactory(new PropertyValueFactory<>("initialDate"));
        dataFimCampeonatoPontosCorridos.setCellValueFactory(new PropertyValueFactory<>("finalDate"));
        modalidadeCampeonatoPontosCorridos.setCellValueFactory(new PropertyValueFactory<>("modality"));
        statusCampeonatoPontosCorridos.setCellValueFactory(new PropertyValueFactory<>("concluded"));
    }

    private void loadDataAndShowPontosCorridos(){
        List<RoundRobin> roundRobins = findRoundRobinUseCase.findAll();
        tableDataRoundRobin.clear();
        tableDataRoundRobin.addAll(roundRobins);
    }

    private void blindTableViewToItemListMataMata(){
        tableDataKnockout = FXCollections.observableArrayList();
        tabelaCampeonatosMataMata.setItems(tableDataKnockout);
    }

    private void blindColumnsToValueSourcesMataMata(){
        nomeCampeonatoMataMata.setCellValueFactory(new PropertyValueFactory<>("name"));
        dataIniCampeonatoMataMata.setCellValueFactory(new PropertyValueFactory<>("initialDate"));
        dataFimCampeonatoMataMata.setCellValueFactory(new PropertyValueFactory<>("finalDate"));
        modalidadeCampeonatoMataMata.setCellValueFactory(new PropertyValueFactory<>("modality"));
        statusCampeonatoMataMata.setCellValueFactory(new PropertyValueFactory<>("concluded"));
    }

    private void loadDataAndShowMataMata(){
        List<Knockout> knockouts = findKnockoutUseCase.findAll();
        tableDataKnockout.clear();
        tableDataKnockout.addAll(knockouts);
    }


    public void nextScene(ActionEvent actionEvent) {
        Knockout selectKnockout = tabelaCampeonatosMataMata.getSelectionModel().getSelectedItem();
        RoundRobin selectRoundRobin = tabelaCampeonatosPontosCorridos.getSelectionModel().getSelectedItem();
        if(selectKnockout != null){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifsp/managementMataMata.fxml"));
                Parent root = loader.load();
                List<Phase> phases = selectKnockout.getSeeding();
                Phase ultimoElemento = phases.get(phases.size() -1);
                List<KnockoutMatch> matches = ultimoElemento.getMatches();
                ManagementMataMataController managementMataMataController = loader.getController();
                managementMataMataController.initialize(matches);

                Scene scene = new Scene(root);

                Stage stage = (Stage) btnAcompanharCamp.getScene().getWindow();

                stage.setScene(scene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(selectRoundRobin != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifsp/pontosCorridosManage.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);

                Stage stage = (Stage) btnAcompanharCamp.getScene().getWindow();

                stage.setScene(scene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void previousScene(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifsp/FirstScene.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) btnVoltar.getScene().getWindow();
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
