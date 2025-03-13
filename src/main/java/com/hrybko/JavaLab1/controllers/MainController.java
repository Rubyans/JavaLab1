package com.hrybko.JavaLab1.controllers;

import com.hrybko.JavaLab1.services.ExcelExportService;
import com.hrybko.JavaLab1.services.ParsingService;
import com.hrybko.JavaLab1.services.ExchangeRateService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/")
public class MainController {

    private final ParsingService parsingService;
    private final ExcelExportService excelExportService;
    private final ExchangeRateService exchangeRateService;

    public MainController(ParsingService parsingService, ExcelExportService excelExportService, ExchangeRateService exchangeRateService) {
        this.parsingService = parsingService;
        this.excelExportService = excelExportService;
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping("/parse")
    public void getListings() {
        parsingService.fetchListings();
    }

    @GetMapping("/getExel")
    public ResponseEntity<ByteArrayResource> exportToExcel() throws IOException {
        byte[] data = excelExportService.generateRandomExcelReport();

        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=random_listings.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("/getExchange")
    public void getExchangeRate() {
        exchangeRateService.fetchAndSaveExchangeRate();
    }
}