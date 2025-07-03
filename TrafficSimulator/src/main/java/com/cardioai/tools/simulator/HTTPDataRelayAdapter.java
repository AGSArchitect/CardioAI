package com.cardioai.tools.simulator;

import com.cardioai.tools.model.MessageVO;
import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

/**
 * HTTPDataRelayAdapter
 *
 * @author Ariel Gonzalez
 * @version 1.0
 */
public class HTTPDataRelayAdapter implements DataRelayAdapter {

    private final String resourceName;

    public HTTPDataRelayAdapter(String resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public void sendMessage(MessageVO message) throws Exception {
        Gson gson = new Gson();
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(resourceName))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(
                        gson.toJson(message), StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IllegalStateException();
        }
    }
}
