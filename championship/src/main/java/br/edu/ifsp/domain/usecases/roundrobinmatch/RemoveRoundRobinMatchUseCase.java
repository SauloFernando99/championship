package br.edu.ifsp.domain.usecases.roundrobinmatch;

import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.entities.championship.RoundRobinMatch;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;

public class RemoveRoundRobinMatchUseCase {

    private RoundRobinMatchDAO roundRobinMatchDAO;

    public RemoveRoundRobinMatchUseCase(RoundRobinMatchDAO roundRobinMatchDAO) {
        this.roundRobinMatchDAO = roundRobinMatchDAO;
    }

    public boolean remove(Integer id){
        if(id == null || roundRobinMatchDAO.findOne(id).isEmpty()) {
            throw new EntityNotFoundException("RoundRobinMatch not found.");
        }
        return roundRobinMatchDAO.deleteByKey(id);
    }

    public boolean remove(RoundRobinMatch roundRobinMatch){
        if(roundRobinMatch == null || roundRobinMatchDAO.findOne(roundRobinMatch.getIdMatch()).isEmpty())
            throw new EntityNotFoundException("RoundRobinMatch not found.");
        return roundRobinMatchDAO.delete(roundRobinMatch);
    }
}