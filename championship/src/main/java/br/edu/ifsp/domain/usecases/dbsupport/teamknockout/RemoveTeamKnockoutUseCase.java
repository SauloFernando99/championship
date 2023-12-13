package br.edu.ifsp.domain.usecases.dbsupport.teamknockout;

import br.edu.ifsp.domain.entities.dbsupport.TeamKnockout;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;

import java.util.List;

public class RemoveTeamKnockoutUseCase {

    private TeamKnockoutDAO teamKnockoutDAO;

    public RemoveTeamKnockoutUseCase(TeamKnockoutDAO teamKnockoutDAO) {

        this.teamKnockoutDAO = teamKnockoutDAO;
    }

    public boolean removeAllByKnockout(Integer idChampionship) {
        if (idChampionship == null) {
            throw new IllegalArgumentException("ID cannot be null.");
        }

        List<TeamKnockout> teamKnockoutsToRemove = teamKnockoutDAO.findAllByKnockout(idChampionship);

        for (TeamKnockout teamKnockout : teamKnockoutsToRemove) {
            teamKnockoutDAO.delete(teamKnockout);
        }
        return true;
    }

    public boolean removeAllByTeam(Integer idTeam) {
        if (idTeam == null) {
            throw new IllegalArgumentException("ID cannot be null.");
        }

        List<TeamKnockout> teamKnockoutsToRemove = teamKnockoutDAO.findAllByTeam(idTeam);

        for (TeamKnockout teamKnockout : teamKnockoutsToRemove) {
            teamKnockoutDAO.delete(teamKnockout);
        }
        return true;
    }
}