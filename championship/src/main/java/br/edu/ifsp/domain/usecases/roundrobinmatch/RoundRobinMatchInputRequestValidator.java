package br.edu.ifsp.domain.usecases.roundrobinmatch;

import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.entities.championship.RoundRobinMatch;
import br.edu.ifsp.domain.usecases.utils.Notification;
import br.edu.ifsp.domain.usecases.utils.Validator;

import java.util.Collection;

public class RoundRobinMatchInputRequestValidator extends Validator<RoundRobinMatch> {
    @Override
    public Notification validate(RoundRobinMatch roundRobinMatch) {
        Notification notification = new Notification();

        if (roundRobinMatch == null) {
            notification.addError("RoundRobinMatch is null");
            return notification;
        }
        if(nullOrEmpty(String.valueOf(roundRobinMatch.getRound().getNumber())))
            notification.addError("Round is null or empty");
        if(nullOrEmpty(String.valueOf(roundRobinMatch.getTeam1().getIdTeam())))
            notification.addError("Team1 is null or empty");
        if(nullOrEmpty(String.valueOf(roundRobinMatch.getTeam2().getIdTeam())))
            notification.addError("FinalDate is null or empty");

        return notification;
    }
}
