package br.edu.ifsp.domain.entities.championship;

import br.edu.ifsp.domain.entities.team.Team;

public class TeamRoundRobin {
    private int wins;
    private int loses;
    private int draws;
    private int goalDifference;
    private int pontuation;

    public TeamRoundRobin(int wins, int loses, int draws, int goalDifference, int pontuation) {
        this.wins = wins;
        this.loses = loses;
        this.draws = draws;
        this.goalDifference = goalDifference;
        this.pontuation = pontuation;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLoses() {
        return loses;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getGoalDifference() {
        return goalDifference;
    }

    public void setGoalDifference(int goalDifference) {
        this.goalDifference = goalDifference;
    }

    public int getPontuation() {
        return pontuation;
    }

    public void setPontuation(int pontuation) {
        this.pontuation = pontuation;
    }

    public void incrementWins() {
        this.wins++;
    }

    public void incrementLoses() {
        this.loses++;
    }

    public void incrementDraws() {
        this.draws++;
    }

    public int calculatePoints() {
        return (wins * 3) + draws;
    }

    public int calculateGoalDifference() {
        return goalDifference;
    }

    // Adicionado método para atualizar os gols
    public void updateGoals(int goalsFor, int goalsAgainst) {
        this.goalDifference += (goalsFor - goalsAgainst);
    }

}
