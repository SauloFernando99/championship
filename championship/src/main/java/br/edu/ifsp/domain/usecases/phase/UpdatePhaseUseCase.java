package br.edu.ifsp.domain.usecases.phase;

import br.edu.ifsp.domain.entities.championship.Phase;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;
import br.edu.ifsp.domain.usecases.utils.Notification;
import br.edu.ifsp.domain.usecases.utils.Validator;

public class UpdatePhaseUseCase {
    private PhaseDAO phaseDAO;

    public UpdatePhaseUseCase(PhaseDAO phaseDAO) {
        this.phaseDAO = phaseDAO;
    }

    public boolean update(Phase phase){
        Validator<Phase> validator = new PhaseInputRequestValidator();
        Notification notification = validator.validate(phase);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer id = phase.getIdPhase();
        if(phaseDAO.findOne(id).isEmpty())
            throw new EntityNotFoundException("Phase not found.");

        return phaseDAO.update(phase);
    }
}