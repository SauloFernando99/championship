package br.edu.ifsp.domain.entities.team;

public class Team {
    private Integer idTeam;
    private String name;
    private String crest;
    private Boolean isActive = true;

    public Team(String name, String crest) {
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}