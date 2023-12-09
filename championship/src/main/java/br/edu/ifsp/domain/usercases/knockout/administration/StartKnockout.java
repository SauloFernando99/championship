package br.edu.ifsp.domain.usercases.knockout.administration;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.championship.Match;
import br.edu.ifsp.domain.entities.championship.Phase;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.services.KnockoutServices;
import br.edu.ifsp.domain.services.PhaseServices;

import java.util.List;

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
