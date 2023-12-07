package br.edu.ifsp.domain.usecases.match.administration;

import br.edu.ifsp.domain.entities.championship.Match;
import br.edu.ifsp.domain.usecases.match.crud.MatchDAO;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;

public class UpdateMatchScoreUseCase {
    private MatchDAO matchDAO;

    public UpdateMatchScoreUseCase(MatchDAO matchDAO) {
        this.matchDAO = matchDAO;
    }

    public boolean updateMatchScore(Integer matchId, Integer newScoreboard1, Integer newScoreboard2) {
        Match existingMatch = matchDAO.findOne(matchId).orElseThrow(() -> new EntityNotFoundException("Match not found."));

        existingMatch.setScoreboard1(newScoreboard1);
        existingMatch.setScoreboard2(newScoreboard2);

        return matchDAO.update(existingMatch);
    }
}