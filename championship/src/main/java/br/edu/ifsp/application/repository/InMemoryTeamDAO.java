package br.edu.ifsp.application.repository;

import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.team.TeamDAO;

import java.util.*;

public class InMemoryTeamDAO implements TeamDAO {
    private static final Map<Integer, Team> db = new LinkedHashMap<>();
    private static int idCounter;

    @Override
    public Integer create(Team book) {
        idCounter++;
        book.setIdTeam(idCounter);
        db.put(idCounter, book);
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
    public boolean update(Team book) {
        Integer id = book.getIdTeam();
        if(db.containsKey(id)) {
            db.replace(id, book);
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
    public boolean delete(Team book) {
        return deleteByKey(book.getIdTeam());
    }
}
