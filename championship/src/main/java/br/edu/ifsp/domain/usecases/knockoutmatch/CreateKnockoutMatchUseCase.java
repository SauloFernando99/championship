package br.edu.ifsp.domain.usecases.knockoutmatch;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.championship.KnockoutMatch;
import br.edu.ifsp.domain.usecases.knockout.dao.KnockoutDAO;
import br.edu.ifsp.domain.usecases.utils.EntityAlreadyExistsException;
import br.edu.ifsp.domain.usecases.utils.Notification;
import br.edu.ifsp.domain.usecases.utils.Validator;

public class CreateKnockoutMatchUseCase {
    private KnockoutMatchDAO knockoutMatchDAO;
    public CreateKnockoutMatchUseCase(KnockoutMatchDAO knockoutMatchDAO) {
        this.knockoutMatchDAO = knockoutMatchDAO;
    }

    public Integer insert(KnockoutMatch knockoutMatch){
        Validator<KnockoutMatch> validator = new KnockoutMatchInputRequestValidator();
        Notification notification = validator.validate(knockoutMatch);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer id = knockoutMatch.getIdMatch();
        if(knockoutMatchDAO.findOne(id).isPresent())
            throw new EntityAlreadyExistsException("This ID is already in use.");

        return knockoutMatchDAO.create(knockoutMatch);
    }
}
