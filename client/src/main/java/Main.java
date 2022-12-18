import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        while (true) {
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet("http://localhost:8084/getBalance?id=1");
            HttpResponse httpResponse = httpClient.execute(httpGet);
        }

    }

}
