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


        LocalDate dataInicio = LocalDate.of(2023, 11, 1);
        LocalDate dataFim = LocalDate.of(2023, 12, 1);
        String modalidade = "Futebol";
        String premiacao = "Prêmio em dinheiro";
        String patrocinio = "Empresa X";
        boolean concluido = false;

        //----------------------------------PONTOS CORRIDOS -------------------------------------//
        System.out.println('\n');
        List<Team> timesPontosCorridos = new ArrayList<>();
        List<Round> tabela = new ArrayList<>();
        RoundRobin pontosCorridos = new RoundRobin(0, dataInicio, dataFim, modalidade, premiacao, patrocinio, concluido, timesPontosCorridos, tabela, 6);
        Team time1PT = new Team(6, "Galacticos", "Marcola da academia", true);
        pontosCorridos.addTeam(time1PT);
        pontosCorridos.addTeam(time1);
        pontosCorridos.addTeam(time2);
        pontosCorridos.addTeam(time3);
        pontosCorridos.addTeam(time4);
        pontosCorridos.addTeam(time5);
        pontosCorridos.showTeams(pontosCorridos.getTeams());
        RoundRobin.verifyAmountTeams(pontosCorridos.getTeams(), pontosCorridos.getTeamAmount());
        pontosCorridos.createRound();
        pontosCorridos.showTeamsActualRound();
        pontosCorridos.manageLastRoundMatch();
        pontosCorridos.showTeamsActualRound();
        pontosCorridos.manageLastRoundMatch();
        pontosCorridos.showTeamsActualRound();
        pontosCorridos.manageLastRoundMatch();
        pontosCorridos.showTeamsActualRound();
        pontosCorridos.printTable();
        pontosCorridos.createRound();
        pontosCorridos.showTeamsActualRound();
        pontosCorridos.manageLastRoundMatch();
        pontosCorridos.showTeamsActualRound();
        pontosCorridos.manageLastRoundMatch();
        pontosCorridos.showTeamsActualRound();
        pontosCorridos.manageLastRoundMatch();
        pontosCorridos.showTeamsActualRound();
        pontosCorridos.printTable();
        pontosCorridos.createRound();
        pontosCorridos.showTeamsActualRound();
        pontosCorridos.manageLastRoundMatch();
        pontosCorridos.showTeamsActualRound();
        pontosCorridos.manageLastRoundMatch();
        pontosCorridos.showTeamsActualRound();
        pontosCorridos.manageLastRoundMatch();
        pontosCorridos.showTeamsActualRound();
        pontosCorridos.printTable();
        pontosCorridos.createRound();
        pontosCorridos.showTeamsActualRound();
        pontosCorridos.manageLastRoundMatch();
        pontosCorridos.showTeamsActualRound();
        pontosCorridos.manageLastRoundMatch();
        pontosCorridos.showTeamsActualRound();
        pontosCorridos.manageLastRoundMatch();
        pontosCorridos.showTeamsActualRound();
        pontosCorridos.printTable();
        pontosCorridos.createRound();
        pontosCorridos.showTeamsActualRound();
        pontosCorridos.manageLastRoundMatch();
        pontosCorridos.showTeamsActualRound();
        pontosCorridos.manageLastRoundMatch();
        pontosCorridos.showTeamsActualRound();
        pontosCorridos.manageLastRoundMatch();
        pontosCorridos.showTeamsActualRound();
        pontosCorridos.printTable();
    }
}
