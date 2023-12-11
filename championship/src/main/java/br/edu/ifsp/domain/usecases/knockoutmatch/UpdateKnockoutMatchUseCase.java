package br.edu.ifsp.domain.usecases.knockoutmatch;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.championship.KnockoutMatch;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;
import br.edu.ifsp.domain.usecases.utils.Notification;
import br.edu.ifsp.domain.usecases.utils.Validator;

public class UpdateKnockoutMatchUseCase {
    private KnockoutMatchDAO knockoutMatchDAO;

    public UpdateKnockoutMatchUseCase(KnockoutMatchDAO knockoutMatchDAO) {
        this.knockoutMatchDAO = knockoutMatchDAO;
    }

    public boolean update(KnockoutMatch knockoutMatch){
        Validator<KnockoutMatch> validator = new KnockoutMatchInputRequestValidator();
        Notification notification = validator.validate(knockoutMatch);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer id = knockoutMatch.getIdMatch();
        if(knockoutMatchDAO.findOne(id).isEmpty())
            throw new EntityNotFoundException("KnockoutMatch not found.");

        return knockoutMatchDAO.update(knockoutMatch);
    }
}