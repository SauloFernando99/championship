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
            generatePhases(knockout);

            knockoutServices.drawTeams(knockout);
            knockoutServices.createMatchesForNextUnfinishedPhase(knockout, 0, knockout.getTeams());
        }
    }

    private void generatePhases(Knockout knockout){

        Double numberOfPhases = calculatePhasesNumber(knockout);
        Integer numberOfTeams = knockout.getTeams().size();

        while (numberOfPhases > 0){
            Phase phase = new Phase();
            for (int i = 0; i < numberOfTeams; i++) {
                Match match = new Match();
                phaseServices.addMatch(phase, match);
            }
            phaseServices.setPhase(phase);
            knockout.addPhase(phase);
            numberOfPhases = numberOfPhases/2;
        }
    }

    private boolean isPowerTwo(int number) {
        return (number & (number - 1)) == 0 && number != 0;
    }

    private Double calculatePhasesNumber (Knockout knockout){
        if (knockout.getTeams().size() <= 0 || (knockout.getTeams().size() & (knockout.getTeams().size() - 1)) != 0) {
            throw new IllegalArgumentException("Number of teams must be power of 2.");
        }

        return (Math.log(knockout.getTeams().size()) / Math.log(2));
    }
}
