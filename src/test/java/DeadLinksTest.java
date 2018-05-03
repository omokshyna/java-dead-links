import com.deadlinks.DeadLinks;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadLinksTest {

    @Test
    public void testMalformedURL() throws MalformedURLException {
        DeadLinks tester = new DeadLinks("Roses are red, URLs are blue");
        assertEquals(tester.parentState ,"Malformed URL.");
    }

    @Test
    public void testOutput() {
        DeadLinks tester = new DeadLinks("https://www.yegor256.com/elegant-objects.html");
        JSONObject json = new JSONObject(tester.linksToJSON());
        String url = json.get("url").toString();
        assertEquals(url, "https://www.yegor256.com/elegant-objects.html");
    }

}
