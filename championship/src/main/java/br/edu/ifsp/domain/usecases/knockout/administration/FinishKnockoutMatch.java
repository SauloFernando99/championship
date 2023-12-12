package br.edu.ifsp.domain.usecases.knockout.administration;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.championship.KnockoutMatch;
import br.edu.ifsp.domain.entities.championship.Match;
import br.edu.ifsp.domain.entities.championship.Phase;
import br.edu.ifsp.domain.services.MatchServices;

import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;

import static br.edu.ifsp.application.main.Main.*;

public class FinishKnockoutMatch {

    MatchServices matchServices = new MatchServices();

    public void setMatchConcludedByIds(Integer matchId) {

        KnockoutMatch match = findKnockoutMatchUseCase.findOne(matchId)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a KnockoutMatch with id: "
                        + matchId));

        match.setConcluded(true);

        updateKnockoutMatchUseCase.update(match);

    }
}
