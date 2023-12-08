package br.edu.ifsp.domain.entities.championship;

import br.edu.ifsp.domain.entities.team.Team;

public class TeamStats {
    private Team team;
    private Integer wins = 0;
    private Integer loses = 0;
    private Integer draws = 0;
    private Integer points = 0;
    private Integer pointsStandings = 0;

    public TeamStats(Team team) {
        this.team = team;
    }

    private void calculatePoints(){
        points = wins * 3 + draws;
    }

    public void registerWin(){
        wins++;
        calculatePoints();
    }

    public void registerLoss(){
        loses++;
        calculatePoints();
    }

    public void registerDraw(){
        draws++;
        calculatePoints();
    }

    public void updatePointsStandings(Integer pointsScored, Integer pointsConceded) {
        this.pointsStandings += pointsScored - pointsConceded;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getLoses() {
        return loses;
    }

    public void setLoses(Integer loses) {
        this.loses = loses;
    }

    public Integer getDraws() {
        return draws;
    }

    public void setDraws(Integer draws) {
        this.draws = draws;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getPointsStandings() {
        return pointsStandings;
    }

    public void setPointsStandings(Integer pointsStandings) {
        this.pointsStandings = pointsStandings;
    }
}
