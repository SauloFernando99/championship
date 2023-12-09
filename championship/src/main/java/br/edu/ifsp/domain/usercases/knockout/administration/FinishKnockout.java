package br.edu.ifsp.domain.usercases.knockout.administration;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.championship.Phase;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.services.KnockoutServices;
import br.edu.ifsp.domain.services.MatchServices;

import java.util.List;

public class FinishKnockout {

    KnockoutServices knockoutServices = new KnockoutServices();
    MatchServices matchServices = new MatchServices();
    public void finishKnockout(Knockout knockout) {
        List<Phase> phases = knockout.getSeeding();

        int lastNonEmptyPhaseIndex = knockoutServices.findLastNonEmptyPhaseIndex(knockout);

        if (lastNonEmptyPhaseIndex != -1) {
            Phase lastNonEmptyPhase = phases.get(lastNonEmptyPhaseIndex);

            if (lastNonEmptyPhase.getMatches().size() == 1 && lastNonEmptyPhase.getFinished()) {

                Team champion = matchServices.getWinner(lastNonEmptyPhase.getMatches().get(0));

                lastNonEmptyPhase.setFinished(true);

                knockout.setChampion(champion);

                knockout.setConcluded(true);
            }
        }
    }
}
