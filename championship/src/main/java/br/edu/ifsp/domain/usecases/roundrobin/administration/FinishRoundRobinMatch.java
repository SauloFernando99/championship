package br.edu.ifsp.domain.usecases.roundrobin.administration;

import br.edu.ifsp.domain.entities.championship.Match;
import br.edu.ifsp.domain.entities.championship.Round;
import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.entities.championship.RoundRobinMatch;
import br.edu.ifsp.domain.entities.team.TeamStats;
import br.edu.ifsp.domain.services.MatchServices;
import br.edu.ifsp.domain.services.RoundRobinServices;
import br.edu.ifsp.domain.services.RoundServices;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;

import java.util.Objects;

import static br.edu.ifsp.application.main.Main.*;

public class FinishRoundRobinMatch {
    MatchServices matchServices = new MatchServices();
    RoundServices roundServices = new RoundServices();
    RoundRobinServices roundRobinServices = new RoundRobinServices();

    public RoundRobinMatch finishMatchByIds(Integer matchId) {

        if (matchId == null)
            throw new IllegalArgumentException("RoundRobinMatch ID is null");

        RoundRobinMatch foundMatch = findRoundRobinMatchUseCase.findOne(matchId)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a RoundRobinMatch with id: "
                        + matchId));

        RoundRobin roundRobin = foundMatch.getRound().getRoundRobin();
        clearAll(roundRobin);

        for (Round round : roundRobin.getTable()) {
            if (Objects.equals(round.getIdRound(), round.getIdRound())) {
                for (RoundRobinMatch match : round.getMatches()) {
                    if (match.getIdMatch() == matchId) {


                        matchServices.concludeMatch(match);

                        updateRoundRobinMatchUseCase.update(match);

                        if (roundServices.allMatchesConcluded(round)) {

                            roundRobinServices.updateTeamStats(roundRobin);

                            roundServices.finishRound(round);

                            updateRoundUseCase.update(round);

                           for (TeamStats teamStats : roundRobin.getTeamStats()){
                               updateTeamStatsUseCase.update(teamStats);
                           }
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
