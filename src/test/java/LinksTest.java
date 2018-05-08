import org.json.simple.JSONObject;
import org.junit.Test;

import java.net.MalformedURLException;

import static org.junit.Assert.assertEquals;

public class LinksTest {

    @Test
    public void testMalformedURL() throws MalformedURLException {
        Links tester = new Links.HTML("Roses are red, URLs are blue");
        assertEquals(((Links.HTML) tester).errorStatus ,"Error while reading parent URL.");
    }

    @Test
    public void testOutput() {
        Links tester = new Links.HTML("https://www.yegor256.com/elegant-objects.html");
        JSONObject json = new JSONObject(tester.printToJSON());
        String url = json.get("url").toString();
        assertEquals(url, "https://www.yegor256.com/elegant-objects.html");
    }

}
