package br.edu.ifsp.application.repository.sqlite;

import br.edu.ifsp.domain.entities.championship.KnockoutMatch;
import br.edu.ifsp.domain.entities.championship.Phase;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.knockoutmatch.KnockoutMatchDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.edu.ifsp.application.main.Main.findPhaseUseCase;
import static br.edu.ifsp.application.main.Main.findTeamUseCase;

public class SqliteKnockoutMatchDAO implements KnockoutMatchDAO {

    @Override
    public Integer create(KnockoutMatch knockoutMatch) {
        String sql = "INSERT INTO KnockoutMatch(date, scoreboard1, scoreboard2, team1, team2, concluded, phase) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, knockoutMatch.getDate().toString());
            stmt.setInt(2, knockoutMatch.getScoreboard1());
            stmt.setInt(3, knockoutMatch.getScoreboard2());
            stmt.setInt(4, knockoutMatch.getTeam1().getIdTeam());
            stmt.setInt(5, knockoutMatch.getTeam2().getIdTeam());
            stmt.setBoolean(6, knockoutMatch.getConcluded());
            stmt.setInt(7, knockoutMatch.getPhase().getIdPhase());
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
    public Optional<KnockoutMatch> findOne(Integer key) {
        String sql = "SELECT * FROM KnockoutMatch WHERE idMatch = ?";
        KnockoutMatch knockoutMatch = null;

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, key);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                knockoutMatch = resultSetToEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(knockoutMatch);
    }

    private KnockoutMatch resultSetToEntity(ResultSet rs) throws SQLException {
        int matchId = rs.getInt("idMatch");
        int scoreboard1 = rs.getInt("scoreboard1");
        int scoreboard2 = rs.getInt("scoreboard2");
        int team1Id = rs.getInt("team1");
        int team2Id = rs.getInt("team2");
        boolean concluded = rs.getBoolean("concluded");
        int phaseId = rs.getInt("phase");

        Team team1 = findTeamUseCase.findOne(team1Id).get();
        Team team2 = findTeamUseCase.findOne(team2Id).get();
        Phase phase = findPhaseUseCase.findOne(phaseId).get();

        return new KnockoutMatch (
                matchId,
                LocalDate.parse(rs.getString("date")),
                scoreboard1,
                scoreboard2,
                team1,
                team2,
                concluded,
                phase);
    }

    @Override
    public List<KnockoutMatch> findAll() {
        String sql = "SELECT * FROM KnockoutMatch";
        List<KnockoutMatch> knockoutMatches = new ArrayList<>();

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                KnockoutMatch knockoutMatch = resultSetToEntity(rs);
                knockoutMatches.add(knockoutMatch);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return knockoutMatches;
    }

    @Override
    public boolean update(KnockoutMatch knockoutMatch) {
        String sql = "UPDATE KnockoutMatch SET date = ?, scoreboard1 = ?, scoreboard2 = ?, team1 = ?, team2 = ?, concluded = ?, phase = ? WHERE idMatch = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, knockoutMatch.getDate().toString());
            stmt.setInt(2, knockoutMatch.getScoreboard1());
            stmt.setInt(3, knockoutMatch.getScoreboard2());
            stmt.setInt(4, knockoutMatch.getTeam1().getIdTeam());
            stmt.setInt(5, knockoutMatch.getTeam2().getIdTeam());
            stmt.setBoolean(6, knockoutMatch.getConcluded());
            stmt.setInt(7, knockoutMatch.getPhase().getIdPhase());
            stmt.setInt(8, knockoutMatch.getIdMatch());
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
    public boolean delete(KnockoutMatch knockoutMatch) {
        if (knockoutMatch == null || knockoutMatch.getIdMatch() == null)
            throw new IllegalArgumentException("KnockoutMatch and KnockoutMatch ID must not be null.");
        return deleteByKey(knockoutMatch.getIdMatch());
    }
}
