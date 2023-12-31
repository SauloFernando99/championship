package br.edu.ifsp.domain.usecases.team;

import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;

public class RemoveTeamUseCase {

    private TeamDAO teamDAO;

    public RemoveTeamUseCase(TeamDAO teamDAO) {
        this.teamDAO = teamDAO;
    }

    public boolean remove(Integer id){
        if(id == null || teamDAO.findOne(id).isEmpty()) {
            throw new EntityNotFoundException("Team not found.");
        }
        return teamDAO.deleteByKey(id);
    }

    public boolean remove(Team team){
        if(team == null || teamDAO.findOne(team.getIdTeam()).isEmpty())
            throw new EntityNotFoundException("Team not found.");
        return teamDAO.delete(team);
    }
}