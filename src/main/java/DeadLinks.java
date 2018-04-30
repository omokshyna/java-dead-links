import java.io.IOException;
import java.util.List;

public interface DeadLinks {
    List<String> badLinks(String url) throws IOException;
}
