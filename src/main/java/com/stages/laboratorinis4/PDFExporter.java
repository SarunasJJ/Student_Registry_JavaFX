package com.stages.laboratorinis4;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.ObservableList;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PDFExporter {

    public void exportToPDF(ObservableList<Student> students, String filename) throws FileNotFoundException, DocumentException {
        Document pdfFile = new Document();
        PdfWriter.getInstance(pdfFile, new FileOutputStream(filename + ".pdf"));
        pdfFile.open();
        PdfPTable table = new PdfPTable(4);
        PdfPCell header = new PdfPCell(new Phrase("First name"));
        table.addCell(header);
        header = new PdfPCell(new Phrase("Last name"));
        table.addCell(header);
        header = new PdfPCell(new Phrase("Student ID"));
        table.addCell(header);
        header = new PdfPCell(new Phrase("Attendance"));
        table.addCell(header);
        table.setHeaderRows(1);

        for(Student student : students){
            table.addCell(student.getFirstName());
            table.addCell(student.getLastName());
            table.addCell(student.getId());
            table.addCell(String.valueOf(student.getAttendanceRate()));
        }

        pdfFile.add(table);
        pdfFile.close();
    }
}
