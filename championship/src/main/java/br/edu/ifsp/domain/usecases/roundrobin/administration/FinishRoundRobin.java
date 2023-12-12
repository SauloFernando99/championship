package br.edu.ifsp.domain.usecases.roundrobin.administration;

import br.edu.ifsp.domain.entities.championship.Round;
import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.services.RoundRobinServices;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;

import static br.edu.ifsp.application.main.Main.findRoundRobinUseCase;
import static br.edu.ifsp.application.main.Main.updateRoundRobinUseCase;

public class FinishRoundRobin {

    RoundRobinServices roundRobinServices = new RoundRobinServices();

    public void finishChampionship(Integer roundRobinId) {

        if (roundRobinId == null)
            throw new IllegalArgumentException("RoundRobin ID is null");

        RoundRobin roundRobin = findRoundRobinUseCase.findOne(roundRobinId)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a RoundRobin with id: "
                        + roundRobinId));

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

            updateRoundRobinUseCase.update(roundRobin);

        } else {
            throw new IllegalStateException("Championship is not concluded. Not all rounds are finished.");
        }
    }
}
