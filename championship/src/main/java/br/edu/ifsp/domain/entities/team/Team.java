package br.edu.ifsp.domain.entities.team;

public class Team {

    private static int teamIdCounter = 1;
    private int idTeam;
    private String name;
    private String crest;
    private Boolean isActive = true;

    public Team(String name, String crest) {
        this.idTeam = teamIdCounter++;
        this.name = name;
        this.crest = crest;

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
}