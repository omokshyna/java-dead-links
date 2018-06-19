import com.google.gson.*;
import jdk.nashorn.internal.parser.JSONParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class LinksTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(System.out);
        System.setErr(System.err);
    }

    @Test
    public void testIfURLIsMalformed() throws MalformedURLException {
        Links tester = new Links.HTML("Roses are red, URLs are blue", new HTTP.Default());
        assertEquals(((Links.HTML) tester).errorStatus ,"Error while reading parent URL.");
    }

    @Test
    public void testIfJSONOutputIsValid() throws FileNotFoundException {
        Links actual = new Links.HTML("https://www.yegor256.com/elegant-objects.html", new HTTP.Default());
        System.out.println(actual.toString());

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        InputStream testJSONPath = getClass().getClassLoader().getResourceAsStream("./test.json");
        String expected = gson.toJson(new JsonParser().
                parse(new InputStreamReader(testJSONPath)));

        assertEquals(expected.trim(), outContent.toString().trim());
    }
}
