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

    public Team(Integer idTeam, String name, String crest, Boolean isActive, Integer points, Integer wins, Integer loses, Integer draw, Integer goalDifference) {
        this.idTeam = idTeam;
        this.name = name;
        this.crest = crest;
        this.isActive = isActive;
        this.points = points;
        this.wins = wins;
        this.loses = loses;
        this.draw = draw;
        this.goalDifference = goalDifference;
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
        this.points = points;
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

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getGoalDifference() {
        return goalDifference;
    }

    public void setGoalDifference(Integer goalDifference) {
        this.goalDifference = goalDifference;
    }
}