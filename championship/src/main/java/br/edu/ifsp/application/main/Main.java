package br.edu.ifsp.application.main;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.championship.Round;
import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.entities.team.Team;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Team time1 = new Team(0, "IFSP", "Marcolinha", true);
        Team time2 = new Team(1, "USP", "Porco", true);
        Team time3 = new Team(2, "UFSCAR", "Porco", true);
        Team time4 = new Team(3, "UNIARA", "Porco", true);
        Team time5 = new Team(4, "UNICEP", "Porco", true);
        Team time6 = new Team(5, "SLA", "Porco", true);

        List<Team> teams = new ArrayList<>();
        teams.add(time1);
        teams.add(time2);
        teams.add(time3);
        teams.add(time4);
        teams.add(time5);
        teams.add(time6);

        LocalDate dataInicio = LocalDate.of(2023, 11, 1);
        LocalDate dataFim = LocalDate.of(2023, 12, 1);
        String modalidade = "Futebol";
        String premiacao = "Prêmio em dinheiro";
        String patrocinio = "Empresa X";
        boolean concluido = false;

        RoundRobin championship = new RoundRobin(1, dataInicio, dataFim, modalidade, premiacao, patrocinio, concluido, teams);
        // Gerenciando partidas (exemplo)
        championship.manageRound(1, 1, 2, 1);
        championship.manageRound(1, 2, 1, 1);
        championship.manageRound(1, 3, 3, 1);


        System.out.println("----------- Tabela do Campeonato ------------");
        championship.getChampionshipTable().printTable();


        championship.finishRound(1); // Exemplo de finalização da rodada 1

        //
        System.out.println("\n----------- Tabela Atualizada ------------");
        championship.getChampionshipTable().printTable();


        Team winner = championship.declareWinner();
        System.out.println("\n----------- Vencedor ------------");
        System.out.println("Vencedor: " + winner.getName());

    }
}
