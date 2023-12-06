package br.edu.ifsp.domain.usecases.match.crud;

import br.edu.ifsp.domain.entities.championship.Match;
import br.edu.ifsp.domain.usecases.utils.DAO;

import java.util.Optional;

public interface MatchDAO extends DAO <Match, Integer> {
    Optional <Match> findById(Integer id);
}
