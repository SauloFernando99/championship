package br.edu.ifsp.application.repository;

import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.entities.team.TeamStats;
import br.edu.ifsp.domain.usecases.team.TeamDAO;
import br.edu.ifsp.domain.usecases.teamstats.TeamStatsDAO;

import java.util.*;

public class InMemoryTeamStatsDAO implements TeamStatsDAO {
    private static final Map<Integer, TeamStats> db = new LinkedHashMap<>();
    private static int idCounter;

    @Override
    public Integer create(TeamStats teamStats) {
        idCounter++;
        teamStats.setIdTeamStats(idCounter);
        db.put(idCounter, teamStats);
        return idCounter;
    }

    @Override
    public Optional<TeamStats> findOne(Integer key) {
        if(db.containsKey(key))
            return Optional.of(db.get(key));
        return Optional.empty();
    }

    @Override
    public List<TeamStats> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public boolean update(TeamStats teamStats) {
        Integer id = teamStats.getIdTeamStats();
        if(db.containsKey(id)) {
            db.replace(id, teamStats);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteByKey(Integer key) {
        if(db.containsKey(key)){
            db.remove(key);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(TeamStats teamStats) {
        return deleteByKey(teamStats.getIdTeamStats());
    }
}
