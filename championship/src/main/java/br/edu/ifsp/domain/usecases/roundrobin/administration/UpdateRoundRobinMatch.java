package br.edu.ifsp.domain.usecases.roundrobin.administration;

import br.edu.ifsp.domain.entities.championship.Match;
import br.edu.ifsp.domain.entities.championship.Round;
import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.entities.championship.RoundRobinMatch;
import br.edu.ifsp.domain.services.MatchServices;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;

import java.util.Objects;

import static br.edu.ifsp.application.main.Main.*;

public class UpdateRoundRobinMatch {

    public MatchServices matchServices = new MatchServices();
    public Match updateMatchByIds(Integer matchId, Integer scoreBoard1, Integer scoreBoard2) {

        if (matchId == null)
            throw new IllegalArgumentException("RoundRobinMatch ID is null");

        RoundRobinMatch foundMatch = findRoundRobinMatchUseCase.findOne(matchId)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a RoundRobinMatch with id: "
                        + matchId));

        RoundRobin roundRobin = foundMatch.getRound().getRoundRobin();


        for (Round round : roundRobin.getTable()) {
            if (Objects.equals(round.getIdRound(), round.getIdRound())) {
                for (RoundRobinMatch match : round.getMatches()) {
                    if (match.getIdMatch() == matchId) {
                        matchServices.updateMatchResult(match, scoreBoard1, scoreBoard2);

                        updateRoundRobinMatchUseCase.update(match);

                        return match;
                    }
                }
            }
        }
        return null;
    }
}
