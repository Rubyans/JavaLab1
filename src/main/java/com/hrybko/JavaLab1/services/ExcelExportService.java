package com.hrybko.JavaLab1.services;

import com.hrybko.JavaLab1.repositories.IphoneRepository;
import com.hrybko.JavaLab1.models.Iphone;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelExportService {

    @Autowired
    private IphoneRepository iphoneRepository;

    public byte[] generateRandomExcelReport() throws IOException {

        final List<Iphone> iphoneList = iphoneRepository.findAll();
        if (iphoneList.isEmpty()) return null;

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Listings");

        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);
        String[] columns = {"Назва", "Інформація про ціну", "Посилання"};

        for (int col = 0; col < columns.length; col++) {
            Cell cell = headerRow.createCell(col);
            cell.setCellValue(columns[col]);
            cell.setCellStyle(headerStyle);
        }

        for (int i = 0; i < iphoneList.size(); i++) {
            Iphone iphone = iphoneList.get(i);
            Row row = sheet.createRow(i + 1);

            row.createCell(0).setCellValue(iphone.getTitle());
            row.createCell(1).setCellValue(iphone.getPrice());
            row.createCell(2).setCellValue(iphone.getUrl());
        }

        for (int col = 0; col < columns.length; col++) {
            sheet.autoSizeColumn(col);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();
    }
}
