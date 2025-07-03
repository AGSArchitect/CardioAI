package com.cardioai.tools.simulator;

/**
 * DataRelayConfig
 *
 * @author Ariel Gonzalez
 * @version 1.0
 */
public class DataRelayConfig {

    private final TrafficSimulatorConfig config;

    public DataRelayConfig(TrafficSimulatorConfig config) {
        this.config = config;
    }

    public String getAdapterName() {
        return config.getAdapterName();
    }

    public String getResourceName() {
        return config.getResourceName();
    }

    public String getOriginCode() {
        return config.getOriginCode();
    }

    public String getDeviceCode() {
        return config.getDeviceCode();
    }

    public long getPauseBeforeInitiating() {
        return config.getPauseBeforeInitiating();
    }

    public long getPauseAfterMessage() {
        return config.getPauseAfterMessage();
    }
    
    public int getMaximun() {
        return config.getMaximun();
    }    
}
