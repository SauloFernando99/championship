package br.edu.ifsp.domain.usecases.knockout.administration;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.championship.KnockoutMatch;
import br.edu.ifsp.domain.entities.championship.Phase;
import br.edu.ifsp.domain.entities.dbsupport.TeamKnockout;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.services.KnockoutServices;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.edu.ifsp.application.main.Main.*;

public class StartKnockoutUseCase {
    private KnockoutServices knockoutServices = new KnockoutServices();

    public void startKnockout(Integer knockoutId) {

        if (knockoutId == null)
            throw new IllegalArgumentException("Knockout ID is null");

        Knockout knockout = findKnockoutUseCase.findOne(knockoutId)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a Knockout with id: "
                        + knockoutId));

        List<TeamKnockout> teamKnockouts = findTeamKnockoutUseCase.findAllByKnockout(knockoutId);

        List<Team> registeredTeams = new ArrayList<>();

        for (TeamKnockout combination: teamKnockouts
             ) {
            registeredTeams.add(combination.getTeam());
        }

        knockout.getTeams().addAll(registeredTeams);

        if (knockoutServices.isPowerTwo(knockout.getTeams().size())) {

            knockoutServices.drawTeams(knockout);

            knockoutServices.createFirstPhaseMatches(knockout);

            updateKnockoutUseCase.update(knockout);

            System.out.println("Campeonato Iniciado");

            for (Phase phase: knockout.getSeeding()
                 ) {

                Integer id = createPhaseUseCase.insert(phase);

                phase.setIdPhase(id);

                System.out.println("Phase: " + phase.getPhase());

                for (KnockoutMatch match : phase.getMatches()) {
                    match.setDate(LocalDate.now());
                    match.setPhase(phase);
                    createKnockoutMatchUseCase.insert(match);
                }
            }
        } else {
            throw new IllegalArgumentException("Number of teams must be power of 2");
        }
    }
}
