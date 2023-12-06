package br.edu.ifsp.domain.usecases.knockout.crud;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.usecases.utils.DAO;

import java.util.Optional;

public interface KnockoutDAO extends DAO <Knockout, Integer> {
    Optional <Knockout> findById(Integer id);
}
