package br.edu.ifsp.application.main;

import br.edu.ifsp.domain.entities.championship.*;
import br.edu.ifsp.domain.entities.team.Team;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Team team1 = new Team(0, "IFSP", "Marcolinha", true);
        Team team2 = new Team(1, "USP", "Porco", true);
        Team team3 = new Team(2, "UFSCAR", "Porco", true);
        Team team4 = new Team(3, "UNIARA", "Porco", true);
        Team team5 = new Team(4, "UNICEP", "Porco", true);
        Team team6 = new Team(5, "SLA", "Porco", true);

        // Lista de times
        List<Team> teams = new ArrayList<>();
        teams.add(team1);
        teams.add(team2);
        teams.add(team3);
        teams.add(team4);
        teams.add(team5);
        teams.add(team6);

        // Configurações do campeonato
        LocalDate startDate = LocalDate.of(2023, 11, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 1);
        String modality = "Futebol";
        String award = "Prêmio em dinheiro";
        String sponsorship = "Empresa X";
        boolean concluded = false;

        // Criação do campeonato
        RoundRobin championship = new RoundRobin(1, startDate, endDate, modality, award, sponsorship, concluded, teams);

        championship.manageRound(1, 1, 2, 1);
        championship.manageRound(1, 2, 0, 3);
        championship.manageRound(1, 3, 1, 1);

        // Finaliza a rodada
        championship.finishRound(championship.getRounds().get(0));

        // Exibe a tabela do campeonato
        championship.getChampionshipTable().printTable();

        // Declara o vencedor
        TeamRoundRobin winner = championship.declareWinner();
        System.out.println("O vencedor é: " + winner.getTeam().getName());
    }

}
