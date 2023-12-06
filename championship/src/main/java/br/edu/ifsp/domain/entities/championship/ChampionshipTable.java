package br.edu.ifsp.domain.entities.championship;

import br.edu.ifsp.domain.entities.team.Team;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ChampionshipTable {


    private List<TeamRoundRobin> teamRoundRobins;


    public ChampionshipTable(List<TeamRoundRobin> teamRoundRobins) {
        this.teamRoundRobins = teamRoundRobins;
    }


    public List<TeamRoundRobin> getTeamRoundRobins() {
        return teamRoundRobins;
    }


    public void updateTable(List<TeamRoundRobin> sortedTeams) {
        for (int i = 0; i < teamRoundRobins.size(); i++) {
            TeamRoundRobin originalTeam = teamRoundRobins.get(i);
            TeamRoundRobin updatedTeam = findUpdatedTeam(originalTeam, sortedTeams);

            // Atualiza as estatísticas apenas se a equipe foi encontrada na lista atualizada
            if (updatedTeam != null) {
                originalTeam.setPontuation(updatedTeam.getPontuation());
                originalTeam.setWins(updatedTeam.getWins());
                originalTeam.setLoses(updatedTeam.getLoses());
                originalTeam.setDraws(updatedTeam.getDraws());
                originalTeam.setGoalDifference(updatedTeam.getGoalDifference());
            }
        }
    }

    private TeamRoundRobin findUpdatedTeam(TeamRoundRobin originalTeam, List<TeamRoundRobin> updatedTeams) {
        return updatedTeams.stream()
                .filter(updatedTeam -> updatedTeam.getTeam().getIdTeam().equals(originalTeam.getTeam().getIdTeam()))
                .findFirst()
                .orElse(null);
    }

    public TeamRoundRobin getEstatisticas(Team team) {
        return teamRoundRobins.stream()
                .filter(stats -> stats.getTeam().getIdTeam().equals(team.getIdTeam()))
                .findFirst()
                .orElse(new TeamRoundRobin(team, 0, 0, 0, 0,0));
    }


    public void printTable() {
        List<TeamRoundRobin> teamRoundRobins = getTeamRoundRobinsFromSomewhere();

        System.out.printf("%-5s%-20s%-5s%-5s%-5s%-5s%-5s%n", "Pos", "Team", "Pts", "W", "D", "L", "GD");
        for (int i = 0; i < teamRoundRobins.size(); i++) {
            TeamRoundRobin teamRoundRobin = teamRoundRobins.get(i);
            System.out.printf("%-5d%-20s%-5d%-5d%-5d%-5d%-5d%n",
                    i + 1, teamRoundRobin.getTeam().getName(), teamRoundRobin.getPontuation(),
                    teamRoundRobin.getWins(), teamRoundRobin.getDraws(), teamRoundRobin.getLoses(),
                    teamRoundRobin.calculateGoalDifference());
        }
    }
    private List<TeamRoundRobin> getTeamRoundRobinsFromSomewhere() {

        return teamRoundRobins;
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
        teamRoundRobin.setPontuation(teamRoundRobin.calculatePoints());
        teamRoundRobin.setGoalDifference(teamRoundRobin.calculateGoalDifference());
    }

}