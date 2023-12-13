package br.edu.ifsp.domain.usecases.dbsupport.teamroundrobin;

import br.edu.ifsp.domain.entities.dbsupport.TeamRoundRobin;
import br.edu.ifsp.domain.usecases.utils.Notification;
import br.edu.ifsp.domain.usecases.utils.Validator;

public class CreateTeamRoundRobinUseCase {
    private TeamRoundRobinDAO teamRoundRobinDAO;
    public CreateTeamRoundRobinUseCase(TeamRoundRobinDAO teamRoundRobinDAO) {
        this.teamRoundRobinDAO = teamRoundRobinDAO;
    }

    public Integer insert(TeamRoundRobin teamRoundRobin){
        Validator<TeamRoundRobin> validator = new TeamRoundRobinInputRequestValidator();
        Notification notification = validator.validate(teamRoundRobin);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer idRoundRobin = teamRoundRobin.getRoundRobin().getIdChampionship();
        Integer idTeam = teamRoundRobin.getTeam().getIdTeam();

        return teamRoundRobinDAO.create(teamRoundRobin);
    }
}
