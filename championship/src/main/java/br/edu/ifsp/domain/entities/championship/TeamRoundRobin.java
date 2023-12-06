package br.edu.ifsp.domain.entities.championship;

import br.edu.ifsp.domain.entities.team.Team;

public class TeamRoundRobin {
    private int wins;
    private int losses;
    private int draws;
    private int goalDifference;

    public void registerWin() {
        wins++;
    }

    public void registerLoss() {
        losses++;
    }

    public void registerDraw() {
        draws++;
    }
    public Integer getGoalDifference() {
        return goalDifference;
    }
    public void registerWin(int goalDifference) {
        wins++;
        this.goalDifference += goalDifference;
    }

    public void registerLoss(int goalDifference) {
        losses++;
        this.goalDifference -= goalDifference;
    }

}
