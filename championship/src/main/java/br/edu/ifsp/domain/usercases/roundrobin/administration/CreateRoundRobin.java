package br.edu.ifsp.domain.usercases.roundrobin.administration;

import br.edu.ifsp.domain.entities.championship.RoundRobin;

import java.time.LocalDate;

public class CreateRoundRobin {
    public RoundRobin createRoundRobin(LocalDate initialDate, LocalDate finalDate, String modality, String award,
                                       String sponsorship){
        RoundRobin roundRobin = new RoundRobin(initialDate, finalDate, modality, award, sponsorship);
        return roundRobin;
    }
}
