package br.edu.ifsp.application.repository;

import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.roundrobin.dao.RoundRobinDAO;
import br.edu.ifsp.domain.usecases.team.TeamDAO;

import java.util.*;

public class InMemoryRoundRobinDAO implements RoundRobinDAO {
    private static final Map<Integer, RoundRobin> db = new LinkedHashMap<>();
    private static int idCounter;

    @Override
    public Integer create(RoundRobin roundRobin) {
        idCounter++;
        roundRobin.setIdChampionship(idCounter);
        db.put(idCounter, roundRobin);
        return idCounter;
    }

    @Override
    public Optional<RoundRobin> findOne(Integer key) {
        if(db.containsKey(key))
            return Optional.of(db.get(key));
        return Optional.empty();
    }

    @Override
    public List<RoundRobin> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public boolean update(RoundRobin roundRobin) {
        Integer id = roundRobin.getIdChampionship();
        if(db.containsKey(id)) {
            db.replace(id, roundRobin);
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
    public boolean delete(RoundRobin roundRobin) {
        return deleteByKey(roundRobin.getIdChampionship());
    }
}
