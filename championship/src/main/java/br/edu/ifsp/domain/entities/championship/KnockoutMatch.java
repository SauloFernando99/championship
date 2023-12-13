package br.edu.ifsp.domain.entities.championship;

import br.edu.ifsp.domain.entities.team.Team;

import java.sql.Date;
import java.time.LocalDate;

public class KnockoutMatch extends Match{
    private Phase phase;

    public KnockoutMatch(Team team1, Team team2, Phase phase) {
        super(team1, team2);
        this.setPhase(phase);
    }

    public KnockoutMatch(Phase phase) {
        this.setPhase(phase);
    }

    public KnockoutMatch(int matchId, LocalDate date, int scoreboard1, int scoreboard2, Team team1, Team team2, boolean concluded, Phase phase) {
        super(matchId, date, scoreboard1, scoreboard2, team1, team2, concluded);
        this.phase = phase;
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
