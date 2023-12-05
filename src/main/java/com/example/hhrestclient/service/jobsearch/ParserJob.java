package com.example.hhrestclient.service.jobsearch;

import com.example.hhrestclient.entity.Job;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ParserJob implements Parser<Job> {

    @Value("${hh.url}")
    private String URL;


    @Override
 //   @PostConstruct
    public Set<Job> parse() {
         System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        WebDriver driver = new ChromeDriver(options);

        /*ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setBrowserName("chrome");
        dc.setCapability(ChromeOptions.CAPABILITY, options);
        java.net.URL url = null;
        try {
            url = new URL("http://selenium-hub:4444/wd/hub");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        WebDriver driver = new RemoteWebDriver(url, dc);*/

//        driver.manage().window().maximize();

        driver.get(URL);

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Set<Job> jobList = new HashSet<>();
        List<WebElement> elements = new ArrayList<>();

        elements = driver.findElements(By.className("vacancy-serp-item-body__main-info"));
        for(WebElement element : elements) {
            Job job = new Job();
            job.setDate(System.currentTimeMillis());
            job.setName(element.findElements(By.tagName("h3")).get(0).getText());
            job.setCompany(element.findElement(By.className("vacancy-serp-item__meta-info-company")).getText());
            String s = element.findElement(By.className("vacancy-serp-item__info"))
                    .findElements(By.className("bloko-text")).get(1)
                    .getText();
            if (s.contains(",")){
                s = s.substring(0, s.indexOf(","));
            }
            job.setCity(s);
            job.setHref(element.findElement(By.className("serp-item__title")).getAttribute("href"));
            jobList.add(job);

            System.out.println(job);

        }
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        driver.quit();
        return jobList;
    }
}
