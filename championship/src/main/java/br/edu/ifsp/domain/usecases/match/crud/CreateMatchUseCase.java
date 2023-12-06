package br.edu.ifsp.domain.usecases.match.crud;

import br.edu.ifsp.domain.entities.championship.Match;
import br.edu.ifsp.domain.usecases.utils.EntityAlreadyExistsException;
import br.edu.ifsp.domain.usecases.utils.Notification;
import br.edu.ifsp.domain.usecases.utils.Validator;

public class CreateMatchUseCase {

    private MatchDAO matchDAO;

    public CreateMatchUseCase(MatchDAO matchDAO) {
        this.matchDAO = matchDAO;
    }

    public Integer insert(Match match){
        Validator<Match> validator = new MatchInputRequestValidator();
        Notification notification = validator.validate(match);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer id = match.getIdMatch();
        if(matchDAO.findById(id).isPresent())
            throw new EntityAlreadyExistsException("This ID is already in use.");

        return matchDAO.create(match);
    }
}
