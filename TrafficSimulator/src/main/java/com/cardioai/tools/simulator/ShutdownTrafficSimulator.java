package com.cardioai.tools.simulator;

import java.util.concurrent.ExecutorService;

/**
 * ShutdownTrafficSimulator
 *
 * @author Ariel Gonzalez
 * @version 1.0
 */
public class ShutdownTrafficSimulator extends Thread {

    private final ExecutorService executor;
    private final RelayMonitor relayMonitor;

    public ShutdownTrafficSimulator(ExecutorService executor, RelayMonitor relayMonitor) {
        this.executor = executor;
        this.relayMonitor = relayMonitor;
    }

    @Override
    public void run() {
        executor.shutdown();
        relayMonitor.shutdown();
    }
}