package br.edu.ifsp.domain.services;

import br.edu.ifsp.domain.entities.championship.Match;
import br.edu.ifsp.domain.entities.championship.Round;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.util.List;
import java.io.IOException;


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

    public boolean allMatchesConcluded(Round round) {
        for (Match match : round.getMatches()) {
            if (!match.getConcluded()) {
                return false;
            }
        }
        return true;
    }
    private static final int LINE_HEIGHT = 15;

    private void addText(PDPageContentStream contentStream, float x, float y, String text) throws IOException {
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(text);
        contentStream.endText();
    }

    public void exportRoundToPDF(int roundId, String filePath, List<Round> allRounds) {
        Round round = findRoundById(roundId, allRounds);

        if (round == null) {
            System.out.println("Rodada com ID " + roundId + " não encontrada.");
            return;
        }

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                float yStart = page.getMediaBox().getHeight() - 50;
                float margin = 50;
                float yPosition = yStart;

                addText(contentStream, margin, yPosition, "Round ID: " + round.getIdRound());
                yPosition -= LINE_HEIGHT;

                addText(contentStream, margin, yPosition, "Date: " + (round.getDate() != null ? round.getDate().toString() : "N/A"));
                yPosition -= LINE_HEIGHT;

                for (Match match : round.getMatches()) {
                    addText(contentStream, margin, yPosition, ""+ match.toString());
                    yPosition -= LINE_HEIGHT;
                }
            }

            document.save(filePath);
            System.out.println("Exportação para PDF concluída com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Round findRoundById(int roundId, List<Round> rounds) {
        return rounds.stream()
                .filter(r -> r.getIdRound() == roundId)
                .findFirst()
                .orElse(null);
    }
}
