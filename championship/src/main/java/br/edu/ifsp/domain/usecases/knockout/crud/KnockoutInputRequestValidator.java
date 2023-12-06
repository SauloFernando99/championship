package br.edu.ifsp.domain.usecases.knockout;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.utils.Notification;
import br.edu.ifsp.domain.usecases.utils.Validator;

public class KnockoutInputRequestValidator extends Validator<Knockout> {
    @Override
    public Notification validate(Knockout knockout) {
        Notification notification = new Notification();

        if (knockout == null) {
            notification.addError("Knockout is null");
            return notification;
        }
        if(nullOrEmpty(String.valueOf(knockout.getInitialDate())))
            notification.addError("Initial date is null or empty");
        if(nullOrEmpty(String.valueOf(knockout.getInitialDate())))
            notification.addError("Final date is null or empty");
        if(nullOrEmpty(knockout.getModality()))
            notification.addError("Modality is null or empty");
        if(nullOrEmpty(knockout.getAward()))
            notification.addError("Award is null or empty");
        if(nullOrEmpty(knockout.getSponsorship()))
            notification.addError("Sponsorship is null or empty");

        return notification;
    }
}
