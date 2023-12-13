package br.edu.ifsp.domain.usecases.dbsupport.teamknockout;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.dbsupport.TeamKnockout;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.utils.DAO;

import java.util.List;
import java.util.Optional;

public interface TeamKnockoutDAO extends DAO <TeamKnockout, Integer> {

    List<TeamKnockout> findAllByKnockout (Integer id);

    List<TeamKnockout> findAllByTeam (Integer id);

    Optional<TeamKnockout> findOne(Integer idTeam, Integer idKnockout);

    boolean removeAllByKnockout(Integer idChampionship);

    boolean removeAllByTeam(Integer idTeam);

}
