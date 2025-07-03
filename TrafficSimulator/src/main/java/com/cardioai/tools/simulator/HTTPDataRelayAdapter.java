package com.cardioai.tools.simulator;

import com.cardioai.tools.model.MessageVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
    public void sendMessage(MessageVO message) {
        /**
         * TODO: Provide Implementation...
         */
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(message);
    }
}
