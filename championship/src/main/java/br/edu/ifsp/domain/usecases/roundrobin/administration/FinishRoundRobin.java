package br.edu.ifsp.domain.usecases.roundrobin.administration;

import br.edu.ifsp.domain.entities.championship.Round;
import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.services.RoundRobinServices;

public class FinishRoundRobin {

    RoundRobinServices roundRobinServices = new RoundRobinServices();

    public void finishChampionship(RoundRobin roundRobin) {
        boolean allRoundsConcluded = true;
        for (Round round : roundRobin.getTable()) {
            if (!round.getFinished()) {
                allRoundsConcluded = false;
                break;
            }
        }

        if (allRoundsConcluded) {
            roundRobin.setConcluded(true);

            Team champion = roundRobinServices.findChampion(roundRobin.getTeamStats());
            System.out.println("Championship concluded! The champion is: " + champion.getName());
        } else {
            throw new IllegalStateException("Championship is not concluded. Not all rounds are finished.");
        }
    }
}
