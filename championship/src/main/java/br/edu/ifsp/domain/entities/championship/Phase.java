package br.edu.ifsp.domain.entities.championship;

import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.services.MatchServices;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Phase {

    private Integer idPhase;
    private String phase;
    private List<KnockoutMatch> matches = new ArrayList<>();
    private Boolean finished;
    private Knockout knockout;

    public Phase(Knockout knockout) {
        this.finished = false;
        this.knockout = knockout;
    }

    public Phase(Integer idPhase, String phase, List<KnockoutMatch> matches, Boolean finished, Knockout knockout) {
        this.idPhase = idPhase;
        this.phase = phase;
        this.matches = matches;
        this.finished = finished;
        this.knockout = knockout;
    }

    public Phase(String phase){
        this.phase = phase;
    }

    public void addMatch(KnockoutMatch match) {
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

    public List<KnockoutMatch> getMatches() {
        return matches;
    }

    public void setMatches(List<KnockoutMatch> matches) {
        this.matches = matches;
    }

    public Knockout getKnockout() {
        return knockout;
    }

    public void setKnockout(Knockout knockout) {
        this.knockout = knockout;
    }

    public void printPhase() {
        if (matches != null) {
            for (KnockoutMatch match : matches) {
                System.out.println(match.toString());
                if (match.getConcluded() != null && match.getConcluded()) {
                    System.out.print("Concluded\n");
                }
                System.out.println("=======================");
            }
        }
    }
    public int getRemainingTeamsCount() {
        Set<Team> remainingTeams = new HashSet<>();

        for (KnockoutMatch match : matches) {
            if (!match.getConcluded()) {
                remainingTeams.add(match.getTeam1());
                remainingTeams.add(match.getTeam2());
            }
        }

        return remainingTeams.size();
    }
}
