package br.edu.ifsp.domain.services;

import br.edu.ifsp.domain.entities.team.TeamStats;

public class TeamStatsService {
    public void updatePointsStandings(TeamStats teamStats, Integer pointsScored, Integer pointsConceded) {
        teamStats.updatePointsStandings(pointsScored, pointsConceded);
    }
}
