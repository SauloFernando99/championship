package br.edu.ifsp.domain.usecases.round;

import br.edu.ifsp.domain.entities.championship.Round;
import br.edu.ifsp.domain.entities.team.Team;

import java.util.List;
import java.util.Optional;

public class FindRoundUseCase {
    private RoundDAO roundDAO;

    public FindRoundUseCase(RoundDAO roundDAO) {
        this.roundDAO = roundDAO;
    }

    public Optional<Round> findOne(Integer id){
        if (id == null)
            throw new IllegalArgumentException("ID can not be null.");
        return roundDAO.findOne(id);
    }

    public List<Round> findAll(){
        return roundDAO.findAll();
    }
}