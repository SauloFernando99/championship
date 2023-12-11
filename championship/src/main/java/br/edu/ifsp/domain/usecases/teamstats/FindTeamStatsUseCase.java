package br.edu.ifsp.domain.usecases.teamstats;

import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.entities.team.TeamStats;

import java.util.List;
import java.util.Optional;

public class FindTeamStatsUseCase {
    private TeamStatsDAO teamStatsDAO;

    public FindTeamStatsUseCase(TeamStatsDAO teamStatsDAO) {
        this.teamStatsDAO = teamStatsDAO;
    }

    public Optional<TeamStats> findOne(Integer id){
        if (id == null)
            throw new IllegalArgumentException("ID can not be null.");
        return teamStatsDAO.findOne(id);
    }

    public List<TeamStats> findAll(){
        return teamStatsDAO.findAll();
    }
}