package br.edu.ifsp.domain.usecases.roundrobin.dao;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.championship.RoundRobin;

import java.util.List;
import java.util.Optional;

public class FindRoundRobinUseCase {
    private RoundRobinDAO roundRobinDAO;

    public FindRoundRobinUseCase(RoundRobinDAO roundRobinDAO) {
        this.roundRobinDAO = roundRobinDAO;
    }

    public Optional<RoundRobin> findOne(Integer id){
        if (id == null)
            throw new IllegalArgumentException("ID can not be null.");
        return roundRobinDAO.findOne(id);
    }

    public List<RoundRobin> findAll(){
        return roundRobinDAO.findAll();
    }
}