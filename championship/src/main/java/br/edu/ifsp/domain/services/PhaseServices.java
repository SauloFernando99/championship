package br.edu.ifsp.domain.services;

import br.edu.ifsp.domain.entities.championship.Match;
import br.edu.ifsp.domain.entities.championship.Phase;

public class PhaseServices {
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
}
