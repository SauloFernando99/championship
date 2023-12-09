package br.edu.ifsp.domain.usercases.knockout.administration;

import br.edu.ifsp.domain.entities.championship.Knockout;

import java.time.LocalDate;

public class CreateKnockout {
    public Knockout createKnockout(LocalDate initialDate, LocalDate finalDate, String modality,
                                   String award, String sponsorship){
        Knockout knockout = new Knockout(initialDate, finalDate, modality, award, sponsorship);
        return knockout;
    }
}
