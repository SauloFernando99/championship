package br.edu.ifsp.domain.usercases.knockout.administration;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.championship.Match;
import br.edu.ifsp.domain.entities.championship.Phase;
import br.edu.ifsp.domain.services.MatchServices;

public class UpdateKnockoutMatch {
    MatchServices matchServices = new MatchServices();
    public void updateMatchResultByIds(Knockout knockout, int phaseId, int matchId, int score1, int score2) {
        Phase targetPhase = findPhaseById(knockout, phaseId);

        if (targetPhase != null) {
            Match targetMatch = findMatchById(targetPhase, matchId);

            if (targetMatch != null) {
                matchServices.updateMatchResult(targetMatch, score1, score2);
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

    private void updateMatchResults(Match match, int score1, int score2) {
        match.setScoreboard1(score1);
        match.setScoreboard1(score2);
    }
}
