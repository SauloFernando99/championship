package br.edu.ifsp.application.repository.sqlite;

import br.edu.ifsp.application.repository.sqlite.ConnectionFactory;
import br.edu.ifsp.domain.entities.championship.RoundRobinMatch;
import br.edu.ifsp.domain.entities.championship.Round;
import br.edu.ifsp.domain.entities.championship.RoundRobin;

import br.edu.ifsp.domain.usecases.round.RoundDAO;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.edu.ifsp.application.main.Main.findRoundRobinMatchUseCase;
import static br.edu.ifsp.application.main.Main.findRoundRobinUseCase;

public class SqliteRoundDAO implements RoundDAO {

    @Override
    public Integer create(Round round) {
        String sql = "INSERT INTO Round(number, knockout) VALUES (?, ?)";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, round.getNumber());
            stmt.setInt(2, round.getRoundRobin().getIdChampionship());
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
    public Optional<Round> findOne(Integer key) {
        String sql = "SELECT * FROM Round WHERE idRound = ?";
        Round round = null;

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, key);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                round = resultSetToEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(round);
    }

    private Round resultSetToEntity(ResultSet rs) throws SQLException {
        int roundId = rs.getInt("idRound");
        int number = rs.getInt("number");
        boolean finished = rs.getBoolean("isFinished");
        int roundRobinId = rs.getInt("roundRobin");

        RoundRobin roundRobin = findRoundRobinUseCase.findOne(roundRobinId).get();

        List<RoundRobinMatch> foundMatches = findRoundRobinMatchUseCase.findAll();
        List<RoundRobinMatch> correctMatches = new ArrayList<>();

        for (RoundRobinMatch match: foundMatches){
            if (match.getRound().getIdRound() == roundId)
                correctMatches.add(match);
        }

        String date = rs.getString("date");

        return new Round(
                roundId,
                number,
                LocalDate.parse(rs.getString("date")),
                correctMatches,
                finished,
                roundRobin);
    }

    @Override
    public List<Round> findAll() {
        String sql = "SELECT * FROM Round";
        List<Round> rounds = new ArrayList<>();

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Round round = resultSetToEntity(rs);
                rounds.add(round);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rounds;
    }

    @Override
    public boolean update(Round round) {
        String sql = "UPDATE Round SET number = ?, date = ?, isFinished = ?, roundRobin = ? WHERE idRound = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, round.getNumber());
            stmt.setString(2, round.getDate().toString());
            stmt.setBoolean(3, round.getFinished());
            stmt.setInt(4, round.getRoundRobin().getIdChampionship());
            stmt.setInt(5, round.getIdRound());
            stmt.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteByKey(Integer key) {
        String sql = "DELETE FROM Round WHERE idRound = ?";
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
    public boolean delete(Round round) {
        if (round == null || round.getIdRound() == null)
            throw new IllegalArgumentException("Round and Round ID must not be null.");
        return deleteByKey(round.getIdRound());
    }
}
