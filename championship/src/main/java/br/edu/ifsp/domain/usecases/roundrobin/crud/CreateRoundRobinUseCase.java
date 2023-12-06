package br.edu.ifsp.domain.usecases.roundrobin.crud;

import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.usecases.utils.EntityAlreadyExistsException;
import br.edu.ifsp.domain.usecases.utils.Notification;
import br.edu.ifsp.domain.usecases.utils.Validator;

public class CreateRoundRobinUseCase {

    private RoundRobinDAO roundRobinDAO;

    public CreateRoundRobinUseCase(RoundRobinDAO roundRobinDAO) {
        this.roundRobinDAO = roundRobinDAO;
    }

    public Integer insert(RoundRobin roundRobin){
        Validator<RoundRobin> validator = new RoundRobinInputRequestValidator();
        Notification notification = validator.validate(roundRobin);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer id = roundRobin.getIdChampionship();
        if(roundRobinDAO.findById(id).isPresent())
            throw new EntityAlreadyExistsException("This ID is already in use.");

        return roundRobinDAO.create(roundRobin);
    }
}
