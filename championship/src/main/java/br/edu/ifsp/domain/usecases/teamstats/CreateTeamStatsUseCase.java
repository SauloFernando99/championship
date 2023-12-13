package br.edu.ifsp.domain.usecases.teamstats;

import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.entities.team.TeamStats;
import br.edu.ifsp.domain.usecases.utils.EntityAlreadyExistsException;
import br.edu.ifsp.domain.usecases.utils.Notification;
import br.edu.ifsp.domain.usecases.utils.Validator;

public class CreateTeamStatsUseCase {
    private TeamStatsDAO teamStatsDAO;
    public CreateTeamStatsUseCase(TeamStatsDAO teamStatsDAO) {
        this.teamStatsDAO = teamStatsDAO;
    }

    public Integer insert(TeamStats teamStats){
        Validator<TeamStats> validator = new TeamStatsInputRequestValidator();
        Notification notification = validator.validate(teamStats);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        return teamStatsDAO.create(teamStats);
    }
}
