package br.edu.ifsp.domain.usecases.dbsupport.teamroundrobin;

import br.edu.ifsp.domain.entities.dbsupport.TeamKnockout;
import br.edu.ifsp.domain.entities.dbsupport.TeamRoundRobin;
import br.edu.ifsp.domain.usecases.utils.DAO;

import java.util.List;

public interface TeamRoundRobinDAO extends DAO <TeamRoundRobin, Integer> {

    List<TeamRoundRobin> findAllByRoundRobin (Integer id);

    List<TeamRoundRobin> findAllByTeam (Integer id);

    boolean removeAllByRoundRobin(Integer idChampionship);

    boolean removeAllByTeam(Integer idTeam);

}
