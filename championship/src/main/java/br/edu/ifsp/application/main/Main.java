package br.edu.ifsp.application.main;

import br.edu.ifsp.HelloApplication;
import br.edu.ifsp.application.repository.InMemoryTeamDAO;
import br.edu.ifsp.domain.entities.championship.*;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.services.KnockoutServices;
import br.edu.ifsp.domain.services.RoundServices;
import br.edu.ifsp.domain.usecases.knockout.administration.*;
import br.edu.ifsp.domain.usecases.roundrobin.administration.*;
import br.edu.ifsp.domain.usecases.team.*;

import java.time.LocalDate;

public class Main {

    public static CreateTeamUseCase createTeamUseCase;
    public static FindTeamUseCase findTeamUseCase;
    public static UpdateTeamUseCase updateTeamUseCase;
    public static RemoveTeamUseCase removeTeamUseCase;

    public static void main(String[] args) {

        configureInjection();

/*        RoundServices roundServices = new RoundServices();
        CreateRoundRobin createRoundRobin = new CreateRoundRobin();
        StartRoundRobin startRoundRobin = new StartRoundRobin();
        UpdateRoundRobinMatch updateRoundRobinMatch = new UpdateRoundRobinMatch();
        FinishRoundRobinMatch finishRoundRobinMatch = new FinishRoundRobinMatch();
        FinishRoundRobin finishRoundRobin = new FinishRoundRobin();
        CreateKnockout createKnockout = new CreateKnockout();
        KnockoutServices knockoutServices = new KnockoutServices();
        StartKnockout startKnockout = new StartKnockout();
        UpdateKnockoutMatch updateKnockoutMatch = new UpdateKnockoutMatch();
        FinishKnockoutMatch finishKnockoutMatch = new FinishKnockoutMatch();
        AdvanceKnockout advanceKnockout = new AdvanceKnockout();
        FinishKnockout finishKnockout = new FinishKnockout();*/

        Team team1 = new Team("IFSP", "Marquinho");
        Team team2 = new Team("USP", "Porco");
        Team team3 = new Team("UFSCAR", "123");
        Team team4 = new Team("UNIARA", "Arara");

        createTeamUseCase.insert(team1);
        createTeamUseCase.insert(team2);
        createTeamUseCase.insert(team3);
        createTeamUseCase.insert(team4);

        HelloApplication.main(args);

        /*String pdfFilePath = "C:\\Users\\User\\Downloads\\rodada.pdf";
        roundServices.exportRoundToPDF(1, pdfFilePath, roundRobin.getTable());*/

/*       updateRoundRobinMatch.updateMatchByIds(roundRobin, 1, 6, 1, 3);
       finishRoundRobinMatch.finishMatchByIds(roundRobin,1,6);
       roundRobin.printTable();
       roundRobin.printStandings();*/

    }

    private static void configureInjection() {
        TeamDAO teamDAO = new InMemoryTeamDAO();
        createTeamUseCase = new CreateTeamUseCase(teamDAO);
        updateTeamUseCase = new UpdateTeamUseCase(teamDAO);
        findTeamUseCase = new FindTeamUseCase(teamDAO);
        removeTeamUseCase = new RemoveTeamUseCase(teamDAO);

    }
}
