import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

interface Links extends Iterable<URL> {
    Iterator<URL> iterator();
    String toString();

    class HTML implements Links {
        private String inputURL;
        private ArrayList<URL> links;
        private HashMap<String, ArrayList<URL>> deadLinks;
        public String errorStatus;

        private boolean isValidLink(String url) {
            String urlRegex = "^(http|https)://[-a-zA-Z0-9+&@#/%?=~_|,!:.;]*[-a-zA-Z0-9+@#/%=&_|]";
            Pattern pattern = Pattern.compile(urlRegex);
            Matcher m = pattern.matcher(url);
            if (m.matches()) {
                return true;
            } else {
                return false;
            }
        }

        private ArrayList<URL> retrieveLinks() {
            ArrayList<URL> tempLinks = new ArrayList<>();
            try {
                Document document = Jsoup.connect(this.inputURL.toString()).get();
                Elements documentLinks = document.select("a[href]");
                for (Element link : documentLinks) {
                    URL tempLink = new URL(link.attr("abs:href").toString());
                    if (isValidLink(tempLink.toString())) {
                        tempLinks.add(tempLink);
                    }
                }
            } catch (MalformedURLException e) {
                errorStatus = "One of the retrieved links is malformed";
            } catch (IOException e) {
                errorStatus = "IOException while connecting";
            } catch (IllegalArgumentException e) {
                errorStatus = "Error while reading parent URL.";
            }
            return tempLinks;
        }

        private int getStatus(URL url) {
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int statusCode = connection.getResponseCode();
                return statusCode;
            } catch (IOException e) {
                return 0;
            }
        }

        private void getDeadLinks() {
            this.deadLinks = new HashMap<>();
            this.deadLinks.put("404", new ArrayList<>());
            this.deadLinks.put("50x", new ArrayList<>());
            this.deadLinks.put("dead", new ArrayList<>());
            for (URL url : this.links) {
                int status = getStatus(url);
                if (status == 404) {
                    this.deadLinks.get("404").add(url);
                }
                if (status >= 500) {
                    this.deadLinks.get("50x").add(url);

                }
            }
            this.deadLinks.get("dead").addAll(this.deadLinks.get("404"));
            this.deadLinks.get("dead").addAll(this.deadLinks.get("50x"));
        }

        @Override
        public Iterator<URL> iterator() {
            return links.iterator();
        }

        @Override
        public String toString() {
            JSONObject obj = new JSONObject();
            obj.put("total", this.links.size());

            obj.put("dead", this.deadLinks.get("dead").size());

            obj.put("url", this.inputURL);

            JSONObject notFoundObj = new JSONObject();
            notFoundObj.put("size", this.deadLinks.get("404").size());
            JSONArray notFoundURLs = new JSONArray();
            for (URL url : this.deadLinks.get("404")) {
                notFoundURLs.add(url.toString());
            }
            notFoundObj.put("urls", notFoundURLs);
            obj.put("404", notFoundObj);

            JSONObject serverErrObj = new JSONObject();
            serverErrObj.put("size", this.deadLinks.get("50x").size());
            JSONArray serverErrURLs = new JSONArray();
            for (URL url : this.deadLinks.get("50x")) {
                serverErrURLs.add(url.toString());
            }
            serverErrObj.put("urls", serverErrURLs);
            obj.put("50x", serverErrObj);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(obj.toJSONString());
            String prettyJsonString = gson.toJson(je);
            return prettyJsonString;
        }

        public HTML(String url) {
            this.inputURL = url;
            this.links = retrieveLinks();
            this.getDeadLinks();
        }
    }
}
