package br.edu.ifsp.domain.entities.team;

public class TeamStats {

    private static int teamStatsIdCounter = 1;
    private int idTeamStats;
    private Team team;
    private Integer wins = 0;
    private Integer losses = 0;
    private Integer draws = 0;
    private Integer points = 0;
    private Integer pointsStandings = 0;

    public TeamStats(Team team) {
        this.idTeamStats = teamStatsIdCounter++;
        this.team = team;
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
}