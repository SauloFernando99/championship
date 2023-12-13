package br.edu.ifsp.application.repository.sqlite;

import br.edu.ifsp.domain.entities.championship.KnockoutMatch;
import br.edu.ifsp.domain.entities.championship.Phase;
import br.edu.ifsp.domain.entities.championship.Knockout;

import br.edu.ifsp.domain.usecases.phase.PhaseDAO;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.edu.ifsp.application.main.Main.findKnockoutMatchUseCase;
import static br.edu.ifsp.application.main.Main.findKnockoutUseCase;

public class SqlitePhaseDAO implements PhaseDAO {

    @Override
    public Integer create(Phase phase) {
        String sql = "INSERT INTO Phase(phaseName, knockout) VALUES (?, ?)";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, phase.getPhase());
            stmt.setInt(2, phase.getKnockout().getIdChampionship());
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
    public Optional<Phase> findOne(Integer key) {
        String sql = "SELECT * FROM Phase WHERE idPhase = ?";
        Phase phase = null;

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, key);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                phase = resultSetToEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(phase);
    }

    private Phase resultSetToEntity(ResultSet rs) throws SQLException {
        int phaseId = rs.getInt("idPhase");
        String phaseName = rs.getString("phaseName");
        boolean finished = rs.getBoolean("finished");
        int knockoutId = rs.getInt("knockout");

        Knockout knockout = findKnockoutUseCase.findOne(knockoutId).get();

        List<KnockoutMatch> foundMatches = findKnockoutMatchUseCase.findAll();
        List<KnockoutMatch> correctMatches = new ArrayList<>();

        for (KnockoutMatch match: foundMatches){
            if (match.getPhase().getIdPhase() == phaseId)
                correctMatches.add(match);
        }

        return new Phase(phaseId, phaseName, correctMatches, finished, knockout);
    }

    @Override
    public List<Phase> findAll() {
        String sql = "SELECT * FROM Phase";
        List<Phase> phases = new ArrayList<>();

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Phase phase = resultSetToEntity(rs);
                phases.add(phase);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return phases;
    }

    @Override
    public boolean update(Phase phase) {
        String sql = "UPDATE Phase SET phaseName = ?, finished = ?, knockout = ? WHERE idPhase = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, phase.getPhase());
            stmt.setBoolean(2, phase.getFinished());
            stmt.setInt(3, phase.getKnockout().getIdChampionship());
            stmt.setInt(4, phase.getIdPhase());
            stmt.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteByKey(Integer key) {
        String sql = "DELETE FROM Phase WHERE idPhase = ?";
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
    public boolean delete(Phase phase) {
        if (phase == null || phase.getIdPhase() == null)
            throw new IllegalArgumentException("Phase and Phase ID must not be null.");
        return deleteByKey(phase.getIdPhase());
    }
}
