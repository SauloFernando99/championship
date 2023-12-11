package br.edu.ifsp.domain.usecases.knockout.administration;

import br.edu.ifsp.application.main.Main;
import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.championship.KnockoutMatch;
import br.edu.ifsp.domain.entities.championship.Match;
import br.edu.ifsp.domain.entities.championship.Phase;
import br.edu.ifsp.domain.services.KnockoutServices;
import br.edu.ifsp.domain.usecases.knockout.dao.FindKnockoutUseCase;
import br.edu.ifsp.domain.usecases.knockout.dao.UpdateKnockoutUseCase;
import br.edu.ifsp.domain.usecases.phase.CreatePhaseUseCase;
import br.edu.ifsp.domain.usecases.phase.FindPhaseUseCase;
import br.edu.ifsp.domain.usecases.phase.UpdatePhaseUseCase;
import br.edu.ifsp.domain.usecases.roundrobinmatch.CreateRoundRobinMatchUseCase;
import br.edu.ifsp.domain.usecases.team.FindTeamUseCase;
import br.edu.ifsp.domain.usecases.team.UpdateTeamUseCase;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;

import static br.edu.ifsp.application.main.Main.createKnockoutMatchUseCase;

public class StartKnockoutUseCase {
    private FindKnockoutUseCase findKnockoutUseCase;
    private UpdateKnockoutUseCase updateKnockoutUseCase;
    private CreatePhaseUseCase createPhaseUseCase;
    private FindPhaseUseCase findPhaseUseCase;
    private UpdatePhaseUseCase updatePhaseUseCase;

    private KnockoutServices knockoutServices = new KnockoutServices();

    public StartKnockoutUseCase(
            FindKnockoutUseCase findKnockoutUseCase,
            UpdateKnockoutUseCase updateKnockoutUseCase,
            CreatePhaseUseCase createPhaseUseCase,
            FindPhaseUseCase findPhaseUseCase,
            UpdatePhaseUseCase updatePhaseUseCase,
            CreateRoundRobinMatchUseCase createKnockoutMatchUseCase
    ) {

        this.findKnockoutUseCase = findKnockoutUseCase;
        this.updateKnockoutUseCase = updateKnockoutUseCase;
        this.createPhaseUseCase = createPhaseUseCase;
        this.findPhaseUseCase = findPhaseUseCase;
        this.updatePhaseUseCase = updatePhaseUseCase;
    }

    public void StartKnockout(Integer knockoutId) {

        if (knockoutId == null)
            throw new IllegalArgumentException("Knockout ID is null");

        Knockout knockout = findKnockoutUseCase.findOne(knockoutId)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a Knockout with id: "
                        + knockoutId));

        if (knockoutServices.isPowerTwo(knockout.getTeams().size())) {

            knockoutServices.drawTeams(knockout);

            knockoutServices.createFirstPhaseMatches(knockout);

            updateKnockoutUseCase.update(knockout);

            for (Phase phase: knockout.getSeeding()
                 ) {
                createPhaseUseCase.insert(phase);

                for (KnockoutMatch match : phase) {
                    createKnockoutMatchUseCase.insert(match);
                }
            }
        }
    }
}
