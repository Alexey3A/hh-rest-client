package com.example.hhrestclient;

import com.example.hhrestclient.entity.Job;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParseWithJsoup {
    static String URL = "https://hh.ru/search/vacancy?ored_clusters=true&search_period=1&order_by=publication_time&search_field=name&search_field=company_name&search_field=description&enable_snippets=false&L_save_area=true&area=113&text=Java+developer";

    public static void main(String[] args) {
        Document doc;
        try {
            doc = Jsoup.connect(URL).get();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        List<Job> jobList = new ArrayList<>();
        Elements elements = doc.getElementsByClass("vacancy-serp-item-body__main-info");

        for(Element element : elements) {
            Job job = new Job();
            job.setDate(System.currentTimeMillis());
            job.setName(element.getElementsByTag("h3").text());
            job.setCompany(element.getElementsByClass("vacancy-serp-item__meta-info-company").get(0).text());
            String s = element.getElementsByClass("vacancy-serp-item__info").get(0)
                    .getElementsByClass("bloko-text").get(1).text();
            if (s.contains(",")){
                s = s.substring(0, s.indexOf(","));
            }
            job.setCity(s);
            job.setHref(element.getElementsByClass("serp-item__title").get(0)
                    .getElementsByAttribute("href").attr("href"));
            jobList.add(job);

            System.out.println(job);
        }
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
