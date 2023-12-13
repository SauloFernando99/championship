package br.edu.ifsp.domain.usecases.knockout.administration;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.championship.Phase;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.services.KnockoutServices;
import br.edu.ifsp.domain.services.MatchServices;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static br.edu.ifsp.application.main.Main.*;

public class FinishKnockoutUseCase {

    KnockoutServices knockoutServices = new KnockoutServices();
    MatchServices matchServices = new MatchServices();

    public void finishKnockout(Integer knockoutId) {

        if (knockoutId == null)
            throw new IllegalArgumentException("Knockout ID is null");

        Knockout knockout = findKnockoutUseCase.findOne(knockoutId)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a Knockout with id: "
                        + knockoutId));

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

            if (lastNonEmptyPhase.getMatches().size() == 1) {
                Team champion = matchServices.getWinner(lastNonEmptyPhase.getMatches().get(0));

                lastNonEmptyPhase.setFinished(true);

                knockout.setChampion(champion);

                knockout.setConcluded(true);

                updateKnockoutUseCase.update(knockout);
                updatePhaseUseCase.update(lastNonEmptyPhase);

                System.out.println("\nChampion: " + knockout.getChampion().getName());
            }
        }
    }
}