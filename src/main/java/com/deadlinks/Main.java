package com.deadlinks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        String url = new String(args[0]);
        DeadLinks urlLinks = new DeadLinks(url);
        if (urlLinks.iterator().hasNext()) {
            for (Iterator<URL> it = urlLinks.iterator(); it.hasNext(); ) {
                System.out.println(it.next().toString());
            }
        }

        try {
            JSONObject json = urlLinks.linksToJSON();
            FileWriter file = new FileWriter("dead-links.json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(json.toJSONString());
            String prettyJsonString = gson.toJson(je);
            file.write(prettyJsonString);
            file.close();
        } catch (IOException e) {
            System.out.println("Couldn't write to file");
        }

    }
}
