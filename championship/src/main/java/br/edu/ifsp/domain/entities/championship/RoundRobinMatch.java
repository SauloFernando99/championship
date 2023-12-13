package br.edu.ifsp.domain.entities.championship;

import br.edu.ifsp.domain.entities.team.Team;

import java.time.LocalDate;

public class RoundRobinMatch extends Match{
    private Round round;

    public RoundRobinMatch(Team team1, Team team2, Round round) {
        super(team1, team2);
        this.round = round;
    }

    public RoundRobinMatch(Team team1, Team team2){
        super(team1, team2);
    }

    public RoundRobinMatch(Round round) {
        this.round = round;
    }

    public RoundRobinMatch(int matchId, LocalDate date, int scoreboard1, int scoreboard2, Team team1, Team team2, boolean concluded, Round round) {
        super(matchId, date, scoreboard1, scoreboard2, team1, team2, concluded);
        this.round = round;
    }

    public void addRound(Round round){
        this.round = round;
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }
}
