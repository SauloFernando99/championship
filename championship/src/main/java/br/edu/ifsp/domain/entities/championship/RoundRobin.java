package br.edu.ifsp.domain.entities.championship;

import br.edu.ifsp.domain.entities.team.Team;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RoundRobin extends Championship {
    private List<Team> teams = new ArrayList<>();
    private List<Round> table = new ArrayList<>();
    private List<TeamStats> teamStats = new ArrayList<>();
    private Team champion;

    public RoundRobin(Integer idChampionship, LocalDate initialDate, LocalDate finalDate, String modality,
                      String award, String sponsorship, Boolean concluded, List<Team> teams) {
        super(idChampionship, initialDate, finalDate, modality, award, sponsorship, concluded, teams);
    }

    public void initializeTeamStats(List<Team> teams) {
        for (Team team : teams) {
            TeamStats teamStat = new TeamStats(team);
            teamStats.add(teamStat);
        }
    }

    public void generateTable(List<Team> teams){
        generateFirstLeg(teams);
        generateSecondLeg();
    }

    private void generateFirstLeg(List<Team> teams) {
        this.teams = teams;
        this.teamStats.clear();

        initializeTeamStats(teams);

        int numTeams = teams.size();

        // Número total de confrontos
        int totalMatches = (numTeams * (numTeams - 1)) / 2;

        List<Match> allMatches = new ArrayList<>();

        // Gerar todos os confrontos possíveis
        for (int i = 0; i < numTeams - 1; i++) {
            for (int j = i + 1; j < numTeams; j++) {
                Team team1 = teams.get(i);
                Team team2 = teams.get(j);

                Match match = new Match(team1, team2);
                allMatches.add(match);
            }
        }

        int matchesPerRound = numTeams / 2;

        // Distribuir os confrontos entre as rodadas
        for (int roundNumber = 0; roundNumber < numTeams - 1; roundNumber++) {
            Round round = new Round();

            for (int i = 0; i < matchesPerRound; i++) {
                Match match = findMatchForRound(allMatches, round.getMatches());
                if (match != null) {
                    round.addMatch(match);
                    allMatches.remove(match);
                }
            }

            table.add(round);
        }
    }

    private Match findMatchForRound(List<Match> allMatches, List<Match> currentRoundMatches) {
        for (Match match : allMatches) {
            Team team1 = match.getTeam1();
            Team team2 = match.getTeam2();

            boolean team1AlreadyPlayed = playedInRound(team1, currentRoundMatches);
            boolean team2AlreadyPlayed = playedInRound(team2, currentRoundMatches);

            if (!team1AlreadyPlayed && !team2AlreadyPlayed) {
                return match;
            }
        }
        return null;
    }

    private boolean playedInRound(Team team, List<Match> matches) {
        for (Match match : matches) {
            if (match.getTeam1().equals(team) || match.getTeam2().equals(team)) {
                return true;
            }
        }
        return false;
    }


    private void generateSecondLeg() {
        List<Round> returnRounds = new ArrayList<>();

        for (Round round : table) {
            Round returnRound = new Round();

            for (Match match : round.getMatches()) {
                // Inverte os times para o confronto de volta
                Match returnMatch = new Match(match.getTeam2(), match.getTeam1());
                returnRound.addMatch(returnMatch);
            }

            returnRounds.add(returnRound);
        }

        table.addAll(returnRounds);
    }

    public void printTable() {
        Integer i = 1;
        for (Round round : table) {
            System.out.println("Round: " + round.getIdRound());
            for (Match match : round.getMatches()) {
                System.out.print("ID: " + match.getIdMatch() + "  " + match.getTeam1().getName() +
                        " vs " + match.getTeam2().getName());

                // Adiciona informações adicionais
                if (match.getScoreboard1() != null && match.getScoreboard2() != null) {
                    System.out.print("  |  Score: " + match.getScoreboard1() + " - " + match.getScoreboard2());
                }

                if (match.getConcluded() != null && match.getConcluded()) {
                    System.out.print("  |  Concluded");
                }

                System.out.println();
            }
            System.out.println();
        }
    }

    public void printStandings() {
        // Atualiza os pontos dos times com base nos resultados dos jogos
        updateTeamStats();

        // Ordena a lista de TeamStats com base nos pontos (em ordem decrescente)
        Collections.sort(teamStats, Comparator.comparingInt(TeamStats::getPoints).reversed());

        // Imprime a tabela de classificação
        System.out.println("Tabela de Classificação:");
        System.out.printf("%-15s%-10s%-10s%-10s%-10s%-15s%n", "Time", "Pontos", "Vitórias", "Empates", "Derrotas", "Saldo");

        for (TeamStats teamStat : teamStats) {
            System.out.printf("%-15s%-10d%-10d%-10d%-10d%-15d%n",
                    teamStat.getTeam().getName(),
                    teamStat.getPoints(),
                    teamStat.getWins(),
                    teamStat.getDraws(),
                    teamStat.getLoses(),
                    teamStat.getPointsStandings());
        }
    }

    // Método para atualizar os pontos dos times com base nos resultados dos jogos
    private void updateTeamStats() {
        for (Round round : table) {
            for (Match match : round.getMatches()) {
                if (match.isConcluded()) {
                    TeamStats teamStat1 = findTeamStats(match.getTeam1());
                    TeamStats teamStat2 = findTeamStats(match.getTeam2());

                    teamStat1.updatePointsStandings(match.getScoreboard1(), match.getScoreboard2());
                    teamStat2.updatePointsStandings(match.getScoreboard2(), match.getScoreboard1());

                    if (match.getScoreboard1() > match.getScoreboard2()) {
                        teamStat1.registerWin();
                        teamStat2.registerLoss();
                    } else if (match.getScoreboard2() > match.getScoreboard1()) {
                        teamStat1.registerLoss();
                        teamStat2.registerWin();
                    } else {
                        teamStat1.registerDraw();
                        teamStat2.registerDraw();
                    }

                }
            }
        }
    }

    // Método auxiliar para encontrar TeamStats para um Team
    private TeamStats findTeamStats(Team team) {
        for (TeamStats teamStat : teamStats) {
            if (teamStat.getTeam() == team) {
                return teamStat;
            }
        }
        return null;
    }

    public Match updateMatchByIds(int roundId, int matchId, Integer scoreboard1, Integer scoreBoard2) {
        for (Round round : table) {
            if (round.getIdRound() == roundId) {
                for (Match match : round.getMatches()) {
                    if (match.getIdMatch() == matchId) {
                        match.updateMatch(scoreboard1,scoreBoard2);
                        return match;
                    }
                }
            }
        }
        return null;
    }
    public Match finishMatchByIds(int roundId, int matchId) {
        for (Round round : table) {
            if (round.getIdRound() == roundId) {
                for (Match match : round.getMatches()) {
                    if (match.getIdMatch() == matchId) {
                        match.concludeMatch();

                        if (allMatchesConcluded(round)) {
                            round.concludeRound();
                        }

                        return match;
                    }
                }
            }
        }
        return null;
    }

    private boolean allMatchesConcluded(Round round) {
        for (Match match : round.getMatches()) {
            if (!match.getConcluded()) {
                return false;
            }
        }
        return true;
    }

    public void finishChampionship() {
        boolean allRoundsConcluded = true;
        for (Round round : table) {
            if (!round.getFinished()) {
                allRoundsConcluded = false;
                break;
            }
        }

        if (allRoundsConcluded) {
            this.setConcluded(true);

            // Encontrar e imprimir o campeão (pode ser o time com mais pontos)
            Team champion = findChampion();
            System.out.println("Championship concluded! The champion is: " + champion.getName());
        } else {
            System.out.println("Championship is not concluded. Not all rounds are finished.");
        }
    }

    private Team findChampion() {
        int maxPoints = -1;
        int maxPointsStandings = -1;
        Team champion = null;

        for (TeamStats teamStat : teamStats) {
            if (teamStat.getPoints() > maxPoints ||
                    (teamStat.getPoints() == maxPoints && teamStat.getPointsStandings() > maxPointsStandings)) {
                champion = teamStat.getTeam();
                maxPoints = teamStat.getPoints();
                maxPointsStandings = teamStat.getPointsStandings();
            }
        }

        return champion;
    }

    @Override
    public List<Team> getTeams() {
        return teams;
    }

    @Override
    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public List<Round> getTable() {
        return table;
    }

    public void setTable(List<Round> table) {
        this.table = table;
    }

    public List<TeamStats> getTeamStats() {
        return teamStats;
    }

    public void setTeamStats(List<TeamStats> teamStats) {
        this.teamStats = teamStats;
    }
}
