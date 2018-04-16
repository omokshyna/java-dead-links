import java.util.ArrayList;
import java.util.List;

import java.net.*;
import java.io.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupBadLinks implements DeadLinks {
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


    public List<String> allLinks(String url) throws IOException {

        Document doc = Jsoup.connect(url).get();
        Elements links = doc.select("a[href]");
        List<String> allLinks = new ArrayList<String>();
        for (Element link : links) {
            allLinks.add(link.attr("abs:href"));

        }
        allLinks.stream()
                .distinct();
        return allLinks;
    }


    @Override
    public List<String> badLinks(String url) throws IOException {
        List<String> allUrls = allLinks(url);
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
        for (String urls : badUrls) {
            badLinks = urls;
            System.out.println(badLinks);
        }

        return badUrls;
    }
}
