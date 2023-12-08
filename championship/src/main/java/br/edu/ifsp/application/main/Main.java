package br.edu.ifsp.application.main;

import br.edu.ifsp.domain.entities.championship.*;
import br.edu.ifsp.domain.entities.team.Team;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
/*        Team team1 = new Team(0, "IFSP", "Marcolinha", true);
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

    }*/

        // Criando uma instância de RoundRobin para teste
        List<Team> teams = new ArrayList<>();
        teams.add(new Team(0, "IFSP", "Marcolinha", true));
        teams.add(new Team(1, "USP", "Porco", true));
        teams.add(new Team(2, "UFSCAR", "Porco", true));
        teams.add(new Team(3, "UNIARA", "Porco", true));

        RoundRobin roundRobin = new RoundRobin(1, LocalDate.now(), LocalDate.now().plusDays(7),
                "Football", "Trophy", "Company X", false, teams);

        roundRobin.generateTable(teams);

        // Round 1
        roundRobin.updateMatchByIds(1, 1, 7, 1);
        roundRobin.finishMatchByIds(1, 1);

        roundRobin.updateMatchByIds(1, 6, 2, 1);
        roundRobin.finishMatchByIds(1, 6);

        // Round 2
        roundRobin.updateMatchByIds(2, 2, 2, 1);
        roundRobin.finishMatchByIds(2, 2);

        roundRobin.updateMatchByIds(2, 5, 2, 1);
        roundRobin.finishMatchByIds(2, 5);

        // Round 3
        roundRobin.updateMatchByIds(3, 3, 2, 1);
        roundRobin.finishMatchByIds(3, 3);

        roundRobin.updateMatchByIds(3, 4, 2, 1);
        roundRobin.finishMatchByIds(3, 4);

        // Round 4
        roundRobin.updateMatchByIds(4, 7, 2, 1);
        roundRobin.finishMatchByIds(4, 7);

        roundRobin.updateMatchByIds(4, 8, 2, 1);
        roundRobin.finishMatchByIds(4, 8);

        // Round 5
        roundRobin.updateMatchByIds(5, 9, 2, 1);
        roundRobin.finishMatchByIds(5, 9);

        roundRobin.updateMatchByIds(5, 10, 2, 1);
        roundRobin.finishMatchByIds(5, 10);

        // Round 6
        roundRobin.updateMatchByIds(6, 11, 1, 2);
        roundRobin.finishMatchByIds(6, 11);

        roundRobin.updateMatchByIds(6, 12, 1, 2);
        roundRobin.finishMatchByIds(6, 12);

        roundRobin.printTable();
        roundRobin.printStandings();

        roundRobin.finishChampionship();
    }
}
