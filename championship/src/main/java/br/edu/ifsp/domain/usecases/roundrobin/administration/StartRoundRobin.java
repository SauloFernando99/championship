package br.edu.ifsp.domain.usecases.roundrobin.administration;

import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.services.RoundRobinServices;

import java.util.List;

public class StartRoundRobin {
    RoundRobinServices roundRobinServices = new RoundRobinServices();

    public void startRoundRobin(RoundRobin roundRobin) {
        List<Team> teams = roundRobin.getTeams();

        if (teams.size() % 2 != 0) {
            throw new IllegalArgumentException("The number of teams must be even to start the Round Robin.");
        }

        roundRobinServices.generateTable(roundRobin, teams);
    }
}
