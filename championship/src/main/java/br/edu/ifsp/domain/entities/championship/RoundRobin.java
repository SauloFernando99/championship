package br.edu.ifsp.domain.entities.championship;

import br.edu.ifsp.domain.entities.team.Team;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RoundRobin extends Championship {

    private List<Round> rounds;
    private ChampionshipTable championshipTable;
    private Integer goalsFor;
    private Integer goalsAgainst;


    public RoundRobin(Integer idChampionship, LocalDate initialDate, LocalDate finalDate, String modality, String award, String sponsorship, Boolean concluded, List<Team> teams) {
        super(idChampionship, initialDate, finalDate, modality, award, sponsorship, concluded, teams);
        this.rounds = new ArrayList<>();

        // Converta a lista de Team para TeamRoundRobin
        List<TeamRoundRobin> teamRoundRobins = teams.stream()
                .map(team -> new TeamRoundRobin(team, 0, 0, 0, 0, 0))
                .collect(Collectors.toList());

        this.championshipTable = new ChampionshipTable(teamRoundRobins);
        generateRounds();
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public ChampionshipTable getChampionshipTable() {
        return championshipTable;
    }

    private void generateRounds() {
        int numberOfTeams = getTeams().size();

        if (numberOfTeams % 2 != 0) {
            // Adicione um time de folga se o número de times for ímpar
            getTeams().add(new Team(-1, "Bye", "", false));
            numberOfTeams++;
        }

        List<Team> teamsCopy = new ArrayList<>(getTeams());

        for (int roundNumber = 1; roundNumber < numberOfTeams; roundNumber++) {
            List<Match> matches = new ArrayList<>();

            for (int i = 0; i < numberOfTeams / 2; i++) {
                Team homeTeam = teamsCopy.get(i);
                Team awayTeam = teamsCopy.get(numberOfTeams - 1 - i);

                if (!homeTeam.getName().equals("Bye") && !awayTeam.getName().equals("Bye")) {
                    Match match = new Match(homeTeam, awayTeam);
                    matches.add(match);
                }
            }

            Round round = new Round(roundNumber, LocalDate.now(), matches);
            rounds.add(round);

            // Rotacione os times para a próxima rodada
            teamsCopy.add(1, teamsCopy.remove(teamsCopy.size() - 1));
        }
    }

    private void updatePunctuation(Match match) {
        int goalsTeamA = match.getScoreboard1();
        int goalsTeamB = match.getScoreboard2();

        TeamRoundRobin team1 = getEstatisticas(match.getTeam1());
        TeamRoundRobin team2 = getEstatisticas(match.getTeam2());

        if (goalsTeamA > goalsTeamB) {
            registerWin(match.getTeam1());
            registerLost(match.getTeam2());
        } else if (goalsTeamA < goalsTeamB) {
            registerWin(match.getTeam2());
            registerLost(match.getTeam1());
        } else {
            // Empate
            registerDraw(match.getTeam1());
            registerDraw(match.getTeam2());
        }

        // Atualizar pontuação e saldo de gols
        updateChampionshipTable(championshipTable);
    }



    public void updateChampionshipTable(ChampionshipTable championshipTable) {
        List<TeamRoundRobin> teamRoundRobins = getTeams().stream()
                .map(team -> championshipTable.getEstatisticas(team))
                .collect(Collectors.toList());


        championshipTable.updateTable(teamRoundRobins);

        System.out.println("Updated Table: " + championshipTable.getTeamRoundRobins());
    }

    public void manageRound(int roundNumber, int matchNumber, int homeGoals, int awayGoals) {
        if (roundNumber > 0 && roundNumber <= rounds.size()) {
            Round round = rounds.get(roundNumber - 1);

            if (matchNumber > 0 && matchNumber <= round.getMatches().size()) {
                Match match = round.getMatches().get(matchNumber - 1);

                // Verificar se a partida já foi concluída antes de atualizar a pontuação
                if (!match.getConcluded()) {
                    // Simule a partida com os gols fornecidos
                    match.updateMatch(homeGoals, awayGoals);
                    match.concludeMatch();
                    // Atualiza a tabela antes de finalizar a rodada
                    updateChampionshipTable(championshipTable);
                } else {
                    System.out.println("A partida já foi finalizada.");
                }
            } else {
                System.out.println("Número de partida inválido.");
            }
        } else {
            System.out.println("Número de rodada inválido.");
        }
    }
    public void finishRound(Round round) {
        for (Match match : round.getMatches()) {
            if (match.getConcluded()) {
                TeamRoundRobin team1 = getEstatisticas(match.getTeam1());
                TeamRoundRobin team2 = getEstatisticas(match.getTeam2());

                team1.updateGoals(match.getScoreboard1(), match.getScoreboard2());
                team2.updateGoals(match.getScoreboard2(), match.getScoreboard1());
            }
        }

        // Atualiza a tabela após a conclusão da rodada
        updateChampionshipTable(championshipTable);
    }

    public TeamRoundRobin declareWinner() {
        // Assumes the championship has concluded
        return championshipTable.getTeamRoundRobins().get(0);
    }


    public void updateGoals(Integer goalsFor, Integer goalsAgainst) {
        this.goalsFor += goalsFor;
        this.goalsAgainst += goalsAgainst;

        // Atualiza os gols nas estatísticas das equipes
        for (TeamRoundRobin teamRoundRobin : championshipTable.getTeamRoundRobins()) {
            teamRoundRobin.updateGoals(goalsFor, goalsAgainst);
        }
    }


    public Integer calculateGoalDifference() {
        return getGoalsFor() - getGoalsAgainst();
    }

    public Integer getGoalsFor() {
        return goalsFor;
    }

    public void setGoalsFor(Integer goalsFor) {
        this.goalsFor = goalsFor;
    }

    public Integer getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(Integer goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }
}
