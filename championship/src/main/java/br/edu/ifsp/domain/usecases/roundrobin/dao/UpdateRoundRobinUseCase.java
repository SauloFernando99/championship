package br.edu.ifsp.domain.usecases.roundrobin.dao;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;
import br.edu.ifsp.domain.usecases.utils.Notification;
import br.edu.ifsp.domain.usecases.utils.Validator;

public class UpdateRoundRobinUseCase {
    private RoundRobinDAO roundRobinDAO;

    public UpdateRoundRobinUseCase(RoundRobinDAO roundRobinDAO) {
        this.roundRobinDAO = roundRobinDAO;
    }

    public boolean update(RoundRobin roundRobin){
        Validator<RoundRobin> validator = new RoundRobinInputRequestValidator();
        Notification notification = validator.validate(roundRobin);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer id = roundRobin.getIdChampionship();
        if(roundRobinDAO.findOne(id).isEmpty())
            throw new EntityNotFoundException("RoundRobin not found.");

        return roundRobinDAO.update(roundRobin);
    }
}