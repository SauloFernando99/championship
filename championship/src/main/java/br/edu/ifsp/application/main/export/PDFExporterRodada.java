package br.edu.ifsp.application.main.export;

import br.edu.ifsp.domain.entities.championship.Round;
import br.edu.ifsp.domain.entities.championship.RoundRobinMatch;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PDFExporterRodada {


    public static void exportRoundToPDF(Round round, String filePath) {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            addRoundInfo(document, round);
            addMatches(document, round.getMatches());

            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void addRoundInfo(Document document, Round round) throws DocumentException {
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLACK);

        Paragraph roundInfo = new Paragraph("ID da Rodada: " + round.getIdRound(), font);
        document.add(roundInfo);

        document.add(Chunk.NEWLINE);
    }

    private static void addMatches(Document document, List<RoundRobinMatch> matches) throws DocumentException {
        PdfPTable table = new PdfPTable(3);

        addTableHeader(table);

        for (RoundRobinMatch match : matches) {
            addMatchData(table, match);
        }

        document.add(table);
    }

    private static void addTableHeader(PdfPTable table) {
        PdfPCell cell;

        cell = new PdfPCell(new Phrase("Casa"));
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Visitante"));
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Resultado"));
        table.addCell(cell);
    }

    private static void addMatchData(PdfPTable table, RoundRobinMatch match) {
        PdfPCell cell;

        cell = new PdfPCell(new Phrase(match.getTeam1Name()));
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(match.getTeam2Name()));
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(match.getScoreboard1() + " - " + match.getScoreboard2()));
        table.addCell(cell);
    }
}
