package br.edu.ifsp.domain.usecases.roundrobin.administration;

import br.edu.ifsp.domain.entities.championship.Round;
import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.services.RoundRobinServices;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static br.edu.ifsp.application.main.Main.*;

public class FinishRoundRobinUseCase {

    RoundRobinServices roundRobinServices = new RoundRobinServices();

    public void finishChampionship(Integer roundRobinId) {

        if (roundRobinId == null)
            throw new IllegalArgumentException("RoundRobin ID is null");

        RoundRobin roundRobin = findRoundRobinUseCase.findOne(roundRobinId)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a RoundRobin with id: "
                        + roundRobinId));

        List<Round> foundRounds = findRoundUseCase.findAll();

        List<Round> registeredRounds = new ArrayList<>();

        for (Round round: foundRounds
        ) {
            if(round.getRoundRobin().getIdChampionship() == roundRobinId){
                registeredRounds.add(round);
            }
        }

        Collections.sort(registeredRounds, Comparator.comparingInt(round -> round.getNumber()));

        roundRobin.getTable().addAll(registeredRounds);

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
