package br.edu.ifsp.domain.usecases.dbsupport.teamknockout;

import br.edu.ifsp.domain.entities.dbsupport.TeamKnockout;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FindTeamKnockoutUseCase {
    private TeamKnockoutDAO teamKnockoutDAO;

    public FindTeamKnockoutUseCase(TeamKnockoutDAO teamKnockoutDAO) {
        this.teamKnockoutDAO = teamKnockoutDAO;
    }

    public Optional<TeamKnockout> findOne(Integer idTeam, Integer idKnockout) {
        if (idTeam == null || idKnockout == null) {
            throw new IllegalArgumentException("IDs cannot be null.");
        }

        List<TeamKnockout> combinations = teamKnockoutDAO.findAll();

        for (TeamKnockout teamKnockout : combinations) {
            if (idTeam.equals(teamKnockout.getTeam().getIdTeam())
                    && idKnockout.equals(teamKnockout.getKnockout().getIdChampionship())) {
                return Optional.of(teamKnockout);
            }
        }

        throw new EntityNotFoundException("TeamKnockout not found for given IDs.");
    }

    public List<TeamKnockout> findAllByKnockout(Integer idKnockout) {
        if (idKnockout == null) {
            throw new IllegalArgumentException("ID cannot be null.");
        }

        List<TeamKnockout> allTeamKnockouts = teamKnockoutDAO.findAll();
        List<TeamKnockout> result = new ArrayList<>();

        if (allTeamKnockouts != null) {
            for (TeamKnockout teamKnockout : allTeamKnockouts) {
                if (idKnockout.equals(teamKnockout.getKnockout().getIdChampionship())) {
                    result.add(teamKnockout);
                }
            }
        }

        return result;
    }

    public List<TeamKnockout> findAllByTeam(Integer idTeam) {
        if (idTeam == null) {
            throw new IllegalArgumentException("ID cannot be null.");
        }

        List<TeamKnockout> allTeamKnockouts = teamKnockoutDAO.findAll();
        List<TeamKnockout> result = new ArrayList<>();

        if(!(allTeamKnockouts == null)){
            for (TeamKnockout teamKnockout : allTeamKnockouts) {
                if (idTeam.equals(teamKnockout.getTeam().getIdTeam())) {
                    result.add(teamKnockout);
                }
            }
        }

        return result;
    }



    public List<TeamKnockout> findAll(){
        return teamKnockoutDAO.findAll();
    }
}