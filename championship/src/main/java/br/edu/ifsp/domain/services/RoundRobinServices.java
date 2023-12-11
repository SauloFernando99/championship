package br.edu.ifsp.domain.services;

import br.edu.ifsp.domain.entities.championship.Match;
import br.edu.ifsp.domain.entities.championship.Round;
import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.entities.championship.RoundRobinMatch;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.entities.team.TeamStats;

import java.util.ArrayList;
import java.util.List;

public class RoundRobinServices {

    public void initializeTeamStats(RoundRobin roundRobin, List<Team> teams) {
        List<TeamStats> teamStats = new ArrayList<>();
        for (Team team : teams) {
            TeamStats teamStat = new TeamStats(team);
            teamStats.add(teamStat);
        }
        roundRobin.setTeamStats(teamStats);
    }

    public void generateTable(RoundRobin roundRobin, List<Team> teams) {
        generateFirstLeg(roundRobin, teams);
        generateSecondLeg(roundRobin);
    }

    public void generateFirstLeg(RoundRobin roundRobin, List<Team> teams) {
        roundRobin.setTeams(teams);
        roundRobin.setTeamStats(new ArrayList<>());

        initializeTeamStats(roundRobin, teams);

        int numTeams = teams.size();
        List<RoundRobinMatch> allMatches = generateAllMatches(teams);

        int matchesPerRound = numTeams / 2;

        for (int roundNumber = 0; roundNumber < numTeams - 1; roundNumber++) {
            Round round = new Round(roundRobin);

            for (int i = 0; i < matchesPerRound; i++) {
                RoundRobinMatch match = findMatchForRound(allMatches, round.getMatches());
                if (match != null) {
                    round.addMatch(match);
                    allMatches.remove(match);
                }
            }

            roundRobin.getTable().add(round);
        }
    }

    public RoundRobinMatch findMatchForRound(List<RoundRobinMatch> allMatches, List<RoundRobinMatch> currentRoundMatches) {
        for (RoundRobinMatch match : allMatches) {
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

    public boolean playedInRound(Team team, List<RoundRobinMatch> matches) {
        for (RoundRobinMatch match : matches) {
            if (match.getTeam1().equals(team) || match.getTeam2().equals(team)) {
                return true;
            }
        }
        return false;
    }

    public List<RoundRobinMatch> generateAllMatches(List<Team> teams) {
        List<RoundRobinMatch> allMatches = new ArrayList<>();

        int numTeams = teams.size();
        for (int i = 0; i < numTeams - 1; i++) {
            for (int j = i + 1; j < numTeams; j++) {
                Team team1 = teams.get(i);
                Team team2 = teams.get(j);

                RoundRobinMatch match = new RoundRobinMatch(team1, team2);
                allMatches.add(match);
            }
        }

        return allMatches;
    }

    public void generateSecondLeg(RoundRobin roundRobin) {
        List<Round> returnRounds = new ArrayList<>();

        for (Round round : roundRobin.getTable()) {
            Round returnRound = new Round(roundRobin);

            for (Match match : round.getMatches()) {
                RoundRobinMatch returnMatch = new RoundRobinMatch(match.getTeam2(), match.getTeam1(), round);
                returnRound.addMatch(returnMatch);
            }

            returnRounds.add(returnRound);
        }

        roundRobin.getTable().addAll(returnRounds);
    }

    public void updateTeamStats(RoundRobin roundRobin) {
        TeamStatsServices teamStatsService = new TeamStatsServices();
        MatchServices matchServices = new MatchServices();

        for (Round round : roundRobin.getTable()) {
            for (Match match : round.getMatches()) {
                if (matchServices.isMatchConcluded(match)) {
                    TeamStats teamStat1 = findTeamStats(roundRobin.getTeamStats(), match.getTeam1());
                    TeamStats teamStat2 = findTeamStats(roundRobin.getTeamStats(), match.getTeam2());

                    teamStatsService.updatePointsStandings(teamStat1, match.getScoreboard1(), match.getScoreboard2());
                    teamStatsService.updatePointsStandings(teamStat2, match.getScoreboard2(), match.getScoreboard1());

                    updateTeamStatsBasedOnResult(teamStat1, teamStat2, match.getScoreboard1(), match.getScoreboard2());
                }
            }
        }
    }

    private void updateTeamStatsBasedOnResult(TeamStats teamStat1, TeamStats teamStat2, int score1, int score2) {
        if (score1 > score2) {
            teamStat1.registerWin();
            teamStat2.registerLoss();
        } else if (score2 > score1) {
            teamStat1.registerLoss();
            teamStat2.registerWin();
        } else {
            teamStat1.registerDraw();
            teamStat2.registerDraw();
        }
    }

    private TeamStats findTeamStats(List<TeamStats> teamStats, Team team) {
        for (TeamStats teamStat : teamStats) {
            if (teamStat.getTeam() == team) {
                return teamStat;
            }
        }
        return null;
    }

    public Team findChampion(List<TeamStats> teamStats) {
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

}
