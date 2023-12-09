package br.edu.ifsp.domain.usercases.roundrobin.administration;

import br.edu.ifsp.domain.entities.championship.Match;
import br.edu.ifsp.domain.entities.championship.Round;
import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.services.MatchServices;

public class UpdateRoundRobinMatch {

    public Match updateMatchByIds(RoundRobin roundRobin, int roundId, int matchId, Integer scoreBoard1, Integer scoreBoard2) {
        MatchServices matchServices = new MatchServices();

        for (Round round : roundRobin.getTable()) {
            if (round.getIdRound() == roundId) {
                for (Match match : round.getMatches()) {
                    if (match.getIdMatch() == matchId) {
                        matchServices.updateMatchResult(match, scoreBoard1, scoreBoard2);
                        return match;
                    }
                }
            }
        }
        return null;
    }
}
