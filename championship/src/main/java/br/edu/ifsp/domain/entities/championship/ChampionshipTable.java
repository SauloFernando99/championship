package br.edu.ifsp.domain.entities.championship;

import br.edu.ifsp.domain.entities.team.Team;

import java.util.List;

public class ChampionshipTable {
    private List<Team> teams;

    public ChampionshipTable(List<Team> teams) {
        this.teams = teams;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void updateTable(List<TeamRoundRobin> sortedTeams) {
        for (int i = 0; i < teams.size(); i++) {
            Team originalTeam = teams.get(i);

            Team updatedTeam = sortedTeams.get(i).getTeam();

            originalTeam.setPoints(updatedTeam.getPoints());
            originalTeam.setWins(updatedTeam.getWins());
            originalTeam.setLoses(updatedTeam.getLoses());
            originalTeam.setDraw(updatedTeam.getDraw());
            originalTeam.setGoalDifference(updatedTeam.getGoalDifference());
        }
    }

    public void printTable() {
        System.out.printf("%-5s%-20s%-5s%-5s%-5s%-5s%-5s%n", "Pos", "Team", "Pts", "W", "D", "L", "GD");
        for (int i = 0; i < teams.size(); i++) {
            Team team = teams.get(i);
            System.out.printf("%-5d%-20s%-5d%-5d%-5d%-5d%-5d%n",
                    i + 1, team.getName(), team.getPoints(), team.getWins(), team.getDraw(), team.getLoses(), team.getGoalDifference());
        }
    }

    public void incrementWins(TeamRoundRobin teamRoundRobin) {
        teamRoundRobin.incrementWins();
    }

    public void incrementLoses(TeamRoundRobin teamRoundRobin) {
        teamRoundRobin.incrementLoses();
    }

    public void incrementDraws(TeamRoundRobin teamRoundRobin) {
        teamRoundRobin.incrementDraws();
    }

    public void updatePointsAndGoalDifference(TeamRoundRobin teamRoundRobin) {
        teamRoundRobin.setPoints(teamRoundRobin.calculatePoints());
        teamRoundRobin.setGoalDifference(teamRoundRobin.calculateGoalDifference());
    }
}
