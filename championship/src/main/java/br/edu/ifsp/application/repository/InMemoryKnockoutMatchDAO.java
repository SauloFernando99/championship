package br.edu.ifsp.application.repository;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.championship.KnockoutMatch;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.knockout.dao.KnockoutDAO;
import br.edu.ifsp.domain.usecases.knockoutmatch.KnockoutMatchDAO;
import br.edu.ifsp.domain.usecases.team.TeamDAO;

import java.util.*;

public class InMemoryKnockoutMatchDAO implements KnockoutMatchDAO {
    private static final Map<Integer, KnockoutMatch> db = new LinkedHashMap<>();
    private static int idCounter;

    @Override
    public Integer create(KnockoutMatch knockoutMatch) {
        idCounter++;
        knockoutMatch.setIdMatch(idCounter);
        db.put(idCounter, knockoutMatch);
        return idCounter;
    }

    @Override
    public Optional<KnockoutMatch> findOne(Integer key) {
        if(db.containsKey(key))
            return Optional.of(db.get(key));
        return Optional.empty();
    }

    @Override
    public List<KnockoutMatch> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public boolean update(KnockoutMatch knockoutMatch) {
        Integer id = knockoutMatch.getIdMatch();
        if(db.containsKey(id)) {
            db.replace(id, knockoutMatch);
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
    public boolean delete(KnockoutMatch knockoutMatch) {
        return deleteByKey(knockoutMatch.getIdMatch());
    }
}
