package br.edu.ifsp.domain.usecases.knockout;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.utils.EntityAlreadyExistsException;
import br.edu.ifsp.domain.usecases.utils.Notification;
import br.edu.ifsp.domain.usecases.utils.Validator;

public class CreateKnockoutUseCase {

    private KnockoutDAO knockoutDAO;

    public CreateKnockoutUseCase(KnockoutDAO teamDAO) {
        this.knockoutDAO = knockoutDAO;
    }

    public Integer insert(Knockout knockout){
        Validator<Knockout> validator = new KnockoutInputRequestValidator();
        Notification notification = validator.validate(knockout);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer id = knockout.getIdChampionship();
        if(knockoutDAO.findById(id).isPresent())
            throw new EntityAlreadyExistsException("This ID is already in use.");

        return knockoutDAO.create(knockout);
    }
}
