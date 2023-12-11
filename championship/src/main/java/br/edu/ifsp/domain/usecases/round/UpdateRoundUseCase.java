package br.edu.ifsp.domain.usecases.round;

import br.edu.ifsp.domain.entities.championship.Round;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;
import br.edu.ifsp.domain.usecases.utils.Notification;
import br.edu.ifsp.domain.usecases.utils.Validator;

public class UpdateRoundUseCase {
    private RoundDAO roundDAO;

    public UpdateRoundUseCase(RoundDAO roundDAO) {
        this.roundDAO = roundDAO;
    }

    public boolean update(Round round){
        Validator<Round> validator = new RoundInputRequestValidator();
        Notification notification = validator.validate(round);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer id = round.getIdRound();
        if(roundDAO.findOne(id).isEmpty())
            throw new EntityNotFoundException("Round not found.");

        return roundDAO.update(round);
    }
}