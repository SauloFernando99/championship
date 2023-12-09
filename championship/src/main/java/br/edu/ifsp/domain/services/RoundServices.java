package br.edu.ifsp.domain.services;

import br.edu.ifsp.domain.entities.championship.Match;
import br.edu.ifsp.domain.entities.championship.Round;

public class RoundServices {

    MatchServices matchServices = new MatchServices();

    public void addMatch(Round round, Match match){
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
}
