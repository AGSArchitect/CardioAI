package com.cardioai.tools.simulator;

import com.cardioai.tools.model.PayloadVO;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TrafficSimulator
 *
 * @author Ariel Gonzalez
 * @version 1.0
 */
public class TrafficSimulator {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrafficSimulator.class);

    @SuppressWarnings("CallToPrintStackTrace")
    public static void main(String[] args) {
        try {
            TrafficSimulatorConfig config = getTrafficSimulatorConfig(args);
            List<PayloadVO> payloads = Collections.synchronizedList(
                    getPayloads(config.getSourceDirectoryPath()));

            DataRelayConfig dataRelayConfig = new DataRelayConfig(config);
            RelayMonitorConfig relayMonitorConfig = new RelayMonitorConfig(config);

            List<Runnable> toExecute = new ArrayList<>();
            List<Supervisable> toMonitor = new ArrayList<>();
            for (int i = 0; i < config.getThreads(); i++) {
                DataRelay dataRelay = new DataRelay(
                        dataRelayConfig, payloads);
                toExecute.add(dataRelay);
                toMonitor.add(dataRelay);
            }

            int threadPoolSize = config.getThreads() + 1;
            ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);
            for (Runnable e : toExecute) {
                executor.submit(e);
            }

            RelayMonitor relayMonitor = new RelayMonitor(
                    relayMonitorConfig, Collections.unmodifiableList(toMonitor));
            executor.submit(relayMonitor);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static TrafficSimulatorConfig getTrafficSimulatorConfig(String[] args) throws IOException {
        String sourceDirectoryPath = null;
        String adapterName = null;
        String resourceName = null;
        String originCode = null;
        String deviceCode = null;
        long pauseBeforeInitiating = 15000;
        long pauseAfterMessage = 0;
        long pauseAfterCycle = 15000;
        int threads = 1;

        for (int e = 0; e < args.length; e++) {
            switch (args[e]) {
                case "-s":
                case "--source": {
                    sourceDirectoryPath = args[++e];
                    File check = new File(sourceDirectoryPath);
                    if (!check.exists() || !check.isDirectory()) {
                        logErrorMessage(
                                LogMessage.TS001, sourceDirectoryPath);
                        throw new IOException();
                    }
                    break;
                }
                case "-a":
                case "--adapter": {
                    adapterName = args[++e];
                    if (!adapterName.equals("HTTP") && !adapterName.equals("ESB")) {
                        logErrorMessage(
                                LogMessage.TS002, String.valueOf(adapterName));
                        throw new IllegalArgumentException();
                    }
                    break;
                }
                case "-n":
                case "--name": {
                    resourceName = args[++e];
                    if (resourceName.startsWith("-")) {
                        logErrorMessage(
                                LogMessage.TS003, String.valueOf(resourceName));
                        throw new IllegalArgumentException();
                    }
                    break;
                }
                case "-o":
                case "--origin-code": {
                    originCode = args[++e];
                    if (!originCode.equals("clinical") && !originCode.equals("consumer")) {
                        logErrorMessage(
                                LogMessage.TS004, String.valueOf(originCode));
                        throw new IllegalArgumentException();
                    }
                    break;
                }
                case "-v":
                case "--device-code": {
                    deviceCode = args[++e];
                    if (deviceCode.startsWith("-")) {
                        logErrorMessage(
                                LogMessage.TS005, String.valueOf(deviceCode));
                        throw new IllegalArgumentException();
                    }
                    break;
                }
                case "-b":
                case "--before": {
                    pauseBeforeInitiating = Long.parseLong(args[++e]);
                    if (pauseBeforeInitiating < 0) {
                        logErrorMessage(
                                LogMessage.TS006, String.valueOf(pauseBeforeInitiating));
                        throw new IllegalArgumentException();
                    }
                    break;
                }
                case "-f":
                case "--after": {
                    pauseAfterMessage = Long.parseLong(args[++e]);
                    if (pauseAfterMessage < 0) {
                        logErrorMessage(
                                LogMessage.TS007, String.valueOf(pauseAfterMessage));
                        throw new IllegalArgumentException();
                    }
                    break;
                }
                case "-c":
                case "--cycle": {
                    pauseAfterCycle = Long.parseLong(args[++e]);
                    if (pauseAfterCycle < 0) {
                        logErrorMessage(
                                LogMessage.TS008, String.valueOf(pauseAfterCycle));
                        throw new IllegalArgumentException();
                    }
                    break;
                }
                case "-h":
                case "--threads": {
                    threads = Integer.parseInt(args[++e]);
                    if ((threads < 1) && (threads > 25)) {
                        logErrorMessage(
                                LogMessage.TS009, String.valueOf(threads));
                        throw new IllegalArgumentException();
                    }
                    break;
                }
                default:
                    logErrorMessage(
                            LogMessage.TS010, args[e]);
                    throw new IllegalStateException();
            }
        }

        return new TrafficSimulatorConfig(
                sourceDirectoryPath, adapterName, resourceName, originCode, deviceCode, pauseBeforeInitiating, pauseAfterMessage, pauseAfterCycle, threads);
    }

    private static List<PayloadVO> getPayloads(String sourceDirectoryPath) throws IOException {
        List<PayloadVO> payloads = new LinkedList<>();

        Gson gson = new Gson();
        File sourceDirectory = new File(sourceDirectoryPath);
        File[] payloadFiles = sourceDirectory.listFiles();
        for (File payloadFile : payloadFiles) {
            String json = Files.readString(Paths.get(payloadFile.toURI()));
            payloads.add(
                    gson.fromJson(json, PayloadVO.class));
        }

        return payloads;
    }

    private static void logErrorMessage(LogMessage error, String value) {
        LOGGER.error(error.getMessage(),
                error.name(), value);
    }
}
