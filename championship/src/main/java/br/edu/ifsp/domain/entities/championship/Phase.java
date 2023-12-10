package br.edu.ifsp.domain.entities.championship;

import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.services.MatchServices;

import java.util.ArrayList;
import java.util.List;

public class Phase {

    private Integer idPhase;
    private String phase;
    private List<Match> matches = new ArrayList<>();
    private Boolean finished = false;

    public Phase() {
    }

    public void addMatch(Match match) {
        matches.add(match);
    }

    public Integer getIdPhase() {
        return idPhase;
    }

    public void setIdPhase(Integer idPhase) {
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
                match.toString();
                if (match.getConcluded() != null && match.getConcluded()) {
                    System.out.print("Concluded\n");
                }
                System.out.println("=======================");
            }
        }
    }
}
