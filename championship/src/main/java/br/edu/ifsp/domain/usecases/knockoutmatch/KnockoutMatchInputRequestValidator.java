package br.edu.ifsp.domain.usecases.knockoutmatch;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.championship.KnockoutMatch;
import br.edu.ifsp.domain.usecases.utils.Notification;
import br.edu.ifsp.domain.usecases.utils.Validator;

import java.util.Collection;

public class KnockoutMatchInputRequestValidator extends Validator<KnockoutMatch> {
    @Override
    public Notification validate(KnockoutMatch knockoutMatch) {
        Notification notification = new Notification();

        if (knockoutMatch == null) {
            notification.addError("KnockoutMatch is null");
            return notification;
        }
        if(nullOrEmpty(knockoutMatch.getPhase().getPhase()))
            notification.addError("Phase is null or empty");
        if(nullOrEmpty(String.valueOf(knockoutMatch.getTeam1().getIdTeam())))
            notification.addError("Team1 is null or empty");
        if(nullOrEmpty(String.valueOf(knockoutMatch.getTeam2().getIdTeam())))
            notification.addError("FinalDate is null or empty");

        return notification;
    }
}
