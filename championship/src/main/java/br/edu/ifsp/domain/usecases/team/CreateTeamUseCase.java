package br.edu.ifsp.domain.usecases.team;

import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.utils.EntityAlreadyExistsException;
import br.edu.ifsp.domain.usecases.utils.Notification;
import br.edu.ifsp.domain.usecases.utils.Validator;

public class CreateTeamUseCase {

    private TeamDAO teamDAO;

    public CreateTeamUseCase(TeamDAO teamDAO) {
        this.teamDAO = teamDAO;
    }

    public Integer insert(Team team){
        Validator<Team> validator = new TeamInputRequestValidator();
        Notification notification = validator.validate(team);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer id = team.getIdTeam();
        if(teamDAO.findById(id).isPresent())
            throw new EntityAlreadyExistsException("This ID is already in use.");

        return teamDAO.create(team);
    }
}
