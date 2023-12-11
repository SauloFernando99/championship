package br.edu.ifsp.domain.services;

import br.edu.ifsp.domain.entities.championship.Match;
import br.edu.ifsp.domain.entities.team.Team;

import java.util.Objects;

public class MatchServices {

    public void updateMatchResult(Match match, Integer scoreboard1, Integer scoreboard2) {
        match.setScoreboard1(scoreboard1);
        match.setScoreboard2(scoreboard2);
    }

    public Boolean notDraw(Match match) throws IllegalArgumentException {
        if (Objects.equals(match.getScoreboard1(), match.getScoreboard2())) {
            throw new IllegalArgumentException("The match result can not be a draw");
        }
        return false;
    }

    public Boolean isDraw(Match match) {
        if (Objects.equals(match.getScoreboard1(), match.getScoreboard2())) {
            return true;
        } else {
            return false;
        }
    }

    public void concludeMatch(Match match) throws IllegalStateException {
        if (match.getConcluded()) {
            throw new IllegalStateException("The match has already ended.");
        } else {
            match.setConcluded(true);
        }
    }

    public Boolean isMatchConcluded(Match match) {
        return match.getConcluded();
    }

    public Team getWinner(Match match){
        if (match.getScoreboard1() > match.getScoreboard2()){
            return match.getTeam1();
        } else if (match.getScoreboard1() < match.getScoreboard2()) {
            return match.getTeam2();
        } else {
            return null;
        }
    }
}
