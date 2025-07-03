package com.cardioai.tools.simulator;

import com.cardioai.tools.model.MessageVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * ESBDataRelayAdapter
 *
 * @author Ariel Gonzalez
 * @version 1.0
 */
public class ESBDataRelayAdapter implements DataRelayAdapter {

    private final String resourceName;

    public ESBDataRelayAdapter(String resourceName) {
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
