package br.edu.ifsp.domain.usercases.knockout.administration;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.championship.Match;
import br.edu.ifsp.domain.entities.championship.Phase;
import br.edu.ifsp.domain.services.MatchServices;

public class FinishKnockoutMatch {

    MatchServices matchServices = new MatchServices();

    public void setMatchConcludedByIds(Knockout knockout, int phaseId, int matchId) {
        Phase targetPhase = findPhaseById(knockout, phaseId);

        if (targetPhase != null) {
            Match targetMatch = findMatchById(targetPhase, matchId);

            if (targetMatch != null && !matchServices.notDraw(targetMatch)) {
                targetMatch.setConcluded(true);
            }
        }
    }

    private Phase findPhaseById(Knockout knockout, int phaseId) {
        for (Phase phase : knockout.getSeeding()) {
            if (phase.getIdPhase() == phaseId) {
                return phase;
            }
        }
        return null;
    }

    private Match findMatchById(Phase phase, int matchId) {
        for (Match match : phase.getMatches()) {
            if (match.getIdMatch() == matchId) {
                return match;
            }
        }
        return null;
    }
}
