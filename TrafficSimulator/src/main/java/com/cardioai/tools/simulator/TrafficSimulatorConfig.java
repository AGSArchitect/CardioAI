package com.cardioai.tools.simulator;

/**
 * TrafficSimulatorConfig
 *
 * @author Ariel Gonzalez
 * @version 1.0
 */
public class TrafficSimulatorConfig {

    private final String sourceDirectoryPath;
    private final String adapterName;
    private final String resourceName;
    private final String originCode;
    private final String deviceCode;
    private final long pauseBeforeInitiating;
    private final long pauseAfterMessage;
    private final long pauseAfterCycle;
    private final int threads;
    private final int maximun;

    public TrafficSimulatorConfig(String sourceDirectoryPath, String adapterName, String resourceName, String originCode, String deviceCode, long pauseBeforeInitiating, long pauseAfterMessage, long pauseAfterCycle, int threads, int maximun) {
        this.sourceDirectoryPath = sourceDirectoryPath;
        this.adapterName = adapterName;
        this.resourceName = resourceName;
        this.originCode = originCode;
        this.deviceCode = deviceCode;
        this.pauseBeforeInitiating = pauseBeforeInitiating;
        this.pauseAfterMessage = pauseAfterMessage;
        this.pauseAfterCycle = pauseAfterCycle;
        this.threads = threads;
        this.maximun = maximun;
    }

    public String getSourceDirectoryPath() {
        return sourceDirectoryPath;
    }

    public String getAdapterName() {
        return adapterName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getOriginCode() {
        return originCode;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public long getPauseBeforeInitiating() {
        return pauseBeforeInitiating;
    }

    public long getPauseAfterMessage() {
        return pauseAfterMessage;
    }

    public long getPauseAfterCycle() {
        return pauseAfterCycle;
    }

    public int getThreads() {
        return threads;
    }

    public int getMaximun() {
        return maximun;
    }
}