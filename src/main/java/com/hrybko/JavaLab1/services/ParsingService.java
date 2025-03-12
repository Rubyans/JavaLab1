package com.hrybko.JavaLab1.services;

import com.hrybko.JavaLab1.repositories.IphoneRepository;
import com.hrybko.JavaLab1.models.Iphone;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParsingService {

    private static final String URL = "https://touch.com.ua/iphone/";
    @Autowired
    IphoneRepository iphoneRepository;

    public List<Iphone> fetchListings() {
        List<Iphone> iphoneList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(URL).get();
            Elements elements = doc.select(".tabloid");

            for (Element element : elements) {
                String title = element.select(".name").text();
                String price = element.select(".price").text();
                String link = element.select(".picture").attr("href");

                iphoneList.add(new Iphone(title, price, "https://touch.com.ua" + link));
            }
            iphoneRepository.saveAll(iphoneList);
            System.out.println("Success get data and write to DB");

        } catch (IOException e) {
            System.err.println("Error parsing info " + e.getMessage());
        }
        return iphoneList;
    }


}


