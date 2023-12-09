package br.edu.ifsp.application.main;

import br.edu.ifsp.domain.entities.championship.*;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.services.MatchServices;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

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


        // Create a Knockout instance
        Knockout knockout = new Knockout(2, LocalDate.now(), LocalDate.now().plusDays(7), "Football", "Trophy", "Sponsor", false, teams, null, null);

        // Draw teams to shuffle their order
        knockout.drawTeams();

        // Start the knockout with the drawn teams
        knockout.startKnockout(knockout.getTeams());

        // Simulate updating match results
        simulateMatchResults(knockout);

        // Print seeding after match results
        System.out.println("\nAfter Match Results:");
        knockout.printSeedingKnockout();

        // Simulate advancing to the next phase
        knockout.nextPhase();

        System.out.println("\nAfter Next Phase:");
        knockout.printSeedingKnockout();

        // Simulate finishing the knockout
        knockout.finishKnockout();
    }
    private static void simulateMatchResults(Knockout knockout) {
        // Assuming all matches have two teams
        for (Phase phase : knockout.getSeeding()) {
            for (Match match : phase.getMatches()) {
                // Simulate random scores
                int score1 = 1;
                int score2 = 2;

                MatchServices matchServices = new MatchServices();
                // Update match results
                matchServices.updateMatchResult(match, score1, score2);

                // Conclude the match
                matchServices.concludeMatch(match);

                System.out.println("Match Result: " + match.getTeam1().getName() + " " + score1 + " vs " +
                        match.getTeam2().getName() + " " + score2);
            }
        }
    }
}
