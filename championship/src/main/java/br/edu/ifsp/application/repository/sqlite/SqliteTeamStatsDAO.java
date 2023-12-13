package br.edu.ifsp.application.repository.sqlite;

import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.entities.team.TeamStats;
import br.edu.ifsp.domain.usecases.teamstats.TeamStatsDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.edu.ifsp.application.main.Main.findRoundRobinUseCase;
import static br.edu.ifsp.application.main.Main.findTeamUseCase;

public class SqliteTeamStatsDAO implements TeamStatsDAO {

    @Override
    public Integer create(TeamStats teamStats) {
        String sql = "INSERT INTO TeamStats(team, wins, losses, draws, points, pointsStandings, roundRobin) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, teamStats.getTeam().getIdTeam());
            stmt.setInt(2, teamStats.getWins());
            stmt.setInt(3, teamStats.getLosses());
            stmt.setInt(4, teamStats.getDraws());
            stmt.setInt(5, teamStats.getPoints());
            stmt.setInt(6, teamStats.getPointsStandings());
            stmt.setInt(7, teamStats.getRoundRobin().getIdChampionship());
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
    public Optional<TeamStats> findOne(Integer key) {
        String sql = "SELECT * FROM TeamStats WHERE idTeamStats = ?";
        TeamStats teamStats = null;

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, key);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                teamStats = resultSetToEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(teamStats);
    }

    private TeamStats resultSetToEntity(ResultSet rs) throws SQLException {
        int teamStatsId = rs.getInt("idTeamStats");
        int wins = rs.getInt("wins");
        int losses = rs.getInt("losses");
        int draws = rs.getInt("draws");
        int points = rs.getInt("points");
        int pointsStandings = rs.getInt("pointsStandings");
        int teamId = rs.getInt("team");
        int roundRobinId = rs.getInt("roundRobin");

        Team team = findTeamUseCase.findOne(teamId).get();
        RoundRobin roundRobin = findRoundRobinUseCase.findOne(roundRobinId).get();

        return new TeamStats(teamStatsId, team, wins, losses, draws, points, pointsStandings, roundRobin);
    }

    @Override
    public List<TeamStats> findAll() {
        String sql = "SELECT * FROM TeamStats";
        List<TeamStats> teamStatsList = new ArrayList<>();

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TeamStats teamStats = resultSetToEntity(rs);
                teamStatsList.add(teamStats);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teamStatsList;
    }

    @Override
    public boolean update(TeamStats teamStats) {
        String sql = "UPDATE TeamStats SET team = ?, wins = ?, losses = ?, draws = ?, " +
                "points = ?, pointsStandings = ?, roundRobin = ? WHERE idTeamStats = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, teamStats.getTeam().getIdTeam());
            stmt.setInt(2, teamStats.getWins());
            stmt.setInt(3, teamStats.getLosses());
            stmt.setInt(4, teamStats.getDraws());
            stmt.setInt(5, teamStats.getPoints());
            stmt.setInt(6, teamStats.getPointsStandings());
            stmt.setInt(7, teamStats.getRoundRobin().getIdChampionship());
            stmt.setInt(8, teamStats.getIdTeamStats());
            stmt.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteByKey(Integer key) {
        String sql = "DELETE FROM TeamStats WHERE idTeamStats = ?";
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
    public boolean delete(TeamStats teamStats) {
        if (teamStats == null || teamStats.getIdTeamStats() == null)
            throw new IllegalArgumentException("TeamStats and TeamStats ID must not be null.");
        return deleteByKey(teamStats.getIdTeamStats());
    }
}
