package br.edu.ifsp.domain.usecases.match.crud;

import br.edu.ifsp.domain.entities.championship.Match;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;
import br.edu.ifsp.domain.usecases.utils.Notification;
import br.edu.ifsp.domain.usecases.utils.Validator;

public class UpdateMatchUseCase {
    private MatchDAO matchDAO;

    public UpdateMatchUseCase(MatchDAO matchDAO) {
        this.matchDAO = matchDAO;
    }

    public boolean update(Match match){
        Validator<Match> validator = new MatchInputRequestValidator();
        Notification notification = validator.validate(match);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer id = match.getIdMatch();
        if(matchDAO.findOne(id).isEmpty())
            throw new EntityNotFoundException("Match not found.");

        return matchDAO.update(match);
    }
}