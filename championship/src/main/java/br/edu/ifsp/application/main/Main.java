package br.edu.ifsp.application.main;

import br.edu.ifsp.HelloApplication;
import br.edu.ifsp.application.repository.*;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.knockout.dao.*;
import br.edu.ifsp.domain.usecases.knockoutmatch.*;
import br.edu.ifsp.domain.usecases.phase.*;
import br.edu.ifsp.domain.usecases.round.*;
import br.edu.ifsp.domain.usecases.roundrobin.dao.*;
import br.edu.ifsp.domain.usecases.roundrobinmatch.*;
import br.edu.ifsp.domain.usecases.team.*;
import br.edu.ifsp.domain.usecases.teamstats.*;

public class Main {

    public static CreateTeamUseCase createTeamUseCase;
    public static FindTeamUseCase findTeamUseCase;
    public static UpdateTeamUseCase updateTeamUseCase;
    public static RemoveTeamUseCase removeTeamUseCase;

    public static CreateKnockoutUseCase createKnockoutUseCase;
    public static FindKnockoutUseCase findKnockoutUseCase;
    public static UpdateKnockoutUseCase updateKnockoutUseCase;
    public static RemoveKnockoutUseCase removeKnockoutUseCase;

    public static CreateRoundRobinUseCase createRoundRobinUseCase;
    public static FindRoundRobinUseCase findRoundRobinUseCase;
    public static UpdateRoundRobinUseCase updateRoundRobinUseCase;
    public static RemoveRoundRobinUseCase removeRoundRobinUseCase;

    public static CreatePhaseUseCase createPhaseUseCase;
    public static FindPhaseUseCase findPhaseUseCase;
    public static UpdatePhaseUseCase updatePhaseUseCase;
    public static RemovePhaseUseCase removePhaseUseCase;

    public static CreateRoundUseCase createRoundUseCase;
    public static FindRoundUseCase findRoundUseCase;
    public static UpdateRoundUseCase updateRoundUseCase;
    public static RemoveRoundUseCase removeRoundUseCase;

    public static CreateTeamStatsUseCase createTeamStatsUseCase;
    public static FindTeamStatsUseCase findTeamStatsUseCase;
    public static UpdateTeamStatsUseCase updateTeamStatsUseCase;
    public static RemoveTeamStatsUseCase removeTeamStatsUseCase;

    public static CreateRoundRobinMatchUseCase createRoundRobinMatchUseCase;
    public static FindRoundRobinMatchUseCase findRoundRobinMatchUseCase;
    public static UpdateRoundRobinMatchUseCase updateRoundRobinMatchUseCase;
    public static RemoveRoundRobinMatchUseCase removeRoundRobinMatchUseCase;

    public static CreateKnockoutMatchUseCase createKnockoutMatchUseCase;
    public static FindKnockoutMatchUseCase findKnockoutMatchUseCase;
    public static UpdateKnockoutMatchUseCase updateKnockoutMatchUseCase;
    public static RemoveKnockoutMatchUseCase removeKnockoutMatchUseCase;


    public static void main(String[] args) {

        configureInjection();

        Team team1 = new Team("IFSP", "Marquinho");
        Team team2 = new Team("USP", "Porco");
        Team team3 = new Team("UFSCAR", "123");
        Team team4 = new Team("UNIARA", "Arara");

        createTeamUseCase.insert(team1);
        createTeamUseCase.insert(team2);
        createTeamUseCase.insert(team3);
        createTeamUseCase.insert(team4);

        HelloApplication.main(args);
    }

    private static void configureInjection() {
        TeamDAO teamDAO = new InMemoryTeamDAO();
        createTeamUseCase = new CreateTeamUseCase(teamDAO);
        updateTeamUseCase = new UpdateTeamUseCase(teamDAO);
        findTeamUseCase = new FindTeamUseCase(teamDAO);
        removeTeamUseCase = new RemoveTeamUseCase(teamDAO);

        KnockoutDAO knockoutDAO = new InMemoryKnockoutDAO();
        createKnockoutUseCase = new CreateKnockoutUseCase(knockoutDAO);
        updateKnockoutUseCase = new UpdateKnockoutUseCase(knockoutDAO);
        findKnockoutUseCase = new FindKnockoutUseCase(knockoutDAO);
        removeKnockoutUseCase = new RemoveKnockoutUseCase(knockoutDAO);

        RoundRobinDAO roundRobinDAO = new InMemoryRoundRobinDAO();
        createRoundRobinUseCase = new CreateRoundRobinUseCase(roundRobinDAO);
        updateRoundRobinUseCase = new UpdateRoundRobinUseCase(roundRobinDAO);
        findRoundRobinUseCase = new FindRoundRobinUseCase(roundRobinDAO);
        removeRoundRobinUseCase = new RemoveRoundRobinUseCase(roundRobinDAO);

        PhaseDAO phaseDAO = new InMemoryPhaseDAO();
        createPhaseUseCase = new CreatePhaseUseCase(phaseDAO);
        updatePhaseUseCase = new UpdatePhaseUseCase(phaseDAO);
        findPhaseUseCase = new FindPhaseUseCase(phaseDAO);
        removePhaseUseCase = new RemovePhaseUseCase(phaseDAO);

        RoundDAO roundDAO = new InMemoryRoundDAO();
        createRoundUseCase = new CreateRoundUseCase(roundDAO);
        updateRoundUseCase = new UpdateRoundUseCase(roundDAO);
        findRoundUseCase = new FindRoundUseCase(roundDAO);
        removeRoundUseCase = new RemoveRoundUseCase(roundDAO);

        TeamStatsDAO teamStatsDAO = new InMemoryTeamStatsDAO();
        createTeamStatsUseCase = new CreateTeamStatsUseCase(teamStatsDAO);
        updateTeamStatsUseCase = new UpdateTeamStatsUseCase(teamStatsDAO);
        findTeamStatsUseCase = new FindTeamStatsUseCase(teamStatsDAO);
        removeTeamStatsUseCase = new RemoveTeamStatsUseCase(teamStatsDAO);

        RoundRobinMatchDAO roundRobinMatchDAO = new InMemoryRoundRobinMatchDAO();
        createRoundRobinMatchUseCase = new CreateRoundRobinMatchUseCase(roundRobinMatchDAO);
        updateRoundRobinMatchUseCase = new UpdateRoundRobinMatchUseCase(roundRobinMatchDAO);
        findRoundRobinMatchUseCase = new FindRoundRobinMatchUseCase(roundRobinMatchDAO);
        removeRoundRobinMatchUseCase = new RemoveRoundRobinMatchUseCase(roundRobinMatchDAO);

        KnockoutMatchDAO knockoutMatchDAO = new InMemoryKnockoutMatchDAO();
        createKnockoutMatchUseCase = new CreateKnockoutMatchUseCase(knockoutMatchDAO);
        updateKnockoutMatchUseCase = new UpdateKnockoutMatchUseCase(knockoutMatchDAO);
        findKnockoutMatchUseCase = new FindKnockoutMatchUseCase(knockoutMatchDAO);
        removeKnockoutMatchUseCase = new RemoveKnockoutMatchUseCase(knockoutMatchDAO);
    }
}
