package br.edu.ifsp.domain.usecases.teamstats;

import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.entities.team.TeamStats;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;
import br.edu.ifsp.domain.usecases.utils.Notification;
import br.edu.ifsp.domain.usecases.utils.Validator;

public class UpdateTeamStatsUseCase {
    private TeamStatsDAO teamStatsDAO;

    public UpdateTeamStatsUseCase(TeamStatsDAO teamStatsDAO) {
        this.teamStatsDAO = teamStatsDAO;
    }

    public boolean update(TeamStats teamStats){
        Validator<TeamStats> validator = new TeamStatsInputRequestValidator();
        Notification notification = validator.validate(teamStats);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer id = teamStats.getIdTeamStats();
        if(teamStatsDAO.findOne(id).isEmpty())
            throw new EntityNotFoundException("TeamStats not found.");

        return teamStatsDAO.update(teamStats);
    }
}