import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;

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
    public void testMalformedURL() throws MalformedURLException {
        Links tester = new Links.HTML("Roses are red, URLs are blue");
        assertEquals(((Links.HTML) tester).errorStatus ,"Error while reading parent URL.");
    }

    @Test
    public void testOut() {
        Links tester = new Links.HTML("https://www.yegor256.com/elegant-objects.html");
        System.out.println(tester.toString());
        String testJSON = "{\n" +
                "  \"total\": 168,\n" +
                "  \"404\": {\n" +
                "    \"urls\": [],\n" +
                "    \"size\": 0\n" +
                "  },\n" +
                "  \"dead\": 54,\n" +
                "  \"50x\": {\n" +
                "    \"urls\": [\n" +
                "      \"https://www.linkedin.com/in/yegor256\",\n" +
                "      \"https://br.linkedin.com/in/fabriciofx\",\n" +
                "      \"https://www.linkedin.com/in/igor-dmitriev-3a29b15a\",\n" +
                "      \"https://www.linkedin.com/in/vassilevsky/\",\n" +
                "      \"https://www.linkedin.com/in/johnpage76\",\n" +
                "      \"https://www.linkedin.com/in/kkaratsetski\",\n" +
                "      \"https://www.linkedin.com/in/mdbs99\",\n" +
                "      \"https://www.linkedin.com/in/pchmielowski\",\n" +
                "      \"https://www.linkedin.com/in/silasreinagel\",\n" +
                "      \"https://www.linkedin.com/in/mdbs99\",\n" +
                "      \"https://www.linkedin.com/in/johnpage76\",\n" +
                "      \"https://www.linkedin.com/in/kkaratsetski\",\n" +
                "      \"https://www.linkedin.com/in/bartoszbilicki\",\n" +
                "      \"https://www.linkedin.com/in/silasreinagel\",\n" +
                "      \"https://in.linkedin.com/in/abhishekmanocha\",\n" +
                "      \"https://www.linkedin.com/in/miroslavgenov\",\n" +
                "      \"https://ph.linkedin.com/in/carlos-miguel-miranda-0b899392\",\n" +
                "      \"https://www.linkedin.com/in/quver\",\n" +
                "      \"https://ie.linkedin.com/in/francesco-bianchi-4b49784\",\n" +
                "      \"https://ca.linkedin.com/in/shawn-fuller-11b5267\",\n" +
                "      \"https://www.linkedin.com/in/bakkiaraj\",\n" +
                "      \"https://www.linkedin.com/in/oksana-semenkova-99622bab\",\n" +
                "      \"https://www.linkedin.com/in/settyblue\",\n" +
                "      \"https://linkedin.com/in/0x13a\",\n" +
                "      \"https://www.linkedin.com/in/patrizio-colomba-03628170/\",\n" +
                "      \"https://www.linkedin.com/in/raulestradaaparicio\",\n" +
                "      \"https://www.linkedin.com/in/jonathanblakes\",\n" +
                "      \"https://www.linkedin.com/in/lrozenblyum\",\n" +
                "      \"https://www.linkedin.com/in/vassilevsky/\",\n" +
                "      \"https://www.linkedin.com/in/pchmielowski\",\n" +
                "      \"https://www.linkedin.com/in/richardfloodlfm?trk\\u003dhp-identity-name\",\n" +
                "      \"https://www.linkedin.com/in/nikem\",\n" +
                "      \"https://at.linkedin.com/in/paulroho\",\n" +
                "      \"https://www.linkedin.com/in/sebastian-schwarz-27b72083\",\n" +
                "      \"https://www.linkedin.com/in/akryvtsun\",\n" +
                "      \"https://www.linkedin.com/in/ricardojob\",\n" +
                "      \"https://www.linkedin.com/in/alayor3/\",\n" +
                "      \"https://www.linkedin.com/in/igor-dmitriev-3a29b15a\",\n" +
                "      \"https://www.linkedin.com/in/pholser\",\n" +
                "      \"https://pl.linkedin.com/in/mkordas\",\n" +
                "      \"https://www.linkedin.com/in/anton-rybochkin-93950b10a\",\n" +
                "      \"https://br.linkedin.com/in/fabriciofx\",\n" +
                "      \"https://www.linkedin.com/in/jameskirkux\",\n" +
                "      \"http://www.linkedin.com/in/andrej-istomin-51ba7530\",\n" +
                "      \"http://in.linkedin.com/in/aneeshdogra\",\n" +
                "      \"http://ie.linkedin.com/in/francesco-bianchi-4b49784\",\n" +
                "      \"http://dk.linkedin.com/in/ion-bordian-06701a88\",\n" +
                "      \"http://de.linkedin.com/in/kkamkou/en\",\n" +
                "      \"http://cz.linkedin.com/in/michal-%C5%A1vec-77065711\",\n" +
                "      \"http://www.linkedin.com/in/nicoskek\",\n" +
                "      \"http://www.linkedin.com/in/philipbuuck\",\n" +
                "      \"http://www.linkedin.com/in/simonjtsai\",\n" +
                "      \"http://nl.linkedin.com/in/thanpa\",\n" +
                "      \"http://www.linkedin.com/in/xipan\"\n" +
                "    ],\n" +
                "    \"size\": 54\n" +
                "  },\n" +
                "  \"url\": \"https://www.yegor256.com/elegant-objects.html\"\n" +
                "}\n";
        assertEquals(testJSON, outContent.toString());
    }

}
