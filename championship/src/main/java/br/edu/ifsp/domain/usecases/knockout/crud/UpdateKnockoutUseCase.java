package br.edu.ifsp.domain.usecases.knockout;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;
import br.edu.ifsp.domain.usecases.utils.Notification;
import br.edu.ifsp.domain.usecases.utils.Validator;

public class UpdateKnockoutUseCase {
    private KnockoutDAO knockoutDAO;

    public UpdateKnockoutUseCase(KnockoutDAO knockoutDAO) {
        this.knockoutDAO = knockoutDAO;
    }

    public boolean update(Knockout knockout){
        Validator<Knockout> validator = new KnockoutInputRequestValidator();
        Notification notification = validator.validate(knockout);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer id = knockout.getIdChampionship();
        if(knockoutDAO.findOne(id).isEmpty())
            throw new EntityNotFoundException("Knockout not found.");

        return knockoutDAO.update(knockout);
    }
}