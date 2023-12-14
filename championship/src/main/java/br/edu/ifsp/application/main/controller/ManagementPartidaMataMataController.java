package br.edu.ifsp.application.main.controller;

import br.edu.ifsp.application.main.Main;
import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.championship.KnockoutMatch;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.services.PhaseServices;
import br.edu.ifsp.domain.usecases.knockout.administration.FinishKnockoutMatchUseCase;
import br.edu.ifsp.domain.usecases.knockout.administration.UpdateKnockoutMatchUseCase;
import br.edu.ifsp.domain.usecases.knockout.administration.FinishKnockoutUseCase;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

import java.util.List;

public class ManagementPartidaMataMataController {

    @FXML
    private Label labelTime1;

    @FXML
    private Label labelTime2;

    private KnockoutMatch selectedMatch;
    @FXML

    private Button btnCancelar;

    @FXML

    private Button btnSalvar;
    @FXML
    private TextField placarTime1;

    @FXML
    private TextField placarTime2;

    private PhaseServices phaseServices = new PhaseServices();


    public void initialize(KnockoutMatch selectedMatch) {
        this.selectedMatch = selectedMatch;
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

    @FXML
    public void cancelarPlacar(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifsp/manageChampionship.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) btnCancelar.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void salvarPlacar(ActionEvent actionEvent) {
        try {
            String placarTime1Text = placarTime1.getText();
            String placarTime2Text = placarTime2.getText();

            if (!isNumeric(placarTime1Text) || !isNumeric(placarTime2Text)) {
                showAlert("Insira valores válidos para os placares.");
                return;
            }

            int placarTime1Value = Integer.parseInt(placarTime1Text);
            int placarTime2Value = Integer.parseInt(placarTime2Text);


            if (placarTime1Value <= 0 || placarTime2Value <= 0) {
                showAlert("Insira valores válidos para os placares.");
                return;
            }

            selectedMatch.setScoreboard1(placarTime1Value);
            selectedMatch.setScoreboard2(placarTime2Value);

            UpdateKnockoutMatchUseCase updateKnockoutMatchUseCase = new UpdateKnockoutMatchUseCase();
            updateKnockoutMatchUseCase.updateMatchResultByIds(selectedMatch.getIdMatch(), placarTime1Value, placarTime2Value);

            if (updateKnockoutMatchUseCase.matchServices.notDraw(selectedMatch)) {
                System.out.println("Partida não é um empate.");

                updateKnockoutMatchUseCase.matchServices.concludeMatch(selectedMatch);
                Main.updateKnockoutMatchUseCase.update(selectedMatch);

                Team vencedor = updateKnockoutMatchUseCase.matchServices.getWinner(selectedMatch);

                if (vencedor != null) {
                    System.out.println("Vencedor identificado corretamente: " + vencedor.getName());
                    showAlert("O vencedor é: " + vencedor.getName());

                    List<KnockoutMatch> matches = selectedMatch.getPhase().getMatches();
                    if (phaseServices.allMatchesFinished(matches)) {
                        if (phaseServices.isFinalMatch(selectedMatch)) {
                            showAlert("                             Parabéns ao campeão \n" +
                                    "                                          " + vencedor.getName());

                            Knockout knockout = getKnockoutFromSelectedMatch();
                            if (knockout != null) {
                                FinishKnockoutUseCase finishKnockoutUseCase = new FinishKnockoutUseCase();
                                finishKnockoutUseCase.finishKnockout(knockout.getId());
                            } else {
                                System.out.println("Não foi possível obter o campeonato para finalização.");
                            }
                        }
                    }
                    FinishKnockoutMatchUseCase finishKnockoutMatchUseCase = new FinishKnockoutMatchUseCase();
                    finishKnockoutMatchUseCase.setMatchConcludedByIds(selectedMatch.getIdMatch());
                } else {
                    System.out.println("Erro: Vencedor não identificado.");
                }
            } else {
                System.out.println("Partida é um empate.");
                showAlert("A partida não pode terminar em empate. Insira os placares novamente.");
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/ifsp/manageChampionship.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnCancelar.getScene().getWindow();
            stage.setScene(scene);

        } catch (NumberFormatException e) {
            // Adicione logs para depuração
            System.out.println("Erro: Conversão de placares para inteiros falhou.");
            e.printStackTrace();
            showAlert("Por favor, insira valores válidos para os placares.");
        } catch (EntityNotFoundException | IllegalArgumentException | IllegalStateException e) {
            e.printStackTrace();
            showAlert("Erro ao processar placares.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erro desconhecido.");
        }
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private Knockout getKnockoutFromSelectedMatch() {
        if (selectedMatch != null && selectedMatch.getPhase() != null) {
            return selectedMatch.getPhase().getKnockout();
        }
        return null;
    }

}
