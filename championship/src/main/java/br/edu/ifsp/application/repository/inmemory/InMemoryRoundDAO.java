package br.edu.ifsp.application.repository.inmemory;

import br.edu.ifsp.domain.entities.championship.Phase;
import br.edu.ifsp.domain.entities.championship.Round;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.phase.PhaseDAO;
import br.edu.ifsp.domain.usecases.round.RoundDAO;
import br.edu.ifsp.domain.usecases.team.TeamDAO;

import java.util.*;

public class InMemoryRoundDAO implements RoundDAO {
    private static final Map<Integer, Round> db = new LinkedHashMap<>();
    private static int idCounter;

    @Override
    public Integer create(Round round) {
        idCounter++;
        round.setIdRound(idCounter);
        db.put(idCounter, round);
        return idCounter;
    }

    @Override
    public Optional<Round> findOne(Integer key) {
        if(db.containsKey(key))
            return Optional.of(db.get(key));
        return Optional.empty();
    }

    @Override
    public List<Round> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public boolean update(Round round) {
        Integer id = round.getIdRound();
        if(db.containsKey(id)) {
            db.replace(id, round);
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
    public boolean delete(Round round) {
        return deleteByKey(round.getIdRound());
    }
}
