package br.edu.ifsp.domain.entities.championship;

import br.edu.ifsp.domain.entities.team.Team;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Knockout extends Championship {
    private List<Phase> seeding;
    private Team champion;

    public Knockout(Integer idChampionship, LocalDate initialDate, LocalDate finalDate, String modality, String award, String sponsorship, Boolean concluded, List<Team> teams, List<Phase> seeding, Team champion) {
        super(idChampionship, initialDate, finalDate, modality, award, sponsorship, concluded, teams);
        this.setSeeding(new ArrayList<>());
        this.setChampion(null);
    }

    public Knockout(List<Team> teams) {
        super(teams);
        this.setSeeding(new ArrayList<>());
        this.setChampion(null);
    }

    public void drawTeams() {
        List<Team> shuffledTeams = new ArrayList<>(getTeams());
        Collections.shuffle(shuffledTeams);
        setTeams(shuffledTeams);
    }

    public void startKnockout(List<Team> teams) {
        if (!isPowerTwo(teams.size())) {
            System.out.println("The number of teams is not a power of 2. It is not possible to generate knockout matches.");
            return;
        }

        Phase phase = new Phase();

        List<Team> copyTeams = new ArrayList<>(teams);

        while (copyTeams.size() >= 2) {
            Team team1 = copyTeams.remove(0);
            Team team2 = copyTeams.remove(0);

            Match match = new Match(team1, team2);
            phase.addMatch(match);
        }
        phase.setPhase();
        this.getSeeding().add(phase);
    }
    private boolean isPowerTwo(int number) {
        return (number & (number - 1)) == 0 && number != 0;
    }

    public void nextPhase() {
        if (getSeeding().isEmpty()) {
            System.out.println("\n" +
                    "There is no previous round to generate the next.");
            return;
        }

        Phase lastRound = getSeeding().get(getSeeding().size() - 1);
        List<Match> lastRoundMatchs = lastRound.getMatches();

        if (!lastRound.allMatchesFinished()) {
            System.out.println("Error generating the next round: not all matches are finished.");
            return;
        }

        List<Team> winnersLastRound = new ArrayList<>();

        for (Match match : lastRoundMatchs) {
            Team winner = match.getWinner();
            if (winner != null) {
                winnersLastRound.add(winner);
            }
        }

        if (winnersLastRound.size() % 2 != 0) {
            System.out.println("Error generating next round: odd number of winners.");
            return;
        }

        startKnockout(winnersLastRound);
    }

    public void finishedKnockout() {
        if (getSeeding().isEmpty()) {
            System.out.println("There is no round to end the knockout stage.");
            return;
        }

        Phase lastPhase = getSeeding().get(getSeeding().size() - 1);

        if (lastPhase.getMatches().size() != 1) {
            System.out.println("The last round doesn't have exactly 1 game. It is not possible to end the knockout stage.");
            return;
        }

        Match endMatch = lastPhase.getMatches().get(0);
        Team winner = endMatch.getWinner();

        if (winner != null) {
            this.setChampion(winner);
            System.out.println("Knockout ended. The champion is: " + getChampion().getName());
            setConcluded(true);
        } else {
            System.out.println("There is no champion yet as the final match has not been completed.");
        }
    }

    public void printSeedingKnockout() {
        for (Phase phase : getSeeding()) {
            phase.setPhase();
            System.out.println("Phase: " + phase.getPhase());
            phase.printPhase();
            System.out.println("=======================");
        }
    }


    public List<Phase> getSeeding() {
        return seeding;
    }

    public void setSeeding(List<Phase> seeding) {
        this.seeding = seeding;
    }

    public Team getChampion() {
        return champion;
    }

    public void setChampion(Team champion) {
        this.champion = champion;
    }
}
