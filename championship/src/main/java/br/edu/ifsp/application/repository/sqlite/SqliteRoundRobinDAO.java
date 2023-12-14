package br.edu.ifsp.application.repository.sqlite;

import br.edu.ifsp.domain.entities.championship.RoundRobin;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.roundrobin.dao.RoundRobinDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.edu.ifsp.application.main.Main.*;

public class SqliteRoundRobinDAO implements RoundRobinDAO {

    @Override
    public Integer create(RoundRobin roundRobin) {
        String sql = "INSERT INTO RoundRobin(name, initialDate, finalDate, modality, award, sponsorship, concluded) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, roundRobin.getName());
            stmt.setString(2, roundRobin.getInitialDate().toString());
            stmt.setString(3, roundRobin.getFinalDate().toString());
            stmt.setString(4, roundRobin.getModality());
            stmt.setString(5, roundRobin.getAward());
            stmt.setString(6, roundRobin.getSponsorship());
            stmt.setBoolean(7, roundRobin.getConcluded());
            stmt.execute();

            ResultSet resultSet = stmt.getGeneratedKeys();
            int generatedKey = resultSet.getInt(1);
            return generatedKey;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<RoundRobin> findOne(Integer key) {
        String sql = "SELECT * FROM RoundRobin WHERE idChampionship = ?";
        RoundRobin roundRobin = null;

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, key);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                roundRobin = resultSetToEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(roundRobin);
    }

    private RoundRobin resultSetToEntity(ResultSet rs) throws SQLException {
        Integer roundRobinId = rs.getInt("idChampionship");
        String name = rs.getString("name");
        String modality = rs.getString("modality");
        String award = rs.getString("award");
        String sponsorship = rs.getString("sponsorship");
        boolean concluded = rs.getBoolean("concluded");
        Integer championId = rs.getInt("champion");

        Team champion;

        if (championId == null) {
            champion = null;
        } else {
            champion = findTeamUseCase.findOne(championId)
                    .orElse(null);
        }

        return new RoundRobin(
                roundRobinId,
                name,
                LocalDate.parse(rs.getString("initialDate")),
                LocalDate.parse(rs.getString("finalDate")),
                modality,
                award,
                sponsorship,
                concluded,
                champion
        );
    }

    @Override
    public List<RoundRobin> findAll() {
        String sql = "SELECT * FROM RoundRobin";
        List<RoundRobin> roundRobins = new ArrayList<>();

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RoundRobin roundRobin = resultSetToEntity(rs);
                roundRobins.add(roundRobin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roundRobins;
    }

    @Override
    public boolean update(RoundRobin roundRobin) {
        String sql = "UPDATE RoundRobin SET name = ?, initialDate = ?, finalDate = ?, " +
                "modality = ?, award = ?, sponsorship = ?, concluded = ?, " +
                "champion = ? WHERE idChampionship = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, roundRobin.getName());
            stmt.setString(2, roundRobin.getInitialDate().toString());
            stmt.setString(3, roundRobin.getFinalDate().toString());
            stmt.setString(4, roundRobin.getModality());
            stmt.setString(5, roundRobin.getAward());
            stmt.setString(6, roundRobin.getSponsorship());
            stmt.setBoolean(7, roundRobin.getConcluded());
            stmt.setInt(8, (roundRobin.getChampion() != null) ? roundRobin.getChampion().getIdTeam() : 0);
            stmt.setInt(9, roundRobin.getIdChampionship());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteByKey(Integer key) {
        String sql = "DELETE FROM RoundRobin WHERE idChampionship = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, key);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(RoundRobin roundRobin) {
        if (roundRobin == null || roundRobin.getIdChampionship() == null)
            throw new IllegalArgumentException("RoundRobin and RoundRobin ID must not be null.");
        return deleteByKey(roundRobin.getIdChampionship());
    }
}
