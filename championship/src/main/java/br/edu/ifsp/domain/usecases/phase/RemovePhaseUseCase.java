package br.edu.ifsp.domain.usecases.phase;

import br.edu.ifsp.domain.entities.championship.Phase;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;

public class RemovePhaseUseCase {

    private PhaseDAO phaseDAO;

    public RemovePhaseUseCase(PhaseDAO phaseDAO) {
        this.phaseDAO = phaseDAO;
    }

    public boolean remove(Integer id){
        if(id == null || phaseDAO.findOne(id).isEmpty()) {
            throw new EntityNotFoundException("Phase not found.");
        }
        return phaseDAO.deleteByKey(id);
    }

    public boolean remove(Phase phase){
        if(phase == null || phaseDAO.findOne(phase.getIdPhase()).isEmpty())
            throw new EntityNotFoundException("Phase not found.");
        return phaseDAO.delete(phase);
    }
}