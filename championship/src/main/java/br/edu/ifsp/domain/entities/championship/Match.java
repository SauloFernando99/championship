package br.edu.ifsp.domain.entities.championship;

import br.edu.ifsp.domain.entities.team.Team;

import java.time.LocalDate;

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
    }

    public void updateMatch(Integer idMatch, Integer scoreboard1, Integer scoreboard2){
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

    public void concludeMatch(Match match) {
        if (this.concluded) {
            System.out.println("The match has already ended.");
            return;
        }
        this.concluded = true;
    }

    public void updatePunctuation(Match match) {
        Team team1 = match.getTeam1();
        Team team2 = match.getTeam2();

        if (this.scoreboard1 > this.scoreboard2) {
            team1.setPoints(+3);
            team1.setWins(+1);
            team1.setGoalDifference(+ (this.scoreboard1 - this.scoreboard2));

            team2.setLoses(+1);
            team2.setGoalDifference(+(this.scoreboard2 - this.scoreboard1));
        } else if (this.scoreboard1 < this.scoreboard2) {
            team2.setPoints(+3);
            team2.setWins(+1);
            team2.setGoalDifference(+(this.scoreboard2 - this.scoreboard1));

            team1.setLoses(+1);
            team1.setGoalDifference(+(this.scoreboard1 - this.scoreboard2));
        } else {
            team1.setPoints(+1);
            team1.setDraw(+1);
            team1.setGoalDifference(+(this.scoreboard1 - this.scoreboard2));

            team2.setPoints(+1);
            team2.setDraw(+1);
            team2.setGoalDifference(+(this.scoreboard2 - this.scoreboard1));
        }
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