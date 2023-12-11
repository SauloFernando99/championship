package br.edu.ifsp.domain.usecases.round;

import br.edu.ifsp.domain.entities.championship.Round;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.utils.Notification;
import br.edu.ifsp.domain.usecases.utils.Validator;

public class RoundInputRequestValidator extends Validator<Round> {
    @Override
    public Notification validate(Round round) {
        Notification notification = new Notification();

        if (round == null) {
            notification.addError("Round is null");
            return notification;
        }

        return notification;
    }
}
