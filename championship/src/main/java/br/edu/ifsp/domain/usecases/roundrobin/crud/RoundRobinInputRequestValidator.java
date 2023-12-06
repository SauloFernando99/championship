package br.edu.ifsp.domain.usecases.roundrobin.crud;

import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.usecases.utils.Notification;
import br.edu.ifsp.domain.usecases.utils.Validator;

public class RoundRobinInputRequestValidator extends Validator<RoundRobin> {
    @Override
    public Notification validate(RoundRobin roundRobin) {
        Notification notification = new Notification();

        if (roundRobin == null) {
            notification.addError("RoundRobin is null");
            return notification;
        }
        if(nullOrEmpty(String.valueOf(roundRobin.getInitialDate())))
            notification.addError("Initial date is null or empty");
        if(nullOrEmpty(String.valueOf(roundRobin.getInitialDate())))
            notification.addError("Final date is null or empty");
        if(nullOrEmpty(roundRobin.getModality()))
            notification.addError("Modality is null or empty");
        if(nullOrEmpty(roundRobin.getAward()))
            notification.addError("Award is null or empty");
        if(nullOrEmpty(roundRobin.getSponsorship()))
            notification.addError("Sponsorship is null or empty");

        return notification;
    }
}
