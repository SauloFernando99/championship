package br.edu.ifsp.application.repository.sqlite;

import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.entities.dbsupport.TeamRoundRobin;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.dbsupport.teamroundrobin.TeamRoundRobinDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.edu.ifsp.application.main.Main.findRoundRobinUseCase;
import static br.edu.ifsp.application.main.Main.findTeamUseCase;

public class SqliteTeamRoundRobinDAO implements TeamRoundRobinDAO {

    @Override
    public List<TeamRoundRobin> findAllByRoundRobin(Integer id) {
        String sql = "SELECT * FROM TeamKnockout WHERE idChampionship = ?";
        List<TeamRoundRobin> teamRoundRobins = new ArrayList<>();

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TeamRoundRobin teamRoundRobin = resultSetToEntity(rs);
                teamRoundRobins.add(teamRoundRobin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teamRoundRobins;
    }

    @Override
    public List<TeamRoundRobin> findAllByTeam(Integer id) {
        String sql = "SELECT * FROM TeamRoundRobin WHERE idTeam = ?";
        List<TeamRoundRobin> teamRoundRobins = new ArrayList<>();

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TeamRoundRobin teamRoundRobin = resultSetToEntity(rs);
                teamRoundRobins.add(teamRoundRobin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teamRoundRobins;
    }

    @Override
    public Integer create(TeamRoundRobin teamRoundRobin) {
        String sql = "INSERT INTO TeamRoundRobin(idTeam, idChampionship) VALUES (?, ?)";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, teamRoundRobin.getTeam().getIdTeam());
            stmt.setInt(2, teamRoundRobin.getRoundRobin().getIdChampionship());
            stmt.execute();

            ResultSet resultSet = stmt.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<TeamRoundRobin> findOne(Integer key) {
        return Optional.empty();
    }

    @Override
    public List<TeamRoundRobin> findAll() {
        return null;
    }

    @Override
    public boolean update(TeamRoundRobin type) {
        return false;
    }

    @Override
    public boolean deleteByKey(Integer key) {
        return false;
    }

    @Override
    public boolean delete(TeamRoundRobin teamRoundRobin) {
        if (
                teamRoundRobin == null ||
                        teamRoundRobin.getTeam().getIdTeam() == null ||
                        teamRoundRobin.getRoundRobin().getIdChampionship() == null
        )
            throw new IllegalArgumentException("TeamRoundRobin, team ID, and championship ID must not be null.");

        String sql = "DELETE FROM TeamRoundRobin WHERE idTeam = ? AND idChampionship = ?";
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, teamRoundRobin.getTeam().getIdTeam());
            stmt.setInt(2, teamRoundRobin.getRoundRobin().getIdChampionship());
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private TeamRoundRobin resultSetToEntity(ResultSet rs) throws SQLException {
        int teamId = rs.getInt("idTeam");
        int knockoutId = rs.getInt("idChampionship");

        Team team = findTeamUseCase.findOne(teamId).get();
        RoundRobin roundRobin = findRoundRobinUseCase.findOne(knockoutId).get();

        return new TeamRoundRobin(
                team,
                roundRobin
        );
    }

    @Override
    public boolean removeAllByRoundRobin(Integer idChampionship) {
        String sql = "DELETE FROM TeamRoundRobin WHERE idChampionship = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, idChampionship);
            stmt.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeAllByTeam(Integer idTeam) {
        String sql = "DELETE FROM TeamRoundRobin WHERE idTeam = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, idTeam);
            stmt.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
