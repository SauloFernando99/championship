package br.edu.ifsp.domain.usecases.knockout.administration;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.championship.KnockoutMatch;
import br.edu.ifsp.domain.entities.championship.Phase;
import br.edu.ifsp.domain.services.KnockoutServices;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;

import static br.edu.ifsp.application.main.Main.findKnockoutUseCase;
import static br.edu.ifsp.application.main.Main.updateKnockoutUseCase;
import static br.edu.ifsp.application.main.Main.createPhaseUseCase;
import static br.edu.ifsp.application.main.Main.createKnockoutMatchUseCase;

public class StartKnockoutUseCase {
    private KnockoutServices knockoutServices = new KnockoutServices();

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

                for (KnockoutMatch match : phase.getMatches()) {
                    createKnockoutMatchUseCase.insert(match);
                }
            }
        }
    }
}
