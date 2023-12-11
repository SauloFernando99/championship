package br.edu.ifsp.domain.entities.championship;
import br.edu.ifsp.domain.entities.team.Team;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Championship {
    private Integer idChampionship;
    private String name;
    private LocalDate initialDate;
    private LocalDate finalDate;
    private String modality;
    private String award;
    private String sponsorship;
    private Boolean concluded = false;
    private List<Team> teams = new ArrayList<>();

    public Championship(Integer idChampionship, String name, LocalDate initialDate, LocalDate finalDate, String modality, String award, String sponsorship, Boolean concluded, List<Team> teams) {
        this.idChampionship = idChampionship;
        this.name = name;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.modality = modality;
        this.award = award;
        this.sponsorship = sponsorship;
        this.concluded = concluded;
        this.teams = teams;
    }

    public Championship(Integer idChampionship, String name, LocalDate initialDate, LocalDate finalDate, String modality, String award, String sponsorship, Boolean concluded) {
        this.idChampionship = idChampionship;
        this.name = name;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.modality = modality;
        this.award = award;
        this.sponsorship = sponsorship;
        this.concluded = concluded;
    }

    public Championship(List<Team> teams) {
        this.teams = teams;
    }

    public Championship(String name, LocalDate initialDate, LocalDate finalDate, String modality, String award, String sponsorship, List<Team> teams) {
        this.name = name;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.modality = modality;
        this.award = award;
        this.sponsorship = sponsorship;
        this.teams = teams;
    }

    public Championship(String name, LocalDate initialDate, LocalDate finalDate, String modality, String award, String sponsorship) {
        this.name = name;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.modality = modality;
        this.award = award;
        this.sponsorship = sponsorship;
    }

    public Championship(){

    }

    public Integer getIdChampionship() {
        return idChampionship;
    }

    public void setIdChampionship(Integer idChampionship) {
        this.idChampionship = idChampionship;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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