package com.deadlinks;

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

public class DeadLinks implements Links {
    private HashMap<URL, Integer> linksWithStatus;
    private ArrayList<URL> deadLinks;
    private ArrayList<URL> notFoundLinks;
    private ArrayList<URL> serverErrLinks;
    private String inputURL;
    public String parentState;
    public String linksState;

    private void setInputURL(String url) {
            this.inputURL = url;
    }

    private boolean validLink(String url) {
        String urlRegex = "^(http|https)://[-a-zA-Z0-9+&@#/%?=~_|,!:.;]*[-a-zA-Z0-9+@#/%=&_|]";
        Pattern pattern = Pattern.compile(urlRegex);
        Matcher m = pattern.matcher(url);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    private void retrieveLinks() {
        this.linksWithStatus = new HashMap<>();
        try {
            Document document = Jsoup.connect(this.inputURL.toString()).get();
            Elements documentLinks = document.select("a[href]");
            System.out.println("Started links extraction.");
            for (Element link : documentLinks) {
                URL tempLink = new URL(link.attr("abs:href").toString());
                if (validLink(tempLink.toString())) {
                    HttpURLConnection connection = (HttpURLConnection) tempLink.openConnection();
                    connection.connect();
                    int statusCode = connection.getResponseCode();
                    this.linksWithStatus.put(tempLink, statusCode);
                }
            }
            linksState = "Links were extracted succesfully.";
        } catch (MalformedURLException e) {
            linksState = "One of the retrieved links is malformed";
        } catch (IOException e) {
            linksState = "IOException while connecting";
        } catch (IllegalArgumentException e) {
            parentState = "Error while reading parent URL.";
        }
    }

    private void getDeadLinks() {
        this.deadLinks = new ArrayList<>();
        this.notFoundLinks = new ArrayList<>();
        this.serverErrLinks = new ArrayList<>();

        for (URL key : this.linksWithStatus.keySet()) {
            if (linksWithStatus.get(key) == 404) {
                notFoundLinks.add(key);
            }
            if (linksWithStatus.get(key) >= 500) {
                this.serverErrLinks.add(key);

            }
        }
        this.deadLinks.addAll(notFoundLinks);
        this.deadLinks.addAll(serverErrLinks);
    }


    @Override
    public Iterator<URL> iterator() {
        return deadLinks.iterator();
    }

    public JSONObject linksToJSON() {
        JSONObject obj = new JSONObject();
        obj.put("total", this.linksWithStatus.size());

        obj.put("dead", this.deadLinks.size());

        obj.put("url", this.inputURL.toString());

        JSONObject notFoundObj = new JSONObject();
        notFoundObj.put("size", this.notFoundLinks.size());
        JSONArray notFoundURLs = new JSONArray();
        for (URL url : this.notFoundLinks) {
            notFoundURLs.add(url.toString());
        }
        notFoundObj.put("urls", notFoundURLs);
        obj.put("404", notFoundObj);

        JSONObject serverErrObj = new JSONObject();
        serverErrObj.put("size", this.serverErrLinks.size());
        JSONArray serverErrURLs = new JSONArray();
        for (URL url : this.serverErrLinks) {
            serverErrURLs.add(url.toString());
        }
        serverErrObj.put("urls", serverErrURLs);
        obj.put("50x", serverErrObj);

        return obj;
    }

    public DeadLinks(String url) {
        setInputURL(url);
        retrieveLinks();
        getDeadLinks();
    }
}
