package com.cardioai.tools.simulator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataRelayAdapterFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataRelayAdapterFactory.class);

    public static DataRelayAdapter getAdapter(String adapterName, String resourceName) {
        DataRelayAdapter adapter = null;

        switch (adapterName) {
            case "HTTP":
                adapter = new HTTPDataRelayAdapter(resourceName);
                break;
            case "ESB":
                adapter = new ESBDataRelayAdapter(resourceName);
                break;
            default:
                LogMessage error = LogMessage.TS002;
                LOGGER.error(error.getMessage(),
                        error.name(), adapterName);
                throw new IllegalStateException();
        }

        return adapter;
    }
}
