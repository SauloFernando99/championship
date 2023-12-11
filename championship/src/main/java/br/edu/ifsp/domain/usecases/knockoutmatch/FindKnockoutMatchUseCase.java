package br.edu.ifsp.domain.usecases.knockoutmatch;

import br.edu.ifsp.domain.entities.championship.KnockoutMatch;

import java.util.List;
import java.util.Optional;

public class FindKnockoutMatchUseCase {
    private KnockoutMatchDAO knockoutMatchDAO;

    public FindKnockoutMatchUseCase(KnockoutMatchDAO knockoutMatchDAO) {
        this.knockoutMatchDAO = knockoutMatchDAO;
    }

    public Optional<KnockoutMatch> findOne(Integer id){
        if (id == null)
            throw new IllegalArgumentException("ID can not be null.");
        return knockoutMatchDAO.findOne(id);
    }

    public List<KnockoutMatch> findAll(){
        return knockoutMatchDAO.findAll();
    }
}