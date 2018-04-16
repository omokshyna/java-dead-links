import java.io.IOException;
import java.util.List;

public interface DeadLinks {
    List<String> badLinks(String url) throws IOException;
    List<String> allLinks(String link) throws IOException;
    int getResponseCode(String link);
}
