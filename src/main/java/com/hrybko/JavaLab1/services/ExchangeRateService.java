package com.hrybko.JavaLab1.services;

import com.hrybko.JavaLab1.models.ExchangeRateModel;
import com.hrybko.JavaLab1.repositories.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;

@Service
public class ExchangeRateService {

    private static final String URL = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    public void fetchAndSaveExchangeRate() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(URL, String.class);

        if (response != null) {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if ("USD".equals(jsonObject.getString("ccy"))) {
                    double buyRate = jsonObject.getDouble("buy");
                    double sellRate = jsonObject.getDouble("sale");

                    ExchangeRateModel exchangeRateModel = new ExchangeRateModel("USD", "UAH", buyRate, sellRate, LocalDateTime.now());
                    exchangeRateRepository.save(exchangeRateModel);
                    System.out.println("Successfully saved exchange rate to DB");
                    break;
                }
            }
        } else {
            System.err.println("Failed to fetch exchange rate data");
        }
    }
}
