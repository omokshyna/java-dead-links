import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Links html = new Links.HTML(args[0]);
        System.out.println(html.toString());

        try {
            JSONObject json = html.printToJSON();
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
