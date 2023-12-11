package br.edu.ifsp.domain.usecases.teamstats;

import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.entities.team.TeamStats;
import br.edu.ifsp.domain.usecases.utils.DAO;

public interface TeamStatsDAO extends DAO <TeamStats, Integer> {
}
