package br.edu.ifsp.domain.usecases.roundrobin.administration;

import br.edu.ifsp.domain.entities.championship.Match;
import br.edu.ifsp.domain.entities.championship.Round;
import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.entities.team.TeamStats;
import br.edu.ifsp.domain.services.MatchServices;
import br.edu.ifsp.domain.services.RoundRobinServices;
import br.edu.ifsp.domain.services.RoundServices;

public class FinishRoundRobinMatch {
    MatchServices matchServices = new MatchServices();
    RoundServices roundServices = new RoundServices();
    RoundRobinServices roundRobinServices = new RoundRobinServices();

    public Match finishMatchByIds(RoundRobin roundRobin, int roundId, int matchId) {
        clearAll(roundRobin);
        for (Round round : roundRobin.getTable()) {
            if (round.getIdRound() == roundId) {
                for (Match match : round.getMatches()) {
                    if (match.getIdMatch() == matchId) {
                        matchServices.concludeMatch(match);

                        if (roundServices.allMatchesConcluded(round)) {
                            roundRobinServices.updateTeamStats(roundRobin);
                            roundServices.finishRound(round);
                        }

                        return match;
                    }
                }
            }
        }
        return null;
    }

    private void clearAll(RoundRobin roundRobin){
        for (TeamStats teamStats: roundRobin.getTeamStats()
             ) {
            teamStats.setWins(0);
            teamStats.setLosses(0);
            teamStats.setDraws(0);
            teamStats.setPoints(0);
            teamStats.setPointsStandings(0);
        }
    }
}
