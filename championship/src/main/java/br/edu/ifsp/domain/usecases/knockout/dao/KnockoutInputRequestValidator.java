package br.edu.ifsp.domain.usecases.knockout.dao;

import br.edu.ifsp.domain.entities.championship.Knockout;
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
        if(nullOrEmpty(knockout.getName()))
            notification.addError("Name is null or empty");
        if(nullOrEmpty(String.valueOf(knockout.getInitialDate())))
            notification.addError("InitialDate is null or empty");
        if(nullOrEmpty(String.valueOf(knockout.getFinalDate())))
            notification.addError("FinalDate is null or empty");
        if(nullOrEmpty(knockout.getModality()))
            notification.addError("Modality is null or empty");
        if(nullOrEmpty(knockout.getAward()))
            notification.addError("Award is null or empty");
        if(nullOrEmpty(knockout.getSponsorship()))
            notification.addError("Sponsorship is null or empty");

        return notification;
    }
}
