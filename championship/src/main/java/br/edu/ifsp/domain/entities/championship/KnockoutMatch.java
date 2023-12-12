package br.edu.ifsp.domain.entities.championship;

import br.edu.ifsp.domain.entities.team.Team;

public class KnockoutMatch extends Match{
    private Phase phase;

    public KnockoutMatch(Team team1, Team team2, Phase phase) {
        super(team1, team2);
        this.setPhase(phase);
    }

    public KnockoutMatch(Phase phase) {
        this.setPhase(phase);
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public String getNameTeam1() {
        return getTeam1().getName();
    }

    public String getNameTeam2() {
        return getTeam2().getName();
    }
}
