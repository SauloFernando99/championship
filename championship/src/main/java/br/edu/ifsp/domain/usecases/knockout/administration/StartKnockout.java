package br.edu.ifsp.domain.usecases.knockout.administration;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.services.KnockoutServices;
import br.edu.ifsp.domain.services.PhaseServices;

public class StartKnockout {
    PhaseServices phaseServices = new PhaseServices();
    KnockoutServices knockoutServices = new KnockoutServices();

    public void StartKnockout(Knockout knockout) {
        if (isPowerTwo(knockout.getTeams().size())) {

            knockoutServices.drawTeams(knockout);
            knockoutServices.createFirstPhaseMatches(knockout);
        }
    }

    private boolean isPowerTwo(int number) {
        return (number & (number - 1)) == 0 && number != 0;
    }
    
}
