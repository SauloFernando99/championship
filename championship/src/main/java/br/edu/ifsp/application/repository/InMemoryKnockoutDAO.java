package br.edu.ifsp.application.repository;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.knockout.dao.KnockoutDAO;
import br.edu.ifsp.domain.usecases.team.TeamDAO;

import java.util.*;

public class InMemoryKnockoutDAO implements KnockoutDAO {
    private static final Map<Integer, Knockout> db = new LinkedHashMap<>();
    private static int idCounter;

    @Override
    public Integer create(Knockout knockout) {
        idCounter++;
        knockout.setIdChampionship(idCounter);
        db.put(idCounter, knockout);
        return idCounter;
    }

    @Override
    public Optional<Knockout> findOne(Integer key) {
        if(db.containsKey(key))
            return Optional.of(db.get(key));
        return Optional.empty();
    }

    @Override
    public List<Knockout> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public boolean update(Knockout knockout) {
        Integer id = knockout.getIdChampionship();
        if(db.containsKey(id)) {
            db.replace(id, knockout);
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
    public boolean delete(Knockout knockout) {
        return deleteByKey(knockout.getIdChampionship());
    }
}
