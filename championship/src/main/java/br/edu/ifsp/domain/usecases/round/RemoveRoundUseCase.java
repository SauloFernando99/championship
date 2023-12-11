package br.edu.ifsp.domain.usecases.round;

import br.edu.ifsp.domain.entities.championship.Round;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;

public class RemoveRoundUseCase {

    private RoundDAO roundDAO;

    public RemoveRoundUseCase(RoundDAO roundDAO) {
        this.roundDAO = roundDAO;
    }

    public boolean remove(Integer id){
        if(id == null || roundDAO.findOne(id).isEmpty()) {
            throw new EntityNotFoundException("Round not found.");
        }
        return roundDAO.deleteByKey(id);
    }

    public boolean remove(Round round){
        if(round == null || roundDAO.findOne(round.getIdRound()).isEmpty())
            throw new EntityNotFoundException("Round not found.");
        return roundDAO.delete(round);
    }
}