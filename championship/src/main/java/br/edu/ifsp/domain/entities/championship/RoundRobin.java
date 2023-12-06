package br.edu.ifsp.domain.entities.championship;

import br.edu.ifsp.domain.entities.team.Team;

import java.time.LocalDate;
import java.util.ArrayList;
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
        this.championshipTable = new ChampionshipTable(teams);
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

    private void updateChampionshipTable() {
        List<TeamRoundRobin> teamRoundRobins = teams.stream()
                .map(team -> new TeamRoundRobin(team, team.getWins(), team.getLoses(), team.getDraw()))
                .collect(Collectors.toList());

        teamRoundRobins.sort((team1, team2) -> {
            // Custom comparator for sorting teams in the championship table
            if (!team1.getWins().equals(team2.getWins())) {
                return team2.getWins().compareTo(team1.getWins());
            } else {
                return team2.getGoalDifference().compareTo(team1.getGoalDifference());
            }
        });

        championshipTable.updateTable(teamRoundRobins);
    }


    public Team declareWinner() {
        // Assumes the championship has concluded
        return championshipTable.getTeams().get(0);
    }

    public void updateGoals(Integer goalsFor, Integer goalsAgainst) {
        this.goalsFor += goalsFor;
        this.goalsAgainst += goalsAgainst;
    }

    public Integer calculatePoints() {
        return (getWins() * 3) + getDraw();
    }

    public Integer calculateGoalDifference() {
        return getGoalsFor() - getGoalsAgainst();
    }

    private void updatePunctuation(Match match) {
        int golsTeamA = match.getScoreboard1();
        int golsTeamB = match.getScoreboard2();

        TeamRoundRobin team1 = new TeamRoundRobin(match.getTeam1(), match.getTeam1().getWins(), match.getTeam1().getLoses(), match.getTeam1().getDraw());
        TeamRoundRobin team2 = new TeamRoundRobin(match.getTeam2(), match.getTeam2().getWins(), match.getTeam2().getLoses(), match.getTeam2().getDraw());

        if (golsTeamA > golsTeamB) {
            team1.incrementWins();
            team2.incrementLoses();
        } else if (golsTeamA < golsTeamB) {
            team1.incrementLoses();
            team2.incrementWins();
        } else {
            team1.incrementDraws();
            team2.incrementDraws();
        }

        // Atualizar pontuação e saldo de gols
        team1.setWins(team1.calculateWins());
        team1.setGoalDifference(team1.calculateGoalDifference());

        team2.setWins(team2.calculateWins());
        team2.setGoalDifference(team2.calculateGoalDifference());

        championshipTable.incrementWins(match.getTeam1());
        championshipTable.incrementLoses(match.getTeam2());
        championshipTable.incrementDraws(match.getTeam1());

        // Atualizar pontuação e saldo de gols na tabela do campeonato
        championshipTable.updatePointsAndGoalDifference(match.getTeam1());
        championshipTable.updatePointsAndGoalDifference(match.getTeam2());
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
                    updatePunctuation(match);
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

    private void finishRound(Round round) {
        for (Match match : round.getMatches()) {
            if (match.getConcluded()) {
                TeamRoundRobin team1 = match.getTeam1();
                TeamRoundRobin team2 = match.getTeam2();

                team1.updateGoals(match.getScoreboard1(), match.getScoreboard2());
                team2.updateGoals(match.getScoreboard2(), match.getScoreboard1());
            }
        }
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
