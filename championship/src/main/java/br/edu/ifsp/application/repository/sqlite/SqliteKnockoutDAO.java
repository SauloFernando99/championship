package br.edu.ifsp.application.repository.sqlite;

import br.edu.ifsp.domain.entities.championship.Knockout;
import br.edu.ifsp.domain.entities.championship.Phase;
import br.edu.ifsp.domain.entities.dbsupport.TeamKnockout;
import br.edu.ifsp.domain.entities.team.Team;
import br.edu.ifsp.domain.usecases.knockout.dao.KnockoutDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import static br.edu.ifsp.application.main.Main.*;

public class SqliteKnockoutDAO implements KnockoutDAO {

    @Override
    public Integer create(Knockout knockout) {
        String sql = "INSERT INTO Knockout(name, initialDate, finalDate, modality, award, sponsorship, concluded) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, knockout.getName());
            stmt.setString(2, knockout.getInitialDate().toString());
            stmt.setString(3, knockout.getFinalDate().toString());
            stmt.setString(4, knockout.getModality());
            stmt.setString(5, knockout.getAward());
            stmt.setString(6, knockout.getSponsorship());
            stmt.setBoolean(7, knockout.getConcluded());
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
    public Optional<Knockout> findOne(Integer key) {
        String sql = "SELECT * FROM Knockout WHERE idChampionship = ?";
        Knockout knockout = null;

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, key);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                knockout = resultSetToEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(knockout);
    }

    private Knockout resultSetToEntity(ResultSet rs) throws SQLException {
        Integer knockoutId = rs.getInt("idChampionship");
        String name = rs.getString("name");
        String modality = rs.getString("modality");
        String award = rs.getString("award");
        String sponsorship = rs.getString("sponsorship");
        boolean concluded = rs.getBoolean("concluded");
        Integer championId = rs.getInt("champion");

        List<Phase> foundSeeding = findPhaseUseCase.findAll();

        List<Phase> correctSeeding = new ArrayList<>();

        for (Phase phase: foundSeeding) {
            if(Objects.equals(phase.getKnockout().getIdChampionship(), knockoutId)){
                correctSeeding.add(phase);
            }
        }

        correctSeeding.sort(Comparator.comparingInt(phase -> phase.getMatches().size()));

        Team champion;

        if (championId == null) {
            champion = null;
        } else {
            champion = findTeamUseCase.findOne(championId)
                    .orElse(null);
        }

        return new Knockout(
                knockoutId,
                name,
                LocalDate.parse(rs.getString("initialDate")),
                LocalDate.parse(rs.getString("finalDate")),
                modality,
                award,
                sponsorship,
                concluded,
                correctSeeding,
                champion
                );
    }

    @Override
    public List<Knockout> findAll() {
        String sql = "SELECT * FROM Knockout";
        List<Knockout> knockouts = new ArrayList<>();

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Knockout knockout = resultSetToEntity(rs);
                knockouts.add(knockout);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return knockouts;
    }

    @Override
    public boolean update(Knockout knockout) {
        String sql = "UPDATE Knockout SET name = ?, initialDate = ?, finalDate = ?, " +
                "modality = ?, award = ?, sponsorship = ?, concluded = ?, " +
                "champion = ? WHERE idChampionship = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, knockout.getName());
            stmt.setString(2, knockout.getInitialDate().toString());
            stmt.setString(3, knockout.getFinalDate().toString());
            stmt.setString(4, knockout.getModality());
            stmt.setString(5, knockout.getAward());
            stmt.setString(6, knockout.getSponsorship());
            stmt.setBoolean(7, knockout.getConcluded());
            stmt.setInt(8, (knockout.getChampion() != null) ? knockout.getChampion().getIdTeam() : null);
            stmt.setInt(9, knockout.getIdChampionship());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteByKey(Integer key) {
        String sql = "DELETE FROM Knockout WHERE idChampionship = ?";

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
    public boolean delete(Knockout knockout) {
        if (knockout == null || knockout.getIdChampionship() == null)
            throw new IllegalArgumentException("Knockout and Knockout ID must not be null.");
        return deleteByKey(knockout.getIdChampionship());
    }
}
