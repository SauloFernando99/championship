package br.edu.ifsp.application.main.controller;

import br.edu.ifsp.domain.entities.championship.*;
import br.edu.ifsp.domain.entities.dbsupport.TeamKnockout;
import br.edu.ifsp.domain.entities.dbsupport.TeamRoundRobin;
import br.edu.ifsp.domain.usecases.knockout.administration.StartKnockoutUseCase;
import br.edu.ifsp.domain.usecases.roundrobin.administration.StartRoundRobinUseCase;
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
import java.util.List;

import static br.edu.ifsp.application.main.Main.*;

public class ManageChampionshipController {


    @FXML
    private Button btnVoltar;

    @FXML
    private Button btnAcompanharCamp;

    @FXML
    private Button btnAdicionarTimes;

    @FXML
    private Button btnIniciarCampeonato;

    @FXML
    private TableView<RoundRobin> tabelaCampeonatosPontosCorridos;
    @FXML
    private TableView<Knockout> tabelaCampeonatosMataMata;

    private ObservableList<RoundRobin> tableDataRoundRobin;
    private ObservableList<Knockout> tableDataKnockout;

    @FXML
    private TableColumn<RoundRobin, Integer> idCampeonatoPontosCorridos;
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
    private TableColumn<Knockout, Integer> idCampeonatoMataMata;
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

    private void blindTableViewToItemListPontosCorridos() {
        tableDataRoundRobin = FXCollections.observableArrayList();
        tabelaCampeonatosPontosCorridos.setItems(tableDataRoundRobin);
    }

    private void blindColumnsToValueSourcesPontosCorridos() {
        idCampeonatoPontosCorridos.setCellValueFactory(new PropertyValueFactory<>("idChampionship"));
        nomeCampeonatoPontosCorridos.setCellValueFactory(new PropertyValueFactory<>("name"));
        dataIniCampeonatoPontosCorridos.setCellValueFactory(new PropertyValueFactory<>("initialDate"));
        dataFimCampeonatoPontosCorridos.setCellValueFactory(new PropertyValueFactory<>("finalDate"));
        modalidadeCampeonatoPontosCorridos.setCellValueFactory(new PropertyValueFactory<>("modality"));
        statusCampeonatoPontosCorridos.setCellValueFactory(new PropertyValueFactory<>("concluded"));
    }

    private void loadDataAndShowPontosCorridos() {

        List<RoundRobin> roundRobins = findRoundRobinUseCase.findAll();

        for (RoundRobin roundRobin : roundRobins) {
            List<TeamRoundRobin> teamRoundRobins =
                    findTeamRoundRobinUseCase.findAllByRoundRobin(
                            roundRobin.getIdChampionship());
            for (TeamRoundRobin teamRoundRobin : teamRoundRobins
            ) {
                roundRobin.addTeam(teamRoundRobin.getTeam());
            }
        }

        tableDataRoundRobin.clear();
        tableDataRoundRobin.addAll(roundRobins);
    }

    private void blindTableViewToItemListMataMata() {
        tableDataKnockout = FXCollections.observableArrayList();
        tabelaCampeonatosMataMata.setItems(tableDataKnockout);
    }

    private void blindColumnsToValueSourcesMataMata() {
        idCampeonatoMataMata.setCellValueFactory(new PropertyValueFactory<>("idChampionship"));
        nomeCampeonatoMataMata.setCellValueFactory(new PropertyValueFactory<>("name"));
        dataIniCampeonatoMataMata.setCellValueFactory(new PropertyValueFactory<>("initialDate"));
        dataFimCampeonatoMataMata.setCellValueFactory(new PropertyValueFactory<>("finalDate"));
        modalidadeCampeonatoMataMata.setCellValueFactory(new PropertyValueFactory<>("modality"));
        statusCampeonatoMataMata.setCellValueFactory(new PropertyValueFactory<>("concluded"));
    }

    private void loadDataAndShowMataMata() {

        List<Knockout> knockouts = findKnockoutUseCase.findAll();

        for (Knockout knockout : knockouts) {
            List<TeamKnockout> teamKnockouts =
                    findTeamKnockoutUseCase.findAllByKnockout(knockout.getIdChampionship());
            for (TeamKnockout teamknockout : teamKnockouts
            ) {
                System.out.println("Adicionado");
                knockout.addTeam(teamknockout.getTeam());
            }
        }

        tableDataKnockout.clear();
        tableDataKnockout.addAll(knockouts);
    }

