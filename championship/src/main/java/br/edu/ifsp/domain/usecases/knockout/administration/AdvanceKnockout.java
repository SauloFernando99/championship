package br.edu.ifsp.domain.usecases.knockout.administration;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.championship.KnockoutMatch;
import br.edu.ifsp.domain.entities.championship.Phase;
import br.edu.ifsp.domain.entities.dbsupport.TeamKnockout;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.services.KnockoutServices;
import br.edu.ifsp.domain.services.PhaseServices;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static br.edu.ifsp.application.main.Main.*;

public class AdvanceKnockout {
    KnockoutServices knockoutServices = new KnockoutServices();
    PhaseServices phaseServices = new PhaseServices();

    public void advancePhase(Knockout knockout) {
        if (knockout == null) {
            throw new IllegalArgumentException("Knockout object is null");
        }

        List<Phase> foundPhases = findPhaseUseCase.findAll();

        List<Phase> registeredPhases = new ArrayList<>();

        for (Phase phase: foundPhases
             ) {
            if(phase.getKnockout().getIdChampionship() == knockoutId){
                registeredPhases.add(phase);
            }
        }

        Collections.sort(registeredPhases, Comparator.comparingInt(phase -> phase.getMatches().size()));

        knockout.getSeeding().addAll(registeredPhases);

        int lastNonEmptyPhaseIndex = knockoutServices.getLastPhaseIndex(knockout);

        if (lastNonEmptyPhaseIndex != -1) {
            Phase lastNonEmptyPhase = knockout.getSeeding().get(lastNonEmptyPhaseIndex);

            if (!lastNonEmptyPhase.getFinished() && phaseServices.allMatchesFinished(lastNonEmptyPhase.getMatches())) {
                List<Team> winners = phaseServices.getWinners(lastNonEmptyPhase.getMatches());

                if (!winners.isEmpty()) {
                    knockoutServices.generateNextPhase(knockout, winners);

                    lastNonEmptyPhase.setFinished(true);
                    updatePhaseUseCase.update(lastNonEmptyPhase);

                    createPhaseUseCase.insert(knockout.getSeeding().get(lastNonEmptyPhaseIndex + 1));

                    for (KnockoutMatch match : knockout.getSeeding().get(lastNonEmptyPhaseIndex + 1).getMatches()) {
                        createKnockoutMatchUseCase.insert(match);
                    }
                } else {
                    throw new IllegalStateException("The list of winners is empty. Cannot advance to the next phase.");
                }
            } else {
                System.out.println("Alguns jogos ainda não foram finalizados. Complete todas as partidas antes de avançar.");
            }
        }
    }
}