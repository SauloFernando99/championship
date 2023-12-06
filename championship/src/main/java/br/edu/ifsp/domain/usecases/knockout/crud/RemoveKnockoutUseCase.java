package br.edu.ifsp.domain.usecases.knockout.crud;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;

public class RemoveKnockoutUseCase {

    private KnockoutDAO knockoutDAO;

    public RemoveKnockoutUseCase(KnockoutDAO knockoutDAO) {
        this.knockoutDAO = knockoutDAO;
    }

    public boolean remove(Integer id){
        if(id == null || knockoutDAO.findOne(id).isEmpty()) {
            throw new EntityNotFoundException("Knockout not found.");
        }
        return knockoutDAO.deleteByKey(id);
    }

    public boolean remove(Knockout knockout){
        if(knockout == null || knockoutDAO.findOne(knockout.getIdChampionship()).isEmpty())
            throw new EntityNotFoundException("Knockout not found.");
        return knockoutDAO.delete(knockout);
    }
}