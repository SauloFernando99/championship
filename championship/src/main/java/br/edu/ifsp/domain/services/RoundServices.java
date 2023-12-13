package br.edu.ifsp.domain.services;

import br.edu.ifsp.domain.entities.championship.Match;
import br.edu.ifsp.domain.entities.championship.Round;
import br.edu.ifsp.domain.entities.championship.RoundRobinMatch;
import java.util.List;
import java.io.IOException;


public class RoundServices {

    MatchServices matchServices = new MatchServices();

    public void addMatch(Round round, RoundRobinMatch match){
        round.addMatch(match);
    }

    public void finishRound(Round round){
        if (round.getFinished()) {
            System.out.println("The round has already ended.");
            return;
        }

        for (Match match : round.getMatches()) {
            if (!matchServices.isMatchConcluded(match)) {
                System.out.println("Cannot conclude the round. Not all matches are finished.");
                return;
            }
        }

        round.setFinished(true);
        System.out.println("The round has been concluded.");
    }

    public boolean allMatchesConcluded(Round round) {
        for (Match match : round.getMatches()) {
            if (!match.getConcluded()) {
                return false;
            }
        }
        return true;
    }
    private static final int LINE_HEIGHT = 15;

    }
