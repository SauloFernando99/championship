package br.edu.ifsp.domain.usecases.dbsupport.teamroundrobin;

import br.edu.ifsp.domain.entities.dbsupport.TeamRoundRobin;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FindTeamRoundRobinUseCase {
    private TeamRoundRobinDAO teamRoundRobinDAO;

    public FindTeamRoundRobinUseCase(TeamRoundRobinDAO teamRoundRobinDAO) {
        this.teamRoundRobinDAO = teamRoundRobinDAO;
    }

    public Optional<TeamRoundRobin> findOne(Integer idTeam, Integer idRoundRobin) {
        if (idTeam == null || idRoundRobin == null) {
            throw new IllegalArgumentException("IDs cannot be null.");
        }

        List<TeamRoundRobin> combinations = teamRoundRobinDAO.findAll();

        for (TeamRoundRobin teamRoundRobin : combinations) {
            if (idTeam.equals(teamRoundRobin.getTeam().getIdTeam())
                    && idRoundRobin.equals(teamRoundRobin.getRoundRobin().getIdChampionship())) {
                return Optional.of(teamRoundRobin);
            }
        }

        throw new EntityNotFoundException("TeamKnockout not found for given IDs.");
    }

    public List<TeamRoundRobin> findAllByRoundRobin(Integer idRoundRobin) {
        if (idRoundRobin == null) {
            throw new IllegalArgumentException("ID cannot be null.");
        }

        List<TeamRoundRobin> allTeamRoundRobins = teamRoundRobinDAO.findAll();
        List<TeamRoundRobin> result = new ArrayList<>();

        for (TeamRoundRobin teamRoundRobin : allTeamRoundRobins) {
            if (idRoundRobin.equals(teamRoundRobin.getRoundRobin().getIdChampionship())) {
                result.add(teamRoundRobin);
            }
        }

        return result;
    }

    public List<TeamRoundRobin> findAllByTeam(Integer idTeam) {
        if (idTeam == null) {
            throw new IllegalArgumentException("ID cannot be null.");
        }

        List<TeamRoundRobin> allTeamRoundRobins = teamRoundRobinDAO.findAll();
        List<TeamRoundRobin> result = new ArrayList<>();

        for (TeamRoundRobin teamRoundRobin : allTeamRoundRobins) {
            if (idTeam.equals(teamRoundRobin.getTeam().getIdTeam())) {
                result.add(teamRoundRobin);
            }
        }

        return result;
    }



    public List<TeamRoundRobin> findAll(){
        return teamRoundRobinDAO.findAll();
    }
}