package br.edu.ifsp.domain.entities.championship;

import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.services.MatchServices;

import java.util.ArrayList;
import java.util.List;

public class Phase {

    private String phase;
    private List<Match> matches;

    public Phase() {
        this.matches = new ArrayList<>();
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

    public void printPhase() {
        if (matches != null) {
            for (Match match : matches) {
                match.printMatch();
                System.out.println("=======================");
            }
        }
    }
}
