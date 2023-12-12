package br.edu.ifsp.domain.usecases.knockout.administration;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.championship.KnockoutMatch;
import br.edu.ifsp.domain.entities.championship.Match;
import br.edu.ifsp.domain.entities.championship.Phase;
import br.edu.ifsp.domain.services.MatchServices;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;

import static br.edu.ifsp.application.main.Main.*;
import static br.edu.ifsp.application.main.Main.updateKnockoutMatchUseCase;

public class UpdateKnockoutMatch {
    MatchServices matchServices = new MatchServices();

    public void updateMatchResultByIds(
           Integer matchId, Integer score1, Integer score2
    ) {

        KnockoutMatch match = findKnockoutMatchUseCase.findOne(matchId)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a KnockoutMatch with id: "
                        + matchId));

        matchServices.updateMatchResult(match, score1, score2);

        updateKnockoutMatchUseCase.update(match);

    }
}
