package br.edu.ifsp.application.main;

import br.edu.ifsp.domain.entities.championship.*;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.services.KnockoutServices;
import br.edu.ifsp.domain.services.RoundServices;
import br.edu.ifsp.domain.usercases.knockout.administration.*;
import br.edu.ifsp.domain.usercases.roundrobin.administration.*;
import br.edu.ifsp.domain.usercases.team.TeamUserCases;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        TeamUserCases teamUserCases = new TeamUserCases();
        RoundServices roundServices = new RoundServices();
        CreateRoundRobin createRoundRobin = new CreateRoundRobin();
        StartRoundRobin startRoundRobin = new StartRoundRobin();
        UpdateRoundRobinMatch updateRoundRobinMatch = new UpdateRoundRobinMatch();
        FinishRoundRobinMatch finishRoundRobinMatch = new FinishRoundRobinMatch();
        FinishRoundRobin finishRoundRobin = new FinishRoundRobin();
        CreateKnockout createKnockout = new CreateKnockout();
        KnockoutServices knockoutServices = new KnockoutServices();
        StartKnockout startKnockout = new StartKnockout();
        UpdateKnockoutMatch updateKnockoutMatch = new UpdateKnockoutMatch();
        FinishKnockoutMatch finishKnockoutMatch = new FinishKnockoutMatch();
        AdvanceKnockout advanceKnockout = new AdvanceKnockout();
        FinishKnockout finishKnockout = new FinishKnockout();

        Team team1 = teamUserCases.createTeam("IFSP", "Marquinho");
        Team team2 = teamUserCases.createTeam("USP", "Porco");
        Team team3 = teamUserCases.createTeam("UFSCAR", "123");
        Team team4 = teamUserCases.createTeam("UNIARA", "Arara");

//        teamUserCases.updateTeam(team1, "SESC", "MARQUINHO DO GRAU");
//
//        Knockout knockout = createKnockout.createKnockout(LocalDate.now(), LocalDate.now().plusDays(7),
//                "League of Legends", "Ryze Skin", "Riot");
//
//        knockout.addTeam(team1);
//        knockout.addTeam(team2);
//        knockout.addTeam(team3);
//        knockout.addTeam(team4);
//
//        startKnockout.StartKnockout(knockout);
//
//        knockout.printSeedingKnockout();
//
//        updateKnockoutMatch.updateMatchResultByIds(knockout, 1, 1, 1, 3);
//        finishKnockoutMatch.setMatchConcludedByIds(knockout,1,1);
//        updateKnockoutMatch.updateMatchResultByIds(knockout, 1, 2, 1, 3);
//        finishKnockoutMatch.setMatchConcludedByIds(knockout,1,2);
//
//        knockout.printSeedingKnockout();
//
//        advanceKnockout.advancePhase(knockout);
//
//        knockout.printSeedingKnockout();
//
//        updateKnockoutMatch.updateMatchResultByIds(knockout, 2, 3, 1, 3);
//        finishKnockoutMatch.setMatchConcludedByIds(knockout,2,3);
//
//        knockout.printSeedingKnockout();
//
//        finishKnockout.finishKnockout(knockout);

        RoundRobin roundRobin = createRoundRobin.createRoundRobin(LocalDate.now(),
                LocalDate.now().plusDays(7), "Futebol", "Troféu", "Nike");

        roundRobin.addTeam(team1);
        roundRobin.addTeam(team2);
        roundRobin.addTeam(team3);
        roundRobin.addTeam(team4);

        startRoundRobin.startRoundRobin(roundRobin);

        roundRobin.printTable();
        roundRobin.printStandings();

        updateRoundRobinMatch.updateMatchByIds(roundRobin, 1, 1, 1, 2);
        finishRoundRobinMatch.finishMatchByIds(roundRobin,1,1);

        String pdfFilePath = "C:\\Users\\User\\Downloads\\rodada.pdf";
        roundServices.exportRoundToPDF(1, pdfFilePath, roundRobin.getTable());

//        updateRoundRobinMatch.updateMatchByIds(roundRobin, 1, 6, 1, 3);
//        finishRoundRobinMatch.finishMatchByIds(roundRobin,1,6);
//        roundRobin.printTable();
//        roundRobin.printStandings();

    }
}
