package br.edu.ifsp.domain.usecases.match.administration;

import br.edu.ifsp.domain.entities.championship.Match;
import br.edu.ifsp.domain.usecases.match.crud.MatchDAO;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;

public class FinishMatchUseCase {
    private MatchDAO matchDAO;

    public FinishMatchUseCase(MatchDAO matchDAO) {
        this.matchDAO = matchDAO;
    }

    public boolean finishMatch(Integer matchId) {

        Match existingMatch = matchDAO.findOne(matchId)
                .orElseThrow(() -> new EntityNotFoundException("Match not found."));

        if (existingMatch.getConcluded()) {
            System.out.println("The match has already been concluded.");
            return false;
        }

        existingMatch.setConcluded(true);

        return matchDAO.update(existingMatch);
    }
}