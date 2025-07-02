package com.cardioai.tools.simulator;

/**
 * RelayMonitorConfig
 *
 * @author Ariel Gonzalez
 * @version 1.0
 */
public class RelayMonitorConfig {

    private final TrafficSimulatorConfig config;

    public RelayMonitorConfig(TrafficSimulatorConfig config) {
        this.config = config;
    }

    public long getPauseBeforeInitiating() {
        return config.getPauseBeforeInitiating();
    }

    public long getPauseAfterCycle() {
        return config.getPauseAfterCycle();
    }
}
