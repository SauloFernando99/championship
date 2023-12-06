package br.edu.ifsp.domain.usecases.team;

import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.utils.Notification;
import br.edu.ifsp.domain.usecases.utils.Validator;

public class TeamInputRequestValidator extends Validator<Team> {
    @Override
    public Notification validate(Team team) {
        Notification notification = new Notification();

        if (team == null) {
            notification.addError("Team is null");
            return notification;
        }
        if(nullOrEmpty(team.getName()))
            notification.addError("Name is null or empty");
        if(nullOrEmpty(team.getCrest()))
            notification.addError("Crest is null or empty");

        return notification;
    }
}
