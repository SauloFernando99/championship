package br.edu.ifsp.domain.entities.team;

public class Team {

    private Integer idTeam;
    private String name;
    private String crest;
    private Boolean isActive;

    public Team(Integer idTeam, String name, String crest, Boolean isActive) {
        this.setIdTeam(idTeam);
        this.setName(name);
        this.setCrest(crest);
        this.setActive(isActive);

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