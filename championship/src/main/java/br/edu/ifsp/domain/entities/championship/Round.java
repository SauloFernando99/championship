package br.edu.ifsp.domain.entities.championship;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Round {
    private static int roundIdCounter = 1; // Contador de IDs de Round
    private int idRound;
    private LocalDate date;
    private List<Match> matches = new ArrayList<>();
    private Boolean isFinished = false;

    public Round(LocalDate date, List<Match> matches) {
        this.idRound = roundIdCounter++;
        this.date = date;
        this.matches = matches;
        this.setFinished(false);
    }

    public Round(){
        this.idRound = roundIdCounter++;
    }

    public void addMatch(Match match){
        matches.add(match);
    }

    public void concludeRound() {
        if (this.isFinished) {
            System.out.println("The round has already ended.");
            return;
        }

        // Verificar se todas as partidas estão finalizadas
        for (Match match : matches) {
            if (!match.getConcluded()) {
                System.out.println("Cannot conclude the round. Not all matches are finished.");
                return;
            }
        }

        this.isFinished = true;
        System.out.println("The round has been concluded.");
    }

    public int getIdRound() {
        return idRound;
    }

    public void setIdRound(int idRound) {
        this.idRound = idRound;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }
}
