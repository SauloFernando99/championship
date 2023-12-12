package br.edu.ifsp.application.repository.sqlite;

import java.sql.Statement;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

public class DataBaseBuilder {

    public void buildDatabaseIfMissing() {
        if (isDatabaseMissing()) {
            System.out.println("Database is missing. Building database: \n");
            buildTables();
        }
    }

    private boolean isDatabaseMissing() {
        return !Files.exists(Paths.get("championship.db"));
    }

    private void buildTables() {
        try (Statement statement = ConnectionFactory.   createStatement()) {
            statement.addBatch(createTeamTable());
            statement.addBatch(createKnockoutTable());
            statement.addBatch(createRoundRobinTable());
            statement.addBatch(createTeamStatsTable());
            statement.addBatch(createPhaseTable());
            statement.addBatch(createRoundTable());
            statement.addBatch(createKnockoutMatchTable());
            statement.addBatch(createRoundRobinMatchTable());
            statement.addBatch(createTeamKnockoutTable());
            statement.addBatch(createTeamRoundRobinTable());
            statement.executeBatch();

            System.out.println("Database successfully created.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private String createTeamTable() {
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE Team (\n");
        builder.append("idTeam INTEGER PRIMARY KEY AUTOINCREMENT, \n");
        builder.append("name VARCHAR(255), \n");
        builder.append("isActive BOOLEAN DEFAULT true \n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }

    private String createKnockoutTable() {
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE Knockout (\n");
        builder.append("idChampionship INTEGER PRIMARY KEY AUTOINCREMENT, \n");
        builder.append("name VARCHAR(255) NOT NULL, \n");
        builder.append("initialDate DATE, \n");
        builder.append("finalDate DATE, \n");
        builder.append("modality VARCHAR(255), \n");
        builder.append("award VARCHAR(255), \n");
        builder.append("sponsorship VARCHAR(255), \n");
        builder.append("concluded BOOLEAN DEFAULT false, \n");
        builder.append("champion INTEGER, \n");
        builder.append("seeding VARCHAR(255), \n");
        builder.append("FOREIGN KEY (champion) REFERENCES Team(idTeam) \n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }
    private String createRoundRobinTable() {
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE RoundRobin (\n");
        builder.append("idChampionship INTEGER PRIMARY KEY AUTOINCREMENT, \n");
        builder.append("name VARCHAR(255) NOT NULL, \n");
        builder.append("initialDate DATE, \n");
        builder.append("finalDate DATE, \n");
        builder.append("modality VARCHAR(255), \n");
        builder.append("award VARCHAR(255), \n");
        builder.append("sponsorship VARCHAR(255), \n");
        builder.append("concluded BOOLEAN DEFAULT false, \n");
        builder.append("champion INTEGER, \n");
        builder.append("seeding VARCHAR(255), \n");
        builder.append("FOREIGN KEY (champion) REFERENCES Team(idTeam) \n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }
    private String createTeamStatsTable() {
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE TeamStats (\n");
        builder.append("idTeamStats INTEGER PRIMARY KEY AUTOINCREMENT, \n");
        builder.append("team INTEGER, \n");
        builder.append("wins INTEGER DEFAULT 0, \n");
        builder.append("losses INTEGER DEFAULT 0, \n");
        builder.append("draws INTEGER DEFAULT 0, \n");
        builder.append("points INTEGER DEFAULT 0, \n");
        builder.append("pointsStandings INTEGER DEFAULT 0, \n");
        builder.append("roundRobin INTEGER, \n");
        builder.append("FOREIGN KEY (team) REFERENCES Team(idTeam), \n");
        builder.append("FOREIGN KEY (roundRobin) REFERENCES RoundRobin(idChampionship) \n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }

    private String createPhaseTable() {
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE Phase (\n");
        builder.append("idPhase INTEGER PRIMARY KEY AUTOINCREMENT, \n");
        builder.append("phaseName VARCHAR(255), \n");
        builder.append("finished BOOLEAN, \n");
        builder.append("knockout INTEGER, \n");
        builder.append("FOREIGN KEY (knockout) REFERENCES Knockout(idChampionship) \n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }

    private String createRoundTable() {
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE Round (\n");
        builder.append("idRound INTEGER PRIMARY KEY AUTOINCREMENT, \n");
        builder.append("number INTEGER, \n");
        builder.append("date DATE, \n");
        builder.append("isFinished BOOLEAN DEFAULT false, \n");
        builder.append("roundRobin INTEGER, \n");
        builder.append("FOREIGN KEY (roundRobin) REFERENCES RoundRobin(idChampionship) \n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }

    private String createKnockoutMatchTable() {
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE KnockoutMatch (\n");
        builder.append("idMatch INTEGER PRIMARY KEY AUTOINCREMENT, \n");
        builder.append("date DATE, \n");
        builder.append("scoreboard1 INTEGER DEFAULT 0, \n");
        builder.append("scoreboard2 INTEGER DEFAULT 0, \n");
        builder.append("team1 INTEGER, \n");
        builder.append("team2 INTEGER, \n");
        builder.append("concluded BOOLEAN DEFAULT false, \n");
        builder.append("phase INTEGER, \n");
        builder.append("FOREIGN KEY (phase) REFERENCES Phase(idPhase), \n");
        builder.append("FOREIGN KEY (team1) REFERENCES Team(idTeam), \n");
        builder.append("FOREIGN KEY (team2) REFERENCES Team(idTeam) \n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }

    private String createRoundRobinMatchTable() {
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE RoundRobinMatch (\n");
        builder.append("idMatch INTEGER PRIMARY KEY AUTOINCREMENT, \n");
        builder.append("date DATE, \n");
        builder.append("scoreboard1 INTEGER DEFAULT 0, \n");
        builder.append("scoreboard2 INTEGER DEFAULT 0, \n");
        builder.append("team1 INTEGER, \n");
        builder.append("team2 INTEGER, \n");
        builder.append("concluded BOOLEAN DEFAULT false, \n");
        builder.append("round INTEGER, \n");
        builder.append("FOREIGN KEY (round) REFERENCES Round(idRound), \n");
        builder.append("FOREIGN KEY (team1) REFERENCES Team(idTeam), \n");
        builder.append("FOREIGN KEY (team2) REFERENCES Team(idTeam) \n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }

    private String createTeamKnockoutTable() {
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE TeamKnockout (\n");
        builder.append("idTeam INTEGER, \n");
        builder.append("idChampionship INTEGER, \n");
        builder.append("PRIMARY KEY (idTeam, idChampionship), \n");
        builder.append("FOREIGN KEY (idTeam) REFERENCES Team(idTeam), \n");
        builder.append("FOREIGN KEY (idChampionship) REFERENCES Knockout(idChampionship) \n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }
    private String createTeamRoundRobinTable() {
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE TeamRoundRobin (\n");
        builder.append("idTeam INTEGER, \n");
        builder.append("idChampionship INTEGER, \n");
        builder.append("PRIMARY KEY (idTeam, idChampionship), \n");
        builder.append("FOREIGN KEY (idTeam) REFERENCES Team(idTeam), \n");
        builder.append("FOREIGN KEY (idChampionship) REFERENCES RoundRobin(idChampionship) \n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }
}
