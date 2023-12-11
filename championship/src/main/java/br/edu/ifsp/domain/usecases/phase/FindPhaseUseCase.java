package br.edu.ifsp.domain.usecases.phase;

import br.edu.ifsp.domain.entities.championship.Phase;
import br.edu.ifsp.domain.entities.team.Team;

import java.util.List;
import java.util.Optional;

public class FindPhaseUseCase {
    private PhaseDAO phaseDAO;

    public FindPhaseUseCase(PhaseDAO phaseDAO) {
        this.phaseDAO = phaseDAO;
    }

    public Optional<Phase> findOne(Integer id){
        if (id == null)
            throw new IllegalArgumentException("ID can not be null.");
        return phaseDAO.findOne(id);
    }

    public List<Phase> findAll(){
        return phaseDAO.findAll();
    }
}