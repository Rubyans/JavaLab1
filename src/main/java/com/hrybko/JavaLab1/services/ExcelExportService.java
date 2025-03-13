package com.hrybko.JavaLab1.services;

import com.hrybko.JavaLab1.models.ExchangeRateModel;
import com.hrybko.JavaLab1.models.IphoneModel;
import com.hrybko.JavaLab1.repositories.ExchangeRateRepository;
import com.hrybko.JavaLab1.repositories.IphoneRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelExportService {

    @Autowired
    private IphoneRepository iphoneRepository;
    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    public byte[] generateRandomExcelReport() throws IOException {
        final List<IphoneModel> iphoneModelList = iphoneRepository.findAll();
        final ExchangeRateModel exchangeRateModel = exchangeRateRepository.findTopByOrderByIdDesc();
        if (iphoneModelList.isEmpty() || exchangeRateModel == null) return null;

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Listings");

        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        Row headerRow = sheet.createRow(0);
        String[] columns = {"Назва", "Інформація про ціну", "Посилання", "Курс"};

        for (int col = 0; col < columns.length; col++) {
            Cell cell = headerRow.createCell(col);
            cell.setCellValue(columns[col]);
            cell.setCellStyle(headerStyle);
        }

        for (int i = 0; i < iphoneModelList.size(); i++) {
            IphoneModel iphone = iphoneModelList.get(i);
            Row row = sheet.createRow(i + 1);

            row.createCell(0).setCellValue(iphone.getTitle());
            row.createCell(1).setCellValue(iphone.getPrice());
            row.createCell(2).setCellValue(iphone.getUrl());
        }

        int lastRow = iphoneModelList.size();
        if (lastRow == 0) lastRow = 1;
        sheet.addMergedRegion(new CellRangeAddress(1, lastRow, 3, 3));

        Row rateRow = sheet.getRow(1);
        if (rateRow == null) rateRow = sheet.createRow(1);
        Cell rateCell = rateRow.createCell(3);
        rateCell.setCellValue("$1 = " + exchangeRateModel.getBuyRate() + " UAH");

        CellStyle rateStyle = workbook.createCellStyle();
        rateStyle.setAlignment(HorizontalAlignment.CENTER);
        rateStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        rateCell.setCellStyle(rateStyle);

        for (int col = 0; col < columns.length; col++) {
            sheet.autoSizeColumn(col);
        }

        // Растягивание ширины столбца "Курс" вручную
        sheet.setColumnWidth(3, 20 * 256);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();
    }
}