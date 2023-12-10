package br.edu.ifsp.domain.usecases.roundrobin.administration;

import br.edu.ifsp.domain.entities.championship.RoundRobin;

import java.time.LocalDate;

public class CreateRoundRobin {
    public RoundRobin createRoundRobin(String name, LocalDate initialDate, LocalDate finalDate, String modality, String award,
                                       String sponsorship){
        RoundRobin roundRobin = new RoundRobin(name, initialDate, finalDate, modality, award, sponsorship);
        return roundRobin;
    }
}
