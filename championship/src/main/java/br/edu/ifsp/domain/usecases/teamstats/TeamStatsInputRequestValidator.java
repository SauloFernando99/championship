package br.edu.ifsp.domain.usecases.teamstats;

import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.entities.team.TeamStats;
import br.edu.ifsp.domain.usecases.utils.Notification;
import br.edu.ifsp.domain.usecases.utils.Validator;

import java.util.Collection;

public class TeamStatsInputRequestValidator extends Validator<TeamStats> {
    @Override
    public Notification validate(TeamStats teamStats) {
        Notification notification = new Notification();

        if (teamStats == null) {
            notification.addError("TeamStats is null");
            return notification;
        }
        if(nullOrEmpty(String.valueOf(teamStats.getTeam())))
            notification.addError("Team is null or empty");

        return notification;
    }
}
