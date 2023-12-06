package br.edu.ifsp.domain.entities.championship;

import br.edu.ifsp.domain.entities.team.Team;

import java.time.LocalDate;
import java.util.Objects;

public class Match {
    private Integer idMatch;
    private LocalDate date;
    private Integer scoreboard1;
    private Integer scoreboard2;
    private Team team1;
    private Team team2;
    private Boolean concluded;

    public Match(Integer idMatch, Integer scoreboard1, Integer scoreboard2, Team team1, Team team2) {
        this.idMatch = idMatch;
        this.team1 = team1;
        this.team2 = team2;
        this.setConcluded(false);
    }

    public Match(Team team1, Team team2) {
        this.team1 = team1;
        this.team2 = team2;
        this.setConcluded(false);
    }

    public void updateMatch(Integer scoreboard1, Integer scoreboard2){
        this.scoreboard1 = scoreboard1;
        this.scoreboard2 = scoreboard2;
    }

    public Team getWinner() {
        if (scoreboard1 > scoreboard2) {
            return team1;
        } else if (scoreboard2 > scoreboard1) {
            return team2;
        } else {
            return null;
        }
    }

    public void printMatch() {
        System.out.println("ID da Partida: " + idMatch);
        System.out.println(team1.getName() + " vs. " + team2.getName());
        System.out.println("Placar: " + scoreboard1 + " - " + scoreboard2);
    }

    public boolean notDraw(Match match){
        if (Objects.equals(scoreboard1, scoreboard2)) {
            System.out.println("The match result cannot be a draw");
            return false;
        }
        return true;
    }

    public void concludeMatch() {
        if (this.concluded) {
            System.out.println("The match has already ended.");
            return;
        }
        this.concluded = true;
    }

    public Integer getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(Integer idMatch) {
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
}