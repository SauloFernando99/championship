package br.edu.ifsp.domain.usecases.dbsupport.teamknockout;

import br.edu.ifsp.domain.entities.dbsupport.TeamKnockout;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.utils.Notification;
import br.edu.ifsp.domain.usecases.utils.Validator;

public class TeamKnockoutInputRequestValidator extends Validator<TeamKnockout> {
    @Override
    public Notification validate(TeamKnockout teamKnockout) {
        Notification notification = new Notification();

        if (teamKnockout == null) {
            notification.addError("TeamKnockout is null");
            return notification;
        }
        if(nullOrEmpty(String.valueOf(teamKnockout.getKnockout())))
            notification.addError("Knockout is null or empty");
        if(nullOrEmpty(String.valueOf(teamKnockout.getTeam())))
            notification.addError("Team is null or empty");

        return notification;
    }
}
