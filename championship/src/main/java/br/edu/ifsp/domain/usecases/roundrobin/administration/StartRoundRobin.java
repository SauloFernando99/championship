package br.edu.ifsp.domain.usecases.roundrobin.administration;

import br.edu.ifsp.domain.entities.championship.Round;
import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.entities.championship.RoundRobinMatch;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.entities.team.TeamStats;
import br.edu.ifsp.domain.services.RoundRobinServices;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;

import java.util.List;

import static br.edu.ifsp.application.main.Main.*;

public class StartRoundRobin {
    RoundRobinServices roundRobinServices = new RoundRobinServices();

    public void startRoundRobin(Integer roundRobinId) {

        if (roundRobinId == null)
            throw new IllegalArgumentException("RoundRobin ID is null");

        RoundRobin roundRobin = findRoundRobinUseCase.findOne(roundRobinId)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a RoundRobin with id: "
                        + roundRobinId));

        List<Team> teams = roundRobin.getTeams();

        if (teams.size() % 2 != 0) {
            throw new IllegalArgumentException("The number of teams must be even to start the Round Robin.");
        }

        roundRobinServices.generateTable(roundRobin,teams);

        updateRoundRobinUseCase.update(roundRobin);

        for (TeamStats teamStats: roundRobin.getTeamStats()
             ) {
            createTeamStatsUseCase.insert(teamStats);
        }

        for (Round round: roundRobin.getTable()
             ) {
            createRoundUseCase.insert(round);
            for (RoundRobinMatch match: round.getMatches()
                 ) {
                createRoundRobinMatchUseCase.insert(match);
            }
        }
    }
}
