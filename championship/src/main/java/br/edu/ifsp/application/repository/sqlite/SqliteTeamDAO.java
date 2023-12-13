package br.edu.ifsp.application.repository.sqlite;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.dbsupport.TeamKnockout;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.team.TeamDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.edu.ifsp.application.main.Main.findTeamKnockoutUseCase;

public class SqliteTeamDAO implements TeamDAO {

    @Override
    public Integer create(Team team) {
        String sql = "INSERT INTO Team(name, crest) VALUES (?, ?)";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, team.getName());
            stmt.setString(2, team.getCrest());
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
    public Optional<Team> findOne(Integer key) {
        String sql = "SELECT * FROM Team WHERE idTeam = ?";
        Team team = null;

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, key);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                team = resultSetToEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(team);
    }

    private Team resultSetToEntity(ResultSet rs) throws SQLException {

        int teamId = rs.getInt("idTeam");

        return new Team(
            rs.getInt("idTeam"),
            rs.getString("name"),
            rs.getString("crest"),
            rs.getBoolean("isActive")
        );
    }

    @Override
    public List<Team> findAll() {
        String sql = "SELECT * FROM Team";
        List<Team> teams = new ArrayList<>();

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Team team = resultSetToEntity(rs);
                teams.add(team);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teams;
    }

    @Override
    public boolean update(Team team) {
        String sql = "UPDATE Team SET name = ?, crest = ?, isActive = ? WHERE idTeam = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, team.getName());
            stmt.setString(2, team.getCrest());
            stmt.setBoolean(3, team.getIsActive());
            stmt.setInt(4, team.getIdTeam());
            stmt.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteByKey(Integer key) {
        String sql = "DELETE FROM Team WHERE idTeam = ?";
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, key);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Team team) {
        if (team == null || team.getIdTeam() == null)
            throw new IllegalArgumentException("Team and team ID must not be null.");
        return deleteByKey(team.getIdTeam());
    }
}
