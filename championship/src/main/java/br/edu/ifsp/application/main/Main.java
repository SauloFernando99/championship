package br.edu.ifsp.application.main;

import br.edu.ifsp.domain.entities.championship.*;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.services.RoundServices;
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

        Team team1 = teamUserCases.createTeam("IFSP", "Marquinho");
        Team team2 = teamUserCases.createTeam("USP", "Porco");
        Team team3 = teamUserCases.createTeam("UFSCAR", "123");
        Team team4 = teamUserCases.createTeam("UNIARA", "Arara");

        teamUserCases.updateTeam(team1, "IFSP", "MARQUINHO DO GRAU");

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
        updateRoundRobinMatch.updateMatchByIds(roundRobin, 1, 6, 1, 3);
        finishRoundRobinMatch.finishMatchByIds(roundRobin,1,6);
        roundRobin.printTable();
        roundRobin.printStandings();
        updateRoundRobinMatch.updateMatchByIds(roundRobin, 2, 2, 1, 2);
        finishRoundRobinMatch.finishMatchByIds(roundRobin,2,2);
        updateRoundRobinMatch.updateMatchByIds(roundRobin, 2, 5, 1, 3);
        finishRoundRobinMatch.finishMatchByIds(roundRobin,2,5);
        roundRobin.printTable();
        roundRobin.printStandings();
        updateRoundRobinMatch.updateMatchByIds(roundRobin, 3, 3, 1, 2);
        finishRoundRobinMatch.finishMatchByIds(roundRobin,3,3);
        updateRoundRobinMatch.updateMatchByIds(roundRobin, 3, 4, 1, 3);
        finishRoundRobinMatch.finishMatchByIds(roundRobin,3,4);
        roundRobin.printTable();
        roundRobin.printStandings();
        updateRoundRobinMatch.updateMatchByIds(roundRobin, 4, 7, 1, 1);
        finishRoundRobinMatch.finishMatchByIds(roundRobin,4,7);
        updateRoundRobinMatch.updateMatchByIds(roundRobin, 4, 8, 1, 1);
        finishRoundRobinMatch.finishMatchByIds(roundRobin,4,8);
        roundRobin.printTable();
        roundRobin.printStandings();
        updateRoundRobinMatch.updateMatchByIds(roundRobin, 5, 9, 1, 1);
        finishRoundRobinMatch.finishMatchByIds(roundRobin,5,9);
        updateRoundRobinMatch.updateMatchByIds(roundRobin, 5, 10, 1, 1);
        finishRoundRobinMatch.finishMatchByIds(roundRobin,5,10);
        roundRobin.printTable();
        roundRobin.printStandings();
        updateRoundRobinMatch.updateMatchByIds(roundRobin, 6, 11, 1, 1);
        finishRoundRobinMatch.finishMatchByIds(roundRobin,6,11);
        updateRoundRobinMatch.updateMatchByIds(roundRobin, 6, 12, 1, 1);
        finishRoundRobinMatch.finishMatchByIds(roundRobin,6,12);

        roundRobin.printTable();
        roundRobin.printStandings();

        finishRoundRobin.finishChampionship(roundRobin);
    }
}
