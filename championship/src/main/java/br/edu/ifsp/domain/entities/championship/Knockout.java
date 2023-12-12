package br.edu.ifsp.domain.entities.championship;

import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.services.MatchServices;
import br.edu.ifsp.domain.services.PhaseServices;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Knockout extends Championship {
    private List<Phase> seeding = new ArrayList<>();
    private Team champion;

    public Knockout(Integer idChampionship, String name, LocalDate initialDate, LocalDate finalDate, String modality, String award, String sponsorship, List<Team> teams, List<Phase> seeding, Team champion) {
        super(idChampionship, name, initialDate, finalDate, modality, award, sponsorship, teams);
        this.setSeeding(new ArrayList<>());
        this.setChampion(null);
    }

    public Knockout (String name, LocalDate initialDate, LocalDate finalDate, String modality, String award, String sponsorship, List<Team> teams){
        super(name, initialDate, finalDate, modality, award, sponsorship, teams);
        this.setChampion(null);
    }

    public Knockout (String name, LocalDate initialDate, LocalDate finalDate, String modality, String award, String sponsorship){
        super(name, initialDate, finalDate, modality, award, sponsorship);
        this.setChampion(null);
    }

    public Knockout(List<Team> teams) {
        super(teams);
        this.setSeeding(new ArrayList<>());
        this.setChampion(null);
    }
    public Knockout(){
        super();
    }
    public void addTeam(Team team) {
        if (team.getIsActive()) {
            getTeams().add(team);
            team.addKnockout(this);
        } else {
            System.out.println("An inactive team can not be included in a championship");
        }
    }

    public void addPhase(Phase phase){
        getSeeding().add(phase);
    }

    public List<Phase> getSeeding() {
        return seeding;
    }

    public void setSeeding(List<Phase> seeding) {
        this.seeding = seeding;
    }

    public Team getChampion() {
        return champion;
    }

    public void setChampion(Team champion) {
        this.champion = champion;
    }

    public void printSeedingKnockout() {

        for (Phase phase : getSeeding()) {
            System.out.println("Phase: " + phase.getPhase() + " ID: " + phase.getIdPhase() +
                    "Championship" + getConcluded() +
                    " Concluded: " + phase.getFinished());
            phase.printPhase();
            System.out.println("=======================");
        }
    }
    @Override
    public String getChampionshipType() {
        return "Mata-Mata";
    }
}
