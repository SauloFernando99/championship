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

    public void advancePhase(Knockout knockout) {

        int lastNonEmptyPhaseIndex = knockoutServices.getLastPhaseIndex(knockout);

        if (lastNonEmptyPhaseIndex != -1) {
            Phase lastNonEmptyPhase = knockout.getSeeding().get(lastNonEmptyPhaseIndex);

            if (!lastNonEmptyPhase.getFinished() && phaseServices.allMatchesFinished(lastNonEmptyPhase.getMatches())) {
                List<Team> winners = phaseServices.getWinners(lastNonEmptyPhase.getMatches());

                if (!winners.isEmpty()) {
                    knockoutServices.generateNextPhase(knockout, winners);

                    lastNonEmptyPhase.setFinished(true);
                } else {
                    throw new IllegalStateException("The list of winners is empty. Cannot advance to the next phase.");
                }
            }
        }
    }


}
