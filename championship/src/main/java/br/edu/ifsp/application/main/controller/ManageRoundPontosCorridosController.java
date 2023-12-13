package br.edu.ifsp.application.main.controller;

import br.edu.ifsp.application.main.export.PDFExporterClassificacao;
import br.edu.ifsp.application.main.export.PDFExporterRodada;
import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.championship.Round;
import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.entities.team.TeamStats;
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

import java.io.File;
import java.util.List;

import static br.edu.ifsp.application.main.Main.findRoundUseCase;

public class ManageRoundPontosCorridosController {
    @FXML
    private Button btnVoltar;
    @FXML
    private Button btnGerenciarRodada;
    @FXML
    private Button btnExportRodada;
    @FXML
    private TableView<Round> tableView;
    @FXML
    private TableColumn<Round, Integer> numRodada;
    @FXML
    private TableColumn<Round, Boolean> statusRodada;
    @FXML
    private ObservableList<Round> tableData;
    private List<Round> selectedRoundList;

    @FXML
    public void initialize(List<Round> roundList) {
        selectedRoundList = roundList;
        blindTableViewToItemList();
        blindColumnsToValueSources();
        loadDataAndShow();
    }
    private void blindTableViewToItemList(){
        tableData = FXCollections.observableArrayList();
        tableView.setItems(tableData);
    }

    private void blindColumnsToValueSources() {
        numRodada.setCellValueFactory(new PropertyValueFactory<>("number"));
        statusRodada.setCellValueFactory(new PropertyValueFactory<>("finished"));
    }

    private void loadDataAndShow() {
        List<Round> rounds = findRoundUseCase.findAll();
        tableData.clear();
        tableData.addAll(rounds);
    }

    @FXML
    public void exportRodada(ActionEvent actionEvent) {

        Round selectedRound = tableView.getSelectionModel().getSelectedItem();

        if (selectedRound == null) {
            showAlert("Nenhuma Rodada foi selecionada!");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar Rodada");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos PDF", "*.pdf"));

        Stage stage = (Stage) btnExportRodada.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            PDFExporterRodada.exportRoundToPDF(selectedRound, file.getAbsolutePath());
        }
    }



    public void nextScene(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifsp/ViewPartidaPontosCorridos.fxml"));
            Parent root = loader.load();

            ViewPartidaPontosCorridosController viewPartidaPontosCorridosController = loader.getController();
            Round round = tableView.getSelectionModel().getSelectedItem();
            viewPartidaPontosCorridosController.initialize(selectedRoundList, round);

            Scene scene = new Scene(root);

            Stage stage = (Stage) btnGerenciarRodada.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
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

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

}
