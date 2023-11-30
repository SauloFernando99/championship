package br.edu.ifsp.domain.entities.championship;

import br.edu.ifsp.domain.entities.team.Team;

import java.util.ArrayList;
import java.util.List;

public class Phase {

    private String phase;
    private List<Match> matches = new ArrayList<>();

    public Phase(String phase, List<Match> matches) {
        this.phase = phase;
        this.matches = matches;
    }

    public Phase() {
        this.matches = new ArrayList<>();
    }

    public void addMatch(Match match) {
        if (matches != null) {
            this.matches.add(match);
        }
    }

    public List<Team> catchWinners() {
        List<Team> winners = new ArrayList<>();

        for (Match match : matches) {
            Team winner = match.getWinner();
            if (winner != null) {
                winners.add(winner);
            } else {
                return null;
            }
        }

        return winners;
    }

    public void setPhase() {
        int numMatches = matches.size();

        switch (numMatches) {
            case 1:
                setPhase("Final");
                break;
            case 2:
                setPhase("Semifinals");
                break;
            case 4:
                setPhase("Quarterfinals");
                break;
            case 8:
                setPhase("Round of 16");
                break;
            default:
                if (numMatches > 8) {
                    setPhase(" Round of " + numMatches*2);
                } else {
                    setPhase("Unknown Phase");
                }
                break;
        }
    }

    public boolean allMatchesFinished() {
        if (matches != null) {
            for (Match match : matches) {
                if (!match.getConcluded()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public void printPhase() {
        if (matches != null) {
            for (Match match : matches) {
                match.printMatch();
                System.out.println("=======================");
            }
        }
    }
    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }
}
