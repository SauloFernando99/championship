package br.edu.ifsp.domain.usecases.team;

import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.utils.DAO;

import java.util.Optional;

public interface TeamDAO extends DAO <Team, Integer> {
    Optional <Team> findById(Integer id);
}
