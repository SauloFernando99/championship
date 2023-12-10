package br.edu.ifsp.application.main.controller;

import br.edu.ifsp.domain.entities.team.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static br.edu.ifsp.application.main.Main.createTeamUseCase;

public class TeamUIController {
    @FXML
    private TextField txtNomeCampeonato;

    @FXML
    private TextArea txtBandeiraCampeonato;

    @FXML
    public void createTeam(ActionEvent actionEvent) {
        String name = txtNomeCampeonato.getText();
        String crest = txtBandeiraCampeonato.getText();

        Team team = new Team(name, crest);

        if(team.getIdTeam() == null){
            createTeamUseCase.insert(team);
        }

        System.out.println("Time criado: " + "\n ID: " + team.getIdTeam() + "\n Nome: " + team.getName()
        + "\n Bandeira: " + team.getCrest() + "\n Status: " + team.getActive());

        backToPreviousScene(actionEvent);
    }

    @FXML
    public void backToPreviousScene(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifsp/FirstScene.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();

            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
