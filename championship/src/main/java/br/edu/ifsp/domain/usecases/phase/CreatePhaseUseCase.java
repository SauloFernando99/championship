package br.edu.ifsp.domain.usecases.phase;

import br.edu.ifsp.domain.entities.championship.Phase;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.utils.EntityAlreadyExistsException;
import br.edu.ifsp.domain.usecases.utils.Notification;
import br.edu.ifsp.domain.usecases.utils.Validator;

public class CreatePhaseUseCase {
    private PhaseDAO phaseDAO;
    public CreatePhaseUseCase(PhaseDAO phaseDAO) {
        this.phaseDAO = phaseDAO;
    }

    public Integer insert(Phase phase){
        Validator<Phase> validator = new PhaseInputRequestValidator();
        Notification notification = validator.validate(phase);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer id = phase.getIdPhase();
        if(phaseDAO.findOne(id).isPresent())
            throw new EntityAlreadyExistsException("This ID is already in use.");

        return phaseDAO.create(phase);
    }
}
