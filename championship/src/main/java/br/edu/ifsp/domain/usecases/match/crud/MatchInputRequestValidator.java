package br.edu.ifsp.domain.usecases.match.crud;

import br.edu.ifsp.domain.entities.championship.Match;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.utils.Notification;
import br.edu.ifsp.domain.usecases.utils.Validator;

public class MatchInputRequestValidator extends Validator<Match> {
    @Override
    public Notification validate(Match match) {
        Notification notification = new Notification();

        if (match == null) {
            notification.addError("Match is null");
            return notification;
        }
        if(nullOrEmpty(String.valueOf(match.getDate())))
            notification.addError("Date is null or empty");
        if(nullOrEmpty(String.valueOf(match.getTeam1())))
            notification.addError("Team1 is null or empty");
        if(nullOrEmpty(String.valueOf(match.getTeam2())))
            notification.addError("Team2 is null or empty");

        return notification;
    }
}
