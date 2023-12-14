package br.edu.ifsp.application.main.controller;

import br.edu.ifsp.application.main.Main;
import br.edu.ifsp.domain.entities.championship.Round;
import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.entities.championship.RoundRobinMatch;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.roundrobin.administration.FinishRoundRobinMatch;
import br.edu.ifsp.domain.usecases.roundrobin.administration.UpdateRoundRobinMatch;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

public class ManagePartidaPontosCorridosController {


    @FXML
    private Label labelTime1;

    @FXML
    private Label labelTime2;
    @FXML
    private TextField placarTime1;
    @FXML
    private TextField placarTime2;

    @FXML

    private Button btnCancelar;

    @FXML

    private Button btnSalvar;
    private RoundRobinMatch selectedMatch;
    private Round selectedRound;
    private List<Round> selectedRoundList;

    private RoundRobin selectedRoundRobin;


    public void initialize(RoundRobinMatch selectedMatch, RoundRobin roundRobin, List<Round> roundList, Round round) {
        this.selectedMatch = selectedMatch;
        this.selectedRound = round;
        this.selectedRoundList = selectedRoundList;
        this.selectedRoundRobin = roundRobin;
        labelTime1.setText(selectedMatch.getNameTeam1());
        labelTime2.setText(selectedMatch.getNameTeam2());


        if (selectedMatch.getConcluded()) {
            showAlert("A partida já foi encerrada.");
            disableEditing();
        }
    }

    private void disableEditing() {

        placarTime1.setEditable(false);
        placarTime2.setEditable(false);
        btnSalvar.setDisable(true);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
    public void previousScene(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifsp/ViewPartidaPontosCorridos.fxml"));
            Parent root = loader.load();

            ViewPartidaPontosCorridosController viewPartidaPontosCorridosController = loader.getController();
            viewPartidaPontosCorridosController.initialize(selectedRoundRobin,selectedRoundList,selectedRound);

            Scene scene = new Scene(root);

            Stage stage = (Stage) btnCancelar.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void savePartida(ActionEvent actionEvent) {
        try {
            Integer placarTime1Value = Integer.parseInt(placarTime1.getText());
            Integer placarTime2Value = Integer.parseInt(placarTime2.getText());

            if (placarTime1Value < 0 || placarTime2Value < 0) {
                showAlert("Insira valores válidos para os placares.");
                return;
            }

            UpdateRoundRobinMatch updateRoundRobinMatchUseCase = new UpdateRoundRobinMatch();
//            updateRoundRobinMatchUseCase.updateMatchByIds(selectedMatch.getIdMatch(), placarTime1Value, placarTime2Value);
//
//            updateRoundRobinMatchUseCase.matchServices.concludeMatch(selectedMatch);
            selectedMatch.setScoreboard1(placarTime1Value);
            selectedMatch.setScoreboard2(placarTime2Value);
            Team vencedor = updateRoundRobinMatchUseCase.matchServices.getWinner(selectedMatch);
            FinishRoundRobinMatch finishRoundRobinMatch = new FinishRoundRobinMatch();
            finishRoundRobinMatch.finishMatchByIds(selectedMatch.getIdMatch());

            if (vencedor != null) {
                System.out.println("Vencedor identificado corretamente: " + vencedor.getName());
                showAlert("O vencedor é: " + vencedor.getName());
            } else {
                showAlert("A partida terminou empatada");
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifsp/viewPartidaPontosCorridos.fxml"));
            Parent root = loader.load();

            ViewPartidaPontosCorridosController viewPartidaPontosCorridosController = loader.getController();
            viewPartidaPontosCorridosController.initialize(selectedRoundRobin,selectedRoundList,selectedRound);

            Platform.runLater(() -> {
                Scene scene = new Scene(root);
                Stage stage = (Stage) btnCancelar.getScene().getWindow();
                stage.setScene(scene);
            });

        } catch (NumberFormatException e) {
            showAlert("Por favor, insira valores válidos para os placares.");
        } catch (EntityNotFoundException | IllegalArgumentException | IllegalStateException e) {
            showAlert("Erro ao salvar o placar da partida.");
            e.printStackTrace();
        } catch (Exception e) {
            showAlert("Erro inesperado.");
            e.printStackTrace();
        }

    }
}