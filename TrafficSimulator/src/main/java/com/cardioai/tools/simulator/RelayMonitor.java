package com.cardioai.tools.simulator;

import java.util.List;

/**
 * RelayMonitor
 *
 * @author Ariel Gonzalez
 * @version 1.0
 */
public class RelayMonitor implements Runnable {

    private final RelayMonitorConfig config;
    private final List<Supervisable> toMonitor;

    public RelayMonitor(RelayMonitorConfig config, List<Supervisable> toMonitor) {
        this.config = config;
        this.toMonitor = toMonitor;
    }

    @Override
    public void run() {
        /**
         * TODO: Provide Implementation...
         */
    }
}
