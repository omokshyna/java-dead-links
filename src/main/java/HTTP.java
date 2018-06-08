import java.net.URL;

interface HTTP {

    Response response(URL url);

    interface Response {
        int code();
        String asString();
    }

}
