package br.edu.ifsp.domain.usecases.roundrobinmatch;

import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.entities.championship.RoundRobinMatch;
import br.edu.ifsp.domain.usecases.utils.DAO;

public interface RoundRobinMatchDAO extends DAO <RoundRobinMatch, Integer> {
}
