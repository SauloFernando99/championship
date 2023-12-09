package br.edu.ifsp.domain.services;

import br.edu.ifsp.domain.entities.championship.Knockout;
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
        // Obtém a primeira fase do campeonato eliminatório
        Phase firstPhase = new Phase();
        knockout.addPhase(firstPhase);

        // Obtém a lista de times do campeonato
        List<Team> teams = knockout.getTeams();

        // Embaralha os times para garantir aleatoriedade
        Collections.shuffle(teams);

        // Cria as partidas
        List<Match> matches = new ArrayList<>();
        for (int i = 0; i < teams.size(); i += 2) {
            Team team1 = teams.get(i);
            Team team2 = teams.get(i + 1);
            Match match = new Match(team1, team2);
            matches.add(match);
        }

        // Adiciona as partidas à primeira fase
        for (Match match : matches) {
            firstPhase.addMatch(match);
        }

        // Define o nome da fase com base no número de partidas
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
        Phase newPhase = new Phase();

        List<Match> matches = new ArrayList<>();
        for (int i = 0; i < teams.size(); i += 2) {
            Team team1 = teams.get(i);
            Team team2 = teams.get(i + 1);
            Match match = new Match(team1, team2);
            matches.add(match);
        }

        for (Match match : matches) {
            newPhase.addMatch(match);
        }

        knockout.addPhase(newPhase);
    }
}
