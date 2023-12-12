package br.edu.ifsp.application.repository.inmemory;

import br.edu.ifsp.domain.entities.championship.Phase;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.phase.PhaseDAO;
import br.edu.ifsp.domain.usecases.team.TeamDAO;

import java.util.*;

public class InMemoryPhaseDAO implements PhaseDAO {
    private static final Map<Integer, Phase> db = new LinkedHashMap<>();
    private static int idCounter;

    @Override
    public Integer create(Phase phase) {
        idCounter++;
        phase.setIdPhase(idCounter);
        db.put(idCounter, phase);
        return idCounter;
    }

    @Override
    public Optional<Phase> findOne(Integer key) {
        if(db.containsKey(key))
            return Optional.of(db.get(key));
        return Optional.empty();
    }

    @Override
    public List<Phase> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public boolean update(Phase phase) {
        Integer id = phase.getIdPhase();
        if(db.containsKey(id)) {
            db.replace(id, phase);
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
    public boolean delete(Phase phase) {
        return deleteByKey(phase.getIdPhase());
    }
}
