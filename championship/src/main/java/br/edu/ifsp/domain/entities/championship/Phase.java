package br.edu.ifsp.domain.entities.championship;

import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.services.MatchServices;

import java.util.ArrayList;
import java.util.List;

public class Phase {

    private static int phaseIdCounter = 1;
    private int idPhase;
    private String phase;
    private List<Match> matches = new ArrayList<>();

    private Boolean finished = false;

    public Phase() {
        this.idPhase = phaseIdCounter++;
    }

    public int getIdPhase() {
        return idPhase;
    }

    public void setIdPhase(int idPhase) {
        this.idPhase = idPhase;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
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
