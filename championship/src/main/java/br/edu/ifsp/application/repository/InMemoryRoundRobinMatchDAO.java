package br.edu.ifsp.application.repository;

import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.entities.championship.RoundRobinMatch;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.roundrobin.dao.RoundRobinDAO;
import br.edu.ifsp.domain.usecases.roundrobinmatch.RoundRobinMatchDAO;
import br.edu.ifsp.domain.usecases.team.TeamDAO;

import java.util.*;

public class InMemoryRoundRobinMatchDAO implements RoundRobinMatchDAO {
    private static final Map<Integer, RoundRobinMatch> db = new LinkedHashMap<>();
    private static int idCounter;

    @Override
    public Integer create(RoundRobinMatch roundRobinMatch) {
        idCounter++;
        roundRobinMatch.setIdMatch(idCounter);
        db.put(idCounter, roundRobinMatch);
        return idCounter;
    }

    @Override
    public Optional<RoundRobinMatch> findOne(Integer key) {
        if(db.containsKey(key))
            return Optional.of(db.get(key));
        return Optional.empty();
    }

    @Override
    public List<RoundRobinMatch> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public boolean update(RoundRobinMatch roundRobinMatch) {
        Integer id = roundRobinMatch.getIdMatch();
        if(db.containsKey(id)) {
            db.replace(id, roundRobinMatch);
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
    public boolean delete(RoundRobinMatch roundRobinMatch) {
        return deleteByKey(roundRobinMatch.getIdMatch());
    }
}
