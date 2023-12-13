package br.edu.ifsp.application.repository.sqlite;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.dbsupport.TeamKnockout;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.dbsupport.teamknockout.TeamKnockoutDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.edu.ifsp.application.main.Main.findKnockoutUseCase;
import static br.edu.ifsp.application.main.Main.findTeamUseCase;

public class SqliteTeamKnockoutDAO implements TeamKnockoutDAO {

    @Override
    public List<TeamKnockout> findAllByKnockout(Integer id) {
        String sql = "SELECT * FROM TeamKnockout WHERE idChampionship = ?";
        List<TeamKnockout> teamKnockouts = new ArrayList<>();

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TeamKnockout teamKnockout = resultSetToEntity(rs);
                teamKnockouts.add(teamKnockout);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teamKnockouts;
    }

    @Override
    public List<TeamKnockout> findAllByTeam(Integer id) {
        String sql = "SELECT * FROM TeamKnockout WHERE idTeam = ?";
        List<TeamKnockout> teamKnockouts = new ArrayList<>();

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TeamKnockout teamKnockout = resultSetToEntity(rs);
                teamKnockouts.add(teamKnockout);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teamKnockouts;
    }

    @Override
    public Optional<TeamKnockout> findOne(Integer idTeam, Integer idKnockout) {
        if (idTeam == null || idKnockout == null) {
            throw new IllegalArgumentException("IDs cannot be null.");
        }

        String sql = "SELECT * FROM TeamKnockout WHERE idTeam = ? AND idChampionship = ?";
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, idTeam);
            stmt.setInt(2, idKnockout);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                TeamKnockout teamKnockout = resultSetToEntity(rs);
                return Optional.of(teamKnockout);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Integer create(TeamKnockout teamKnockout) {
        String sql = "INSERT INTO TeamKnockout(idTeam, idChampionship) VALUES (?, ?)";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, teamKnockout.getTeam().getIdTeam());
            stmt.setInt(2, teamKnockout.getKnockout().getIdChampionship());
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
    public Optional<TeamKnockout> findOne(Integer key) {
        return Optional.empty();
    }

    @Override
    public List<TeamKnockout> findAll() {
        String sql = "SELECT * FROM TeamKnockout";
        List<TeamKnockout> teamKnockouts = new ArrayList<>();

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TeamKnockout teamKnockout = resultSetToEntity(rs);
                teamKnockouts.add(teamKnockout);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teamKnockouts;
    }

    @Override
    public boolean update(TeamKnockout type) {
        return false;
    }

    @Override
    public boolean deleteByKey(Integer key) {
        return false;
    }

    @Override
    public boolean delete(TeamKnockout teamKnockout) {
        if (
                teamKnockout == null ||
                teamKnockout.getTeam().getIdTeam() == null ||
                teamKnockout.getKnockout().getIdChampionship() == null
        )
            throw new IllegalArgumentException("TeamKnockout, team ID, and championship ID must not be null.");

        String sql = "DELETE FROM TeamKnockout WHERE idTeam = ? AND idChampionship = ?";
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, teamKnockout.getTeam().getIdTeam());
            stmt.setInt(2, teamKnockout.getKnockout().getIdChampionship());
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private TeamKnockout resultSetToEntity(ResultSet rs) throws SQLException {
        int teamId = rs.getInt("idTeam");
        int knockoutId = rs.getInt("idChampionship");

        Team team = findTeamUseCase.findOne(teamId).get();
        Knockout knockout = findKnockoutUseCase.findOne(knockoutId).get();

        return new TeamKnockout(
                team,
                knockout
        );
    }

    @Override
    public boolean removeAllByKnockout(Integer idChampionship) {
        String sql = "DELETE FROM TeamKnockout WHERE idChampionship = ?";

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
        String sql = "DELETE FROM TeamKnockout WHERE idTeam = ?";

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
