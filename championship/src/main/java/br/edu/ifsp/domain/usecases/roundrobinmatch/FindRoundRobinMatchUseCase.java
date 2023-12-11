package br.edu.ifsp.domain.usecases.roundrobinmatch;

import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.entities.championship.RoundRobinMatch;

import java.util.List;
import java.util.Optional;

public class FindRoundRobinMatchUseCase {
    private RoundRobinMatchDAO roundRobinMatchDAO;

    public FindRoundRobinMatchUseCase(RoundRobinMatchDAO roundRobinMatchDAO) {
        this.roundRobinMatchDAO = roundRobinMatchDAO;
    }

    public Optional<RoundRobinMatch> findOne(Integer id){
        if (id == null)
            throw new IllegalArgumentException("ID can not be null.");
        return roundRobinMatchDAO.findOne(id);
    }

    public List<RoundRobinMatch> findAll(){
        return roundRobinMatchDAO.findAll();
    }
}