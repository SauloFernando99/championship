package br.edu.ifsp.application.repository.sqlite;

import br.edu.ifsp.domain.entities.championship.RoundRobinMatch;
import br.edu.ifsp.domain.entities.championship.Round;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.roundrobinmatch.RoundRobinMatchDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.edu.ifsp.application.main.Main.findRoundUseCase;
import static br.edu.ifsp.application.main.Main.findTeamUseCase;

public class SqliteRoundRobinMatchDAO implements RoundRobinMatchDAO {

    @Override
    public Integer create(RoundRobinMatch roundRobinMatch) {
        String sql = "INSERT INTO KnockoutMatch(date, scoreboard1, scoreboard2, team1, team2, concluded, phase) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, roundRobinMatch.getDate().toString());
            stmt.setInt(2, roundRobinMatch.getScoreboard1());
            stmt.setInt(3, roundRobinMatch.getScoreboard2());
            stmt.setInt(4, roundRobinMatch.getTeam1().getIdTeam());
            stmt.setInt(5, roundRobinMatch.getTeam2().getIdTeam());
            stmt.setBoolean(6, roundRobinMatch.getConcluded());
            stmt.setInt(7, roundRobinMatch.getRound().getIdRound());
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
    public Optional<RoundRobinMatch> findOne(Integer key) {
        String sql = "SELECT * FROM KnockoutMatch WHERE idMatch = ?";
        RoundRobinMatch roundRobinMatch = null;

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, key);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                roundRobinMatch = resultSetToEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(roundRobinMatch);
    }

    private RoundRobinMatch resultSetToEntity(ResultSet rs) throws SQLException {
        int matchId = rs.getInt("idMatch");
        int scoreboard1 = rs.getInt("scoreboard1");
        int scoreboard2 = rs.getInt("scoreboard2");
        int team1Id = rs.getInt("team1");
        int team2Id = rs.getInt("team2");
        boolean concluded = rs.getBoolean("concluded");
        int roundId = rs.getInt("round");

        String date = rs.getString("date");

        LocalDate convertedReturnDate = null;

        if(date != null)
            convertedReturnDate = LocalDate.parse(date);

        Team team1 = findTeamUseCase.findOne(team1Id).get();
        Team team2 = findTeamUseCase.findOne(team2Id).get();
        Round round = findRoundUseCase.findOne(roundId).get();

        return new RoundRobinMatch (
                matchId,
                LocalDate.parse(rs.getString("date")),
                scoreboard1,
                scoreboard2,
                team1,
                team2,
                concluded,
                round);
    }

    @Override
    public List<RoundRobinMatch> findAll() {
        String sql = "SELECT * FROM RoundRobinMatch";
        List<RoundRobinMatch> roundRobinMatchs = new ArrayList<>();

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RoundRobinMatch roundRobinMatch = resultSetToEntity(rs);
                roundRobinMatchs.add(roundRobinMatch);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roundRobinMatchs;
    }

    @Override
    public boolean update(RoundRobinMatch roundRobinMatch) {
        String sql = "UPDATE RoundRobinMatch SET date = ?, scoreboard1 = ?, scoreboard2 = ?, team1 = ?, team2 = ?, concluded = ?, phase = ? WHERE idMatch = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, roundRobinMatch.getDate().toString());
            stmt.setInt(2, roundRobinMatch.getScoreboard1());
            stmt.setInt(3, roundRobinMatch.getScoreboard2());
            stmt.setInt(4, roundRobinMatch.getTeam1().getIdTeam());
            stmt.setInt(5, roundRobinMatch.getTeam2().getIdTeam());
            stmt.setBoolean(6, roundRobinMatch.getConcluded());
            stmt.setInt(7, roundRobinMatch.getRound().getIdRound());
            stmt.setInt(8, roundRobinMatch.getIdMatch());
            stmt.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteByKey(Integer key) {
        String sql = "DELETE FROM KnockoutMatch WHERE idMatch = ?";
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
    public boolean delete(RoundRobinMatch roundRobinMatch) {
        if (roundRobinMatch == null || roundRobinMatch.getIdMatch() == null)
            throw new IllegalArgumentException("KnockoutMatch and KnockoutMatch ID must not be null.");
        return deleteByKey(roundRobinMatch.getIdMatch());
    }
}
