    package br.edu.ifsp.application.main.controller.team;

    import br.edu.ifsp.domain.entities.team.Team;
    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.scene.control.Button;
    import javafx.scene.control.TextArea;
    import javafx.scene.control.TextField;
    import javafx.stage.Stage;

    import static br.edu.ifsp.application.main.Main.updateTeamUseCase;

    public class EditTeamController {

        @FXML
        private TextField txtNomeTime;

        @FXML
        private TextArea txtBandeiraTime;
        private boolean  status;
        @FXML
        private Button btnAtivarTime;
        @FXML
        private Button btnInativar;
        @FXML
        private Button btnCancelar;
        private Team teamToEdit;

        public void initializeData(Team team) {
            this.teamToEdit = team;

            txtNomeTime.setText(team.getName());
            txtBandeiraTime.setText(team.getCrest());
        }

        @FXML
        private void saveChanges() {
            String newName = txtNomeTime.getText();
            String newCrest = txtBandeiraTime.getText();

            teamToEdit.setName(newName);
            teamToEdit.setCrest(newCrest);
            teamToEdit.setActive(status);

            boolean updateSuccess = updateTeamUseCase.update(teamToEdit);

            if (updateSuccess) {
                closeStage();
            } else {
                System.out.println("Erro ao atualizar o time. Verifique os dados e tente novamente.");
            }
        }

        @FXML
        private void cancelEdit() {
            closeStage();
        }

        @FXML
        private void closeStage() {
            Stage stage = (Stage) btnCancelar.getScene().getWindow();
            stage.close();
        }

        @FXML
        private void ativarTime() {
            this.status = true;
        }

        @FXML
        private void inativarTime() {
            this.status = false;
        }
    }
