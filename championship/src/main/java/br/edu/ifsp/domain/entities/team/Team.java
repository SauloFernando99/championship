package br.edu.ifsp.domain.entities.team;

public class Team {

    private Integer idTeam;
    private String name;
    private String crest;
    private Boolean isActive;
    private Integer points;
    private Integer wins;
    private Integer loses;
    private Integer draw;
    private Integer goalDifference;

    public Team(Integer idTeam, String name, String crest, Boolean isActive) {
        this.idTeam = idTeam;
        this.name = name;
        this.crest = crest;
        this.isActive = isActive;
        this.points = 0;
        this.wins = 0;
        this.loses = 0;
        this.draw = 0;
        this.goalDifference = 0;
    }

    public Integer getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(Integer idTeam) {
        this.idTeam = idTeam;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCrest() {
        return crest;
    }

    public void setCrest(String crest) {
        this.crest = crest;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points+=points;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins++;
    }

    public Integer getLoses() {
        return loses;
    }

    public void setLoses(Integer loses) {
        this.loses++;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw++;
    }

    public Integer getGoalDifference() {
        return goalDifference;
    }

    public void setGoalDifference(Integer goalDifference) {
        this.goalDifference+=goalDifference;
    }
}