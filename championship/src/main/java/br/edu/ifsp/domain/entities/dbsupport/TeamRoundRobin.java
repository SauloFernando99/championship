package br.edu.ifsp.domain.entities.dbsupport;

import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.entities.team.Team;

public class TeamRoundRobin {
    private Team team;
    private RoundRobin roundRobin;

    public TeamRoundRobin(Team team, RoundRobin roundRobin) {
        this.team = team;
        this.roundRobin = roundRobin;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public RoundRobin getRoundRobin() {
        return roundRobin;
    }

    public void setRoundRobin(RoundRobin roundRobin) {
        this.roundRobin = roundRobin;
    }
}
