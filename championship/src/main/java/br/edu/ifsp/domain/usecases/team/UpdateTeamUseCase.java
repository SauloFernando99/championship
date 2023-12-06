package br.edu.ifsp.domain.usecases.team;

import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;
import br.edu.ifsp.domain.usecases.utils.Notification;
import br.edu.ifsp.domain.usecases.utils.Validator;

public class UpdateTeamUseCase {
    private TeamDAO teamDAO;

    public UpdateTeamUseCase(TeamDAO teamDAO) {
        this.teamDAO = teamDAO;
    }

    public boolean update(Team team){
        Validator<Team> validator = new TeamInputRequestValidator();
        Notification notification = validator.validate(team);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer id = team.getIdTeam();
        if(teamDAO.findOne(id).isEmpty())
            throw new EntityNotFoundException("Team not found.");

        return teamDAO.update(team);
    }
}