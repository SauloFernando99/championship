package br.edu.ifsp.domain.usecases.knockout.dao;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.team.Team;

import java.util.List;
import java.util.Optional;

public class FindKnockoutUseCase {
    private KnockoutDAO knockoutDAO;

    public FindKnockoutUseCase(KnockoutDAO knockoutDAO) {
        this.knockoutDAO = knockoutDAO;
    }

    public Optional<Knockout> findOne(Integer id){
        if (id == null)
            throw new IllegalArgumentException("ID can not be null.");
        return knockoutDAO.findOne(id);
    }

    public List<Knockout> findAll(){
        return knockoutDAO.findAll();
    }
}