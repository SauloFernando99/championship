package br.edu.ifsp.domain.entities.championship;
import br.edu.ifsp.domain.entities.team.Team;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Championship {
    private Integer idChampionship;
    private LocalDate initialDate;
    private LocalDate finalDate;
    private String modality;
    private String award;
    private String sponsorship;
    private Boolean concluded;
    private List<Team> teams;
    private Map<Team, TeamRoundRobin> statics = new HashMap<>();

    public Championship(Integer idChampionship, LocalDate initialDate, LocalDate finalDate, String modality, String award, String sponsorship, Boolean concluded, List<Team> teams) {
        this.idChampionship = idChampionship;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.modality = modality;
        this.award = award;
        this.sponsorship = sponsorship;
        this.concluded = concluded;
        this.teams = teams;
    }

    public Championship(List<Team> teams) {
    }

    public void addTeam(Team team) {
        statics.put(team, new TeamRoundRobin(0, 0, 0, 0, 0));
    }

    public void registerWin(Team team) {
        TeamRoundRobin stats = statics.get(team);
        if (stats != null) {
            stats = new TeamRoundRobin(stats.getWins() + 1, stats.getLoses(), stats.getDraws(), stats.getGoalDifference(), stats.getPontuation());
            statics.put(team, stats);
        }
    }

    public void registerLost(Team team) {
        TeamRoundRobin stats = statics.get(team);
        if (stats != null) {
            stats = new TeamRoundRobin(stats.getWins(), stats.getLoses() + 1, stats.getDraws(), stats.getGoalDifference(), stats.getPontuation());
            statics.put(team, stats);
        }
    }

    public Map<Team, TeamRoundRobin> getStatics() {
        return statics;
    }
    public TeamRoundRobin getEstatisticas(Team team) {
        return statics.getOrDefault(team, new TeamRoundRobin(0, 0, 0, 0, 0));
    }


    public Integer getIdChampionship() {
        return idChampionship;
    }

    public void setIdChampionship(Integer idChampionship) {
        this.idChampionship = idChampionship;
    }

    public LocalDate getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(LocalDate initialDate) {
        this.initialDate = initialDate;
    }

    public LocalDate getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(LocalDate finalDate) {
        this.finalDate = finalDate;
    }

    public String getModality() {
        return modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public String getSponsorship() {
        return sponsorship;
    }

    public void setSponsorship(String sponsorship) {
        this.sponsorship = sponsorship;
    }

    public Boolean getConcluded() {
        return concluded;
    }

    public void setConcluded(Boolean concluded) {
        this.concluded = concluded;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}