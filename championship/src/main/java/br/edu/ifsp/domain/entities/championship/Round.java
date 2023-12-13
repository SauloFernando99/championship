package br.edu.ifsp.domain.entities.championship;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Round {
    private Integer idRound;
    private Integer number;
    private LocalDate date;
    private List<RoundRobinMatch> matches = new ArrayList<>();
    private Boolean isFinished = false;
    private RoundRobin roundRobin;

    public Round(LocalDate date, List<RoundRobinMatch> matches) {
        this.date = date;
        this.matches = matches;
    }

    public Round(RoundRobin roundRobin){
        this.roundRobin = roundRobin;
    }

    public Round(int roundId, int number, LocalDate date, List<RoundRobinMatch> correctMatches, boolean finished, RoundRobin roundRobin) {
        this.idRound = roundId;
        this.number = number;
        this.date = date;
        this.matches = correctMatches;
        this.isFinished = finished;
        this.roundRobin = roundRobin;
    }

    public void addMatch(RoundRobinMatch match){
        matches.add(match);
    }

    public Integer getIdRound() {
        return idRound;
    }

    public void setIdRound(Integer idRound) {
        this.idRound = idRound;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public RoundRobin getRoundRobin() {
        return roundRobin;
    }

    public void setRoundRobin(RoundRobin roundRobin) {
        this.roundRobin = roundRobin;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<RoundRobinMatch> getMatches() {
        return matches;
    }

    public void setMatches(List<RoundRobinMatch> matches) {
        this.matches = matches;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }
}
