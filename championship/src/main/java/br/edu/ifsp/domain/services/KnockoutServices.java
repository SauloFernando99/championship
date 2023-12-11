package br.edu.ifsp.domain.services;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.championship.KnockoutMatch;
import br.edu.ifsp.domain.entities.championship.Match;
import br.edu.ifsp.domain.entities.championship.Phase;
import br.edu.ifsp.domain.entities.team.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KnockoutServices {

    PhaseServices phaseServices = new PhaseServices();
    public void drawTeams(Knockout knockout){
        List<Team> shuffledTeams = new ArrayList<>(knockout.getTeams());
        Collections.shuffle(shuffledTeams);
        knockout.setTeams(shuffledTeams);
    }

    public void createFirstPhaseMatches(Knockout knockout) {

        Phase firstPhase = new Phase(knockout);
        knockout.addPhase(firstPhase);

        List<Team> teams = knockout.getTeams();

        Collections.shuffle(teams);

        List<KnockoutMatch> matches = new ArrayList<>();
        for (int i = 0; i < teams.size(); i += 2) {
            Team team1 = teams.get(i);
            Team team2 = teams.get(i + 1);
            KnockoutMatch match = new KnockoutMatch(team1, team2, firstPhase);
            matches.add(match);
        }

        for (KnockoutMatch match : matches) {
            firstPhase.addMatch(match);
        }

        phaseServices.setPhase(firstPhase);
    }

    public int getLastPhaseIndex(Knockout knockout) {
        List<Phase> seeding = knockout.getSeeding();

        if (seeding.isEmpty()) {
            throw new IllegalStateException("A lista de seeding está vazia. Não há fases disponíveis.");
        }

        return seeding.size() - 1;
    }

    public void generateNextPhase(Knockout knockout, List<Team> teams) {
        int lastPhaseIndex = getLastPhaseIndex(knockout);
        if (lastPhaseIndex == -1) {
            throw new IllegalStateException("There is no previous phase to create the next.");
        }
        Phase newPhase = new Phase(knockout);

        List<KnockoutMatch> matches = new ArrayList<>();
        for (int i = 0; i < teams.size(); i += 2) {
            Team team1 = teams.get(i);
            Team team2 = teams.get(i + 1);
            KnockoutMatch match = new KnockoutMatch(team1, team2, newPhase);
            matches.add(match);
        }

        for (KnockoutMatch knockoutMatch : matches) {
            newPhase.addMatch(knockoutMatch);
        }

        phaseServices.setPhase(newPhase);

        knockout.addPhase(newPhase);
    }

    public boolean isPowerTwo(int number) {
        return (number & (number - 1)) == 0 && number != 0;
    }
}
