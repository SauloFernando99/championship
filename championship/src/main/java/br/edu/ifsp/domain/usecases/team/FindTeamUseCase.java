package br.edu.ifsp.domain.usecases.team;

import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.utils.Validator;

import java.util.List;
import java.util.Optional;

public class FindTeamUseCase {
    private TeamDAO teamDAO;

    public FindTeamUseCase(TeamDAO teamDAO) {
        this.teamDAO = teamDAO;
    }

    public Optional<Team> findOne(Integer id){
        if (id == null)
            throw new IllegalArgumentException("ID can not be null.");
        return teamDAO.findOne(id);
    }

    public List<Team> findAll(){
        return teamDAO.findAll();
    }
}