package br.edu.ifsp.domain.entities.dbsupport;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.team.Team;

import java.util.List;

public class TeamKnockout {
    private Team team;
    private Knockout knockout;

    public TeamKnockout(Team team, Knockout knockout) {
        this.team = team;
        this.knockout = knockout;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Knockout getKnockout() {
        return knockout;
    }

    public void setKnockout(Knockout knockout) {
        this.knockout = knockout;
    }
}
