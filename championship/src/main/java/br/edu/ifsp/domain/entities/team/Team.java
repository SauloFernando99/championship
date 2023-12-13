package br.edu.ifsp.domain.entities.team;

import br.edu.ifsp.domain.entities.championship.Championship;
import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.championship.RoundRobin;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private Integer idTeam;
    private String name;
    private String crest;
    private Boolean isActive = true;
    private List<Knockout> knockouts = new ArrayList<>();
    private List<RoundRobin> roundRobins = new ArrayList<>();

    public Team(Integer idTeam, String name, String crest, Boolean isActive) {
        this.idTeam = idTeam;
        this.name = name;
        this.crest = crest;
        this.isActive = isActive;
    }

    public Team(String name, String crest) {
        this.name = name;
        this.crest = crest;
    }

    public Team(Integer idTeam, String name, String crest, boolean isActive, List<Knockout> knockouts) {
        this.idTeam = idTeam;
        this.name = name;
        this.crest = crest;
        this.isActive = isActive;
        this.knockouts = knockouts;
    }

    public void addKnockout(Knockout knockout){
        knockouts.add(knockout);
    }

    public void addRoundRobin(RoundRobin roundRobin){
        roundRobins.add(roundRobin);
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

    public Boolean getActive() {
        return isActive;
    }

    public List<Knockout> getKnockouts() {
        return knockouts;
    }

    public void setKnockouts(List<Knockout> knockouts) {
        this.knockouts = knockouts;
    }

    public List<RoundRobin> getRoundRobins() {
        return roundRobins;
    }

    public void setRoundRobins(List<RoundRobin> roundRobins) {
        this.roundRobins = roundRobins;
    }
}