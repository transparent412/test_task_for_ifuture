package ru.senderov.clientpart.services;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.senderov.clientpart.util.ClientPropertyStorage;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ClientRequestService {

    private final ClientPropertyStorage storage;

    @Value("${server_app.get_balance.http_path}")
    private String GET_HTTP_PATH_TO_GET_BALANCE;

    @Value("${server_app.change_balance.http_path}")
    private String GET_HTTP_PATH_TO_CHANGE_BALANCE;

    public ClientRequestService(ClientPropertyStorage storage) {
        this.storage = storage;
    }

    public void generationClientsRequests() {
        ExecutorService exc = Executors.newFixedThreadPool(storage.getThreadCount());

        while (true) {

            exc.submit(() -> {
                double readProbability = storage.getReadQuota() / (storage.getReadQuota() + storage.getWriteQuota());

                if (ThreadLocalRandom.current().nextDouble() < readProbability) {
                    try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
                        httpClient.execute(buildGetBalanceRequest(GET_HTTP_PATH_TO_GET_BALANCE, storage.getReadIdList()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
                        httpClient.execute(buildChangeBalanceRequest(GET_HTTP_PATH_TO_CHANGE_BALANCE, storage.getWriteIdList()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            });

        }
    }

    private HttpGet buildGetBalanceRequest(String baseUrl, List<Long> readIdList) {
        return new HttpGet(buildGetBalanceUrl(baseUrl, readIdList).toString());
    }

    private HttpGet buildChangeBalanceRequest(String baseUrl, List<Long> writeIdList) {
        return new HttpGet(buildChangeBalanceUrl(baseUrl, writeIdList).toString());
    }

    private StringBuilder buildGetBalanceUrl(String baseUrl, List<Long> readIdList) {
        StringBuilder url = new StringBuilder(baseUrl);

        if (!readIdList.isEmpty() && url.indexOf("?") == -1) {
            url.append("?");
            url.append("id=");
            url.append(readIdList.get(new Random().nextInt(readIdList.size())));
        }

        return url;
    }

    private StringBuilder buildChangeBalanceUrl(String baseUrl, List<Long> writeIdList) {
        StringBuilder url = new StringBuilder(baseUrl);

        if (url.indexOf("?") == -1) {
            url.append("?");
        }
        url.append("id=");

        if (!writeIdList.isEmpty()) {
            url.append(writeIdList.get(new Random().nextInt(writeIdList.size())));
            url.append("&amount=1");
        }

        return url;
    }

}