    @FXML
    public void nextScene(ActionEvent actionEvent) {
        Knockout selectKnockout = tabelaCampeonatosMataMata.getSelectionModel().getSelectedItem();
        RoundRobin selectRoundRobin = tabelaCampeonatosPontosCorridos.getSelectionModel().getSelectedItem();

        if (selectKnockout != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifsp/managementMataMata.fxml"));
                Parent root = loader.load();

                ManagementMataMataController managementMataMataController = loader.getController();
                managementMataMataController.initialize(selectKnockout);

                Scene scene = new Scene(root);

                Stage stage = (Stage) btnAcompanharCamp.getScene().getWindow();
                stage.setScene(scene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (selectRoundRobin != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifsp/pontosCorridosManage.fxml"));
                Parent root = loader.load();

                PontosCorridosManageController pontosCorridosManageController = loader.getController();
                pontosCorridosManageController.initialize(selectRoundRobin);

                Scene scene = new Scene(root);

                Stage stage = (Stage) btnAcompanharCamp.getScene().getWindow();
                stage.setScene(scene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void adicionarTimes(ActionEvent event) {
        Knockout selectKnockout = tabelaCampeonatosMataMata.getSelectionModel().getSelectedItem();
        RoundRobin selectRoundRobin = tabelaCampeonatosPontosCorridos.getSelectionModel().getSelectedItem();

        if (selectKnockout != null) {
            if (selectKnockout.getSeeding().size() >= 1) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText("Campeonato Mata-Mata já iniciado");
                alert.setContentText("O campeonato Mata-Mata já foi iniciado e novos times não podem ser adicionados.");
                alert.showAndWait();
            } else {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifsp/AdicionarTimesMataMata.fxml"));
                    Parent root = loader.load();

                    AdicionarTimesMataMataController adicionarTimesMataMataController = loader.getController();
                    adicionarTimesMataMataController.initialize(selectKnockout);

                    Scene scene = new Scene(root);

                    Stage stage = (Stage) btnAdicionarTimes.getScene().getWindow();
                    stage.setScene(scene);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (selectRoundRobin != null) {
            if (selectRoundRobin.getTable().size() >= 1) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText("Campeonato Pontos-Corridos já iniciado");
                alert.setContentText("O campeonato Pontos-Corridos já foi iniciado e novos times não podem ser adicionados.");
                alert.showAndWait();
            } else {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifsp/AdicionarTimesRoundRobin.fxml"));
                    Parent root = loader.load();

                    AdicionarTimesRoundRobinController adicionarTimesRoundRobinController = loader.getController();
                    adicionarTimesRoundRobinController.initialize(selectRoundRobin);

                    Scene scene = new Scene(root);

                    Stage stage = (Stage) btnAdicionarTimes.getScene().getWindow();
                    stage.setScene(scene);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void iniciarCampeonato(ActionEvent event) {
        Knockout selectKnockout = tabelaCampeonatosMataMata.getSelectionModel().getSelectedItem();
        RoundRobin selectRoundRobin = tabelaCampeonatosPontosCorridos.getSelectionModel().getSelectedItem();

        StartKnockoutUseCase startKnockoutUseCase = new StartKnockoutUseCase();
        StartRoundRobinUseCase startRoundRobinUseCase = new StartRoundRobinUseCase();

        if (selectKnockout != null) {

            if (selectKnockout.getSeeding().size() >= 1) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText("Campeonato Mata-Mata já iniciado");
                alert.setContentText("O campeonato Mata-Mata já foi iniciado e não pode ser reiniciado.");
                alert.showAndWait();

            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmação");
                alert.setHeaderText("Iniciar Campeonato Mata-Mata");
                alert.setContentText("Deseja realmente iniciar o campeonato Mata-Mata?");
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK) {
                    try {

                        startKnockoutUseCase.startKnockout(selectKnockout.getIdChampionship());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            if (selectRoundRobin != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmação");
                alert.setHeaderText("Iniciar Campeonato Pontos Corridos");
                alert.setContentText("Deseja realmente iniciar o campeonato Pontos Corridos?");
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK) {
                    try {
                        startRoundRobinUseCase.startRoundRobin(selectRoundRobin.getIdChampionship());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
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
