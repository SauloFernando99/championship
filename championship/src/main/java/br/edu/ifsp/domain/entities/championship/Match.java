package br.edu.ifsp.domain.entities.championship;

import br.edu.ifsp.domain.entities.team.Team;

import java.time.LocalDate;
import java.util.Objects;

public class Match {

    private static int matchIdCounter = 1;
    private int idMatch;
    private LocalDate date;
    private Integer scoreboard1 = 0;
    private Integer scoreboard2 = 0;
    private Team team1;
    private Team team2;
    private Boolean concluded = false;

    public Match(Team team1, Team team2) {
        this.idMatch = matchIdCounter++;
        this.team1 = team1;
        this.team2 = team2;
    }

    public Match(){}

    public int getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(int idMatch) {
        this.idMatch = idMatch;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getScoreboard1() {
        return scoreboard1;
    }

    public void setScoreboard1(Integer scoreboard1) {
        this.scoreboard1 = scoreboard1;
    }

    public Integer getScoreboard2() {
        return scoreboard2;
    }

    public void setScoreboard2(Integer scoreboard2) {
        this.scoreboard2 = scoreboard2;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public Boolean getConcluded() {
        return concluded;
    }

    public void setConcluded(Boolean concluded) {
        this.concluded = concluded;
    }

    public void printMatch() {
        System.out.println("ID da Partida: " + idMatch);
        System.out.println(team1.getName() + " vs. " + team2.getName());
        System.out.println("Placar: " + scoreboard1 + " - " + scoreboard2);
    }
}