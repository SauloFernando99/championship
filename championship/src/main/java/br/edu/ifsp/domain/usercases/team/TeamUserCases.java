package br.edu.ifsp.domain.usercases.team;

import br.edu.ifsp.domain.entities.team.Team;

public class TeamUserCases {
    public Team createTeam(String name, String crest){
        Team team = new Team(name, crest);
        return team;
    }

    public void updateTeam(Team team, String name, String crest){
        team.setName(name);
        team.setCrest(crest);
    }

    public void inactivateTeam(Team team){
        team.setActive(false);
    }
}
