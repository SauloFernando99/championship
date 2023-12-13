package br.edu.ifsp.domain.entities.team;

import br.edu.ifsp.domain.entities.championship.Round;
import br.edu.ifsp.domain.entities.championship.RoundRobin;

public class TeamStats {
    private Integer idTeamStats;
    private Team team;
    private Integer wins = 0;
    private Integer losses = 0;
    private Integer draws = 0;
    private Integer points = 0;
    private Integer pointsStandings = 0;
    private RoundRobin roundRobin;

    public TeamStats(Team team, RoundRobin roundRobin) {
        this.team = team;
        this.roundRobin = roundRobin;
    }

    public TeamStats(Integer idTeamStats, Team team, Integer wins, Integer losses,
                     Integer draws, Integer points, Integer pointsStandings,
                     RoundRobin roundRobin) {
        this.idTeamStats = idTeamStats;
        this.team = team;
        this.wins = wins;
        this.losses = losses;
        this.draws = draws;
        this.points = points;
        this.pointsStandings = pointsStandings;
        this.roundRobin = roundRobin;
    }

    private void calculatePoints(){
        points = (wins * 3) + draws;
    }

    public void registerWin(){
        wins++;
        calculatePoints();
    }

    public void registerLoss(){
        losses++;
        calculatePoints();
    }

    public void registerDraw(){
        draws++;
        calculatePoints();
    }

    public void updatePointsStandings(Integer pointsScored, Integer pointsConceded) {
        this.pointsStandings += pointsScored - pointsConceded;
    }

    public Integer getIdTeamStats() {
        return idTeamStats;
    }

    public void setIdTeamStats(Integer idTeamStats) {
        this.idTeamStats = idTeamStats;
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

    public Integer getLosses() {
        return losses;
    }

    public void setLosses(Integer loses) {
        this.losses = loses;
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

    public RoundRobin getRoundRobin() {
        return roundRobin;
    }

    public void setRoundRobin(RoundRobin roundRobin) {
        this.roundRobin = roundRobin;
    }
}
