package br.edu.ifsp.domain.services;

import br.edu.ifsp.domain.entities.championship.Match;
import br.edu.ifsp.domain.entities.championship.Phase;
import br.edu.ifsp.domain.entities.team.Team;

import java.util.ArrayList;
import java.util.List;

public class PhaseServices {

    MatchServices matchServices = new MatchServices();
    public void addMatch(Phase phase, Match match) {
        phase.getMatches().add(match);
    }

    public void setPhase(Phase phase) {
        int numMatches = phase.getMatches().size();

        switch (numMatches) {
            case 1:
                phase.setPhase("Final");
                break;
            case 2:
                phase.setPhase("Semifinals");
                break;
            case 4:
                phase.setPhase("Quarterfinals");
                break;
            case 8:
                phase.setPhase("Round of 16");
                break;
            default:
                if (numMatches > 8) {
                    phase.setPhase(" Round of " + numMatches*2);
                } else {
                    phase.setPhase("Unknown Phase");
                }
                break;
        }
    }

    public Boolean allMatchesFinished(List<Match> matches){
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

    public List<Team> getWinners (List<Match> matches){
        List<Team> winners = new ArrayList<>();
        if (allMatchesFinished(matches)){
            for (Match match : matches) {
                winners.add(matchServices.getWinner(match));
            }
        }
        return winners;
    }
}
