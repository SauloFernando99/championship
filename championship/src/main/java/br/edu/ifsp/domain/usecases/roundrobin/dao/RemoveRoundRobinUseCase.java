package br.edu.ifsp.domain.usecases.roundrobin.dao;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;

public class RemoveRoundRobinUseCase {

    private RoundRobinDAO roundRobinDAO;

    public RemoveRoundRobinUseCase(RoundRobinDAO roundRobinDAO) {
        this.roundRobinDAO = roundRobinDAO;
    }

    public boolean remove(Integer id){
        if(id == null || roundRobinDAO.findOne(id).isEmpty()) {
            throw new EntityNotFoundException("RoundRobin not found.");
        }
        return roundRobinDAO.deleteByKey(id);
    }

    public boolean remove(RoundRobin roundRobin){
        if(roundRobin == null || roundRobinDAO.findOne(roundRobin.getIdChampionship()).isEmpty())
            throw new EntityNotFoundException("RoundRobin not found.");
        return roundRobinDAO.delete(roundRobin);
    }
}