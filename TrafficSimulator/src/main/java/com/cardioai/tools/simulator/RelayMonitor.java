package com.cardioai.tools.simulator;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RelayMonitor
 *
 * @author Ariel Gonzalez
 * @version 1.0
 */
public class RelayMonitor implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(RelayMonitor.class);

    private final RelayMonitorConfig config;
    private final List<Supervisable> toMonitor;
    private int cycles;
    private boolean shutdown;

    public RelayMonitor(RelayMonitorConfig config, List<Supervisable> toMonitor) {
        this.config = config;
        this.toMonitor = toMonitor;
        this.cycles = 0;
        this.shutdown = false;
    }

    @Override
    @SuppressWarnings({"CallToPrintStackTrace", "UseSpecificCatch"})
    public void run() {
        try {
            if (config.getPauseBeforeInitiating() > 0) {
                try {
                    synchronized (this) {
                        Utils.logErrorMessage(
                                LOGGER, LogMessage.TS201, String.valueOf(config.getPauseBeforeInitiating()));
                        wait(config.getPauseBeforeInitiating());
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            while (!shutdown) {
                displayMetrics();
                if (config.getPauseAfterCycle() > 0) {
                    try {
                        synchronized (this) {
                            wait(config.getPauseAfterCycle());
                        }
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void displayMetrics() {
        cycles++;
        for (Supervisable e : toMonitor) {
            DataRelayMetrics metrics = e.getMetrics();
            Utils.logDataRelayMetrics(
                    LOGGER, LogMessage.TS205, cycles, metrics.getOriginId(),
                    metrics.getMessagesRelayed(), metrics.getMessagesFailed());
        }
    }

    public synchronized void shutdown() {
        shutdown = true;
        displayMetrics();
    }
}
