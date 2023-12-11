package br.edu.ifsp.domain.usecases.phase;

import br.edu.ifsp.domain.entities.championship.Phase;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.utils.Notification;
import br.edu.ifsp.domain.usecases.utils.Validator;

public class PhaseInputRequestValidator extends Validator<Phase> {
    @Override
    public Notification validate(Phase phase) {
        Notification notification = new Notification();

        if (phase == null) {
            notification.addError("Phase is null");
            return notification;
        }
        if(nullOrEmpty(phase.getPhase()))
            notification.addError("Phase name is null or empty");

        return notification;
    }
}
