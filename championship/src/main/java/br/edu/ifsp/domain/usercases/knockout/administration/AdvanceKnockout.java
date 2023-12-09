package br.edu.ifsp.domain.usercases.knockout.administration;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.championship.Phase;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.services.KnockoutServices;
import br.edu.ifsp.domain.services.PhaseServices;

import java.util.List;

public class AdvanceKnockout {

    KnockoutServices knockoutServices = new KnockoutServices();
    PhaseServices phaseServices = new PhaseServices();

    public void AdvancePhase(Knockout knockout) {
        int lastNonEmptyPhaseIndex = knockoutServices.findLastNonEmptyPhaseIndex(knockout);

        if (lastNonEmptyPhaseIndex != -1) {
            Phase lastNonEmptyPhase = knockout.getSeeding().get(lastNonEmptyPhaseIndex);

            if (phaseServices.allMatchesFinished(lastNonEmptyPhase.getMatches())) {

                List<Team> winners = phaseServices.getWinners(lastNonEmptyPhase.getMatches());

                knockoutServices.createMatchesForNextUnfinishedPhase(knockout, lastNonEmptyPhaseIndex + 1, winners);

                lastNonEmptyPhase.setFinished(true);
            }
        }
    }

}
