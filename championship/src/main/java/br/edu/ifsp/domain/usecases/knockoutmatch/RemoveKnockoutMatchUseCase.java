package br.edu.ifsp.domain.usecases.knockoutmatch;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.championship.KnockoutMatch;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;

public class RemoveKnockoutMatchUseCase {

    private KnockoutMatchDAO knockoutMatchDAO;

    public RemoveKnockoutMatchUseCase(KnockoutMatchDAO knockoutMatchDAO) {
        this.knockoutMatchDAO = knockoutMatchDAO;
    }

    public boolean remove(Integer id){
        if(id == null || knockoutMatchDAO.findOne(id).isEmpty()) {
            throw new EntityNotFoundException("KnockoutMatch not found.");
        }
        return knockoutMatchDAO.deleteByKey(id);
    }

    public boolean remove(KnockoutMatch knockoutMatch){
        if(knockoutMatch == null || knockoutMatchDAO.findOne(knockoutMatch.getIdMatch()).isEmpty())
            throw new EntityNotFoundException("KnockoutMatch not found.");
        return knockoutMatchDAO.delete(knockoutMatch);
    }
}