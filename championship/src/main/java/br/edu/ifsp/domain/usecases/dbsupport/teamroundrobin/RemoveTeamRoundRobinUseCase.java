package br.edu.ifsp.domain.usecases.dbsupport.teamroundrobin;

import br.edu.ifsp.domain.entities.dbsupport.TeamRoundRobin;

import java.util.List;

public class RemoveTeamRoundRobinUseCase {

    private TeamRoundRobinDAO teamRoundRobinDAO;

    public RemoveTeamRoundRobinUseCase(TeamRoundRobinDAO teamRoundRobinDAO) {

        this.teamRoundRobinDAO = teamRoundRobinDAO;
    }

    public boolean removeAllByRoundRobin(Integer idChampionship) {
        if (idChampionship == null) {
            throw new IllegalArgumentException("ID cannot be null.");
        }

        List<TeamRoundRobin> teamRoundRobinsToRemove = teamRoundRobinDAO.findAllByRoundRobin(idChampionship);

        for (TeamRoundRobin teamRoundRobin : teamRoundRobinsToRemove) {
            teamRoundRobinDAO.delete(teamRoundRobin);
        }
        return true;
    }

    public boolean removeAllByTeam(Integer idTeam) {
        if (idTeam == null) {
            throw new IllegalArgumentException("ID cannot be null.");
        }

        List<TeamRoundRobin> teamRoundRobinsToRemove = teamRoundRobinDAO.findAllByTeam(idTeam);

        for (TeamRoundRobin teamRoundRobin : teamRoundRobinsToRemove) {
            teamRoundRobinDAO.delete(teamRoundRobin);
        }
        return true;
    }
}