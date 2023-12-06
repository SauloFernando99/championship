package br.edu.ifsp.domain.usecases.roundrobin.crud;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.usecases.utils.DAO;

import java.util.Optional;

public interface RoundRobinDAO extends DAO <RoundRobin, Integer> {
    Optional <RoundRobin> findById(Integer id);
}
