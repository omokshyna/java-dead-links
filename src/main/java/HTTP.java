import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

interface HTTP {

    Response response(URL url);

    interface Response {
        int code();
        String asString();
    }

    class Default implements HTTP {

        @Override
        public Response response(URL url) {
            return new Response() {
                private int statusCode;
                private String statusMsg;

                @Override
                public int code() {
                    try {
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.connect();
                        this.statusCode = connection.getResponseCode();
                        return this.statusCode;
                    } catch (IOException e) {
                        this.statusMsg = "Invalid URL";
                        return 0;
                    }
                }

                @Override
                public String asString() {
                    return statusMsg;
                }
            };
        }
    }
}
