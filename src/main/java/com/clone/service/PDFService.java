package com.clone.service;

import com.clone.entity.Property;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;

@Service
public class PDFService {
    public void generateBookingPdf(String filePath, Property property) {
        // Implement PDF generation logic here
        try{
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            PdfPTable table = new PdfPTable(2);
            table.addCell("Id");
            table.addCell(property.getId().toString());
            table.addCell("Name");
            table.addCell(property.getPropertyName());
            table.addCell("no of rooms");
            table.addCell(property.getNo_of_rooms().toString());
            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
