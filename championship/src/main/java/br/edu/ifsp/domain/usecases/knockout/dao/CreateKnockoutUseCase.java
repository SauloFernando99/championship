package br.edu.ifsp.domain.usecases.knockout.dao;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.utils.EntityAlreadyExistsException;
import br.edu.ifsp.domain.usecases.utils.Notification;
import br.edu.ifsp.domain.usecases.utils.Validator;

public class CreateKnockoutUseCase {
    private KnockoutDAO knockoutDAO;
    public CreateKnockoutUseCase(KnockoutDAO knockoutDAO) {
        this.knockoutDAO = knockoutDAO;
    }

    public Integer insert(Knockout knockout){
        Validator<Knockout> validator = new KnockoutInputRequestValidator();
        Notification notification = validator.validate(knockout);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        return knockoutDAO.create(knockout);
    }
}
