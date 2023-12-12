package br.edu.ifsp.application.main.controller;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.championship.KnockoutMatch;
import br.edu.ifsp.domain.entities.championship.Match;
import br.edu.ifsp.domain.entities.championship.Phase;
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

import java.util.List;

import static br.edu.ifsp.application.main.Main.*;

public class ManagementMataMataController {
    @FXML
    private Button btnVoltar;
    @FXML
    private TableView<KnockoutMatch> tableView;

    @FXML
    private TableColumn<Team, String> time1;

    @FXML
    private TableColumn<Team, String> time2;
    private ObservableList<KnockoutMatch> tableData;

    @FXML
    public void initialize(List<KnockoutMatch> matches) {
        blindTableViewToItemList();
        blindColumnsToValueSources();
        loadDataAndShow(matches);
    }

    private void blindTableViewToItemList(){
        tableData = FXCollections.observableArrayList();
        tableView.setItems(tableData);
    }

    private void blindColumnsToValueSources(){
        time1.setCellValueFactory(new PropertyValueFactory<>("name"));
        time2.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    private void loadDataAndShow(List<KnockoutMatch> matches){
        matches = findKnockoutMatchUseCase.findAll();
        for(KnockoutMatch knockoutMatch: matches){
            tableData.clear();
            tableData.addAll(knockoutMatch);
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

    public void editarResultado(ActionEvent actionEvent) {
    }

    public void nextSeed(ActionEvent actionEvent) {
    }
}
