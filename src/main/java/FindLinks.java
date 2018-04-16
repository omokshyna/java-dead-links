import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;

import org.openqa.selenium.chrome.ChromeDriver;


import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class FindLinks {
    public int getResponseCode(String link) {
        URL url;
        HttpURLConnection con = null;
        Integer responsecode = 0;
        try {
            url = new URL(link);
            con = (HttpURLConnection) url.openConnection();
            responsecode = con.getResponseCode();
        } catch (Exception e) {
        } finally {
            if (null != con)
                con.disconnect();
        }
        return responsecode;

    }

    public List<String> allLinks(String link) {
        System.setProperty("webdriver.chrome.driver", "E:\\SeleniumDrivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get(link);


        List<WebElement> links = driver.findElements(By.xpath("//*[@href]"));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


        List<String> allLinks = links
                .stream()
                .map(ele -> ele.getAttribute("href"))
                .distinct()
                .collect(Collectors.toList());

        driver.quit();
        return allLinks;
    }

    int countBadLinks(String link) {
        List<String> allUrls = allLinks(link);
        System.out.println(allUrls.size());
        List<String> badUrls = new ArrayList<String>();
        String badLinks = "";
        int i = 0;
        for (String linkk : allUrls) {
            int code = getResponseCode(linkk);
            if (code == 404) {
                i++;
                badUrls.add(linkk);

            }

        }
        System.out.println(i);
        for (String url : badUrls) {
            badLinks = url;
            System.out.println(badLinks);
        }

        return i;
    }
}