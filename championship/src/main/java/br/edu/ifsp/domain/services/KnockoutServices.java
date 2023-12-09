package br.edu.ifsp.domain.services;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.championship.Match;
import br.edu.ifsp.domain.entities.championship.Phase;
import br.edu.ifsp.domain.entities.team.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KnockoutServices {
    public void drawTeams(Knockout knockout){
        List<Team> shuffledTeams = new ArrayList<>(knockout.getTeams());
        Collections.shuffle(shuffledTeams);
        knockout.setTeams(shuffledTeams);
    }

    public void createMatchesForNextUnfinishedPhase(Knockout knockout, Integer index, List<Team> winners) {
        Phase phase = knockout.getSeeding().get(index);

        if (!phase.getFinished()) {
            List<Match> phaseMatches = phase.getMatches();

            for (int i = 0; i < winners.size(); i += 2) {
                if (i + 1 < winners.size()) {
                    Team team1 = winners.get(i);
                    Team team2 = winners.get(i + 1);

                    Match match = new Match(team1, team2);
                    phaseMatches.add(match);
                }
            }
        }
    }

    public int findLastNonEmptyPhaseIndex(Knockout knockout) {
        List<Phase> phases = knockout.getSeeding();

        for (int i = phases.size() - 1; i >= 0; i--) {
            Phase phase = phases.get(i);

            if (!phase.getMatches().isEmpty()) {
                return i;
            }
        }
        return -1;
    }

}
