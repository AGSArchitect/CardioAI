package com.cardioai.tools.simulator;

import com.cardioai.tools.model.MessageVO;
import com.google.gson.Gson;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.eventbridge.EventBridgeClient;
import software.amazon.awssdk.services.eventbridge.model.PutEventsRequest;
import software.amazon.awssdk.services.eventbridge.model.PutEventsRequestEntry;
import software.amazon.awssdk.services.eventbridge.model.PutEventsResponse;

/**
 * ESBDataRelayAdapter
 *
 * @author Ariel Gonzalez
 * @version 1.0
 */
public class ESBDataRelayAdapter implements DataRelayAdapter {

    private static final String EVENT_SOURCE = "cardioai";
    private static final String EVENT_TYPE = "cardioai.esb";

    private final String resourceName;
    private final EventBridgeClient client;

    public ESBDataRelayAdapter(String resourceName) {
        this.resourceName = resourceName;
        this.client = EventBridgeClient.builder()
                .region(Region.US_EAST_2)
                .build();
    }

    @Override
    public void sendMessage(MessageVO message) {
        Gson gson = new Gson();
        PutEventsRequestEntry entry = PutEventsRequestEntry.builder()
                .eventBusName(resourceName)
                .source(EVENT_SOURCE)
                .detail(gson.toJson(message))
                .detailType(EVENT_TYPE)
                .build();

        PutEventsRequest putRequest = PutEventsRequest.builder()
                .entries(entry)
                .build();

        PutEventsResponse response = client.putEvents(putRequest);
        if (response.sdkHttpResponse().statusCode() != 200) {
            throw new IllegalStateException();
        }
    }
}
