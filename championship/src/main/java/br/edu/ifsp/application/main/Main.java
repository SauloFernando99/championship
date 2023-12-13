package br.edu.ifsp.application.main;

import br.edu.ifsp.HelloApplication;
import br.edu.ifsp.application.repository.sqlite.*;
import br.edu.ifsp.domain.usecases.dbsupport.teamknockout.CreateTeamKnockoutUseCase;
import br.edu.ifsp.domain.usecases.dbsupport.teamknockout.FindTeamKnockoutUseCase;
import br.edu.ifsp.domain.usecases.dbsupport.teamknockout.RemoveTeamKnockoutUseCase;
import br.edu.ifsp.domain.usecases.dbsupport.teamknockout.TeamKnockoutDAO;
import br.edu.ifsp.domain.usecases.dbsupport.teamroundrobin.CreateTeamRoundRobinUseCase;
import br.edu.ifsp.domain.usecases.dbsupport.teamroundrobin.FindTeamRoundRobinUseCase;
import br.edu.ifsp.domain.usecases.dbsupport.teamroundrobin.RemoveTeamRoundRobinUseCase;
import br.edu.ifsp.domain.usecases.dbsupport.teamroundrobin.TeamRoundRobinDAO;
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

    public static CreateTeamKnockoutUseCase createTeamKnockoutUseCase;
    public static FindTeamKnockoutUseCase findTeamKnockoutUseCase;
    public static RemoveTeamKnockoutUseCase removeTeamKnockoutUseCase;

    public static CreateTeamRoundRobinUseCase createTeamRoundRobinUseCase;
    public static FindTeamRoundRobinUseCase findTeamRoundRobinUseCase;
    public static RemoveTeamRoundRobinUseCase removeTeamRoundRobinUseCase;


    public static void main(String[] args) {

        configureInjection();
        setupDataBase();

        /*Team team1 = new Team("IFSP", "Marquinho");
        Team team2 = new Team("USP", "Porco");
        Team team3 = new Team("UFSCAR", "123");
        Team team4 = new Team("UNIARA", "Arara");

        createTeamUseCase.insert(team1);
        createTeamUseCase.insert(team2);
        createTeamUseCase.insert(team3);
        createTeamUseCase.insert(team4);

        List<Team> teams = new ArrayList<>();

        teams.add(team1);
        teams.add(team2);
        teams.add(team3);
        teams.add(team4);

        Knockout knockout = new Knockout(
                "Teste Knockout", LocalDate.now(), LocalDate.now().plusDays(7),
                "LoL", "100k", "Riot", teams

        );

        team1.addKnockout(knockout);
        team2.addKnockout(knockout);
        team3.addKnockout(knockout);
        team4.addKnockout(knockout);

        createKnockoutUseCase.insert(knockout);

        updateTeamUseCase.update(team1);
        updateTeamUseCase.update(team2);
        updateTeamUseCase.update(team3);
        updateTeamUseCase.update(team4);

        StartKnockoutUseCase startKnockoutUseCase = new StartKnockoutUseCase();
        startKnockoutUseCase.StartKnockout(1);

//        UpdateKnockoutMatch updateKnockoutMatch = new UpdateKnockoutMatch();
//        updateKnockoutMatch.updateMatchResultByIds(1,1,2);
//        updateKnockoutMatch.updateMatchResultByIds(2,1,2);
//
//        System.out.println("\nID knockout: " + knockout.getIdChampionship());
//
//        FinishKnockoutMatch finishKnockoutMatch = new FinishKnockoutMatch();
//        finishKnockoutMatch.setMatchConcludedByIds(1);
//        finishKnockoutMatch.setMatchConcludedByIds(2);
//
//        knockout.printSeedingKnockout();
//
//        AdvanceKnockout advanceKnockout = new AdvanceKnockout();
//        advanceKnockout.advancePhase(1);
//
//        updateKnockoutMatch.updateMatchResultByIds(3, 2, 3);
//        finishKnockoutMatch.setMatchConcludedByIds(3);
//
//        FinishKnockout finishKnockout = new FinishKnockout();
//        finishKnockout.finishKnockout(1);
//
//        knockout.printSeedingKnockout();

        RoundRobin roundRobin = new RoundRobin("TesteRoundRobin", LocalDate.now(),
                LocalDate.now().plusDays(7), "LOL", "100K dol",
                "Riot", teams);

        team1.addRoundRobin(roundRobin);
        team2.addRoundRobin(roundRobin);
        team3.addRoundRobin(roundRobin);
        team4.addRoundRobin(roundRobin);

//        System.out.println(team1.getKnockouts().get(0).getName());
//        System.out.println(team1.getRoundRobins().get(0).getName());

        createRoundRobinUseCase.insert(roundRobin);

        updateTeamUseCase.update(team1);
        updateTeamUseCase.update(team2);
        updateTeamUseCase.update(team3);
        updateTeamUseCase.update(team4);

        roundRobin.printStandings();

        StartRoundRobin startRoundRobin = new StartRoundRobin();
        startRoundRobin.startRoundRobin(1);

        roundRobin.printStandings();
        roundRobin.printTable();

        UpdateRoundRobinMatch updateRoundRobinMatch = new UpdateRoundRobinMatch();
        FinishRoundRobinMatch finishRoundRobinMatch = new FinishRoundRobinMatch();

        updateRoundRobinMatch.updateMatchByIds(1,1,2);
        finishRoundRobinMatch.finishMatchByIds(1);
        updateRoundRobinMatch.updateMatchByIds(2,1,3);
        finishRoundRobinMatch.finishMatchByIds(2);
        updateRoundRobinMatch.updateMatchByIds(3,2,1);
        finishRoundRobinMatch.finishMatchByIds(3);
        updateRoundRobinMatch.updateMatchByIds(4,4,2);
        finishRoundRobinMatch.finishMatchByIds(4);
        updateRoundRobinMatch.updateMatchByIds(5,1,3);
        finishRoundRobinMatch.finishMatchByIds(5);
        updateRoundRobinMatch.updateMatchByIds(6,4,2);
        finishRoundRobinMatch.finishMatchByIds(6);
        updateRoundRobinMatch.updateMatchByIds(7,1,5);
        finishRoundRobinMatch.finishMatchByIds(7);
        updateRoundRobinMatch.updateMatchByIds(8,2,1);
        finishRoundRobinMatch.finishMatchByIds(8);
        updateRoundRobinMatch.updateMatchByIds(9,3,1);
        finishRoundRobinMatch.finishMatchByIds(9);
        updateRoundRobinMatch.updateMatchByIds(10,3,4);
        finishRoundRobinMatch.finishMatchByIds(10);
        updateRoundRobinMatch.updateMatchByIds(11,1,2);
        finishRoundRobinMatch.finishMatchByIds(11);
        updateRoundRobinMatch.updateMatchByIds(12,0,0);
        finishRoundRobinMatch.finishMatchByIds(12);

        roundRobin.printTable();
        roundRobin.printStandings();

        FinishRoundRobin finishRoundRobin = new FinishRoundRobin();
        finishRoundRobin.finishChampionship(1);
*/

       /* Knockout knockout = findKnockoutUseCase.findOne(1).get();

        Team team = findTeamUseCase.findOne(1).get();

        TeamKnockout teamKnockout = new TeamKnockout(team, knockout);

        TeamKnockout teamKnockout2 = findTeamKnockoutUseCase.findOne(1, 1).get();

        System.out.println(teamKnockout2.getTeam().getName() + " , " + teamKnockout2.getKnockout().getName());
*/

        HelloApplication.main(args);
    }

    private static void setupDataBase(){
        DataBaseBuilder dbBuilder = new DataBaseBuilder();
        dbBuilder.buildDatabaseIfMissing();
    }

    private static void configureInjection() {
        TeamDAO teamDAO = new SqliteTeamDAO();
        createTeamUseCase = new CreateTeamUseCase(teamDAO);
        updateTeamUseCase = new UpdateTeamUseCase(teamDAO);
        findTeamUseCase = new FindTeamUseCase(teamDAO);
        removeTeamUseCase = new RemoveTeamUseCase(teamDAO);

        KnockoutDAO knockoutDAO = new SqliteKnockoutDAO();
        createKnockoutUseCase = new CreateKnockoutUseCase(knockoutDAO);
        updateKnockoutUseCase = new UpdateKnockoutUseCase(knockoutDAO);
        findKnockoutUseCase = new FindKnockoutUseCase(knockoutDAO);
        removeKnockoutUseCase = new RemoveKnockoutUseCase(knockoutDAO);

        RoundRobinDAO roundRobinDAO = new SqliteRoundRobinDAO();
        createRoundRobinUseCase = new CreateRoundRobinUseCase(roundRobinDAO);
        updateRoundRobinUseCase = new UpdateRoundRobinUseCase(roundRobinDAO);
        findRoundRobinUseCase = new FindRoundRobinUseCase(roundRobinDAO);
        removeRoundRobinUseCase = new RemoveRoundRobinUseCase(roundRobinDAO);

        PhaseDAO phaseDAO = new SqlitePhaseDAO();
        createPhaseUseCase = new CreatePhaseUseCase(phaseDAO);
        updatePhaseUseCase = new UpdatePhaseUseCase(phaseDAO);
        findPhaseUseCase = new FindPhaseUseCase(phaseDAO);
        removePhaseUseCase = new RemovePhaseUseCase(phaseDAO);

        RoundDAO roundDAO = new SqliteRoundDAO();
        createRoundUseCase = new CreateRoundUseCase(roundDAO);
        updateRoundUseCase = new UpdateRoundUseCase(roundDAO);
        findRoundUseCase = new FindRoundUseCase(roundDAO);
        removeRoundUseCase = new RemoveRoundUseCase(roundDAO);

        TeamStatsDAO teamStatsDAO = new SqliteTeamStatsDAO();
        createTeamStatsUseCase = new CreateTeamStatsUseCase(teamStatsDAO);
        updateTeamStatsUseCase = new UpdateTeamStatsUseCase(teamStatsDAO);
        findTeamStatsUseCase = new FindTeamStatsUseCase(teamStatsDAO);
        removeTeamStatsUseCase = new RemoveTeamStatsUseCase(teamStatsDAO);

        RoundRobinMatchDAO roundRobinMatchDAO = new SqliteRoundRobinMatchDAO();
        createRoundRobinMatchUseCase = new CreateRoundRobinMatchUseCase(roundRobinMatchDAO);
        updateRoundRobinMatchUseCase = new UpdateRoundRobinMatchUseCase(roundRobinMatchDAO);
        findRoundRobinMatchUseCase = new FindRoundRobinMatchUseCase(roundRobinMatchDAO);
        removeRoundRobinMatchUseCase = new RemoveRoundRobinMatchUseCase(roundRobinMatchDAO);

        KnockoutMatchDAO knockoutMatchDAO = new SqliteKnockoutMatchDAO();
        createKnockoutMatchUseCase = new CreateKnockoutMatchUseCase(knockoutMatchDAO);
        updateKnockoutMatchUseCase = new UpdateKnockoutMatchUseCase(knockoutMatchDAO);
        findKnockoutMatchUseCase = new FindKnockoutMatchUseCase(knockoutMatchDAO);
        removeKnockoutMatchUseCase = new RemoveKnockoutMatchUseCase(knockoutMatchDAO);

        TeamKnockoutDAO teamKnockoutDAO = new SqliteTeamKnockoutDAO();
        createTeamKnockoutUseCase = new CreateTeamKnockoutUseCase(teamKnockoutDAO);
        findTeamKnockoutUseCase = new FindTeamKnockoutUseCase(teamKnockoutDAO);
        removeTeamKnockoutUseCase = new RemoveTeamKnockoutUseCase(teamKnockoutDAO);

        TeamRoundRobinDAO teamRoundRobinDAO = new SqliteTeamRoundRobinDAO();
        createTeamRoundRobinUseCase = new CreateTeamRoundRobinUseCase(teamRoundRobinDAO);
        findTeamRoundRobinUseCase = new FindTeamRoundRobinUseCase(teamRoundRobinDAO);
        removeTeamRoundRobinUseCase = new RemoveTeamRoundRobinUseCase(teamRoundRobinDAO);
    }
}
