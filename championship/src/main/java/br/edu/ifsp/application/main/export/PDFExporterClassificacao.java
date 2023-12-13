package br.edu.ifsp.application.main.export;
import br.edu.ifsp.domain.entities.team.TeamStats;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.ObservableList;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PDFExporterClassificacao {

    public static void exportTableToPDF(ObservableList<TeamStats> teamStats, String filePath) {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            PdfPTable table = createPdfTable(teamStats);
            document.add(table);

            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private static PdfPTable createPdfTable(ObservableList<TeamStats> teamStats) {
        PdfPTable table = new PdfPTable(6);

        addTableHeader(table);
        addTableData(table, teamStats);

        return table;
    }

    private static void addTableHeader(PdfPTable table) {
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("Times"));
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("V"));
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("D"));
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("E"));
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("P"));
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("SG"));
        table.addCell(cell);
    }

    private static void addTableData(PdfPTable table, List<TeamStats> teamStats) {
        for (TeamStats stats : teamStats) {
            table.addCell(stats.getTeam().getName());
            table.addCell(String.valueOf(stats.getWins()));
            table.addCell(String.valueOf(stats.getLosses()));
            table.addCell(String.valueOf(stats.getDraws()));
            table.addCell(String.valueOf(stats.getPoints()));
            table.addCell(String.valueOf(stats.getPointsStandings()));
        }
    }
}
