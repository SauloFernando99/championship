package br.edu.ifsp.domain.usecases.roundrobinmatch;

import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.entities.championship.RoundRobinMatch;
import br.edu.ifsp.domain.usecases.utils.EntityAlreadyExistsException;
import br.edu.ifsp.domain.usecases.utils.Notification;
import br.edu.ifsp.domain.usecases.utils.Validator;

public class CreateRoundRobinMatchUseCase {
    private RoundRobinMatchDAO roundRobinMatchDAO;
    public CreateRoundRobinMatchUseCase(RoundRobinMatchDAO roundRobinMatchDAO) {
        this.roundRobinMatchDAO = roundRobinMatchDAO;
    }

    public Integer insert(RoundRobinMatch roundRobinMatch){
        Validator<RoundRobinMatch> validator = new RoundRobinMatchInputRequestValidator();
        Notification notification = validator.validate(roundRobinMatch);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        return roundRobinMatchDAO.create(roundRobinMatch);
    }
}
