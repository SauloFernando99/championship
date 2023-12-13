package br.edu.ifsp.domain.usecases.dbsupport.teamknockout;

import br.edu.ifsp.domain.entities.dbsupport.TeamKnockout;
import br.edu.ifsp.domain.usecases.utils.Notification;
import br.edu.ifsp.domain.usecases.utils.Validator;

public class CreateTeamKnockoutUseCase {
    private TeamKnockoutDAO teamKnockoutDAO;
    public CreateTeamKnockoutUseCase(TeamKnockoutDAO teamKnockoutDAO) {
        this.teamKnockoutDAO = teamKnockoutDAO;
    }

    public Integer insert(TeamKnockout teamKnockout){
        Validator<TeamKnockout> validator = new TeamKnockoutInputRequestValidator();
        Notification notification = validator.validate(teamKnockout);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        return teamKnockoutDAO.create(teamKnockout);
    }
}
