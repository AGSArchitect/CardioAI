package com.cardioai.tools.simulator;

import com.cardioai.tools.model.MessageVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
        System.out.println(
                gson.toJson(message));
    }
}
