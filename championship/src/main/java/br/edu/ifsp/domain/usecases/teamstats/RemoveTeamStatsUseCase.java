package br.edu.ifsp.domain.usecases.teamstats;

import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.entities.team.TeamStats;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;

public class RemoveTeamStatsUseCase {

    private TeamStatsDAO teamStatsDAO;

    public RemoveTeamStatsUseCase(TeamStatsDAO teamStatsDAO) {
        this.teamStatsDAO = teamStatsDAO;
    }

    public boolean remove(Integer id){
        if(id == null || teamStatsDAO.findOne(id).isEmpty()) {
            throw new EntityNotFoundException("TeamStats not found.");
        }
        return teamStatsDAO.deleteByKey(id);
    }

    public boolean remove(TeamStats teamStats){
        if(teamStats == null || teamStatsDAO.findOne(teamStats.getIdTeamStats()).isEmpty())
            throw new EntityNotFoundException("TeamStats not found.");
        return teamStatsDAO.delete(teamStats);
    }
}