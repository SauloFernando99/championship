package br.edu.ifsp.domain.entities.championship;

import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.entities.team.TeamStats;
import br.edu.ifsp.domain.services.RoundServices;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RoundRobin extends Championship {
    private List<Round> table = new ArrayList<>();
    private List<TeamStats> teamStats = new ArrayList<>();
    private Team champion;

    RoundServices roundServices = new RoundServices();

    public RoundRobin(String name, LocalDate initialDate, LocalDate finalDate, String modality,
                      String award, String sponsorship, List<Team> teams) {
        super(name, initialDate, finalDate, modality, award, sponsorship, teams);
    }

    public void addTeam(Team team){
        this.getTeams().add(team);
        team.addRoundRobin(this);
    }

    public void printTable() {
        Integer i = 1;
        for (Round round : table) {
            System.out.println("ID: " + round.getIdRound() + " Round: " + round.getNumber() +
                    " Status: " + round.getFinished());
            for (Match match : round.getMatches()) {
                System.out.print("ID: " + match.getIdMatch() + "  " + match.getTeam1().getName() +
                        " vs " + match.getTeam2().getName());

                // Adiciona informações adicionais
                if (match.getScoreboard1() != null && match.getScoreboard2() != null) {
                    System.out.print("  |  Score: " + match.getScoreboard1() + " - " + match.getScoreboard2());
                }

                if (match.getConcluded() != null && match.getConcluded()) {
                    System.out.print("  |  Concluded");
                }

                System.out.println();
            }
            System.out.println();
        }
    }

    public void printStandings() {

        Collections.sort(teamStats, Comparator.comparingInt(TeamStats::getPoints).reversed());
        System.out.println("ID: " + getIdChampionship() + "Nome: " + getName());
        System.out.println("\nTabela de Classificação:");
        System.out.printf("%-10s%-15s%-10s%-10s%-10s%-10s%-15s%n","ID", "Time", "Pontos", "Vitórias", "Empates", "Derrotas", "Saldo");

        for (TeamStats teamStat : teamStats) {
            System.out.printf("%-10d%-15s%-10d%-10d%-10d%-10d%-15d%n",
                    teamStat.getTeam().getIdTeam(),
                    teamStat.getTeam().getName(),
                    teamStat.getPoints(),
                    teamStat.getWins(),
                    teamStat.getDraws(),
                    teamStat.getLosses(),
                    teamStat.getPointsStandings());
        }
    }

    public List<Round> getTable() {
        return table;
    }

    public void setTable(List<Round> table) {
        this.table = table;
    }

    public List<TeamStats> getTeamStats() {
        return teamStats;
    }

    public void setTeamStats(List<TeamStats> teamStats) {
        this.teamStats = teamStats;
    }
    @Override
    public String getChampionshipType() {
        return "Pontos-Corridos";
    }
}
