package br.edu.ifsp.domain.usecases.dbsupport.teamroundrobin;

import br.edu.ifsp.domain.entities.dbsupport.TeamKnockout;
import br.edu.ifsp.domain.entities.dbsupport.TeamRoundRobin;
import br.edu.ifsp.domain.usecases.utils.Notification;
import br.edu.ifsp.domain.usecases.utils.Validator;

public class TeamRoundRobinInputRequestValidator extends Validator<TeamRoundRobin> {
    @Override
    public Notification validate(TeamRoundRobin teamRoundRobin) {
        Notification notification = new Notification();

        if (teamRoundRobin == null) {
            notification.addError("TeamKnockout is null");
            return notification;
        }
        if(nullOrEmpty(String.valueOf(teamRoundRobin.getRoundRobin())))
            notification.addError("Round is null or empty");
        if(nullOrEmpty(String.valueOf(teamRoundRobin.getTeam())))
            notification.addError("Team is null or empty");

        return notification;
    }
}
