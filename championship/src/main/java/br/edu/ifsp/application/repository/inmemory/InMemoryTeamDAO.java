package br.edu.ifsp.application.repository.inmemory;

import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.team.TeamDAO;

import java.util.*;

public class InMemoryTeamDAO implements TeamDAO {
    private static final Map<Integer, Team> db = new LinkedHashMap<>();
    private static int idCounter;

    @Override
    public Integer create(Team team) {
        idCounter++;
        team.setIdTeam(idCounter);
        db.put(idCounter, team);
        return idCounter;
    }

    @Override
    public Optional<Team> findOne(Integer key) {
        if(db.containsKey(key))
            return Optional.of(db.get(key));
        return Optional.empty();
    }

    @Override
    public List<Team> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public boolean update(Team team) {
        Integer id = team.getIdTeam();
        if(db.containsKey(id)) {
            db.replace(id, team);
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
    public boolean delete(Team team) {
        return deleteByKey(team.getIdTeam());
    }
}
