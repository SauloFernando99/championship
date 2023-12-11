package br.edu.ifsp.domain.entities.championship;

import br.edu.ifsp.domain.entities.team.Team;

public class KnockoutMatch extends Match{
    private Phase phase;

    public KnockoutMatch(Team team1, Team team2, Phase phase) {
        super(team1, team2);
        this.phase = phase;
    }

    public KnockoutMatch(Phase phase) {
        this.phase = phase;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }
}
