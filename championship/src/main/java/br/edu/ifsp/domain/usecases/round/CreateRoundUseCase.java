package br.edu.ifsp.domain.usecases.round;

import br.edu.ifsp.domain.entities.championship.Round;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.utils.EntityAlreadyExistsException;
import br.edu.ifsp.domain.usecases.utils.Notification;
import br.edu.ifsp.domain.usecases.utils.Validator;

public class CreateRoundUseCase {
    private RoundDAO roundDAO;
    public CreateRoundUseCase(RoundDAO roundDAO) {
        this.roundDAO = roundDAO;
    }

    public Integer insert(Round round){
        Validator<Round> validator = new RoundInputRequestValidator();
        Notification notification = validator.validate(round);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());


        return roundDAO.create(round);
    }
}
