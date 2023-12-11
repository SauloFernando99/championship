package br.edu.ifsp.domain.usecases.roundrobinmatch;

import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.entities.championship.RoundRobinMatch;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;
import br.edu.ifsp.domain.usecases.utils.Notification;
import br.edu.ifsp.domain.usecases.utils.Validator;

public class UpdateRoundRobinMatchUseCase {
    private RoundRobinMatchDAO roundRobinMatchDAO;

    public UpdateRoundRobinMatchUseCase(RoundRobinMatchDAO roundRobinMatchDAO) {
        this.roundRobinMatchDAO = roundRobinMatchDAO;
    }

    public boolean update(RoundRobinMatch roundRobinMatch){
        Validator<RoundRobinMatch> validator = new RoundRobinMatchInputRequestValidator();
        Notification notification = validator.validate(roundRobinMatch);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer id = roundRobinMatch.getIdMatch();
        if(roundRobinMatchDAO.findOne(id).isEmpty())
            throw new EntityNotFoundException("RoundRobinMatch not found.");

        return roundRobinMatchDAO.update(roundRobinMatch);
    }
}