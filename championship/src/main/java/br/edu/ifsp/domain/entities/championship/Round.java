package br.edu.ifsp.domain.entities.championship;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Round {

    private Integer idRound;
    private LocalDate date;
    private List<Match> matches = new ArrayList<>();
    private Boolean isFinished;

    public Round(Integer idRound, LocalDate date, List<Match> matches) {
        this.idRound = idRound;
        this.date = date;
        this.matches = matches;
        this.setFinished(false);
    }

    public Integer getIdRound() {
        return idRound;
    }

    public void setIdRound(Integer idRound) {
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
